package devicereport;

import devicereport.database.DatabaseConnection;
import devicereport.importer.ReportJsonImporter;
import devicereport.importer.ReportJsonImporter.ImportedReport;
import devicereport.model.Device;
import devicereport.model.DiagnosticReport;
import devicereport.repository.DeviceRepository;
import devicereport.repository.DiagnosticReportRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: mvn exec:java -Dexec.args=\"/path/to/report.json\"");
            return;
        }

        Path reportPath = Path.of(args[0]);

        ReportJsonImporter importer = new ReportJsonImporter();
        DeviceRepository deviceRepository = new DeviceRepository();
        DiagnosticReportRepository reportRepository = new DiagnosticReportRepository();

        try (Connection connection = DatabaseConnection.getConnection()) {
            ImportedReport importedReport = importer.read(reportPath);

            Device device = importedReport.device();
            DiagnosticReport report = importedReport.report();

            Long deviceId = deviceRepository.findIdBySerial(connection, device.getSerial());

            if (deviceId == null) {
                deviceId = deviceRepository.insert(connection, device);
                System.out.println("Device inserted with id: " + deviceId);
            } else {
                System.out.println("Device already exists with id: " + deviceId);
            }

            DiagnosticReport reportWithDeviceId = new DiagnosticReport(
                    report.getId(),
                    deviceId,
                    report.getBatteryLevel(),
                    report.getBatteryTemperature(),
                    report.getGeneratedAt()
            );

            reportRepository.insert(connection, reportWithDeviceId);
            System.out.println("Report inserted for device id: " + deviceId);

            System.out.println();

            System.out.println("Imported real report:");
            System.out.println("  serial: " + device.getSerial());
            System.out.println("  model: " + device.getModel());
            System.out.println("  Android: " + device.getAndroidVersion());
            System.out.println("  battery level: " + report.getBatteryLevel());
            System.out.println("  temperature: " + report.getBatteryTemperature());
            System.out.println("  generated at: " + report.getGeneratedAt());
        } catch (SQLException e) {
            System.err.println("Database operation failed.");
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("Reading report failed.");
            System.err.println(e.getMessage());
        }
    }
}
