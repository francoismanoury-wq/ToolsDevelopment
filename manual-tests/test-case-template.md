# Test Case Template

**Project:** Activo | **Lab 3:** Test Design and Test Management

---

## Field Reference

| Field | Description |
|-------|-------------|
| **ID** | Unique identifier — format `TC-NNN` |
| **Title** | Short, descriptive name of the test |
| **Feature** | Module under test (e.g. Authentication, Activity Creation) |
| **Priority** | 🔴 High / 🟡 Medium / 🟢 Low |
| **Type** | Positive / Negative / Boundary / Workflow |
| **Technique** | EP · BVA · State Transition · Use Case |
| **Preconditions** | System state required before execution |
| **Test Data** | Specific input values to use |
| **Steps** | Numbered, actionable instructions |
| **Expected Result** | What the system should do |
| **Actual Result** | What actually happened *(filled after execution)* |
| **Status** | ✅ Pass · ❌ Fail · ⚠️ Blocked · — Not Run |
| **Selenium Candidate** | Yes / No |

---

## Blank Template

```
| Field              | Value        |
|--------------------|--------------|
| ID                 | TC-XXX       |
| Title              |              |
| Feature            |              |
| Priority           | High / Medium / Low |
| Type               | Positive / Negative / Boundary / Workflow |
| Technique          | EP / BVA / State Transition / Use Case |
| Preconditions      |              |
| Test Data          |              |
| Steps              | 1. · 2. · 3. |
| Expected Result    |              |
| Actual Result      |              |
| Status             | Not Run      |
| Selenium Candidate | Yes / No     |
```

---

## Completed Example

| Field | Value |
|-------|-------|
| **ID** | TC-001 |
| **Title** | Valid login with correct credentials |
| **Feature** | Authentication |
| **Priority** | 🔴 High |
| **Type** | Positive |
| **Technique** | Equivalence Partitioning |
| **Preconditions** | App running at http://localhost:5173; account `nathan@activo.app` exists |
| **Test Data** | Email: `nathan@activo.app` · Password: `activo123` |
| **Steps** | 1. Navigate to http://localhost:5173 · 2. Click "Login" · 3. Enter email and password · 4. Click "Sign in" |
| **Expected Result** | User redirected to home page; username visible in navbar; no error shown |
| **Actual Result** | `POST /auth/login` returned HTTP 200 with valid JWT token. Login accepted. |
| **Status** | ✅ Pass |
| **Selenium Candidate** | Yes |
