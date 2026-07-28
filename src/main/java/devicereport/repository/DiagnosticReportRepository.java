package devicereport.repository;

import devicereport.model.DiagnosticReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticReportRepository {
    public void insert(Connection connection, DiagnosticReport report) throws SQLException {
        String sql =
                "INSERT INTO diagnostic_reports " +
                        "(device_id, battery_level, battery_temperature, generated_at) " +
                        "VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, report.getDeviceId());
            statement.setInt(2, report.getBatteryLevel());
            statement.setDouble(3, report.getBatteryTemperature());
            statement.setString(4, report.getGeneratedAt());

            statement.executeUpdate();
        }
    }

    public List<DiagnosticReport> findAll(Connection connection) throws SQLException {
        String sql = "SELECT id, device_id, battery_level, battery_temperature, generated_at FROM diagnostic_reports";

        List<DiagnosticReport> reports = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                DiagnosticReport report = new DiagnosticReport(
                        resultSet.getLong("id"),
                        resultSet.getLong("device_id"),
                        resultSet.getInt("battery_level"),
                        resultSet.getDouble("battery_temperature"),
                        resultSet.getString("generated_at")
                );

                reports.add(report);
            }
        }

        return reports;
    }
}
