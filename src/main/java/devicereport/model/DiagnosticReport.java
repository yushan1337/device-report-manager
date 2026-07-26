package devicereport.model;

public class DiagnosticReport {
    private Long id;
    private Long deviceId;
    private Integer batteryLevel;
    private Double batteryTemperature;
    private String generatedAt;

    public DiagnosticReport(Long id, Long deviceId, Integer batteryLevel, Double batteryTemperature, String generatedAt) {
        this.id = id;
        this.deviceId = deviceId;
        this.batteryLevel = batteryLevel;
        this.batteryTemperature = batteryTemperature;
        this.generatedAt = generatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public Double getBatteryTemperature() {
        return batteryTemperature;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }
}