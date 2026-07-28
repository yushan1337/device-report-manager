package devicereport;

import devicereport.database.DatabaseConnection;
import devicereport.model.Device;
import devicereport.repository.DeviceRepository;
import devicereport.model.DiagnosticReport;
import devicereport.repository.DiagnosticReportRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Device device = new Device(
                null,
                "JDBC-TEST-004",
                "JDBC Test Device",
                "15"
        );

        DeviceRepository deviceRepository = new DeviceRepository();
        DiagnosticReportRepository reportRepository = new DiagnosticReportRepository();

        try (Connection connection = DatabaseConnection.getConnection()) {
            Long deviceId = deviceRepository.insert(connection, device);

            DiagnosticReport report = new DiagnosticReport(
                    null,
                    deviceId,
                    88,
                    32.5,
                    "2026-07-28 14:35:00"
            );

            reportRepository.insert(connection, report);

            System.out.println("Device inserted with id: " + deviceId);
            System.out.println("Report inserted for device id: " + deviceId);
            System.out.println();

            System.out.println("Devices in database:");
            for (Device savedDevice : deviceRepository.findAll(connection)) {
                System.out.println("  " + savedDevice.getId()
                        + " | " + savedDevice.getSerial()
                        + " | " + savedDevice.getModel()
                        + " | Android " + savedDevice.getAndroidVersion());
            }

            System.out.println();

            System.out.println("Reports in database:");
            for (DiagnosticReport savedReport : reportRepository.findAll(connection)) {
                System.out.println("  " + savedReport.getId()
                        + " | device_id=" + savedReport.getDeviceId()
                        + " | battery=" + savedReport.getBatteryLevel()
                        + " | temp=" + savedReport.getBatteryTemperature()
                        + " | generated_at=" + savedReport.getGeneratedAt());
            }
        } catch (SQLException e) {
            System.err.println("Database operation failed.");
            System.err.println(e.getMessage());
        }
    }
}
