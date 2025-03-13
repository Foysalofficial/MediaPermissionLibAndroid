# MediaPermissionLib

![GitHub release (latest by date)](https://img.shields.io/github/v/release/Foysalofficial/MediaPermissionLib)
![GitHub license](https://img.shields.io/github/license/Foysalofficial/MediaPermissionLib)

A lightweight Android library to simplify requesting and managing storage permissions (e.g., `READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE`, `READ_MEDIA_*`) with a customizable dialog. Supports Android 6.0 (API 23) and above, up to Android 16 (API 35).

## Features
- Easy-to-use API for requesting storage permissions.
- Customizable permission request dialog with title, message, and button text.
- Callback interface for handling permission granted/denied scenarios.
- Persistent state tracking to avoid redundant permission requests.
- Compatible with modern Android permission models (e.g., scoped storage).

## Installation

Add the library to your project via JitPack:

### Step 1: Add JitPack Repository
In your **root** `build.gradle` (or `settings.gradle` for newer projects):

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
