# Test Plan — Activo Web Application
## Lab 3: Test Design and Test Management

**Team:** AFGANP  
**Date:** 2026-06-19  
**Version:** 1.0

---

## 1. Introduction

Activo is a full-stack community web application that lets users discover, host, and join local group activities (hiking trips, jazz nights, sports events, etc.). It is built on Vue 3 (frontend), FastAPI/PostgreSQL (backend), deployed via Docker Compose with an nginx reverse proxy.

This test plan covers manual test design for Lab 3. It defines what will be tested, how, and under which conditions.

---

## 2. Scope of Testing

### In Scope

| # | Feature | Priority |
|---|---------|----------|
| 1 | User Authentication — register, login, logout, wrong credentials | High |
| 2 | Activity Creation — form validation, required fields, capacity | High |
| 3 | Activity Discovery — browse, filter by category/date/status, search | High |
| 4 | Join / Leave Activity — state changes, capacity enforcement | High |
| 5 | Vote on Activity — toggle vote (vote / unvote) | Medium |
| 6 | Comment on Activity — post, display, delete own comment | Medium |
| 7 | Badge System — automatic badge award on trigger actions | Medium |
| 8 | Role-Based Access — user vs moderator vs admin permissions | High |

### Out of Scope (Lab 3)

- Real-time chat (WebSocket) — covered in Lab 5 (Robot Framework E2E)
- Photo gallery — dependent on base64 upload, deferred to exploratory testing
- Weather widget — depends on external API key, not testable in CI
- Calendar / .ics export — deferred to later labs
- Analytics dashboard — host-only feature, deferred to later labs

---

## 3. Test Approach

### Testing Types

| Type | Description | Coverage |
|------|-------------|---------|
| Functional | Verify each feature behaves as specified | Primary |
| Negative | Test with invalid / unexpected inputs | Primary |
| Boundary | Test at and around valid range limits | Primary |
| Role-based | Test same action under different user roles | Secondary |

### Test Design Techniques

1. **Equivalence Partitioning (EP)** — Divide login/registration inputs into valid and invalid classes; test one representative from each class instead of exhaustive combinations.
2. **Boundary Value Analysis (BVA)** — Test capacity field at 0, 1, and max; test title/description at minimum and maximum allowed lengths.
3. **State Transition Testing** — Model the Join/Leave lifecycle: `not joined → joined → left → re-joined`. Also used for badge state: `locked → earned`.
4. **Use Case Testing** — Walk through complete user workflows end-to-end (registration → login → create activity → join another's activity).

### Test Levels

- **Manual tests only** for this lab (13 test cases)
- Selenium automation candidates identified (TC-001, TC-008, TC-010) — to be implemented in Lab 4

---

## 4. Test Environment

| Item | Value |
|------|-------|
| Application URL | http://localhost:5173 |
| API (Swagger) | http://localhost:8000/docs |
| Launch command | `docker compose up --build` (first run ~60 s) |
| OS | Windows 11 |
| Browser | Google Chrome (latest) |
| DB | PostgreSQL 16 (Docker volume `pgdata`) |

### Test Accounts

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@activo.app | activo123 |
| Moderator | maria@activo.app | activo123 |
| Regular user | nathan@activo.app | activo123 |
| Regular user | paul@activo.app | activo123 |

### Reset Procedure

To return the database to a clean seed state:
```bash
docker compose down -v && docker compose up --build
```

---

## 5. Entry and Exit Criteria

### Entry Criteria

- Docker Compose stack starts successfully (`docker compose up --build`)
- Login page loads at http://localhost:5173
- All four seed accounts are accessible with password `activo123`
- Swagger UI is accessible at http://localhost:8000/docs

### Exit Criteria

- All 13 planned test cases have been executed
- All High priority test cases pass or defects are filed
- At least one defect report written for any found failure
- Test results recorded with Actual Result and Status in the test case sheet

---

## 6. Test Deliverables and Schedule

| Deliverable | File | Target Date |
|-------------|------|-------------|
| Test Plan (this document) | `docs/test-plan.md` | 2026-06-19 |
| Test Case Template | `manual-tests/test-case-template.md` | 2026-06-19 |
| Manual Test Cases (13 cases) | `manual-tests/manual-test-cases.md` | 2026-06-19 |
| Defect Report | `reports/defect-report-example.md` | 2026-06-19 |
| Selenium automation (Lab 4) | `automation/selenium/` | TBD |

### Automation Candidates for Lab 4

| Test Case | Reason |
|-----------|--------|
| TC-001 — Valid login | Repetitive, well-defined selectors, login critical path |
| TC-008 — Create activity | Form with multiple fields, good Selenium exercise |
| TC-010 — Full join workflow | Multi-step navigation, verifies UI state after action |
