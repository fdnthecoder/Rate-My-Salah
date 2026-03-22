# Release Notes - Rate My Salah

## v0.4.0 (2026-03-21) - Islamic Day Boundary & User Settings
- **Islamic Day Logic**: Implemented "Islamic day" logic where the day transitions at a configurable time (default 8 PM CT).
- **Customizable Day Start**: Added a setting for users to adjust when the Islamic day begins using a 24-hour slider.
- **Dynamic UI**: HomeScreen now displays the current Islamic day date and the effective start time at the top.
- **Improved Reactivity**: ViewModel now correctly reloads data whenever the day start time is changed.
- **Unit Testing**: Added `DateUtilsTest` to verify day boundary calculations and updated `SalahViewModelTest` to align with the new logic.

## v0.3.0 (2026-03-21) - UI State & Data Integrity
- **Instant UI Updates**: Fixed an issue where the SalahCard button label ("Rate" vs "Edit") would not update immediately after saving.
- **Data Constraints**: Enforced unique logs per prayer per day at the database level using a unique index.
- **ViewModel Refresh**: Enhanced ViewModel to refresh all relevant state flows (today, selected date, month) after any save or delete operation.
- **Mock Repository**: Improved `FakeSalahRepository` to better simulate real database behavior in tests.

## v0.2.0 (2026-03-21) - Calendar & Month View
- **Calendar Screen**: Added a monthly calendar view to visualize prayer consistency.
- **Consistency Indicators**: Implemented color-coded indicators (Green, Yellow, Red) on the calendar based on the number of prayers logged per day.
- **Day Details**: Enabled navigating to a specific day from the calendar to view or edit logs.

## v0.1.0 (2026-03-21) - MVP Release
- **Core Functionality**: Ability to rate the five daily prayers on a scale of 1-10 with optional notes.
- **Home Screen**: View today's prayer status at a glance.
- **Local Storage**: All data is stored locally using Room database.
- **Statistics**: Basic statistics showing average ratings per prayer.
- **Settings**: Initial support for Dark Mode.
