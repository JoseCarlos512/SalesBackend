# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Spring Boot 3.4.4 REST API for electronic billing/sales management (Facturación Electrónica) with **SUNAT integration** for Peru.
Java 17, MySQL 8, JWT authentication, Maven.

- Base URL: `http://localhost:9898/api`
- Swagger UI: `http://localhost:9898/api/swagger-ui.html`

---

## Build & Run Commands

```bash
./mvnw clean package -DskipTests   # build JAR
./mvnw spring-boot:run             # run in development
./mvnw test                        # run all tests
./mvnw test -Dtest=ClassName       # single test class
```

---

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
| `SUNAT_RUC` | `20000000001` | Company RUC |
| `SUNAT_RAZON_SOCIAL` | `MI EMPRESA SAC` | Company legal name |
| `SUNAT_NOMBRE_COMERCIAL` | `MI EMPRESA` | Trade name |
| `SUNAT_DIRECCION` | `AV. PRINCIPAL 123, LIMA` | Company address |
| `SUNAT_UBIGEO` | `150101` | UBIGEO code |
| `SUNAT_USERNAME` | `20000000001MODDATOS` | SOL credentials user |
| `SUNAT_PASSWORD` | `moddatos` | SOL credentials password |
| `SUNAT_AMBIENTE` | `beta` | `beta` or `produccion` |
| `SUNAT_CERT_PATH` | `classpath:cert/demo.pfx` | Path to PKCS12 certificate |
| `SUNAT_CERT_PASSWORD` | `demo` | Certificate password |
| `SUNAT_CERT_ALIAS` | *(auto-detect)* | Certificate alias (optional) |

---

## Architecture

**Package root:** `sys_facturation.com`

| Layer | Package | Annotation |
|---|---|---|
| Controllers | `controller/` | `@RestController` |
| Service interfaces | `service/` | — |
| Service implementations | `implement/` | `@Service` |
| Repositories | `repository/` | `JpaRepository<Entity, Long>` |
| Entities | `entity/` | `@Entity` |
| DTOs | `dto/` | plain class |
| SUNAT integration | `sunat/` | `@Service` / `@Component` |
| Config beans | `config/` | `@Configuration` / `@ConfigurationProperties` |
| Utilities | `util/` | static methods |

---

## REST API — URL Convention

Standard REST: `GET /resource` (list), `GET /{id}`, `POST /` (create), `PUT /{id}`, `DELETE /{id}`.

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
| **SUNAT billing** | `POST /billing/{saleId}` |

---

## Domain Model

```
Rol ─────────────── User
                       │
Categories ─── Articles
                       │
Person ──────── Sales ─┤
                       └─ SalesDetails ─── Articles

Provider ──────── Income
                       └─ IncomeDetail
```

**Sales entity key fields:**
- `tipoComprobante` — `"03"` boleta, `"01"` factura
- `serieComprobante` — `"B001"` boleta, `"F001"` factura
- `numComprobante` — 8-digit correlative
- `person` — customer (required for SUNAT)
- `detalles` — `List<SalesDetails>` (cascade ALL, fetch EAGER)
- `sunatCodigo` / `sunatDescripcion` — CDR result from SUNAT

**SalesDetails key fields:**
- `article` — FK to Articles (provides code + description for SUNAT XML)
- `precio` — unit price **WITHOUT IGV** (net)
- `sales` — `@JsonIgnore` to prevent circular serialization

---

## SUNAT Electronic Billing Flow

```
POST /billing/{saleId}
        │
        ├── BillingXmlBuilder      → UBL 2.1 XML
        │   • IGV 18% calculated from SalesDetails
        │   • NumberToWords for the Note field ("SON X CON 00/100 SOLES")
        │   • Reads company data from SunatProperties
        │
        ├── BillingSignerService   → RSA-SHA256 XML digital signature
        │   • Loads PKCS12 certificate (classpath or filesystem)
        │   • Uses javax.xml.crypto.dsig (built into JDK 17 — no extra deps)
        │   • Inserts signature into UBLExtensions/ExtensionContent
        │   • If certificate missing → logs warning and continues unsigned (beta only)
        │
        ├── SunatSoapClient        → SOAP 1.1 via RestTemplate
        │   • Creates ZIP with signed XML inside
        │   • POST to beta or produccion endpoint (from SunatProperties)
        │   • Basic Auth with SOL credentials
        │   • Parses <applicationResponse> from SOAP response
        │
        └── SunatBillingService    → orchestrates all, parses CDR, persists result
            • Updates sale.estado (ACEPTADO | RECHAZADO | ERROR_SUNAT)
            • Updates sale.sunatCodigo + sale.sunatDescripcion
```

**File naming convention for SUNAT:**
`{RUC}-{tipoComprobante}-{serie}-{numero}.zip`
Example: `20000000001-03-B001-00000001.zip`

**Certificate:** place `.pfx`/`.p12` in `src/main/resources/cert/`.
See `src/main/resources/cert/README.txt` for instructions.

---

## Security & Authentication

- JWT tokens (HS256). All routes require auth except `/auth/**` and Swagger paths.
- Add `Authorization: Bearer <token>` to authenticated requests.
- CORS configured centrally in `SecurityConfig` — **do not** add `@CrossOrigin` to controllers.
- `UserDetailsServiceImpl` loads user+role from DB; role becomes `ROLE_<nombre>`.

---

## Key Conventions

- `@Transactional(readOnly = true)` for all read-only operations
- Use `org.springframework.transaction.annotation.Transactional` (not jakarta)
- Timestamps (`createdAt`/`create_at`, `updatedAt`/`update_at`) managed by `@PrePersist`/`@PreUpdate` — **never set manually in controllers**
- No `@CrossOrigin` on controllers — configured centrally
- DTOs must never expose the password field
- `@Service` on all service implementations (never `@Repository` on service classes)
- All repositories extend `JpaRepository<Entity, Long>`

---

## Adding New Resources

1. Create `Entity` in `entity/` — add `@PrePersist`/`@PreUpdate` if it has timestamps
2. Create `*Dao` in `repository/` extending `JpaRepository<Entity, Long>`
3. Create `*Service` interface in `service/`
4. Create `*Implement` in `implement/` with `@Service`
5. Create `*Controller` in `controller/` using REST URL conventions above
6. Add DTOs to `dto/` and mapper methods to `util/MapperUtils` if needed

---

## What Has Been Done

### Security fixes
- JWT secret externalized via `@Value("${jwt.secret}")` — configurable via `JWT_SECRET` env var
- Removed `/auth/generate-temp-password` debug endpoint (was exposing raw passwords)
- Removed password logging from `UserDetailsServiceImpl`
- Removed `password` field from `UserDTO` (API response no longer leaks hashed passwords)
- `ddl-auto` changed from `create-drop` → `update`
- All credentials externalized with env var support

### Bug fixes
- `SalesDetails @OneToOne` → `@ManyToOne` (a sale has **multiple** detail lines)
- `Sales.id` type: `Integer` → `Long`
- `IncomeController.delete()` now returns `ResponseEntity<Void>`
- `@Repository` annotation corrected to `@Service` on `IncomeImplements`, `PersonServiceImpl`, `ProviderServiceImpl`

### Code quality
- All repositories: `CrudRepository` → `JpaRepository`
- `PersonServiceImpl` and `ProviderServiceImpl`: raw `EntityManager` → `JpaRepository`
- `@Transactional` unified to Spring's (not jakarta); `readOnly = true` on reads
- `@PrePersist`/`@PreUpdate` added to `Articles`, `Categories`, `Sales`, `Income`
- CORS centralized in `SecurityConfig` with `app.cors.allowed-origins` property
- `System.out.println` → SLF4J logger in `IncomeImplements`
- Removed `abstract` keyword from service interface methods
- Removed wrong import (`hibernate Collation`) from `CategoriesService`
- Removed unused imports across entities and DTOs
- Duplicate `mssql-jdbc` dependency removed from `pom.xml`
- MySQL connector: deprecated `mysql:mysql-connector-java` → `com.mysql:mysql-connector-j`
- `BCryptPasswordEncoder` in `SecurityConfig` now uses the shared bean (not `new`)

### Microservice-ready changes
- All env vars externalized (DB, JWT, CORS, SUNAT, server port)
- REST URL standardization across all controllers
- `@JsonIgnore` / `@JsonIgnoreProperties` on entity back-references to prevent circular JSON

### SUNAT electronic billing integration
- `Sales` entity: added `person` FK (customer) + `List<SalesDetails> detalles` (cascade)
- `SalesDetails` entity: added `article` FK (product) + `@JsonIgnore` on `sales`
- `Sales` entity: added `sunatCodigo` + `sunatDescripcion` fields for CDR result
- `SunatProperties` — `@ConfigurationProperties(prefix = "sunat")` for all SUNAT config
- `BillingXmlBuilder` — generates UBL 2.1 XML for boleta (03) / factura (01)
- `BillingSignerService` — RSA-SHA256 XML digital signature using JDK's `javax.xml.crypto.dsig`
- `SunatSoapClient` — SOAP 1.1 client via `RestTemplate` (no extra dependencies)
- `SunatBillingService` — orchestrates full flow: XML → sign → ZIP → SOAP → CDR → persist
- `BillingController` — `POST /billing/{saleId}` endpoint
- `NumberToWords` — converts amounts to Spanish words for SUNAT Note field
- `BillingResponseDTO` — structured response with estado, codigoSunat, descripcionSunat
- Certificate placeholder in `src/main/resources/cert/README.txt`
