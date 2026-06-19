# Project Charter

<div align="center">

# Activo WebApp
### Tools for Software Development — AFGANP Team

</div>

---

## Overview

| | |
|---|---|
| **Project** | Activo — Community Activity Platform |
| **Repository** | https://github.com/JesusAlejandroTM/Activo |
| **Type** | Full-stack web app (Vue 3 + FastAPI + PostgreSQL + Docker) |
| **Course** | Tools for Software Development |
| **Institution** | Žilinská univerzita v Žiline |
| **Team** | AFGANP |
| **Start Date** | June 2026 |

---

## Description

> Activo is a self-hosted platform for discovering, hosting, and joining local group activities — from hiking trips to jazz nights. Users create activities, join others, vote, comment, chat in real time, upload photos, and earn badges.

**Key features tested across labs:**

- User authentication (register, login, JWT)
- Activity creation, search, and filtering
- Join / Leave lifecycle with capacity enforcement
- Role-based access (user / moderator / admin)
- Badge system with automatic award logic

---

## Lab Roadmap

| Lab | Topic | Tool | Status |
|-----|-------|------|--------|
| Lab 1 | Project selection & planning | Git, Markdown | ✅ Done |
| Lab 2 | Unit testing | JUnit 5, IntelliJ | ✅ Done |
| Lab 3 | Test design & management | Manual testing | ✅ Done |
| Lab 4 | UI automation | Selenium WebDriver | Upcoming |
| Lab 5 | End-to-end testing | Robot Framework | Upcoming |
| Lab 6 | API testing | Postman | Upcoming |

---

## Team

| Name | Role |
|------|------|
| Alexis Baubion | Test designer |
| Gwenael Bourcet | API tester |
| Francois Manoury | Documentation lead |
| Nathan Poupeau--Avallart | Test manager |
| Alejandro Torres | Automation tester |
| Pierre Vie | Repository lead |

---

## Success Criteria

- All lab deliverables committed to `ToolsDevelopment` on time
- Test artifacts cover the five features from `project-selection.md`
- At least one automation script runs and passes per automation lab
- Defects found during testing are documented with reproduction steps and fix proposals
