# 💬 Chat Application – COM5015M

This is a **Java-based Chat Application** developed for the **COM5015M Advanced Programming** module at **York St John University**. It demonstrates **Object-Oriented Programming (OOP)** principles, multi-threading, MySQL integration, and includes a live **web dashboard** for real-time chat statistics.

> 👨‍💻 Group Project by: **Karan Chawla** and **Niraj Punware**

---

## ✨ Features

- ✅ **Multithreaded server** that supports multiple clients.
- 🔐 **Password-protected login** (`1234`) before accessing the chat.
- 🖥️ **Java Swing GUI** with username prompt and chat window.
- 🚫 **Offensive word filtering** (basic moderation).
- 🟢 Displays **online/offline status** of clients.
- 💾 **MySQL database** integration to store users and messages.
- 🌐 **Web dashboard** to show:
  - Active user count  
  - Recent chat messages  
  - Auto-refresh every 5 seconds  
  - Dark mode interface
- 🌍 **Unicode support** for improved user experience.

---

## 🛠️ Setup Instructions

### Prerequisites
- Java SDK 8 or higher  
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) (or any Java IDE)  
- MySQL (via [XAMPP](https://www.apachefriends.org/))  
- MySQL JDBC Driver (place in `/lib` folder)

### Database Setup

1. Start **XAMPP** and launch **MySQL** and **Apache**.
2. Visit `http://localhost/phpmyadmin`  
3. Create the database and tables by running:

```sql
CREATE DATABASE karanchawla_chatapp;
USE karanchawla_chatapp;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE,
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    message TEXT,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);
