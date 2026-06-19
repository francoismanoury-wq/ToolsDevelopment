# Test Case Template

Use this template for every manual test case. Copy the table below and fill in each field.

---

## Field Descriptions

| Field | Description |
|-------|-------------|
| **ID** | Unique identifier — format `TC-NNN` (e.g. TC-001) |
| **Title** | Short, descriptive name of the test |
| **Feature** | Feature or module under test (e.g. Authentication, Activity Creation) |
| **Priority** | High / Medium / Low — reflects business impact of a failure |
| **Type** | Positive / Negative / Boundary / Workflow |
| **Technique** | Test design technique used (EP, BVA, State Transition, Use Case) |
| **Preconditions** | System state required before the test can run |
| **Test Data** | Specific input values to use |
| **Steps** | Numbered step-by-step instructions (actionable, unambiguous) |
| **Expected Result** | What the system should do when steps are followed correctly |
| **Actual Result** | What actually happened — fill in after execution |
| **Status** | Pass / Fail / Blocked / Not Run |
| **Selenium Candidate** | Yes / No — whether this test is suitable for Selenium automation |

---

## Template

```
| Field              | Value |
|--------------------|-------|
| ID                 | TC-XXX |
| Title              | |
| Feature            | |
| Priority           | High / Medium / Low |
| Type               | Positive / Negative / Boundary / Workflow |
| Technique          | EP / BVA / State Transition / Use Case |
| Preconditions      | |
| Test Data          | |
| Steps              | 1. \n 2. \n 3. |
| Expected Result    | |
| Actual Result      | |
| Status             | Not Run |
| Selenium Candidate | Yes / No |
```

---

## Example (completed)

| Field              | Value |
|--------------------|-------|
| ID                 | TC-001 |
| Title              | Valid login with correct credentials |
| Feature            | Authentication |
| Priority           | High |
| Type               | Positive |
| Technique          | Equivalence Partitioning |
| Preconditions      | App running at http://localhost:5173; account nathan@activo.app exists |
| Test Data          | Email: nathan@activo.app / Password: activo123 |
| Steps              | 1. Navigate to http://localhost:5173 · 2. Click "Login" · 3. Enter email and password · 4. Click "Sign in" |
| Expected Result    | User redirected to home page; username visible in navbar |
| Actual Result      | POST /auth/login returned HTTP 200 with valid JWT token. Login accepted. |
| Status             | Pass |
| Selenium Candidate | Yes |
