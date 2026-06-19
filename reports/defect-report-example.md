# Defect Report — Activo Web Application

**Lab 3: Test Design and Test Management**  
**Team:** AFGANP | **Date:** 2026-06-19

---

## Defect Index

| ID | Title | Severity | Priority | Status |
|----|-------|----------|----------|--------|
| [DEF-001](#def-001) | Raw API error string shown to user on full activity join | 🟡 Medium | 🔴 High | Open |
| [DEF-002](#def-002) | Backend accepts `max_participants=0` — no capacity validation | 🟡 Medium | 🔴 High | Open |

---

## DEF-001

**Title:** Joining a full activity shows raw API error string instead of a user-friendly message  
**Related test case:** TC-012  
**Found by:** AFGANP — 2026-06-19

| Field | Value |
|-------|-------|
| **Severity** | 🟡 Medium |
| **Priority** | 🔴 High |
| **Status** | Open |
| **Environment** | API http://localhost:8000 · Docker Compose |

### Description

When a user tries to join an activity that is already at maximum capacity, the backend returns:

```json
{"detail": "Activity is full"}
```

The frontend forwards this raw `detail` string directly into the toast notification, instead of mapping it to a readable user message. The user sees `Activity is full` with no further context or call to action.

### Steps to Reproduce

1. Create an activity with `max_participants = 1` (as `nathan@activo.app`)
2. Log in as `maria@activo.app`
3. Navigate to that activity's detail page
4. Click **"Join Activity"**

### Expected Result

> "This activity is full. You cannot join at this time."

### Actual Result

Toast notification displays the raw API value: `Activity is full`

### Root Cause

In `frontend/src/stores/activities.js`, the catch block forwards `error.response.data.detail` directly to the toast without mapping it to a localised message.

### Suggested Fix

```js
// Before
toast.error(error.response.data.detail)

// After
const knownErrors = {
  'Activity is full':  'This activity is full. You cannot join at this time.',
  'Already joined':    'You have already joined this activity.',
}
const msg = knownErrors[error.response?.data?.detail] ?? 'Something went wrong. Please try again.'
toast.error(msg)
```

---

## DEF-002

**Title:** Backend accepts `max_participants = 0` — no Pydantic validation for minimum capacity  
**Related test case:** TC-006  
**Found by:** AFGANP — 2026-06-19

| Field | Value |
|-------|-------|
| **Severity** | 🟡 Medium |
| **Priority** | 🔴 High |
| **Status** | Open |
| **Environment** | API http://localhost:8000 · Docker Compose |

### Description

Sending `POST /activities` with `max_participants=0` returns HTTP **200** and creates the activity. There is no server-side constraint enforcing `max_participants >= 1`. An activity with `max_participants=0` is permanently un-joinable — any join attempt immediately returns "Activity is full", with no way for users to know the activity was misconfigured.

### Steps to Reproduce

```http
POST /activities
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Zero Cap Test",
  "category": "sport",
  "date": "2026-07-01T09:00:00",
  "max_participants": 0,
  "location": "Test"
}
```

**Response:** HTTP 200 — activity created.

### Expected Result

HTTP 422 with validation error:

```json
{
  "detail": [{"msg": "Input should be greater than or equal to 1", "loc": ["body", "max_participants"]}]
}
```

### Root Cause

Two-layer failure:

1. **Backend** — `ActivityCreate` schema in `backend/app/schemas.py` defines `max_participants` as `Optional[int]` with no `ge=1` constraint.
2. **Frontend** — the capacity `<input type="number">` in `CreateActivityView.vue` has no `min="1"` attribute and no Vue-side validator.

### Suggested Fix

**Backend** (`backend/app/schemas.py`):

```python
from pydantic import BaseModel, Field
from typing import Optional

class ActivityCreate(BaseModel):
    max_participants: Optional[int] = Field(None, ge=1)
```

**Frontend** (`CreateActivityView.vue`):

```html
<input type="number" v-model="form.max_participants" min="1" />
<p v-if="form.max_participants !== null && form.max_participants < 1"
   class="text-red-500 text-sm">
  Capacity must be at least 1.
</p>
```

---

## Severity Guide

| Level | Meaning |
|-------|---------|
| 🔴 Critical | App crashes or data is corrupted; no workaround |
| 🟠 High | Core feature is broken; workaround is difficult |
| 🟡 Medium | Feature partially works; workaround exists |
| 🟢 Low | Minor UX or cosmetic issue |
