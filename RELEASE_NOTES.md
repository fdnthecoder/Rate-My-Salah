# Release Notes - Bug Fix Release

## [v1.0.1] - Task 1 & 2 Complete

### Fixed
- **Prayer Count & Color Logic (Task 1):** The calendar now correctly counts only unique prayers per day (max 5). This ensures that multiple logs for the same prayer (e.g., updating a rating) don't inflate the count and break the color coding.
- **Future Date Blocking (Task 2):** Users are now prevented from logging or editing prayers for future dates. 
    - Future dates in the calendar are visually dimmed and non-clickable.
    - The "Rate" button is disabled on the Day Detail screen for future dates.
    - The ViewModel strictly blocks any save attempts for dates beyond today.

### Technical Changes
- Added unique index on `(date, salahName)` in the database to prevent duplicate entries.
- Updated `SalahViewModel` to reload state immediately after saving, ensuring the UI stays in sync.
- Added comprehensive unit tests for date validation and update logic.
