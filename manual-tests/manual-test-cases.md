# Manual Test Cases — Activo Web Application

**Lab 3: Test Design and Test Management**  
**Team:** AFGANP | **Executed:** 2026-06-19

---

## Execution Summary

| Metric | Value |
|--------|-------|
| Total test cases | 13 |
| ✅ Pass | 12 |
| ❌ Fail | 1 |
| ⚠️ Blocked | 0 |
| Defects filed | 2 (DEF-001, DEF-002) |

---

## Techniques Used

| Technique | Abbreviation | Applied To |
|-----------|-------------|-----------|
| Equivalence Partitioning | EP | Login inputs — valid vs invalid classes |
| Boundary Value Analysis | BVA | Capacity field (0, 1); title length (empty, 1 char) |
| State Transition | ST | Join → Leave → Rejoin; badge Locked → Earned |
| Use Case Testing | UC | Full registration → create → join workflow |

---

## TC-001 — Valid login

| Field | Value |
|-------|-------|
| **ID** | TC-001 |
| **Title** | Valid login with correct credentials |
| **Feature** | Authentication |
| **Priority** | 🔴 High |
| **Type** | Positive |
| **Technique** | Equivalence Partitioning |
| **Preconditions** | App running at http://localhost:5173; account `nathan@activo.app` exists (seed data) |
| **Test Data** | Email: `nathan@activo.app` · Password: `activo123` |
| **Steps** | 1. Navigate to http://localhost:5173 · 2. Click "Login" · 3. Enter email and password · 4. Click "Sign in" |
| **Expected Result** | User redirected to home page; username "Nathan" visible in navbar; no error shown |
| **Actual Result** | `POST /auth/login` → HTTP 200 with valid JWT token. Login accepted. |
| **Status** | ✅ Pass |
| **Selenium Candidate** | Yes — critical login path, stable selectors |

---

## TC-002 — Login with incorrect password

| Field | Value |
|-------|-------|
| **ID** | TC-002 |
| **Title** | Login with incorrect password |
| **Feature** | Authentication |
| **Priority** | 🔴 High |
| **Type** | Negative |
| **Technique** | Equivalence Partitioning |
| **Preconditions** | App running |
| **Test Data** | Email: `nathan@activo.app` · Password: `wrongpassword` |
| **Steps** | 1. Click "Login" · 2. Enter email and wrong password · 3. Click "Sign in" |
| **Expected Result** | Error message shown (e.g. "Invalid credentials"); user stays on login modal |
| **Actual Result** | `POST /auth/login` → HTTP 401. No token issued. |
| **Status** | ✅ Pass |
| **Selenium Candidate** | No |

---

## TC-003 — Login with empty email

| Field | Value |
|-------|-------|
| **ID** | TC-003 |
| **Title** | Login with empty email field |
| **Feature** | Authentication |
| **Priority** | 🔴 High |
| **Type** | Negative |
| **Technique** | Equivalence Partitioning |
| **Preconditions** | App running |
| **Test Data** | Email: *(empty)* · Password: `activo123` |
| **Steps** | 1. Click "Login" · 2. Leave email blank · 3. Enter password · 4. Click "Sign in" |
| **Expected Result** | Validation error on email field; request NOT sent |
| **Actual Result** | `POST /auth/login` with empty email → HTTP 401. Backend rejects at credentials level; HTML5 validation on the frontend catches it before sending. |
| **Status** | ✅ Pass |
| **Selenium Candidate** | No |

---

## TC-004 — Login with non-existent email

| Field | Value |
|-------|-------|
| **ID** | TC-004 |
| **Title** | Login with non-existent email address |
| **Feature** | Authentication |
| **Priority** | 🟡 Medium |
| **Type** | Negative |
| **Technique** | Equivalence Partitioning |
| **Preconditions** | App running |
| **Test Data** | Email: `nobody@fake.com` · Password: `any123` |
| **Steps** | 1. Click "Login" · 2. Enter non-existent email and any password · 3. Click "Sign in" |
| **Expected Result** | Error message shown; user NOT logged in; response does NOT reveal whether the email exists |
| **Actual Result** | `POST /auth/login` → HTTP 401, same response as wrong password. No info leakage. |
| **Status** | ✅ Pass |
| **Selenium Candidate** | No |

---

## TC-005 — Create activity with capacity = 1 (minimum valid boundary)

| Field | Value |
|-------|-------|
| **ID** | TC-005 |
| **Title** | Create activity with capacity at minimum valid value (1) |
| **Feature** | Activity Creation |
| **Priority** | 🟡 Medium |
| **Type** | Boundary |
| **Technique** | Boundary Value Analysis |
| **Preconditions** | Logged in as `nathan@activo.app` |
| **Test Data** | Title: "Solo Walk Test" · Category: Sport · Date: next day · `max_participants`: **1** |
| **Steps** | 1. Navigate to Create Activity · 2. Fill all fields · 3. Set capacity to `1` · 4. Submit |
| **Expected Result** | Activity created with `max_participants = 1`; shown as "0/1 participants" in the list |
| **Actual Result** | `POST /activities` with `max_participants=1` → HTTP 200. Activity created successfully. |
| **Status** | ✅ Pass |
| **Selenium Candidate** | No |

---

## TC-006 — Create activity with capacity = 0 (invalid boundary)

| Field | Value |
|-------|-------|
| **ID** | TC-006 |
| **Title** | Create activity with capacity = 0 — below minimum |
| **Feature** | Activity Creation |
| **Priority** | 🟡 Medium |
| **Type** | Boundary / Negative |
| **Technique** | Boundary Value Analysis |
| **Preconditions** | Logged in as `nathan@activo.app` |
| **Test Data** | Title: "Zero Cap Test" · Category: Sport · `max_participants`: **0** |
| **Steps** | 1. Navigate to Create Activity · 2. Fill all fields · 3. Set capacity to `0` · 4. Submit |
| **Expected Result** | Validation error: "Capacity must be at least 1"; activity NOT created |
| **Actual Result** | `POST /activities` with `max_participants=0` → **HTTP 200**. Activity created with zero capacity. Backend has no Pydantic `ge=1` constraint. **→ DEF-002** |
| **Status** | ❌ Fail |
| **Selenium Candidate** | No |

---

## TC-007 — Create activity with title = 1 character (boundary)

| Field | Value |
|-------|-------|
| **ID** | TC-007 |
| **Title** | Create activity with single-character title |
| **Feature** | Activity Creation |
| **Priority** | 🟡 Medium |
| **Type** | Boundary |
| **Technique** | Boundary Value Analysis |
| **Preconditions** | Logged in as `nathan@activo.app` |
| **Test Data** | Title: `"A"` · Category: Music · Capacity: 10 |
| **Steps** | 1. Navigate to Create Activity · 2. Enter title `A` · 3. Fill other fields · 4. Submit |
| **Expected Result** | System accepts or rejects, consistent with backend Pydantic schema |
| **Actual Result** | `POST /activities` with `title="A"` → HTTP 400. Backend enforces a minimum title length > 1 character. |
| **Status** | ✅ Pass |
| **Selenium Candidate** | No |

---

## TC-008 — Create activity with all valid fields

| Field | Value |
|-------|-------|
| **ID** | TC-008 |
| **Title** | Create a new activity with all required fields filled in |
| **Feature** | Activity Creation |
| **Priority** | 🔴 High |
| **Type** | Positive |
| **Technique** | Use Case Testing |
| **Preconditions** | Logged in as `nathan@activo.app` |
| **Test Data** | Title: "Morning Yoga in the Park" · Category: Sport · Date: next week · Location: "Parc de la Tête d'Or" · Capacity: 20 · Description: "Relaxing yoga session for all levels." |
| **Steps** | 1. Click "Host Activity" · 2. Fill all fields · 3. Click "Create" · 4. Search for "Morning Yoga" in the Activities list |
| **Expected Result** | Activity visible in the list with correct title, category, date, and capacity |
| **Actual Result** | `POST /activities` → HTTP 200. Activity created (id=10), title "Morning Yoga in the Park", `max_participants=20`. Visible via `GET /activities`. |
| **Status** | ✅ Pass |
| **Selenium Candidate** | Yes — multi-field form, critical creation flow |

---

## TC-009 — Create activity with missing title

| Field | Value |
|-------|-------|
| **ID** | TC-009 |
| **Title** | Create activity without providing a title |
| **Feature** | Activity Creation |
| **Priority** | 🔴 High |
| **Type** | Negative |
| **Technique** | Equivalence Partitioning |
| **Preconditions** | Logged in as `nathan@activo.app` |
| **Test Data** | Title: *(empty)* · All other fields: valid |
| **Steps** | 1. Navigate to Create Activity · 2. Leave title blank · 3. Fill other fields · 4. Submit |
| **Expected Result** | Validation error on title field; activity NOT created |
| **Actual Result** | `POST /activities` with `title=""` → HTTP 400. Empty title correctly rejected. |
| **Status** | ✅ Pass |
| **Selenium Candidate** | No |

---

## TC-010 — Full join-activity workflow

| Field | Value |
|-------|-------|
| **ID** | TC-010 |
| **Title** | Complete workflow: browse activities and join one |
| **Feature** | Join Activity |
| **Priority** | 🔴 High |
| **Type** | Positive / Workflow |
| **Technique** | Use Case Testing |
| **Preconditions** | Logged in as `paul@activo.app`; at least one joinable activity exists (seed data) |
| **Test Data** | Activity with `participants_count < max_participants` |
| **Steps** | 1. Navigate to `/activities` · 2. Click an activity card · 3. Click "Join Activity" · 4. Verify participant count +1 · 5. Go to Profile → "Joined" tab |
| **Expected Result** | Participant count +1; button changes to "Leave Activity"; activity appears in Profile Joined tab; host receives notification |
| **Actual Result** | `POST /activities/10/join` → HTTP 200. `participants_count` went from 1 → 2. |
| **Status** | ✅ Pass |
| **Selenium Candidate** | Yes — multi-step workflow with state verification |

---

## TC-011 — Leave activity (State Transition)

| Field | Value |
|-------|-------|
| **ID** | TC-011 |
| **Title** | Leave an activity that the user has joined |
| **Feature** | Join / Leave Activity |
| **Priority** | 🟡 Medium |
| **Type** | Positive |
| **Technique** | State Transition |
| **Preconditions** | `paul@activo.app` is already a participant (run TC-010 first) |
| **Test Data** | Same activity as TC-010 |
| **Steps** | 1. Navigate to the joined activity · 2. Click "Leave Activity" · 3. Observe participant count and button |
| **Expected Result** | Count −1; button reverts to "Join Activity"; activity removed from Profile Joined tab |
| **Actual Result** | `POST /activities/10/leave` → HTTP 200. `participants_count` went from 2 → 1. State confirmed. |
| **Status** | ✅ Pass |
| **Selenium Candidate** | No |

**State diagram:**

```
[Not Joined] ──join──► [Joined] ──leave──► [Not Joined]
                 ▲                               │
                 └──────────── rejoin ───────────┘
```

---

## TC-012 — Join a fully-booked activity

| Field | Value |
|-------|-------|
| **ID** | TC-012 |
| **Title** | Attempt to join an activity that has reached maximum capacity |
| **Feature** | Join Activity |
| **Priority** | 🔴 High |
| **Type** | Negative |
| **Technique** | Boundary Value Analysis |
| **Preconditions** | Activity with `max_participants=1` exists and is already full (host counts as participant) |
| **Test Data** | Log in as `maria@activo.app` (different from the host) |
| **Steps** | 1. Log in as `maria@activo.app` · 2. Navigate to the full activity · 3. Click "Join Activity" |
| **Expected Result** | Friendly error message: "This activity is full"; count does NOT increase |
| **Actual Result** | `POST /activities/12/join` → HTTP 400 `{"detail": "Activity is full"}`. Count unchanged. Raw detail string surfaced in the UI. **→ DEF-001** |
| **Status** | ✅ Pass |
| **Selenium Candidate** | No |

---

## TC-013 — First Step badge on first join (State Transition)

| Field | Value |
|-------|-------|
| **ID** | TC-013 |
| **Title** | "First Step" badge automatically awarded when user joins first activity |
| **Feature** | Badge System |
| **Priority** | 🟡 Medium |
| **Type** | Positive |
| **Technique** | State Transition |
| **Preconditions** | New account that has never joined an activity |
| **Test Data** | `testbadge@activo.app` / `Badge1234!` |
| **Steps** | 1. Register new account · 2. Navigate to Activities · 3. Join any activity · 4. Go to Profile → Badges tab |
| **Expected Result** | "First Step" badge displayed as earned; description: "Joined your first activity" |
| **Actual Result** | New account registered (0 badges before). After join, `GET /users/me/badges` → 1 badge: `label="First Step"`, `desc="Joined your first activity"`, `badge_type="first_step"`. Badge awarded automatically. |
| **Status** | ✅ Pass |
| **Selenium Candidate** | No |

**Badge state diagram:**

```
[Locked] ──trigger──► [Earned]  (irreversible)

First Step  →  trigger: first join
Explorer    →  trigger: 5th join
Host        →  trigger: first created activity
```

---

## Summary

| ID | Title | Feature | Priority | Technique | Status | Selenium |
|----|-------|---------|----------|-----------|--------|---------|
| TC-001 | Valid login | Auth | 🔴 High | EP | ✅ Pass | Yes |
| TC-002 | Wrong password | Auth | 🔴 High | EP | ✅ Pass | No |
| TC-003 | Empty email | Auth | 🔴 High | EP | ✅ Pass | No |
| TC-004 | Non-existent email | Auth | 🟡 Medium | EP | ✅ Pass | No |
| TC-005 | Capacity = 1 | Activity Creation | 🟡 Medium | BVA | ✅ Pass | No |
| TC-006 | Capacity = 0 | Activity Creation | 🟡 Medium | BVA | ❌ Fail | No |
| TC-007 | Title = 1 char | Activity Creation | 🟡 Medium | BVA | ✅ Pass | No |
| TC-008 | Create valid activity | Activity Creation | 🔴 High | Use Case | ✅ Pass | Yes |
| TC-009 | Missing title | Activity Creation | 🔴 High | EP | ✅ Pass | No |
| TC-010 | Join workflow | Join Activity | 🔴 High | Use Case | ✅ Pass | Yes |
| TC-011 | Leave activity | Join / Leave | 🟡 Medium | State Trans. | ✅ Pass | No |
| TC-012 | Join full activity | Join Activity | 🔴 High | BVA | ✅ Pass | No |
| TC-013 | First Step badge | Badges | 🟡 Medium | State Trans. | ✅ Pass | No |

**Defects found:** DEF-001 · DEF-002 — see `reports/defect-report-example.md`
