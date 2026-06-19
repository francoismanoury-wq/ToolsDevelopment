# Defect Report — Activo Web Application
## Lab 3: Test Design and Test Management

**Team:** AFGANP  
**Date:** 2026-06-19

---

## Defect #1

| Field | Value |
|-------|-------|
| **Defect ID** | DEF-001 |
| **Title** | Joining a full activity returns a raw API error string instead of a user-friendly message |
| **Related Test Case** | TC-012 |
| **Reported by** | AFGANP |
| **Date Found** | 2026-06-19 |
| **Severity** | Medium |
| **Priority** | High |
| **Status** | Confirmed (reproduced via API) |
| **Environment** | API http://localhost:8000, Docker Compose stack |

### Description

When a user tries to join an activity that has already reached its maximum capacity, the backend returns `{"detail": "Activity is full"}` (HTTP 400). The frontend forwards this raw detail string directly to the toast notification instead of a human-readable message.

Instead of seeing something like *"Sorry, this activity is full"*, the user sees a toast notification containing the raw JSON string `Activity is full` with no context or guidance.

### Steps to Reproduce

1. Create an activity with `max_participants = 1` (use account `nathan@activo.app`)
2. Log in as `maria@activo.app`, navigate to that activity detail page
3. Click "Join Activity"

### Expected Result

A friendly error message is shown in the toast notification:  
*"This activity is full. You cannot join at this time."*

### Actual Result

The toast notification displays the raw API response detail:  
`Activity is full`  
No further guidance is shown to the user.

### Root Cause (Hypothesis)

The frontend activity store (`stores/activities.js`) catches the API error but forwards the raw `error.response.data.detail` string directly to the toast component without mapping it to a user-friendly message.

### Suggested Fix

In the catch block of the join action in `frontend/src/stores/activities.js`, map known error detail strings to UI messages:

```js
// Before (current behaviour)
toast.error(error.response.data.detail)

// After (proposed fix)
const knownErrors = {
  'Activity is full': 'This activity is full. You cannot join at this time.',
  'Already joined': 'You have already joined this activity.',
}
const msg = knownErrors[error.response?.data?.detail] ?? 'Something went wrong. Please try again.'
toast.error(msg)
```

---

## Defect #2

| Field | Value |
|-------|-------|
| **Defect ID** | DEF-002 |
| **Title** | Backend accepts max_participants=0 — no validation that capacity must be at least 1 |
| **Related Test Case** | TC-006 |
| **Reported by** | AFGANP |
| **Date Found** | 2026-06-19 |
| **Severity** | Medium |
| **Priority** | High |
| **Status** | Confirmed (TC-006 failed — HTTP 200 returned for capacity=0) |
| **Environment** | API http://localhost:8000, Docker Compose stack |

### Description

The backend API accepts `max_participants=0` and creates the activity successfully (HTTP 200). There is no server-side Pydantic validation that capacity must be >= 1. Additionally, the frontend Create Activity form does not validate the capacity field either, so a user can submit 0 and no error is shown at any layer.

### Steps to Reproduce

1. Log in as `nathan@activo.app`
2. Send `POST /activities` with body: `{"title": "Test", "category": "sport", "date": "...", "max_participants": 0, "location": "Test"}`
3. Observe response: HTTP 200, activity created with `max_participants=0`

### Expected Result

Backend should return HTTP 422 with a validation error:  
`{"detail": [{"msg": "Input should be greater than or equal to 1", "loc": ["body", "max_participants"]}]}`

### Actual Result

HTTP 200. Activity created with `max_participants=0`, making it permanently un-joinable (any join attempt will immediately return "Activity is full").

### Root Cause

Two-layer failure:
1. **Backend** — the Pydantic schema for `ActivityCreate` (in `backend/app/schemas.py`) defines `max_participants` as `Optional[int]` with no `ge=1` constraint.
2. **Frontend** — the capacity input has no `min="1"` HTML attribute and no Vue-side validator.

### Suggested Fix

**Backend** (`backend/app/schemas.py`) — add a Pydantic field constraint:

```python
from pydantic import BaseModel, Field
class ActivityCreate(BaseModel):
    max_participants: Optional[int] = Field(None, ge=1, description="Must be at least 1 if set")
```

**Frontend** (`CreateActivityView.vue`) — add HTML and Vue validation:

```html
<input type="number" v-model="form.max_participants" min="1" />
<p v-if="form.max_participants !== null && form.max_participants < 1" class="text-red-500 text-sm">
  Capacity must be at least 1.
</p>
```

---

## Defect Severity Guide

| Severity | Definition |
|----------|-----------|
| **Critical** | Application crashes or data is corrupted; no workaround |
| **High** | Core feature is broken; workaround is painful |
| **Medium** | Feature partially works; workaround exists |
| **Low** | Minor UX issue or cosmetic defect |
