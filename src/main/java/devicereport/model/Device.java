package devicereport.model;

public class Device {
    private Long id;
    private String serial;
    private String model;
    private String androidVersion;

    public Device(Long id, String serial, String model, String androidVersion) {
        this.id = id;
        this.serial = serial;
        this.model = model;
        this.androidVersion = androidVersion;
    }

    public Long getId() {
        return id;
    }

    public String getSerial() {
        return serial;
    }

    public String getModel() {
        return model;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }
}