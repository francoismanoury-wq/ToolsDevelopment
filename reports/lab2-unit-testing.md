# Lab 2 - Unit Testing Report

**Team:** AFGANP  
**Date:** 2026-06-18  
**Tool:** JUnit 5.10.2 + IntelliJ IDEA Coverage Runner

## 1. Tests written

| # | Test name | Type | Expected result |
|---|-----------|------|-----------------|
| 1 | shouldReturnAFor93Points | Normal | A |
| 2 | shouldReturnAFor100Points | Normal | A |
| 3 | shouldReturnBFor85Points | Boundary | B |
| 4 | shouldReturnBFor92Points | Boundary | B |
| 5 | shouldReturnEFor61Points | Boundary | E |
| 6 | shouldReturnFXFor60Points | Boundary | FX |
| 7 | shouldReturnFXFor0Points | Normal | FX |
| 8 | shouldThrowExceptionForNegativePoints | Invalid input | Exception |
| 9 | shouldThrowExceptionForMoreThan100Points | Invalid input | Exception |

**Total: 9 tests — all passing ✅**

## 2. Demonstrated failing test

Changed `>= 85` to `> 85` in Rating.java temporarily.  
Result: `shouldReturnBFor85Points` failed with:
- Expected: B
- Actual: C

This confirms the tests correctly detect boundary errors.

## 3. Coverage results

- Class: 100%
- Method: 100%
- Line: 100%
- Branch: 85% (12/14)

## 4. Coverage interpretation

**What coverage proves:**
- All branches of the method were executed at least once
- Both exception paths (-1 and 101) were triggered

**What coverage does NOT prove:**
- That boundary values are correctly defined
- That the method meets real business requirements
- Security, usability or performance

**Missing test idea:**  
Testing `Integer.MIN_VALUE` would verify very large
negative numbers are also rejected.