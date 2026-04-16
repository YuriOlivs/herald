
# Herald - Multi-channel Notification System

Herald is a multi-channel notification system built with Java Spring Boot. Developed as a study project , it handles the scheduling, prioritization, and delivery of alerts via Email and Telegram. The system is designed with security in mind, featuring built-in authentication and encryption at rest for all transmitted data.
## 🛠 Architecture

The system relies on a microservices architecture, utilizing both REST APIs and asynchronous messaging via RabbitMQ. This ensures that each service maintains its own domain logic and independent data persistence, allowing for robust scalability.

### Services
Each microservice is entirely decoupled and can be deployed independently:

* **herald-gateway:** It acts as the single entry point, receiving requests, authenticating users via herald-auth, and routing traffic to other services.

* **herald-auth:** It is strictly responsible for user authentication, API key management, and handling sensitive secrets across the application.

* **herald-scheduler:** It manages message scheduling logic and constantly polls for due messages, dispatching them to the main service for immediate processing.

* **herald-service:** It acts as the primary consumer for channel-specific queues, processes outgoing messages, and handles the final data persistence in the database.

### Shared Module

* **herald-shared:** A internal Maven library shared across services. It contains common domain models, DTOs, and exception definitions, avoiding code duplication across the system. It is not a deployable service — it is packaged and installed locally as a dependency during the build process.
## ⚙️ Tech Stack

**Backend:** Java, Spring Boot (Spring Security, WebFlux)

**Database:** PostgreSQL

**Message Broker:** RabbitMQ

**Infrastructure:** Docker, Docker Compose

**Documentation:** OpenAPI / Swagger

**Other APIs:** Telegram API
## 📥 Installation

### Prerequisites
- Docker and Docker Compose installed
- Java 17+
- Maven

### Environment Variables

Each service has its own `application.yaml` in its `src/main/resources` folder. Before running the project, fill in the required fields in each file:

**herald-gateway**
- `gateway.auth-url` — herald-auth service URL
- `gateway.auth-key` — secret key shared between gateway and herald-auth

**herald-auth**
- `spring.datasource.url` — database connection URL
- `spring.datasource.username` — database username
- `spring.datasource.password` — database password

**herald-service**
- `spring.datasource.url` — database connection URL
- `spring.datasource.username` — database username
- `spring.datasource.password` — database password
- `spring.mail.username` — e-mail that will be used to send via SMTP
- `spring.mail.password` — app password
- `spring.rabbitmq.username` — rabbitMQ username
- `spring.rabbitmq.password` — rabbitMQ password
- `security.encryption-key` — 32 character key used for AES encryption at rest
- `security.internal-key` — secret key shared between services
- `mail.from.address` — r-mail used as "from"
- `mail.from.name` — name for the e-mail used as "from"
- `telegram.config.token` — Bot token

**herald-scheduler**
- `spring.datasource.url` — database connection URL
- `spring.datasource.username` — database username
- `spring.datasource.password` — database password
- `security.internal-key` — secret key shared between services

### Running the project

Clone the monorepo:

```bash
git clone https://github.com/YuriOlivs/herald
cd herald
```

Start all services:

```bash
docker-compose up --build
```
## 🚀 Deployment

The system is fully containerized. Each service has its own `Dockerfile` using multi-stage builds to keep images lean. The `docker-compose.yml` at the root orchestrates all services and infrastructure.

The only publicly exposed port is **8080** (herald-gateway) — all other services communicate internally through Docker's network and are not accessible from outside.

| Service | Internal Port |
|---|---|
| herald-gateway | 8080 (public) |
| herald-auth | 8083 |
| herald-service | 8081 |
| herald-scheduler | 8082 |
| RabbitMQ Management | 15672 |
| PgAdmin | 8084 |

## 

- Developed by [@YuriOlivs](https://www.github.com/YuriOlivs)

