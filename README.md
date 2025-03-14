---

# MediaPermissionLib

![GitHub release (2025-03-13)](https://github.com/Foysalofficial/MediaPermissionLibAndroid)
![GitHub license](https://img.shields.io/github/license/Foysalofficial/MediaPermissionLib)

A lightweight Android library to simplify requesting and managing storage permissions (e.g., `READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE`, `READ_MEDIA_*`) with a customizable dialog. Supports Android 6.0 (API 23) and above, up to Android 16 (API 35).

## Features
- Easy-to-use API for requesting storage permissions.
- Customizable permission request dialog with title, message, and button text.
- Callback interface for handling permission granted/denied scenarios.
- Persistent state tracking to avoid redundant permission requests.
- Compatible with modern Android permission models (e.g., scoped storage).

## Installation

### Step 1: Add JitPack Repository
In your **root** `build.gradle` (or `settings.gradle` for newer projects):

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Or in `settings.gradle`:

```gradle
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

```new gradle kts
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add Dependency
In your app `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.Foysalofficial:MediaPermissionLibAndroid:5.0'
}
```

### Step 2: Add Update System Dependency
In your app `build.gradle`:

```gradle
dependencies {
    implementation ("com.github.Foysalofficial:MediaPermissionLibAndroid:5.0")
}
```

Sync your project with Gradle files.

## Usage

### Basic Example
Request storage permissions with a dialog in your activity:

```java
package com.mediapermissionlib.byfoysaltech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mediapermissionlib.byfoysaltechyt.DialogConfig;
import com.mediapermissionlib.byfoysaltechyt.PermissionCallback;
import com.mediapermissionlib.byfoysaltechyt.PermissionManager;

public class MainActivity extends AppCompatActivity {

    Button check_permission_allow;
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        check_permission_allow = findViewById(R.id.check_permission);

        permissionManager = new PermissionManager(this, new PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                startActivity(new Intent(MainActivity.this, SuccessPermissionAc.class));
                finish();
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(MainActivity.this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }, true);

        if (!permissionManager.isFirstTime()) {
            startActivity(new Intent(MainActivity.this, SuccessPermissionAc.class));
            finish();
        }

        check_permission_allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogConfig config = new DialogConfig("Allow Access",
                        "This app needs storage permissions to play videos and access media.",
                        "Yes", "No");
                permissionManager.requestStoragePermissionsWithDialog(config);
            }
        });
    }
}
```

## Key Classes
- **PermissionManager**: Manages permission requests and state.
  - Constructor: `PermissionManager(Activity activity, PermissionCallback callback, boolean showDialog)`
  - Methods:
    - `requestStoragePermissionsWithDialog(DialogConfig config)`: Shows a dialog and requests permissions.
    - `isFirstTime()`: Checks if permissions were previously granted.
- **DialogConfig**: Configures the permission dialog (title, message, positive/negative buttons).
- **PermissionCallback**: Interface for handling permission results.

## Permissions Handled
- `READ_EXTERNAL_STORAGE` (pre-Android 13)
- `WRITE_EXTERNAL_STORAGE` (pre-Android 13)
- `READ_MEDIA_AUDIO` (Android 13+)
- `READ_MEDIA_VIDEO` (Android 13+)
- `READ_MEDIA_IMAGES` (Android 13+)

Add to your `AndroidManifest.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.mediapermissionlib.byfoysaltech">

    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
    <uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.MediaPermissionLib">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".SuccessPermissionAc" />
    </application>
</manifest>
```

## Example Layout
`activity_main.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <Button
        android:id="@+id/check_permission"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Check Permissions"
        android:layout_centerInParent="true" />

</RelativeLayout>

```

## Requirements
- Min SDK: 23 (Android 6.0 Marshmallow)
- Compile SDK: 35 (Android 16 Baklava)
- Java: 17 (required by AGP 8.x)

## Contributing
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file.

## Contact
For questions or support, reach out to Foysal Tech or open an issue on GitHub.

Happy coding! 🚀
```

---

MIT License

Copyright (c) 2025 Foysal Tech

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

### Revised `CHANGELOG.md`
```markdown
# Changelog

All notable changes to `MediaPermissionLib` will be documented in this file.

## [3.0] - 2025-03-13
### Added
- Updated library to support Android 16 (API 35).
- Improved dialog customization with `DialogConfig`.
- Added persistent state tracking via `isFirstTime()`.

### Changed
- Updated `minSdk` to 23 (Android 6.0 Marshmallow).
- Migrated to Java 17 for compatibility with AGP 8.x.
- Fixed publishing issues with JitPack.

## [2.0] - 2025-03-12
### Added
- Support for Android 13+ media permissions (`READ_MEDIA_*`).
- Customizable permission dialog.

### Changed
- Refactored `PermissionManager` for better usability.

## [1.0] - 2025-03-10
### Added
- Initial release with basic storage permission handling (`READ_EXTERNAL_STORAGE`, `WRITE_EXTERNAL_STORAGE`).
- Simple callback interface for permission results.
```

---
