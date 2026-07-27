CREATE DATABASE IF NOT EXISTS device_report_manager
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE device_report_manager;

CREATE TABLE IF NOT EXISTS devices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    serial VARCHAR(128) NOT NULL UNIQUE,
    model VARCHAR(128),
    android_version VARCHAR(32),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS diagnostic_reports (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    device_id BIGINT NOT NULL,
    battery_level INT,
    battery_temperature DECIMAL(5, 2),
    generated_at DATETIME,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT fk_report_device
    FOREIGN KEY (device_id)
    REFERENCES devices(id)
);