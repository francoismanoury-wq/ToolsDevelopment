
# Project Selection

## Project name
Activo

## Project URL
https://github.com/JesusAlejandroTM/Activo

## Application type
Web (self-hosted) + API sandbox (JSONPlaceholder)

## Main functionality
Activo is an asset management web application. It allows users to manage
physical assets, assign them to people, track their location and status.

## Features selected for testing
1. User Login – valid credentials, invalid credentials, empty fields
2. Asset Creation – create a new asset, missing required fields, duplicate names
3. Asset Search / Filter – search by name, category, status
4. Asset Assignment – assign an asset to a user, re-assign, unassign
5. Asset Status Update – change status (active/inactive/maintenance)

## Manual testing possibilities
- Login form: positive and negative scenarios
- Asset form validation: required fields, invalid data
- Search: exact match, partial match, no results
- Role-based access: admin vs standard user

## Automation possibilities
- Selenium (Lab 4): Login flow, asset creation form, search
- Robot Framework (Lab 5): End-to-end asset lifecycle
- Postman (Lab 6): JSONPlaceholder – GET/POST/PUT/DELETE + status codes

## Risks
- Activo requires local setup – teammates may not run it easily
- No public API – using JSONPlaceholder for Lab 6
- UI may change if repo is updated – Selenium selectors could break
- Dynamic element IDs may make Selenium locators unstable