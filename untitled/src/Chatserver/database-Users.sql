CREATE DATABASE karanchawla_chatapp;

USE karanchawla_chatapp;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE,
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP
);