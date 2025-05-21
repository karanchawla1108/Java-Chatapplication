use karanchawla_chatapp;

CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    message TEXT,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);
