package devicereport.importer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import devicereport.model.Device;
import devicereport.model.DiagnosticReport;

import java.io.IOException;
import java.nio.file.Path;

public class ReportJsonImporter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ImportedReport read(Path path) throws IOException {
        JsonNode root = objectMapper.readTree(path.toFile());

        String serial = root.path("device_serial").asText();
        String model = root.path("device").path("model").asText();
        String androidVersion = root.path("device").path("android_version").asText();

        int batteryLevel = root.path("battery").path("level_percent").asInt();
        double batteryTemperature = root.path("battery").path("temperature_c").asDouble();

        String generatedAt = convertGeneratedAt(root.path("generated_at").asText());

        Device device = new Device(
                null,
                serial,
                model,
                androidVersion
        );

        DiagnosticReport report = new DiagnosticReport(
                null,
                null,
                batteryLevel,
                batteryTemperature,
                generatedAt
        );

        return new ImportedReport(device, report);
    }

    private String convertGeneratedAt(String value) {
        if (value.length() == 17 && value.charAt(10) == '_') {
            return value.substring(0, 10)
                    + " "
                    + value.substring(11, 13)
                    + ":"
                    + value.substring(13, 15)
                    + ":"
                    + value.substring(15, 17);
        }

        return value;
    }

    public record ImportedReport(Device device, DiagnosticReport report) {
    }
}
