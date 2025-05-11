
# 🏗️ MRAMMA – The Future of Construction Management

MRAMMA is an all-in-one intelligent construction management platform built to transform how modern construction firms operate. It combines advanced project planning, financial tracking, online procurement, HR workflows, and claim management into one seamless ecosystem.

> 🚧 "Smart. Scalable. Secure. Built for the construction industry of tomorrow."

---

## 📌 Table of Contents

- [🌍 Project Context](#-project-context)
- [❗ Problem Statement](#-problem-statement)
- [🚀 Proposed Solution](#-proposed-solution)
- [🛠️ Tech Stack](#-tech-stack)
- [🔐 Keycloak Authentication](#-keycloak-authentication)
- [🧩 System Architecture](#-system-architecture)
- [✅ Key Functional Modules](#-key-functional-modules)
- [🖥️ Live Demo](#-live-demo)
- [👨‍💻 Team](#-team)
- [📄 License](#-license)

---

## 🌍 Project Context

Construction is a high-stakes industry where poor coordination and siloed systems can lead to:

- Delays and budget overruns
- Communication breakdowns
- Untraceable documents
- Risky manual tracking

Digital transformation is no longer optional—it’s necessary. MRAMMA brings this vision to life with a smart, affordable, and fully integrated web platform.

---

## ❗ Problem Statement

Despite the availability of tools like **Procore**, **PlanGrid**, and **Buildertrend**, most are:

- Overpriced for small/mid-size enterprises
- Complex to adopt
- Weak in budget tracking, collaboration, or HR modules

🧨 **Pain Points Identified**:

- Poor financial visibility  
- Communication gaps & delays  
- Limited integration across tools  
- Lack of contextual task alerts and smart notifications

---

## 🚀 Proposed Solution

MRAMMA delivers an all-in-one cloud-ready construction platform with:

- 📦 **Modular Architecture** – each feature is a plug-and-play microservice
- 🔐 **Enterprise-grade security** using **Keycloak**
- 📊 **Real-time dashboards** for finances, tasks, and KPIs
- 🛍️ **Built-in online marketplace** for tools and rental equipment
- 🧠 **Smart budgeting, tracking, and forecasting**

---

## 🛠️ Tech Stack

| Layer                 | Technology Used                        |
|----------------------|----------------------------------------|
| 🌐 Frontend           | Angular 16                             |
| 🔙 Backend            | Spring Boot (REST API)                 |
| 🔐 Authentication     | Keycloak (self-hosted)                 |
| 🗃️ Database           | MySQL                                  |
| 🐳 Containerization   | Docker (coming soon)                   |
| 📈 Monitoring         | NGX Charts / Chart.js                  |

---

## 🔐 Keycloak Authentication

We use **Keycloak** for secure authentication and authorization. It supports:

- User registration and login
- Role-based access control
- Token-based session management
- Integration with Angular and Spring Boot

### 🔧 Realms & Clients

| Realm Name           | Purpose                         |
|----------------------|----------------------------------|
| `constructionRealm`  | Main authentication realm for MRAMMA |
| `admin-console`      | Default admin realm              |

| Client ID            | Use Case                         |
|----------------------|----------------------------------|
| `mramma-frontend`    | Angular frontend (public client) |
| `mramma-backend`     | Spring Boot backend (confidential client) |

### ▶️ How to Run Keycloak Locally

1. **Download and extract Keycloak (v23.0.6)**  
   [https://www.keycloak.org/downloads](https://www.mediafire.com/folder/3ivnej0a1kwfq/keycloak-23.04666666)

2. **Start Keycloak in development mode**  
   ```bash
   cd keycloak-23.0.6/bin
   ./kc.bat start-dev
   ```

3. **Login to Admin Console**  
   http://localhost:8080  
   (Default: `admin / admin` unless changed)

4. **Import Realm & Clients (Optional)**  
   Use the exported `constructionRealm-realm.json` (available in `/realms/`)  
   > Settings → Import → Select JSON

5. **Frontend & Backend Configuration**
   - Frontend uses `keycloak-js` to connect to the `mramma-frontend` public client.
   - Backend uses `spring-boot-starter-oauth2-resource-server` with the `mramma-backend` confidential client.

---

## 🧩 System Architecture

### 🔌 Logical Architecture

- **Frontend (Angular)** handles UI/UX, routing, form validation, and user interactions.
- **Backend (Spring Boot)** serves RESTful endpoints for all modules: Auth, Claims, Projects, Tasks, HR, Finance, and Shop.
- **Keycloak** manages identity, access control, and session security.
- **MySQL** is the shared relational DB across all services (scalable per module).

### 🌐 Physical Architecture

- Microservices are containerized and exposed through a common gateway (planned).
- Modular services can scale independently.

---

## ✅ Key Functional Modules

### 🔐 User & Role Management
- Signup/login using **Keycloak**
- Profile management with editable info and avatar
- Role-based access (Admin, Manager, Engineer, Employee)

### 🧾 Claim & Rating System
- Submit service claims (defects, delays)
- Rate contractors and service quality
- Admin dashboard for claim moderation

### 🏗️ Project & Study Management
- Create and manage construction projects
- Attach plans, reports, blueprints
- Evaluate feasibility, risks, and deadlines

### 📋 Task Planning & Alerts
- Assign tasks, set deadlines
- Automatic reminders and missed-deadline notifications
- Task progress tracker (real-time)

### 🛒 Marketplace Module
- Order tools, materials, and rentals online
- Handle inventory levels and vendor orders
- Track stock availability in real-time

### 💸 Financial Tracker
- Real-time expense monitoring
- Estimated vs actual cost comparisons
- Role-secured access to sensitive budgets

### 👥 HR Module
- Employee onboarding, profile management
- Leave requests and approvals
- Performance tracking and reviews

---

## 🖥️ Live Demo

> 🧪 Localhost for development:
```
Frontend: http://localhost:4200  
Backend APIs: http://localhost:8088/api  
Keycloak Console: http://localhost:8080  
```

---

## 👨‍💻 Team

Developed by a passionate cross-functional team:

- **Moncef Halleb** ( User & Role Management + Claim & Rating System)  
- Linda Mlika ( Marketplace Module)  
- Yasmine Mechmech ( Financial Tracker)  
- Moataz Benchikh ( Task Planning & Alerts)  
- Balkis Barhoumi (HR Module)  
- Ilyess Saidi ( Project & Study Management)

---

## 📄 License

This project is licensed under the **MIT License** – feel free to use, modify, and share with attribution.

---

## 🤝 Contributions

Want to contribute or report a bug?  
Open a pull request or contact the team!

---
