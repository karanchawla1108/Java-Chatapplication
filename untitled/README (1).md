

# Java Chat Application – COM5015M

This project is a java-based chat Application for the COM5015M Advanced Programming module at York St John University. It features a multi-client server, a Swing GUI, MYSQL databse integration, and web dashboard for chat statistics.The application demostrate the OOP (Object Oriented Programme) principle and address basic chat communication and logging features.




## Features

- Multithreading server supporting multiple Clients.
- Password protected before starting client need to add password "1234".
- Java Swing GUI Client with username entry and chat window.
- Offensive Word Filtering.
- Shows the client connection online and offline.
- MySQL storing user and user messages.
- Web-based dashboard displaying user count and recent messages,    automatically the 5 second refresh page dashboard with dark mode styling.
- Used Unicode to make code little more user friendly.
 
## Setup Instruction
- Used the IntelliJ Idea.
- Java SDK 8 or higher.
- JDBC driver for the MYSQL Connector, stored in lib.




## Database Configuration.

I used the XAMPP Apache HTTP Server for Database connection.
Run the follwing command to create Database.

```bash
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
 
```


## How To Run

- Compile the code.
- First start the server.java by right click server and press "Run Server".
- Second Run the Client.java by right click Client and press "Run Client".
- Password for Client terminal "1234".
- For multiple Client need to change setting firstly go to >  Run / Debug Configuartion > Edit Configuration > Client. 
- Press Modify Options Add > Allow Multiple Instances, apply and then OK.
- Now right click on the client to start the other connection, "Run Client" it open the other terminal, now it can communicate with each other. Also its can use many times.
- Open the Web Dashboard => http://localhost:8000. This page automatically refresh every 5 seconds, so the chat updated in very 5 seconds.


## AI and Third-Party Content Declaration

This project was written by myself with assistance from OpenAI’s ChatGPT for the following tasks:
- Commenting and documentation guidance.
- Structure suggestion and improvement.
- I created the Client and server basic code application and use OpenAI for the suggestion and extra functionality with full understanding of the code.

All logic and code execution were manually implemented, debugged, and tested by myself. Testing imformation i am going to provide in the report. No third-party librabies outside of standard java and JDBC were used.


## Authors

- Name: Karan chawla
- Module: COM5015M – Advanced Programming
- University: York St John University

