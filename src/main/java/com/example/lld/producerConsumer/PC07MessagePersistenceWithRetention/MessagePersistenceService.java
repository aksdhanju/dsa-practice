package com.example.lld.producerConsumer.PC07MessagePersistenceWithRetention;

import java.sql.*;

public class MessagePersistenceService {
    private final Connection connection;

    public MessagePersistenceService() throws SQLException {
        connection = getConnection();
        createTableIfNotExists();
    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:./messagebrokerdb");
    }

    private void createTableIfNotExists() throws SQLException {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS messages (id IDENTITY PRIMARY KEY,topic VARCHAR(255),"value" VARCHAR(255),retry_count INT,status VARCHAR(50),timestamp BIGINT);""";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    public void saveMessage(String topic, String content, int retryCount, String status) {
        try (PreparedStatement ps = connection.prepareStatement("""
        INSERT INTO messages (topic, "value", retry_count, status, timestamp) VALUES (?, ?, ?, ?, ?)""")) {
            ps.setString(1, topic);
            ps.setString(2, content);
            ps.setInt(3, retryCount);
            ps.setString(4, status);
            ps.setLong(5, System.currentTimeMillis());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExpiredMessages(long expirationThreshold) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM messages WHERE timestamp < ?")) {
            ps.setLong(1, expirationThreshold);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() throws SQLException {
        connection.close();
    }
}
