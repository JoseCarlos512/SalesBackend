# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Spring Boot 3.4.4 REST API for electronic billing/sales management (Facturación Electrónica). Java 17, MySQL 8, JWT authentication, Maven build.

- Base URL: `http://localhost:9898/api`
- Swagger UI: `http://localhost:9898/api/swagger-ui.html`

## Build & Run Commands

```bash
# Build (skip tests)
./mvnw clean package -DskipTests

# Run the application
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ApplicationTests
```

## Environment Variables

All sensitive values are externalized. Set these for production:

| Variable | Default (dev only) | Description |
|---|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/dsistemaventas?...` | Full JDBC URL |
| `DB_USERNAME` | `root` | DB user |
| `DB_PASSWORD` | `12345678` | DB password |
| `JWT_SECRET` | `change-this-secret-...` | **Must override in prod** |
| `JWT_EXPIRATION` | `86400000` | Token TTL in ms (24h) |
| `CORS_ORIGINS` | `http://localhost:4200,http://localhost:3000` | Comma-separated allowed origins |
| `DDL_AUTO` | `update` | Use `validate` in production |
| `SERVER_PORT` | `9898` | HTTP port |

## Architecture

**Layered pattern:** `Controller → Service (interface + implement) → Repository (Dao) → Entity`

**Package root:** `sys_facturation.com`

| Layer | Package | Naming |
|---|---|---|
| Controllers | `controller/` | `*Controller` |
| Service interfaces | `service/` | `*Service` |
| Service implementations | `implement/` | `*Implement` / `*Impl` |
| Repositories | `repository/` | `*Dao` (extends `JpaRepository`) |
| Entities | `entity/` | plain class names |
| DTOs | `dto/` | `*DTO` / `*Request` / `*Response` |
| Security | `security/` | `JwtUtil`, `JwtAuthFilter`, `SecurityConfig`, `UserDetailsServiceImpl` |

## REST API — URL Convention

All routes follow REST conventions (no verb in URL):

| Resource | Base path |
|---|---|
| Auth | `POST /auth/login` |
| Articles | `/articles` |
| Categories | `/categories` |
| Sales | `/sales` |
| Sales details | `/sales-details` |
| Income (purchases) | `/income` |
| Persons (customers) | `/persons` |
| Providers (suppliers) | `/providers` |
| Roles | `/roles` |
| Users | `/users` |

Standard methods: `GET /resource` (list), `GET /resource/{id}`, `POST /resource` (create), `PUT /resource/{id}` (update), `DELETE /resource/{id}`.

## Security & Authentication

- JWT tokens (HS256). All routes require auth except `/auth/**` and Swagger paths.
- Add `Authorization: Bearer <token>` header to authenticated requests.
- Obtain token via `POST /api/auth/login` with `{"usuario": "...", "password": "..."}`.
- CORS is configured centrally in `SecurityConfig` via `app.cors.allowed-origins`.
- **Do not** add `@CrossOrigin` to individual controllers.

## Database & Entities

- MySQL `dsistemaventas`. `ddl-auto=update` by default (use `validate` in production).
- Timestamp fields (`createdAt`, `updatedAt`, `create_at`, `update_at`) are managed automatically via `@PrePersist`/`@PreUpdate` — never set them manually in controllers.
- `SalesDetails` uses `@ManyToOne` → `Sales` (one sale has many detail lines).
- `Articles` → `Categories` (many-to-one FK `id_categories`).
- `User` → `Rol` (many-to-one FK `idrol`).
- `Income` contains `List<IncomeDetail>` (one-to-many, cascade ALL).

## Key Patterns

**Service implementations** must use `org.springframework.transaction.annotation.Transactional` (not `jakarta`), and mark read-only methods with `@Transactional(readOnly = true)`.

**Adding new resources** — follow this pattern:
1. Create `Entity` in `entity/` with `@PrePersist`/`@PreUpdate` for timestamps
2. Create `*Dao` in `repository/` extending `JpaRepository<Entity, Long>`
3. Create `*Service` interface in `service/`
4. Create `*Implement` in `implement/` with `@Service` (never `@Repository`)
5. Create `*Controller` in `controller/` using REST URL conventions above
6. Add DTOs to `dto/` and mapper methods to `util/MapperUtils` if needed

**DTOs must never expose the password field.** `UserDTO` intentionally omits it.
