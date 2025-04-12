# 🏗️ Construction Management System (Microservices Architecture)

This project is a microservices-based Construction Management System designed to efficiently handle construction operations such as project tracking, task planning, user management, reclamations, and more.

---

## 📂 Project Structure

```
project-construction-ms/
├── Back/                  # Backend microservices
│   ├── user-service/      # User management service
│   ├── reclamation-service/   # Project planning service
│   ├── reponse-service                # Other domain-specific services
├── config-server/         # Centralized config for all services
├── discovery/             # Eureka service registry
├── gateway/               # API Gateway for routing and security
```

---

## ⚙️ Technologies Used

- **Java 17**, **Spring Boot**, **Spring Cloud**
- **Eureka** (Service Discovery)
- **Spring Cloud Gateway** (Routing)
- **Spring Security + Keycloak** (Authentication & Authorization)
- **MYSQL** (Database)
- **Docker & Docker Compose** (Containerized deployment)
- **Lombok**, **MapStruct**, **JPA/Hibernate**

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- Docker & Docker Compose (recommended)
- MYSQL

### Running the Project

1. **Clone the repository**:

   ```bash
   git clone https://github.com/lily26969/project-construction-ms.git
   cd project-construction-ms
   ```

2. **Build all services**:

   ```bash
   cd Back
   mvn clean install
   ```

3. **Run with Docker Compose** (if available):

   ```bash
   docker-compose up --build
   ```

4. **Access key services**:

   - API Gateway: `http://localhost:8088`
   - Eureka Dashboard: `http://localhost:8761`
   - Keycloak: `http://localhost:8080`

---

## ✅ Features

- 👷 User registration, roles, and profile management
- 📋 Project & task planning modules
- 📁 Document upload & file storage
- 📊 Statistics and monitoring
- 📬 Reclamations with AI auto-response (Gemini)
- 🔐 Secure login via Keycloak (OAuth2)

---

## 📬 Reclamation Auto-Reply (Gemini AI)

Reclamation uploads are supported with:
- PDF file extraction using Apache PDFBox
- Gemini API integration to auto-generate intelligent replies based on the document and message

---

## 📦 Contribution Guide

1. Fork the repo
2. Create a new branch (`git checkout -b feature-name`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to your branch (`git push origin feature-name`)
5. Create a pull request 🚀

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 👩‍💻 Maintained by

**Moncef Halleb** – [GitHub Profile](https://github.com/MoncefHalleb)

---

> For any issue or help, feel free to open an issue or contact the maintainer.
