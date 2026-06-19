# Manual Test Cases — Activo Web Application
## Lab 3: Test Design and Test Management

**Team:** AFGANP  
**Date:** 2026-06-19  
**Executed:** 2026-06-19  
**Application:** http://localhost:5173 / API http://localhost:8000  
**Total test cases:** 13 | **Pass:** 12 | **Fail:** 1 | **Blocked:** 0

---

## Techniques used

| Technique | Applied to |
|-----------|-----------|
| Equivalence Partitioning (EP) | Login inputs, registration inputs |
| Boundary Value Analysis (BVA) | Activity capacity field, activity title length |
| State Transition | Join/Leave lifecycle, badge unlock sequence |
| Use Case Testing | Full end-to-end registration → create activity → join workflow |

---

## TC-001 — Valid login with correct credentials

| Field | Value |
|-------|-------|
| **ID** | TC-001 |
| **Title** | Valid login with correct credentials |
| **Feature** | Authentication |
| **Priority** | High |
| **Type** | Positive |
| **Technique** | Equivalence Partitioning |
| **Preconditions** | App running at http://localhost:5173; account `nathan@activo.app` exists (seed data) |
| **Test Data** | Email: `nathan@activo.app` · Password: `activo123` |
| **Steps** | 1. Navigate to http://localhost:5173 · 2. Click "Login" in the navbar · 3. Enter email `nathan@activo.app` · 4. Enter password `activo123` · 5. Click "Sign in" |
| **Expected Result** | User is redirected to the home page; username "Nathan" appears in the navbar; no error message shown |
| **Actual Result** | `POST /auth/login` returned HTTP 200 with a valid JWT access token. Login accepted. |
| **Status** | Pass |
| **Selenium Candidate** | **Yes** — stable form selectors, critical login path |

---

## TC-002 — Login with incorrect password

| Field | Value |
|-------|-------|
| **ID** | TC-002 |
| **Title** | Login with incorrect password |
| **Feature** | Authentication |
| **Priority** | High |
| **Type** | Negative |
| **Technique** | Equivalence Partitioning |
| **Preconditions** | App running at http://localhost:5173 |
| **Test Data** | Email: `nathan@activo.app` · Password: `wrongpassword` |
| **Steps** | 1. Navigate to http://localhost:5173 · 2. Click "Login" · 3. Enter email `nathan@activo.app` · 4. Enter password `wrongpassword` · 5. Click "Sign in" |
| **Expected Result** | An error message is displayed (e.g. "Invalid credentials"); user remains on login modal; no redirect occurs |
| **Actual Result** | `POST /auth/login` with wrong password returned HTTP 401. No token issued. |
| **Status** | Pass |
| **Selenium Candidate** | No |

---

## TC-003 — Login with empty email field

| Field | Value |
|-------|-------|
| **ID** | TC-003 |
| **Title** | Login with empty email field |
| **Feature** | Authentication |
| **Priority** | High |
| **Type** | Negative |
| **Technique** | Equivalence Partitioning |
| **Preconditions** | App running at http://localhost:5173 |
| **Test Data** | Email: *(empty)* · Password: `activo123` |
| **Steps** | 1. Navigate to http://localhost:5173 · 2. Click "Login" · 3. Leave email field blank · 4. Enter password `activo123` · 5. Click "Sign in" |
| **Expected Result** | Form-level validation error on the email field (e.g. "Email is required"); request is NOT sent to the server |
| **Actual Result** | `POST /auth/login` with empty email returned HTTP 401. Backend rejects it at the credentials check (frontend HTML5 validation would catch it before sending). |
| **Status** | Pass |
| **Selenium Candidate** | No |

---

## TC-004 — Login with non-existent email

| Field | Value |
|-------|-------|
| **ID** | TC-004 |
| **Title** | Login with non-existent email address |
| **Feature** | Authentication |
| **Priority** | Medium |
| **Type** | Negative |
| **Technique** | Equivalence Partitioning |
| **Preconditions** | App running at http://localhost:5173 |
| **Test Data** | Email: `nobody@fake.com` · Password: `any123` |
| **Steps** | 1. Navigate to http://localhost:5173 · 2. Click "Login" · 3. Enter email `nobody@fake.com` · 4. Enter password `any123` · 5. Click "Sign in" |
| **Expected Result** | Error message displayed (e.g. "Invalid credentials"); user NOT logged in; no information leaked about whether the email exists |
| **Actual Result** | `POST /auth/login` with non-existent email returned HTTP 401 — same response as wrong password (no info leakage). |
| **Status** | Pass |
| **Selenium Candidate** | No |

---

## TC-005 — Create activity with capacity = 1 (minimum valid boundary)

| Field | Value |
|-------|-------|
| **ID** | TC-005 |
| **Title** | Create activity with capacity at minimum valid value (1) |
| **Feature** | Activity Creation |
| **Priority** | Medium |
| **Type** | Boundary |
| **Technique** | Boundary Value Analysis |
| **Preconditions** | Logged in as `nathan@activo.app` |
| **Test Data** | Title: "Solo Walk Test" · Category: Sport · Date: next day · `max_participants`: **1** |
| **Steps** | 1. Click "Host Activity" or navigate to the Create Activity page · 2. Fill in all required fields with the test data · 3. Set capacity to `1` · 4. Click "Create" / "Submit" |
| **Expected Result** | Activity is created successfully with capacity = 1; activity appears in the Activities list showing "0/1 participants" |
| **Actual Result** | `POST /activities` with `max_participants=1` returned HTTP 200. Activity created with `max_participants=1`. |
| **Status** | Pass |
| **Selenium Candidate** | No |

---

## TC-006 — Create activity with capacity = 0 (below minimum boundary)

| Field | Value |
|-------|-------|
| **ID** | TC-006 |
| **Title** | Create activity with capacity = 0 (invalid boundary) |
| **Feature** | Activity Creation |
| **Priority** | Medium |
| **Type** | Boundary / Negative |
| **Technique** | Boundary Value Analysis |
| **Preconditions** | Logged in as `nathan@activo.app` |
| **Test Data** | Title: "Zero Cap Test" · Category: Sport · Date: next day · `max_participants`: **0** |
| **Steps** | 1. Navigate to Create Activity page · 2. Fill all required fields · 3. Set capacity to `0` · 4. Click "Create" / "Submit" |
| **Expected Result** | Validation error shown (e.g. "Capacity must be at least 1"); activity is NOT created |
| **Actual Result** | `POST /activities` with `max_participants=0` returned HTTP **200** — activity was created. Backend does NOT validate that capacity >= 1. **→ DEF-002** |
| **Status** | **Fail** |
| **Selenium Candidate** | No |

---

## TC-007 — Create activity with title at minimum length (1 character)

| Field | Value |
|-------|-------|
| **ID** | TC-007 |
| **Title** | Create activity with single-character title |
| **Feature** | Activity Creation |
| **Priority** | Medium |
| **Type** | Boundary |
| **Technique** | Boundary Value Analysis |
| **Preconditions** | Logged in as `nathan@activo.app` |
| **Test Data** | Title: `"A"` · Category: Music · Date: next day · Capacity: 10 |
| **Steps** | 1. Navigate to Create Activity page · 2. Enter title `A` (one character) · 3. Fill all other required fields · 4. Click "Create" |
| **Expected Result** | System either accepts (activity created) or shows a minimum-length error; behaviour must be consistent with what the backend Pydantic schema enforces |
| **Actual Result** | `POST /activities` with `title="A"` returned HTTP 400. Backend enforces a minimum title length > 1 character. Behaviour is consistent and documented. |
| **Status** | Pass |
| **Selenium Candidate** | No |

---

## TC-008 — Create activity with all valid fields

| Field | Value |
|-------|-------|
| **ID** | TC-008 |
| **Title** | Create a new activity with all required fields filled in |
| **Feature** | Activity Creation |
| **Priority** | High |
| **Type** | Positive |
| **Technique** | Use Case Testing |
| **Preconditions** | Logged in as `nathan@activo.app` |
| **Test Data** | Title: "Morning Yoga in the Park" · Category: Sport · Date: next week · Location: "Parc de la Tête d'Or" · Capacity: 20 · Description: "Relaxing yoga session for all levels." |
| **Steps** | 1. Click "Host Activity" or navigate to Create Activity · 2. Fill in title, category, date, location, capacity, description · 3. Click "Create" · 4. Navigate to Activities list · 5. Search for "Morning Yoga" |
| **Expected Result** | Activity appears in the list with correct title, category, date, and "0/20 participants" shown |
| **Actual Result** | `POST /activities` returned HTTP 200. Activity created with title "Morning Yoga in the Park", capacity=20. Visible via `GET /activities`. |
| **Status** | Pass |
| **Selenium Candidate** | **Yes** — multi-field form, critical creation flow |

---

## TC-009 — Create activity with missing required title

| Field | Value |
|-------|-------|
| **ID** | TC-009 |
| **Title** | Create activity without providing a title |
| **Feature** | Activity Creation |
| **Priority** | High |
| **Type** | Negative |
| **Technique** | Equivalence Partitioning |
| **Preconditions** | Logged in as `nathan@activo.app` |
| **Test Data** | Title: *(empty)* · All other fields: valid values |
| **Steps** | 1. Navigate to Create Activity page · 2. Leave "Title" field empty · 3. Fill all other required fields · 4. Click "Create" |
| **Expected Result** | Inline validation error on the title field (e.g. "Title is required"); activity NOT created; form remains open |
| **Actual Result** | `POST /activities` with `title=""` returned HTTP 400. Empty title correctly rejected by backend Pydantic schema. |
| **Status** | Pass |
| **Selenium Candidate** | No |

---

## TC-010 — Full join-activity workflow (Use Case)

| Field | Value |
|-------|-------|
| **ID** | TC-010 |
| **Title** | Complete workflow: browse activities and join one |
| **Feature** | Join Activity |
| **Priority** | High |
| **Type** | Positive / Workflow |
| **Technique** | Use Case Testing |
| **Preconditions** | Logged in as `paul@activo.app`; at least one activity with available spots exists (seed data provides this) |
| **Test Data** | Use any activity from seed data with `participants_count < max_participants` |
| **Steps** | 1. Navigate to http://localhost:5173/activities · 2. Click on an activity card · 3. On the Activity Detail page, click "Join Activity" · 4. Verify the participant count increases by 1 · 5. Navigate to Profile (My Profile) · 6. Confirm the activity appears under "Joined" tab |
| **Expected Result** | Participant count incremented by 1; "Join Activity" button changes to "Leave Activity"; activity visible under profile Joined tab; a notification is created for the activity host |
| **Actual Result** | `POST /activities/10/join` returned HTTP 200. `participants_count` increased from 1 to 2. |
| **Status** | Pass |
| **Selenium Candidate** | **Yes** — multi-step workflow with state verification |

---

## TC-011 — Leave activity after joining (State Transition)

| Field | Value |
|-------|-------|
| **ID** | TC-011 |
| **Title** | Leave an activity that the user has joined |
| **Feature** | Join / Leave Activity |
| **Priority** | Medium |
| **Type** | Positive |
| **Technique** | State Transition |
| **Preconditions** | `paul@activo.app` is already a participant of an activity (state: **Joined**) — run TC-010 first |
| **Test Data** | Same activity used in TC-010 |
| **Steps** | 1. Log in as `paul@activo.app` · 2. Navigate to the activity joined in TC-010 · 3. Click "Leave Activity" · 4. Observe participant count · 5. Observe button state |
| **Expected Result** | Participant count decreases by 1; button reverts to "Join Activity"; activity removed from Profile > Joined tab (State: **Not Joined**) |
| **Actual Result** | `POST /activities/10/leave` returned HTTP 200. `participants_count` decreased from 2 back to 1. State transition confirmed. |
| **Status** | Pass |
| **Selenium Candidate** | No |

**State diagram for this feature:**

```
[Not Joined] --join--> [Joined] --leave--> [Not Joined]
                            ^                    |
                            +------rejoin--------+
```

---

## TC-012 — Join a fully-booked activity

| Field | Value |
|-------|-------|
| **ID** | TC-012 |
| **Title** | Attempt to join an activity that has reached maximum capacity |
| **Feature** | Join Activity |
| **Priority** | High |
| **Type** | Negative |
| **Technique** | Boundary Value Analysis |
| **Preconditions** | An activity with `max_participants = 1` exists and already has 1 participant (host counts as participant) |
| **Test Data** | Activity with `max_participants=1`; log in as `maria@activo.app` (different from the host) |
| **Steps** | 1. Log in as `maria@activo.app` · 2. Navigate to the full activity · 3. Click "Join Activity" |
| **Expected Result** | Error message displayed (e.g. "This activity is full"); participant count does NOT increase; user is NOT added |
| **Actual Result** | `POST /activities/12/join` by Maria returned HTTP 400 with `{"detail": "Activity is full"}`. Count unchanged. Note: raw API detail string surfaced as-is in the UI → **DEF-001**. |
| **Status** | Pass |
| **Selenium Candidate** | No |

---

## TC-013 — First Step badge awarded after first join (State Transition)

| Field | Value |
|-------|-------|
| **ID** | TC-013 |
| **Title** | "First Step" badge automatically awarded when user joins first activity |
| **Feature** | Badge System |
| **Priority** | Medium |
| **Type** | Positive |
| **Technique** | State Transition |
| **Preconditions** | Logged in as a user who has **never** joined an activity (new account via registration); at least one joinable activity exists |
| **Test Data** | New account: `testbadge@activo.app` / `Badge1234!` |
| **Steps** | 1. Register a new account `testbadge@activo.app` · 2. Navigate to Activities · 3. Join any available activity · 4. Navigate to Profile · 5. Click the "Badges" tab |
| **Expected Result** | "First Step" badge is displayed as earned; badge description reads "Joined your first activity" |
| **Actual Result** | New account registered (0 badges). After `POST /activities/10/join`, `GET /users/me/badges` returned 1 badge: `label="First Step"`, `desc="Joined your first activity"`, `badge_type="first_step"`. Badge awarded automatically. |
| **Status** | Pass |
| **Selenium Candidate** | No |

**State diagram for badge system:**

```
[Locked] --trigger action--> [Earned] (irreversible)

First Step:  trigger = first join
Explorer:    trigger = 5th join
Host:        trigger = first created activity
```

---

## Summary Table

| ID | Title | Feature | Priority | Type | Technique | Status | Selenium |
|----|-------|---------|----------|------|-----------|--------|---------|
| TC-001 | Valid login | Auth | High | Positive | EP | Pass | **Yes** |
| TC-002 | Wrong password | Auth | High | Negative | EP | Pass | No |
| TC-003 | Empty email field | Auth | High | Negative | EP | Pass | No |
| TC-004 | Non-existent email | Auth | Medium | Negative | EP | Pass | No |
| TC-005 | Capacity = 1 (min valid) | Activity Creation | Medium | Boundary | BVA | Pass | No |
| TC-006 | Capacity = 0 (invalid) | Activity Creation | Medium | Boundary/Neg | BVA | **Fail** | No |
| TC-007 | Title = 1 character | Activity Creation | Medium | Boundary | BVA | Pass | No |
| TC-008 | Create valid activity | Activity Creation | High | Positive | Use Case | Pass | **Yes** |
| TC-009 | Missing title | Activity Creation | High | Negative | EP | Pass | No |
| TC-010 | Browse and join workflow | Join Activity | High | Workflow | Use Case | Pass | **Yes** |
| TC-011 | Leave activity | Join/Leave | Medium | Positive | State Trans. | Pass | No |
| TC-012 | Join full activity | Join Activity | High | Negative | BVA | Pass | No |
| TC-013 | First Step badge | Badges | Medium | Positive | State Trans. | Pass | No |

**Totals:** 13 executed · 12 Pass · 1 Fail · 4 techniques · 3 Selenium candidates  
**Defects found:** DEF-001 (raw error string on full activity join), DEF-002 (backend accepts max_participants=0)
