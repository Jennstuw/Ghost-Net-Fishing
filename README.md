

# 🌊 Ghost Net Fishing – Web Application

A Java Spring Boot application for reporting, tracking, and recovering abandoned fishing nets ("ghost nets") in the ocean.


## 📖 Project Overview

Ghost nets are abandoned fishing nets that drift in the ocean and harm marine ecosystems.
This application allows:

* People to **report ghost nets** (anonymously or with contact details)
* Salvaging personnel to **view**, **register**, and **recover** nets
* Coordinated tracking of recovery progress
* Viewing all nets and their statuses

This project was developed as part of the *Software Engineering / Web Engineering* module using **Spring Boot**, **JPA**, **MySQL**, and **Thymeleaf**.

---

## 🚀 Features

### ✅ MUST Requirements

* Report a ghost net (optionally anonymous)
* View all nets awaiting recovery
* Salvager can register for recovering a net
* Salvager can mark a net as recovered

### 🎯 COULD Requirements

* View active recovery assignments
* Mark nets as lost (non-anonymous reporting only)

---

## 🛠 Tech Stack

### **Backend**

* Java 17
* Spring Boot 3
* Spring MVC
* Spring Data JPA (Hibernate)
* MySQL 8

### **Frontend**

* Thymeleaf templating engine
* HTML, CSS

### **Build Tools**

* Maven
* Lombok (optional)

---



### Layers:

* **Controller Layer**: Handles HTTP requests
* **Service Layer**: Business logic
* **Repository Layer**: Data persistence with JPA
* **Entity Layer**: Domain model objects

---

## 🗄 Database Schema (ER Diagram)

```mermaid
erDiagram
    Person ||--o{ GhostNet : "1 to many"
    
    Person {
        int id PK
        string name
        string phoneNumber
        boolean isAnonymous
    }

    GhostNet {
        int id PK
        double latitude
        double longitude
        double estimatedSize
        string status
        datetime reportedDate
        datetime recoveryDate
        int reportedBy_id FK
        int salvager_id FK
    }
```

---

## 📘 UML Class Diagram

```mermaid
classDiagram

    class Person {
        - Long id
        - String name
        - String phoneNumber
        - boolean isAnonymous
        + getters()
        + setters()
    }

    class GhostNet {
        - Long id
        - Double latitude
        - Double longitude
        - Double estimatedSize
        - NetStatus status
        - reportedDate
        - recoveryDate
        + markRecovered()
        + getLocationString()
    }

    class NetStatus {
        <<enumeration>>
        REPORTED
        RECOVERY_PENDING
        RECOVERED
        LOST
    }

    class GhostNetService {
        + report()
        + register()
        + markRecovered()
        + markLost()
    }

    Person "1" --> "*" GhostNet : reports
```

---

## 📈 Sequence Diagram

```mermaid
sequenceDiagram
    participant Reporter
    participant UI
    participant Controller
    participant PersonService
    participant GhostNetService
    participant Repository

    Reporter ->> UI: Open report form
    UI ->> Controller: POST /nets/report
    Controller ->> PersonService: createPerson()
    Controller ->> GhostNetService: reportGhostNet()
    GhostNetService ->> Repository: save()

    Salvager ->> UI: View nets
    UI ->> Controller: GET /nets/view
    Controller ->> GhostNetService: getPendingNets()

    Salvager ->> UI: Register
    UI ->> Controller: POST /nets/register/{id}
    Controller ->> PersonService: createPerson()
    Controller ->> GhostNetService: registerForRecovery()
    GhostNetService ->> Repository: save()

    Salvager ->> UI: Mark recovered
    UI ->> Controller: POST /nets/mark-recovered/{id}
    Controller ->> GhostNetService: markRecovered()
```

---

## 🧩 Project Structure

```
ghost-net-fishing/
│
├── src/main/java/com/ghostnet/
│   ├── controller/
│   ├── entity/
│   ├── repository/
│   ├── service/
│   └── GhostNetApplication.java
│
├── src/main/resources/
│   ├── templates/ (Thymeleaf HTML)
│   └── application.properties
│
├── pom.xml
└── README.md
```

---

## 🔧 Installation & Setup

### 1️⃣ Install Requirements

* Java 17
* Maven
* MySQL

### 2️⃣ Create MySQL Database

```sql
CREATE DATABASE ghostnet_db;
CREATE USER 'ghostnet_user'@'localhost' IDENTIFIED BY 'ghostnet_pass';
GRANT ALL PRIVILEGES ON ghostnet_db.* TO 'ghostnet_user'@'localhost';
FLUSH PRIVILEGES;
```

---

## ▶️ How to Run the Project

### **Step 1: Build**

```
mvn clean install
```

### **Step 2: Run**

```
mvn spring-boot:run
```

### **Step 3: Open Browser**

```
http://localhost:8080
```

---

## 🖼 Screenshots

### Screen 1
![Screen 1](screenshots/s1.png)
### Screen 2
![Screen 2](screenshots/s2.png)
### Screen 3
![Screen 3](screenshots/s3.png)
### Screen 4
![Screen 4](screenshots/s4.png)
### Screen 5
![Screen 5](screenshots/s5.png)

---

## 🚀 Future Improvements

* Interactive world map (Leaflet / OpenLayers)
* User authentication system
* Email/SMS notifications
* REST API version
* Admin dashboard for analytics

---

## 📄 License

MIT License — free to use and modify.


