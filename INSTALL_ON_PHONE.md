# Install Rate My Salah on Your Phone

## 📱 Your APK is Ready!

**Location:** `RateMySalah.apk` on your Desktop (15 MB)

---

## Installation Methods

### Method 1: AirDrop (Easiest for Mac + iPhone)

**❌ NOT SUPPORTED** - iPhones cannot install APK files (Android only)

---

### Method 2: USB Cable (Direct Install)

#### Step 1: Enable USB Debugging on Your Android Phone
1. Go to **Settings** → **About Phone**
2. Tap **Build Number** 7 times (you'll see "You are now a developer!")
3. Go back to **Settings** → **System** → **Developer Options**
4. Enable **USB Debugging**

#### Step 2: Connect Your Phone
```bash
# Connect your phone via USB cable
# Then run this command to install directly:
cd "/Users/fdnthebest/Desktop/Projects/Rate My Salah"
~/Library/Android/sdk/platform-tools/adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Expected output:
```
Performing Streamed Install
Success
```

#### Step 3: Launch the App
- Look for "Rate My Prayer" icon in your app drawer
- Tap to open!

---

### Method 3: File Transfer (No USB Debugging Required)

#### Option A: Google Drive / Dropbox
1. Upload `RateMySalah.apk` from your Desktop to Google Drive or Dropbox
2. Open Drive/Dropbox on your phone
3. Download the APK
4. Tap the downloaded file
5. If prompted, allow **Install from Unknown Sources** for Chrome/Files
6. Tap **Install**

#### Option B: Email Yourself
1. Email `RateMySalah.apk` to yourself
2. Open email on your phone
3. Download the attachment
4. Follow steps 4-6 above

#### Option C: Messaging Apps (WhatsApp, Telegram, etc.)
1. Send `RateMySalah.apk` to yourself via WhatsApp Web or Telegram
2. Open the file on your phone
3. Follow installation prompts

---

### Method 4: HTTP Server (Tech-Savvy)

#### Step 1: Start a Local Server
```bash
cd ~/Desktop
python3 -m http.server 8000
```

#### Step 2: Connect to Same WiFi
- Make sure your phone and Mac are on the same WiFi network

#### Step 3: Find Your Mac's IP Address
```bash
ipconfig getifaddr en0
# Example output: 192.168.1.100
```

#### Step 4: Download on Phone
1. Open browser on your phone
2. Go to: `http://YOUR_IP_ADDRESS:8000`
   - Example: `http://192.168.1.100:8000`
3. Tap `RateMySalah.apk` to download
4. Install as described above

---

## 🔐 Security Warning Handling

When installing, you may see:

**"For your security, your phone is not allowed to install unknown apps from this source"**

### To Fix:
1. Tap **Settings** in the warning dialog
2. Toggle **Allow from this source** ON
3. Press back and try installing again

**This is normal for apps not from Google Play Store!**

---

## ✅ After Installation

### First Launch:
1. Open the app
2. You'll see the Home screen with today's 5 prayers
3. Try rating a prayer to test it out!

### Verify It Works:
1. Rate Fajr prayer (tap "Rate" → Enter 8 → Add note → Save)
2. Go to Calendar tab → Today should show "1" with color
3. Go to Stats tab → Should show average rating
4. Go to Settings → Toggle dark mode

---

## 🔄 Rebuilding the APK (If You Make Code Changes)

```bash
cd "/Users/fdnthebest/Desktop/Projects/Rate My Salah"

# Clean and build
./gradlew clean assembleDebug

# Copy to Desktop (overwrites old version)
cp app/build/outputs/apk/debug/app-debug.apk ~/Desktop/RateMySalah.apk

# If phone is connected via USB, install directly
~/Library/Android/sdk/platform-tools/adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## 📊 APK Details

- **File Name:** RateMySalah.apk
- **Size:** ~15 MB
- **Version:** 1.0 (Debug)
- **Package:** com.fdnthemuslim.ratemysalah
- **Min Android:** 7.0 (API 24)
- **Target Android:** 14 (API 34)

---

## ❓ Troubleshooting

### "App not installed"
- **Solution:** Uninstall any previous version first, then reinstall

### "Parse error: There was a problem parsing the package"
- **Solution:** Re-download the APK (might be corrupted)
- Or rebuild: `./gradlew clean assembleDebug`

### Can't find the app after install
- **Solution:** Check app drawer, search for "Rate My Prayer" or "Salah"

### App crashes on open
- **Solution:** Clear app data (Settings → Apps → Rate My Prayer → Clear Data)
- Or reinstall completely

---

## 🚀 Quick Install Commands

**If your Android phone is connected via USB:**
```bash
# Enable USB debugging on phone first!
cd "/Users/fdnthebest/Desktop/Projects/Rate My Salah"
~/Library/Android/sdk/platform-tools/adb install -r ~/Desktop/RateMySalah.apk
```

**Done!** The app should now be on your phone! 📱✨
