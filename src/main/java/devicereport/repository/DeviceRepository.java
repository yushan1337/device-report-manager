package devicereport.repository;

import devicereport.model.Device;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DeviceRepository {
    public Long insert(Connection connection, Device device) throws SQLException {
        String sql =
                "INSERT INTO devices (serial, model, android_version) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, device.getSerial());
            statement.setString(2, device.getModel());
            statement.setString(3, device.getAndroidVersion());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        throw new SQLException("Creating device failed, no ID obtained.");
    }

    public List<Device> findAll(Connection connection) throws SQLException {
        String sql = "SELECT id, serial, model, android_version FROM devices";

        List<Device> devices = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Device device = new Device(
                        resultSet.getLong("id"),
                        resultSet.getString("serial"),
                        resultSet.getString("model"),
                        resultSet.getString("android_version")
                );

                devices.add(device);
            }
        }

        return devices;
    }

    public Long findIdBySerial(Connection connection, String serial) throws SQLException {
        String sql = "SELECT id FROM devices WHERE serial = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, serial);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("id");
                }
            }
        }

        return null;
    }
}
