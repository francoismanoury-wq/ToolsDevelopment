# Test Plan — Activo Web Application

**Lab 3: Test Design and Test Management**  
**Team:** AFGANP | **Date:** 2026-06-19 | **Version:** 1.0

---

## 1. Introduction

> Activo is a full-stack community web application built on **Vue 3** (frontend), **FastAPI + PostgreSQL** (backend), deployed via **Docker Compose** with an nginx reverse proxy. Users discover, create, and join local group activities, vote, comment, chat in real time, and earn achievement badges.

This test plan defines **what** will be tested, **how**, and **under which conditions** for Lab 3 manual testing.

---

## 2. Scope of Testing

### In Scope

| # | Feature | Priority |
|---|---------|----------|
| 1 | User Authentication — register, login, logout, wrong credentials | 🔴 High |
| 2 | Activity Creation — form validation, required fields, capacity | 🔴 High |
| 3 | Activity Discovery — browse, filter by category / date / status | 🔴 High |
| 4 | Join / Leave Activity — state changes, capacity enforcement | 🔴 High |
| 5 | Vote on Activity — toggle vote / unvote | 🟡 Medium |
| 6 | Comment on Activity — post, display, delete own comment | 🟡 Medium |
| 7 | Badge System — automatic award on trigger actions | 🟡 Medium |
| 8 | Role-Based Access — user vs moderator vs admin | 🔴 High |

### Out of Scope

| Feature | Reason | Planned Lab |
|---------|--------|-------------|
| Real-time chat (WebSocket) | Requires async tooling | Lab 5 (Robot Framework) |
| Photo gallery | Base64 upload, not representative for manual | Exploratory |
| Weather widget | External API key required | — |
| Calendar / .ics export | Low priority for manual coverage | Later labs |
| Analytics dashboard | Host-only, deferred | Later labs |

---

## 3. Test Approach

### Test Design Techniques

| Technique | Applied To |
|-----------|-----------|
| **Equivalence Partitioning (EP)** | Login / registration inputs — valid vs invalid classes |
| **Boundary Value Analysis (BVA)** | `max_participants` field (0, 1, max); title length (1 char, empty) |
| **State Transition** | Join → Leave → Rejoin lifecycle; badge locked → earned |
| **Use Case Testing** | Full workflows: register → login → create → join |

### Test Types

| Type | Goal |
|------|------|
| Functional | Verify features behave as specified |
| Negative | Test system response to invalid / unexpected inputs |
| Boundary | Test at and around value range limits |
| Workflow | Walk through end-to-end user journeys |

---

## 4. Test Environment

| Item | Value |
|------|-------|
| Application URL | http://localhost:5173 |
| API / Swagger UI | http://localhost:8000/docs |
| Launch command | `docker compose up --build` |
| First startup | ~60 seconds (migrations + seed) |
| OS | Windows 11 |
| Browser | Google Chrome (latest) |
| Database | PostgreSQL 16 (Docker volume `pgdata`) |

### Test Accounts

| Role | Email | Password |
|------|-------|----------|
| Admin | `admin@activo.app` | `activo123` |
| Moderator | `maria@activo.app` | `activo123` |
| User | `nathan@activo.app` | `activo123` |
| User | `paul@activo.app` | `activo123` |

### Reset Procedure

```bash
# Wipe database and re-seed
docker compose down -v && docker compose up --build
```

---

## 5. Entry and Exit Criteria

### Entry Criteria

- [ ] Docker Compose stack starts successfully
- [ ] http://localhost:5173 loads the login page
- [ ] All four seed accounts log in with `activo123`
- [ ] Swagger UI accessible at http://localhost:8000/docs

### Exit Criteria

- [ ] All 13 test cases executed
- [ ] All High priority cases Pass **or** have a filed defect
- [ ] At least one defect report written for each failure
- [ ] Actual Result and Status filled in for every test case

---

## 6. Deliverables and Schedule

| Deliverable | File | Status |
|-------------|------|--------|
| Test Plan | `docs/test-plan.md` | ✅ Done |
| Test Case Template | `manual-tests/test-case-template.md` | ✅ Done |
| Manual Test Cases (13) | `manual-tests/manual-test-cases.md` | ✅ Done |
| Defect Reports | `reports/defect-report-example.md` | ✅ Done |
| Selenium automation | `automation/selenium/` | Upcoming — Lab 4 |

### Selenium Automation Candidates (Lab 4)

| Test Case | Justification |
|-----------|--------------|
| TC-001 — Valid login | Critical path, stable form selectors |
| TC-008 — Create activity | Multi-field form, core feature |
| TC-010 — Join workflow | Multi-step navigation with state verification |
