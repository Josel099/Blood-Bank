# Blood Banking System - Backend

This is the backend for the **Blood Banking System**, built using **Spring Boot** with MySQL as the database.

## 📌 Features
- Blood donation & request management
- API documentation with Swagger

---
## 🚀 Getting Started

### 1️⃣ Prerequisites
Ensure you have the following installed:
- **Java 17+**
- **Maven**
- **MySQL**

### 2️⃣ Clone the Repository
```sh
git clone https://github.com/Josel099/Blood-Bank.git
cd your-repo
```

### 3️⃣ Configure the Database
Execute the SQL file located at:
```
/src/main/resources/initial_setup.sql
```


### 4️⃣ Set Up Environment Variables
Run the application with the following **VM arguments**:
```
-Dblb.datasource.username=user-name-of-your-db
-Dblb.datasource.password=password-of-your-db
```

Set these values in your **application.properties**:
```properties
spring.application.name=Blood_Bank
server.port=8080
#################### MySQL DB ###################
spring.datasource.url=jdbc:mysql://localhost:3306/blb?autoReconnect=true
spring.datasource.username =${blb.datasource.username}
spring.datasource.password =${blb.datasource.password}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

### 5️⃣ Build and Run the Application
```sh
mvn clean install
mvn spring-boot:run
```

---
## 📜 API Documentation
The API is documented using **Swagger**.

- Access the Swagger UI at:
  **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**
- View the OpenAPI spec at:
  **[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)**

---
## 🛠 Technologies Used
- **Spring Boot** (REST API)
- **Spring Security** (Authentication & Authorization)
- **MySQL** (Database)
- **JPA** (ORM)
- **Swagger** (API Documentation)
---

## 🔗 Frontend Repository

- The frontend for this application is built using React. You can find the repository here:
  **[https://github.com/Josel099/BloodBankUI.git](https://github.com/Josel099/BloodBankUI.git)**

## 📩 Contact
For any issues or contributions, feel free to reach out or open a pull request.

---
**📝 License:** MIT

