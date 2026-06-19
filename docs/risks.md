# Risk Register — Activo

**Team:** AFGANP | **Last updated:** 2026-06-19

---

## Risk Matrix

| ID | Risk | Likelihood | Impact | Level | Mitigation |
|----|------|-----------|--------|-------|-----------|
| R-01 | Docker required to run the app locally | Medium | 🔴 High | 🔴 High | Document setup steps; verify each teammate can run `docker compose up` |
| R-02 | Activo API is only accessible locally — not cloud-hosted | Low | 🟡 Medium | 🟡 Medium | Use JSONPlaceholder as API testing target for Lab 6 |
| R-03 | Activo is an external repo — UI may change without notice | Medium | 🔴 High | 🔴 High | Pin automation tests to a specific commit; avoid hardcoding class names |
| R-04 | OpenWeatherMap API key required for weather widget | High | 🟢 Low | 🟢 Low | Exclude weather widget from test scope; key is optional per the README |
| R-05 | Database state carries over between test runs | Medium | 🟡 Medium | 🟡 Medium | Reset DB before each full test session: `docker compose down -v && docker compose up --build` |

---

## Detailed Explanations

### R-01 — Docker required to run the app

Activo is a multi-service application (Vue frontend, FastAPI backend, PostgreSQL database, nginx reverse proxy). All services run together via Docker Compose. Teammates who do not have Docker Desktop installed cannot start the app at all. The first build also takes approximately 60 seconds while migrations and seed data are applied.

**Why it matters:** If a teammate cannot run the app, they cannot execute manual test cases or record actual results.

---

### R-02 — Activo API is only accessible locally

Activo's FastAPI backend runs at `http://localhost:8000` — it is a self-hosted application with no public cloud endpoint. For Lab 6, which requires API testing with Postman against an accessible API, the team uses **JSONPlaceholder** (`https://jsonplaceholder.typicode.com`) as a substitute, since it provides standard REST endpoints (GET, POST, PUT, DELETE) that are always online.

**Why it matters:** Without this decision, Lab 6 would have no testable API if Docker is not running.

---

### R-03 — External repo — UI may change without notice

Activo's source code lives at `https://github.com/JesusAlejandroTM/Activo` and is maintained by external authors. If they push an update that changes button labels, CSS class names, or page structure, Selenium selectors written for Lab 4 may break without any warning.

**Why it matters:** A single renamed CSS class can cause an entire Selenium test suite to fail, even if the feature itself works correctly.

---

### R-04 — OpenWeatherMap API key required for the weather widget

According to the Activo README, the weather widget (which shows a live forecast for each activity's location) is disabled unless the `VITE_OPENWEATHER_KEY` environment variable is set. This key requires registration on the OpenWeatherMap website.

**Why it matters:** Any test that touches the weather widget will either fail or show no data without this key. This feature is therefore excluded from our test scope.

---

### R-05 — Database state carries over between test runs

Activo uses a PostgreSQL database that persists data in a Docker volume (`pgdata`). Test cases that create activities, join events, or register users modify the database permanently. If a previous test run left unexpected data (e.g. a full activity, a duplicate user), subsequent test cases may fail for reasons unrelated to the feature being tested.

**Why it matters:** Join full activity test and badge on first join test both depend on a specific database state. Running them on a dirty database produces unreliable results.

---

## Legend

| Level | Meaning |
|-------|---------|
| 🔴 High | Likely to affect lab delivery — needs active mitigation |
| 🟡 Medium | May affect test quality — monitor and address if triggered |
| 🟢 Low | Unlikely to impact — acceptable, document and move on |
