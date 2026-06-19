# Project Selection

---

## Selected Project

| | |
|---|---|
| **Name** | Activo |
| **URL** | https://github.com/JesusAlejandroTM/Activo |
| **Type** | Web (self-hosted) + API sandbox (JSONPlaceholder) |

---

## What is Activo?

> Activo is a community web application for discovering, hosting, and joining local group activities — hiking trips, jazz nights, sports events, and more. Users register, create activities with capacity limits and GPS coordinates, join others, vote, comment, chat in real time, and earn achievement badges.

**Tech stack:** Vue 3 · FastAPI · PostgreSQL · Docker · nginx · JWT auth

---

## Features Selected for Testing

| # | Feature | Scenarios |
|---|---------|-----------|
| 1 | **User Authentication** | Valid login, wrong password, empty fields, new registration |
| 2 | **Activity Creation** | All fields valid, missing required fields, invalid capacity |
| 3 | **Activity Search / Filter** | Search by name, filter by category/status, no results |
| 4 | **Join / Leave Activity** | Join, leave, re-join, join when full |
| 5 | **Badge System** | First Step, Host, Explorer — automatic award on trigger |

---

## Testing Opportunities

### Manual Testing
- Login form: positive and negative scenarios (EP technique)
- Activity form validation: required fields, boundary values
- Search: exact match, partial match, no results
- Role-based access: user vs moderator vs admin

### Automation Candidates

| Lab | Tool | Use Case |
|-----|------|----------|
| Lab 4 | Selenium | Login flow, activity creation form, join workflow |
| Lab 5 | Robot Framework | End-to-end activity lifecycle |
| Lab 6 | Postman | JSONPlaceholder — GET/POST/PUT/DELETE + status codes |

---

## Risks

| Risk | Impact | Mitigation |
|------|--------|-----------|
| Local Docker setup required | Teammates may not run the app | Document setup steps clearly |
| No public API | Lab 6 uses JSONPlaceholder as substitute | Agreed substitute documented |
| UI may change if repo is updated | Selenium selectors could break | Pin to a specific commit |
| Dynamic element IDs | Locators may be unstable | Use stable `data-testid` attributes |
