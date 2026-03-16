# Rate My Salah - Testing & Installation Manual

## Prerequisites

### Required Software
1. **Android Studio** (Latest version - Hedgehog 2023.1.1 or newer)
   - Download from: https://developer.android.com/studio
   
2. **Java Development Kit (JDK) 17**
   - Usually bundled with Android Studio
   - Verify: `java -version` should show 17.x

3. **Git** (Already installed on your Mac)

---

## Option 1: Open Project in Android Studio (Recommended)

### Step 1: Open the Project
```bash
# Navigate to project directory
cd "/Users/fdnthebest/Desktop/Projects/Rate My Salah"

# Open in Android Studio (from command line)
open -a "Android Studio" .
```

**OR** in Android Studio:
1. Click **File → Open**
2. Navigate to `/Users/fdnthebest/Desktop/Projects/Rate My Salah`
3. Click **Open**

### Step 2: Wait for Gradle Sync
- Android Studio will automatically sync Gradle dependencies
- This may take 2-5 minutes on first run
- Watch the bottom status bar for "Gradle Build" progress
- **If sync fails**: Click "Sync Project with Gradle Files" button (🐘 icon in toolbar)

### Step 3: Setup Android Emulator

#### Create a New Emulator (if you don't have one):
1. Click **Tools → Device Manager** (or AVD Manager icon in toolbar)
2. Click **Create Device**
3. Choose a device: **Pixel 6** or **Pixel 5** (recommended)
4. Click **Next**
5. Select System Image: **API 34 (Android 14)** or **API 33 (Android 13)**
   - If not downloaded, click the download link next to it
6. Click **Next** → **Finish**

### Step 4: Run the App

#### Option A: Using the Run Button
1. Make sure your emulator is selected in the device dropdown (top toolbar)
2. Click the **Run** button (▶️ green play icon)
3. OR press **⌃R** (Control+R)

#### Option B: Using Menu
1. Click **Run → Run 'app'**
2. Select your emulator/device
3. Click **OK**

### Step 5: Wait for App to Launch
- Emulator will boot up (30-60 seconds for first boot)
- App will install and launch automatically
- You should see the **Home Screen** with today's prayers

---

## Option 2: Build from Command Line

### Step 1: Verify Gradle
```bash
cd "/Users/fdnthebest/Desktop/Projects/Rate My Salah"
./gradlew --version
```

Expected output: Gradle 8.2, Kotlin, etc.

### Step 2: Clean and Build
```bash
./gradlew clean build
```

This compiles the app and creates an APK. It will take 2-5 minutes.

**Expected output:**
```
BUILD SUCCESSFUL in 2m 30s
```

**If you see errors about missing wrapper**, the issue is now fixed!

### Step 3: Install on Emulator/Device

#### Start Emulator (if not running):
```bash
# List available emulators
~/Library/Android/sdk/emulator/emulator -list-avds

# Start an emulator (replace with your emulator name)
~/Library/Android/sdk/emulator/emulator -avd Pixel_6_API_34 &
```

#### Install the APK:
```bash
# Build and install
./gradlew installDebug

# OR build APK only
./gradlew assembleDebug
# APK will be at: app/build/outputs/apk/debug/app-debug.apk
```

#### Launch the App:
```bash
# Find the package
~/Library/Android/sdk/platform-tools/adb shell pm list packages | grep ratemysalah

# Launch
~/Library/Android/sdk/platform-tools/adb shell am start -n com.fdnthemuslim.ratemysalah/.MainActivity
```

---

## Complete Testing Checklist

### ✅ Basic Functionality Tests

#### 1. App Launch
- [ ] App opens without crashing
- [ ] Home screen displays
- [ ] Today's date shows (both Gregorian and Hijri)
- [ ] All 5 prayer cards visible (Fajr, Dhuhr, Asr, Maghrib, Isha)

#### 2. Rating a Prayer (Home Screen)
- [ ] Click **"Rate"** button on Fajr card
- [ ] Dialog opens with title "Rate Fajr"
- [ ] Enter rating: **8**
- [ ] Enter notes: **"Good focus today"**
- [ ] Click **"Save"**
- [ ] Dialog closes
- [ ] Card now shows "Rating: 8/10"
- [ ] Notes appear below rating
- [ ] Button changes from "Rate" to "Edit"

#### 3. Editing a Rating
- [ ] Click **"Edit"** button on Fajr
- [ ] Dialog shows previous rating (8) and notes
- [ ] Change rating to **9**
- [ ] Click **"Save"**
- [ ] Card updates to "Rating: 9/10"

#### 4. Navigation - Bottom Nav Bar
- [ ] Click **Calendar** icon - Calendar screen opens
- [ ] Click **Stats** icon - Statistics screen opens
- [ ] Click **Settings** icon - Settings screen opens
- [ ] Click **Home** icon - Returns to home screen
- [ ] All previously entered ratings still visible

#### 5. Calendar Screen
- [ ] Current month displays (e.g., "March 2026")
- [ ] Days of week headers show (Sun, Mon, Tue, Wed, Thu, Fri, Sat)
- [ ] Today's date is highlighted (bold)
- [ ] Today's date shows count (e.g., "1" if you rated 1 prayer)
- [ ] Legend shows at bottom with color explanations
- [ ] Click **left arrow** - Previous month displays
- [ ] Click **right arrow** - Next month displays
- [ ] Return to current month

#### 6. Navigate to Day Detail
- [ ] On calendar, click on **today's date**
- [ ] DayDetailScreen opens
- [ ] Shows correct date at top
- [ ] Shows Hijri date
- [ ] Shows all 5 prayer cards
- [ ] Fajr card shows the rating you entered earlier
- [ ] Back arrow visible in top left

#### 7. Rate Another Prayer (Day Detail)
- [ ] Click **"Rate"** on Dhuhr card
- [ ] Enter rating: **7**
- [ ] Leave notes empty
- [ ] Click **"Save"**
- [ ] Card updates with rating
- [ ] Click **back arrow** - Returns to calendar
- [ ] Today's date now shows **"2"** (2 prayers rated)

#### 8. Calendar Color Coding
Navigate to calendar and verify colors:
- [ ] Gray background = 0 prayers
- [ ] Red/pink background = 1-2 prayers  
- [ ] Yellow background = 3-4 prayers
- [ ] Green background = 5 prayers (all prayers)

**To test all colors:**
1. Rate 3 more prayers on Home screen (Asr, Maghrib, Isha)
2. Go to calendar
3. Today should now show **"5"** with green background

#### 9. Statistics Screen
- [ ] Click **Stats** in bottom nav
- [ ] Overall average displays (should be ~7.8 if you've rated all 5)
- [ ] Individual prayer averages show:
  - Fajr: 9.0/10
  - Dhuhr: 7.0/10
  - Asr: (your rating)/10
  - Maghrib: (your rating)/10
  - Isha: (your rating)/10

#### 10. Settings - Dark Mode
- [ ] Click **Settings** in bottom nav
- [ ] Toggle switch shows (OFF by default)
- [ ] Click the switch to turn ON
- [ ] App immediately switches to dark theme
- [ ] All screens now have dark backgrounds
- [ ] Text is light colored
- [ ] Toggle switch again - Returns to light theme

#### 11. Data Persistence
- [ ] Close the app completely (swipe up from recent apps)
- [ ] Reopen the app
- [ ] Go to Home screen
- [ ] All ratings still visible
- [ ] Go to Calendar - color coding matches previous state
- [ ] Go to Settings - dark mode state is remembered

#### 12. Historical Dates
- [ ] Go to Calendar
- [ ] Navigate to previous month
- [ ] Click on a date (e.g., March 1)
- [ ] DayDetailScreen opens for that date
- [ ] Rate Fajr prayer for that date
- [ ] Go back to calendar
- [ ] March 1 now shows "1" with appropriate color
- [ ] Navigate to current month - today's ratings still there

---

## Common Issues & Solutions

### Issue 1: "Gradle sync failed"
**Solution:**
1. Click **File → Invalidate Caches → Invalidate and Restart**
2. Wait for Android Studio to restart
3. Let Gradle sync again

### Issue 2: "SDK not found"
**Solution:**
1. Open **Android Studio → Preferences → Appearance & Behavior → System Settings → Android SDK**
2. Ensure **Android 13.0 (API 33)** or **Android 14.0 (API 34)** is installed
3. Click **Apply** if you need to install

### Issue 3: App crashes on launch
**Solution:**
1. Check Logcat (bottom panel in Android Studio)
2. Look for red error messages
3. Most common: Database initialization error
   - **Fix**: Uninstall app from emulator and reinstall

### Issue 4: "Emulator is not responding"
**Solution:**
1. Close emulator
2. In Device Manager, click **Cold Boot Now**
3. Wait for full boot, then run app again

### Issue 5: Build takes too long
**Solution:**
1. Close other applications
2. Ensure you have at least 8GB RAM free
3. Build from command line: `./gradlew installDebug` (faster)

---

## Performance Expectations

### Build Times:
- **First build**: 2-5 minutes (downloads dependencies)
- **Subsequent builds**: 30-60 seconds (incremental)
- **Clean build**: 1-2 minutes

### App Performance:
- **Launch time**: 1-2 seconds
- **Screen transitions**: Instant
- **Database operations**: <100ms (unnoticeable)
- **Calendar rendering**: <500ms

---

## Quick Testing Script

Want to test everything quickly? Follow this:

```
1. Open app → Home screen shows
2. Click "Rate" on Fajr → Enter 8, notes: "test" → Save
3. Click "Rate" on Dhuhr → Enter 7 → Save  
4. Click "Rate" on Asr → Enter 9 → Save
5. Click "Rate" on Maghrib → Enter 6 → Save
6. Click "Rate" on Isha → Enter 8 → Save
7. Click Calendar → Today shows "5" with green
8. Click Stats → Overall average ~7.6/10
9. Click Settings → Toggle dark mode → Theme changes
10. Close app → Reopen → All data persists ✅
```

**Total test time: 2-3 minutes**

---

## Debugging Tools

### Logcat (Android Studio)
- View real-time logs: **View → Tool Windows → Logcat**
- Filter by app: Select "com.fdnthemuslim.ratemysalah"
- Look for errors (red), warnings (orange), info (blue)

### Database Inspector
1. Run app on emulator
2. Click **View → Tool Windows → App Inspection**
3. Select **Database Inspector** tab
4. Explore `rate_my_salah_database`
   - `salah_logs` table - view all prayer entries
   - `app_settings` table - view dark mode setting

### Layout Inspector
1. Run app
2. Click **Tools → Layout Inspector**
3. Shows live UI hierarchy
4. Useful for debugging UI issues

---

## Ready to Test!

### Quickest Path to Testing:
1. Open project in Android Studio
2. Wait for Gradle sync
3. Click Run ▶️
4. Test using the Quick Testing Script above

### Alternative (Command Line):
```bash
cd "/Users/fdnthebest/Desktop/Projects/Rate My Salah"
./gradlew installDebug
# Launch emulator, then open app
```

---

## Expected Results

**✅ All tests should pass if:**
- App launches without crashes
- You can rate prayers and see them saved
- Navigation works smoothly
- Calendar updates with colors
- Stats calculate correctly
- Dark mode toggles
- Data persists after app restart

**Your app is feature-complete and ready for production polish! 🎉**
