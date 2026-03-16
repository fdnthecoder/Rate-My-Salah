# Rate My Salah 🕌

<div align="center">

**An Android app for Muslims to rate and track their daily prayer quality**

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-7.0%2B-green.svg)](https://developer.android.com)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-2024.02.00-brightgreen.svg)](https://developer.android.com/jetpack/compose)
[![Room](https://img.shields.io/badge/Room-2.6.1-orange.svg)](https://developer.android.com/training/data-storage/room)

</div>

---

## 📖 About

**Rate My Salah** helps Muslims maintain consistency and quality in their daily prayers by providing a simple way to:
- ✅ Track all 5 daily prayers (Fajr, Dhuhr, Asr, Maghrib, Isha)
- ⭐ Rate each prayer on a scale of 1-10
- 📝 Add notes about your prayer experience
- 📅 View calendar with color-coded prayer completion
- 📊 Analyze statistics and track improvement over time
- 🌙 Support for dark mode

---

## ✨ Features

### 🏠 Home Screen
- See today's date in both Gregorian and Hijri calendar
- Quick access to rate all 5 prayers
- Edit previously logged prayers
- View notes and ratings at a glance

### 📅 Calendar View
- Monthly calendar with color-coded days:
  - 🟢 **Green**: All 5 prayers completed
  - 🟡 **Yellow**: 3-4 prayers completed
  - 🔴 **Red**: 1-2 prayers completed
  - ⚪ **Gray**: No prayers logged
- Navigate between months
- Tap any day to see detailed prayer logs

### 📊 Statistics
- Overall average prayer rating
- Individual prayer averages (Fajr, Dhuhr, Asr, Maghrib, Isha)
- Track your spiritual progress over time

### ⚙️ Settings
- Toggle between light and dark mode
- Persistent theme preference

---

## 🛠️ Tech Stack

- **Language**: Kotlin 1.9.22
- **UI Framework**: Jetpack Compose with Material3
- **Database**: Room 2.6.1 with KSP
- **Architecture**: MVVM (Model-View-ViewModel)
- **Navigation**: Jetpack Navigation Compose
- **Async**: Kotlin Coroutines & Flow
- **Build System**: Gradle 8.5 with Kotlin DSL
- **Min SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)

---

## 📦 Installation

### Download APK
1. Go to [Releases](https://github.com/fdnthecoder/Rate-My-Salah/releases)
2. Download the latest `RateMySalah.apk`
3. Transfer to your Android phone
4. Enable "Install from Unknown Sources" if prompted
5. Install and enjoy!

### Build from Source
```bash
# Clone the repository
git clone https://github.com/fdnthecoder/Rate-My-Salah.git
cd Rate-My-Salah

# Build debug APK
./gradlew assembleDebug

# APK will be at: app/build/outputs/apk/debug/app-debug.apk
```

**For detailed installation instructions, see [INSTALL_ON_PHONE.md](INSTALL_ON_PHONE.md)**

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK with API 34

### Setup
1. **Clone the repository**
   ```bash
   git clone https://github.com/fdnthecoder/Rate-My-Salah.git
   ```

2. **Open in Android Studio**
   - File → Open → Select project directory
   - Wait for Gradle sync to complete

3. **Run the app**
   - Connect an Android device or start an emulator
   - Click Run ▶️ (or press Ctrl+R / Cmd+R)

---

## 📱 Usage Guide

### Rating a Prayer
1. Open the app and go to the **Home** screen
2. Click **"Rate"** button on any prayer card
3. Enter a rating from 1-10
4. Optionally add notes about your prayer experience
5. Click **"Save"**

### Viewing History
1. Go to the **Calendar** tab
2. Browse through months using arrow buttons
3. Click on any day to see prayer details

### Checking Progress
1. Go to the **Stats** tab
2. View your overall average rating
3. See individual prayer averages

### Toggling Dark Mode
1. Go to the **Settings** tab
2. Toggle the dark mode switch
3. Theme will change immediately

---

## 📂 Project Structure

```
app/src/main/java/com/fdnthemuslim/ratemysalah/
├── MainActivity.kt                 # App entry point
├── data/
│   ├── entity/
│   │   ├── SalahLog.kt            # Prayer log entity
│   │   └── AppSettings.kt         # App settings entity
│   ├── dao/
│   │   ├── SalahLogDao.kt         # Prayer log DAO
│   │   └── AppSettingsDao.kt      # Settings DAO
│   ├── converters/
│   │   └── Converters.kt          # Type converters for Room
│   ├── database/
│   │   └── AppDatabase.kt         # Room database
│   └── repository/
│       └── SalahRepository.kt     # Data repository
├── viewmodel/
│   ├── SalahViewModel.kt          # Main ViewModel
│   └── SalahViewModelFactory.kt   # ViewModel factory
├── ui/
│   ├── components/
│   │   ├── SalahCard.kt           # Prayer card component
│   │   ├── RatingDialog.kt        # Rating dialog
│   │   ├── CalendarGrid.kt        # Calendar component
│   │   └── BottomNavBar.kt        # Bottom navigation
│   ├── screens/
│   │   ├── HomeScreen.kt          # Home screen
│   │   ├── CalendarScreen.kt      # Calendar screen
│   │   ├── DayDetailScreen.kt     # Day detail screen
│   │   ├── StatsScreen.kt         # Statistics screen
│   │   └── SettingsScreen.kt      # Settings screen
│   └── theme/                      # Material3 theme
├── navigation/
│   └── NavGraph.kt                # Navigation setup
└── utils/
    ├── Constants.kt               # App constants
    └── DateUtils.kt               # Date utilities
```

---

## 🧪 Testing

Comprehensive testing manual available in [TESTING.md](TESTING.md)

Quick test checklist:
- ✅ Rate prayers and verify persistence
- ✅ Navigate between all screens
- ✅ Check calendar color coding
- ✅ Verify statistics calculations
- ✅ Toggle dark mode
- ✅ Close and reopen app (data persistence)

---

## 🗺️ Roadmap

### Completed ✅
- [x] Core app architecture (MVVM + Room)
- [x] All 5 screens with navigation
- [x] Prayer rating and logging
- [x] Calendar with color coding
- [x] Statistics and averages
- [x] Dark mode support
- [x] Hijri calendar integration

### Planned 🚧
- [ ] Prayer time notifications
- [ ] Export data (CSV/JSON)
- [ ] Backup and restore
- [ ] Widgets for home screen
- [ ] Prayer streak tracking
- [ ] Qibla direction finder
- [ ] Multiple language support (Arabic, Urdu, etc.)
- [ ] Cloud sync

---

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Commit your changes**
   ```bash
   git commit -m 'Add some amazing feature'
   ```
4. **Push to the branch**
   ```bash
   git push origin feature/amazing-feature
   ```
5. **Open a Pull Request**

### Development Guidelines
- Follow Kotlin coding conventions
- Use meaningful commit messages
- Test on multiple Android versions
- Update documentation as needed

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Fdn The Muslim**
- GitHub: [@fdnthecoder](https://github.com/fdnthecoder)
- Repository: [Rate-My-Salah](https://github.com/fdnthecoder/Rate-My-Salah)

---

## 🙏 Acknowledgments

- Allah (SWT) for guidance
- The Muslim community for inspiration
- Jetpack Compose team for amazing tools
- All contributors and testers

---

## 📞 Support

If you encounter any issues or have questions:
1. Check [TESTING.md](TESTING.md) for troubleshooting
2. Check [INSTALL_ON_PHONE.md](INSTALL_ON_PHONE.md) for installation help
3. Open an issue on GitHub
4. Contact via GitHub profile

---

<div align="center">

**May Allah accept our prayers** 🤲

⭐ Star this repo if you find it helpful!

</div>
