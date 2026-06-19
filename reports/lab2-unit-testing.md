# Lab 2 — Unit Testing Report

**Team:** AFGANP | **Date:** 2026-06-18 | **Tool:** JUnit 5.10.2 + IntelliJ IDEA Coverage Runner

---

## 1. Tests Written

| # | Test Name | Type | Expected Result | Status |
|---|-----------|------|-----------------|--------|
| 1 | `shouldReturnAFor93Points` | Normal | A | ✅ Pass |
| 2 | `shouldReturnAFor100Points` | Normal | A | ✅ Pass |
| 3 | `shouldReturnBFor85Points` | Boundary | B | ✅ Pass |
| 4 | `shouldReturnBFor92Points` | Boundary | B | ✅ Pass |
| 5 | `shouldReturnEFor61Points` | Boundary | E | ✅ Pass |
| 6 | `shouldReturnFXFor60Points` | Boundary | FX | ✅ Pass |
| 7 | `shouldReturnFXFor0Points` | Normal | FX | ✅ Pass |
| 8 | `shouldThrowExceptionForNegativePoints` | Invalid input | Exception | ✅ Pass |
| 9 | `shouldThrowExceptionForMoreThan100Points` | Invalid input | Exception | ✅ Pass |

> **Total: 9 tests — all passing ✅**

---

## 2. Demonstrated Failing Test

To prove the test suite detects regressions, `>= 85` was temporarily changed to `> 85` in `Rating.java`:

```java
// Before (correct)
if (points >= 85) return "B";

// After (deliberately broken)
if (points > 85) return "B";
```

**Result:** `shouldReturnBFor85Points` failed immediately:

| | Value |
|---|---|
| Expected | `B` |
| Actual | `C` |

> This confirms the tests correctly detect off-by-one boundary errors.

---

## 3. Coverage Results

| Metric | Coverage |
|--------|---------|
| Class | 100% |
| Method | 100% |
| Line | 100% |
| Branch | 85% (12 / 14) |

---

## 4. Coverage Interpretation

**What coverage proves:**
- All branches of the grading method were executed at least once
- Both exception paths (points = −1 and points = 101) were triggered

**What coverage does NOT prove:**
- That boundary thresholds (85, 92, etc.) are correctly defined
- That the method meets the real business grading requirements
- Security, usability, or performance properties

**Missing test idea:**  
Testing `Integer.MIN_VALUE` would verify that very large negative numbers are also rejected, improving branch coverage from 85% to 100%.
