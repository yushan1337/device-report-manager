package devicereport;

import devicereport.model.Device;
import devicereport.model.DiagnosticReport;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Device device = new Device(
                null,
                "R5CTEST1234",
                "Android Test Device",
                "15"
        );

        List<DiagnosticReport> reports = new ArrayList<>();

        reports.add(new DiagnosticReport(
                null,
                null,
                76,
                31.2,
                "2026-07-26 22:30:00"
        ));

        reports.add(new DiagnosticReport(
                null,
                null,
                64,
                30.8,
                "2026-07-26 22:35:00"
        ));

        System.out.println("Device:");
        System.out.println("  serial: " + device.getSerial());
        System.out.println("  model: " + device.getModel());
        System.out.println("  Android: " + device.getAndroidVersion());

        System.out.println();

        System.out.println("Reports:");

        for (DiagnosticReport report : reports) {
            System.out.println("  battery level: " + report.getBatteryLevel());
            System.out.println("  temperature: " + report.getBatteryTemperature());
            System.out.println("  generated at: " + report.getGeneratedAt());
            System.out.println();
        }
    }
}