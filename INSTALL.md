# Installing Taily Counter via ADB

## 1. Download the APK
Get the APK file from the [releases](./releases) section of this repository.

## 2. Connect the watch to your PC
1. Enable **Developer mode** on your watch:
- Go to **Settings** → **About watch** → **Build number**.
- Tap repeatedly until you see *“You are now a developer”*.
2. In **Developer options**, enable **Wireless debugging** and click on **Pair with device**
3. Connect your watch to the computer:
```
adb pair <ip>:<port>
adb connect <ip>:<port>
```
4. Install the apk:
```
adb install counter.apk
```
