
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
- [🤝 Contact & Connect](#-contact--connect)

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

MRAMMA delivers an all-in-one construction platform with:

- 📦 **Modular Architecture** – each feature is a plug-and-play microservice
- 🔐 **Enterprise-grade security** using **Keycloak**
- 📊 **Real-time dashboards** for finances, tasks
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

---

## 🔐 Keycloak Authentication

We use **Keycloak** for secure authentication and authorization. It supports:

- User registration and login
- Role-based access control
- Token-based session management
- Integration with Angular and Spring Boot

### 🔧 Realms & Clients

| Realm Name           | Purpose                              |
|----------------------|---------------------------------------|
| `constructionRealm`  | Main authentication realm for MRAMMA |
| `admin-console`      | Default admin realm                  |

| Client ID     | Use Case                              |
|---------------|----------------------------------------|
| `frontapp`    | Angular frontend (public client)       |
| `backapp`     | Spring Boot backend (confidential client) |

### ▶️ How to Run Keycloak Locally

1. **Download and extract Keycloak (v23.0.6)**  
   [Download from Mediafire](https://www.mediafire.com/folder/3ivnej0a1kwfq/keycloak-23.04666666)

2. **Start Keycloak in development mode**  
   ```bash
   cd keycloak-23.0.6/bin
   ./kc.sh start-dev
   ```
   Or for Windows:
   ```cmd
   cd keycloak-23.0.6/bin
   kc.bat start-dev
   ```

3. **Login to Admin Console**  
   Visit: [http://localhost:8080](http://localhost:8080)  
   Default credentials: `admin / admin`

4. **Import Realm & Clients (Optional)**  
   Use the exported `constructionRealm-realm.json` via  
   **Admin Console → Realm Settings → Import**

5. **Frontend & Backend Configuration**
   - Angular frontend uses `keycloak-js` with `frontapp` client.
   - Spring Boot backend uses `backapp` with Keycloak adapter.

---

## 🧩 System Architecture

### 🔌 Logical Architecture

- **Frontend**: Angular SPA
- **Backend**: Spring Boot RESTful APIs
- **Security**: Keycloak for identity management
- **Database**: MySQL

### 🌐 Physical Architecture

- Modular microservices structure
- Future-ready for containerized deployment and horizontal scaling

---

## ✅ Key Functional Modules

- **User & Role Management**
- **Claim & Rating System**
- **Project & Study Management**
- **Task Planning & Real-Time Alerts**
- **Online Marketplace**
- **Financial Tracking**
- **HR & Leave Management**

---

## 🖥️ Live Demo

```
Frontend: http://localhost:4200  
Backend API: http://localhost:8088/api  
Keycloak: http://localhost:8080  
```

---

## 👨‍💻 Team

| Name              | Module                          |
|-------------------|----------------------------------|
| **Moncef Halleb** | User & Role Management, Claims   |
| Linda Mlika       | Marketplace                     |
| Yasmine Mechmech  | Financial Tracker               |
| Moataz Benchikh   | Task Planning                   |
| Balkis Barhoumi   | HR                              |
| Ilyess Saidi      | Project & Study Management      |

---

## 📄 License

Licensed under the **MIT License**.  
Use, modify, or redistribute with attribution.

---

## 🤝 Contact & Connect

We’re always open to feedback, collaborations, and new ideas!  
Feel free to reach out or connect with the MRAMMA team:

| Name              | Role/Module                          | Contact                                    | LinkedIn / GitHub           |
| ----------------- | ------------------------------------ | ------------------------------------------ | --------------------------- |
| **Moncef Halleb** | User & Role Management, Claim System | [Email](mailto:moncef.halleb@esprit.tn)    | [LinkedIn](https://www.linkedin.com/in/moncef-halleb-a01a75237) / [GitHub](https://github.com/MoncefHalleb) |
| Linda Mlika       | Marketplace Module                   | [Email](mailto:linda.mlika@esprit.tn)      | [LinkedIn](#) / [GitHub](#) |
| Yasmine Mechmech  | Financial Tracker                    | [Email](mailto:yasmine.mechmech@esprit.tn) | [LinkedIn](#) / [GitHub](#) |
| Moataz Benchikh   | Task Planning & Alerts               | [Email](mailto:moataz.benchikh@esprit.tn)  | [LinkedIn](#) / [GitHub](#) |
| Balkis Barhoumi   | HR Module                            | [Email](mailto:balkis.barhoumi@esprit.tn)  | [LinkedIn](#) / [GitHub](#) |
| Ilyess Saidi      | Project & Study Management           | [Email](mailto:ilyess.saidi@esprit.tn)     | [LinkedIn](#) / [GitHub](#) |

> ✉️ **General Inquiries**: [moncef.halleb@esprit.tn](mailto:moncef.halleb@esprit.tn)
