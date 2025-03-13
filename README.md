```markdown
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

---

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
```

Or in `settings.gradle`:

```gradle
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add Dependency
In your app `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.Foysalofficial:mediapermissionlibbyft:3.0'
}
```

Sync your project with Gradle files.

---

## Usage

### Basic Example
Here's how to use `PermissionManager` in your activity to request storage permissions with a dialog:

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

---

## Key Classes
- **PermissionManager**: Manages permission requests and state.
  - Constructor: `PermissionManager(Activity activity, PermissionCallback callback, boolean showDialog)`
  - Methods:
    - `requestStoragePermissionsWithDialog(DialogConfig config)`: Shows a dialog and requests permissions.
    - `isFirstTime()`: Checks if permissions were previously granted.
- **DialogConfig**: Configures the permission dialog (title, message, positive/negative buttons).
- **PermissionCallback**: Interface for handling permission results.

---

## Permissions Handled
- `READ_EXTERNAL_STORAGE` (pre-Android 13)
- `WRITE_EXTERNAL_STORAGE` (pre-Android 13)
- `READ_MEDIA_AUDIO` (Android 13+)
- `READ_MEDIA_VIDEO` (Android 13+)
- `READ_MEDIA_IMAGES` (Android 13+)

Ensure these permissions are declared in your app's `AndroidManifest.xml`:

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

---

## Example Layout
Your `activity_main.xml` might look like this:

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

---

## Requirements
- Min SDK: 23 (Android 6.0 Marshmallow)
- Compile SDK: 35 (Android 16 Baklava)
- Java: 17 (required by AGP 8.x)

---

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

---

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```markdown
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

## Contact
For questions or support, reach out to Foysal Tech or open an issue on GitHub.

Happy coding! 🚀

---

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

---

# Library Module Configuration

### `mediapermissionlibbyft/build.gradle.kts`
The build configuration for your library module.

```kotlin
plugins {
    alias(libs.plugins.android.library)
    id("maven-publish")
}

android {
    namespace = "com.mediapermissionlib.byfoysaltechyt"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation("androidx.activity:activity:1.10.1")
    implementation("androidx.core:core:1.13.1")
}

publishing {
    publications {
        create<MavenPublication>("releaseAar") {
            groupId = "com.github.Foysalofficial"
            artifactId = "mediapermissionlibbyft"
            version = "3.0"
            from(components["release"])
        }
    }
}
```

---

### `mediapermissionlibbyft/src/main/AndroidManifest.xml`
The manifest for the library module.

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
    <uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />

</manifest>
```

---

### `jitpack.yml`
Configuration for JitPack builds.

```yaml
jdk:
  - amazoncorretto-17
android:
  modules:
    - mediapermissionlibbyft
```

---

### `build.gradle.kts` (Root)
The root-level build file (assuming a multi-module setup).

```kotlin
plugins {
    id("com.android.library") apply false
}

subprojects {
    repositories {
        mavenCentral()
    }
}
```

---

## How to Use These Markdown Blocks

1. **Extract Files from Markdown:**
   - For each section, copy the content inside the code block (e.g., ```java ... ```) into the specified file path in your project:
     - `app/src/main/java/com/mediapermissionlib/byfoysaltech/MainActivity.java`
     - `app/src/main/AndroidManifest.xml`
     - `app/src/main/res/layout/activity_main.xml`
     - `mediapermissionlibbyft/build.gradle.kts`
     - `mediapermissionlibbyft/src/main/AndroidManifest.xml`
     - `README.md`
     - `LICENSE`
     - `CHANGELOG.md`
     - `jitpack.yml`
     - `build.gradle.kts`

2. **Commit and Push:**
   ```bash
   git add .
   git commit -m "Add all project files in Markdown format"
   git push origin main
   ```

3. **Tag the Release:**
   ```bash
   git tag 3.0
   git push origin 3.0
   ```

4. **Verify on GitHub:**
   Visit [https://github.com/Foysalofficial/MediaPermissionLibAndroid/](https://github.com/Foysalofficial/MediaPermissionLibAndroid/) to ensure all files are correctly uploaded and `README.md` renders properly.

---

Below is a complete set of files in GitHub Markdown format for your `MediaPermissionLib` repository, including the `README.md`, a basic `LICENSE` file (MIT License), and a sample `CHANGELOG.md`. These files will help users understand your project, its licensing, and version history. I'll provide each file in Markdown format with appropriate content.

---

### 1. `README.md`
This is the main documentation file for your repository.

```markdown
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
```

Or in `settings.gradle`:

```gradle
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add Dependency
In your **app** `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.Foysalofficial:mediapermissionlibbyft:3.0'
}
```

Sync your project with Gradle files.

## Usage

### Basic Example
Here's how to use `PermissionManager` in your activity to request storage permissions with a dialog:

```java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mediapermissionlib.byfoysaltechyt.DialogConfig;
import com.mediapermissionlib.byfoysaltechyt.PermissionCallback;
import com.mediapermissionlib.byfoysaltechyt.PermissionManager;

public class MainActivity extends AppCompatActivity {

    private Button checkPermissionButton;
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissionButton = findViewById(R.id.check_permission);

        // Initialize PermissionManager
        permissionManager = new PermissionManager(this, new PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                // Handle permission granted
                startActivity(new Intent(MainActivity.this, SuccessPermissionAc.class));
                finish();
            }

            @Override
            public void onPermissionDenied() {
                // Handle permission denied
                Toast.makeText(MainActivity.this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }, true); // Auto-show dialog if permissions not granted

        // Skip permission request if already granted
        if (!permissionManager.isFirstTime()) {
            startActivity(new Intent(MainActivity.this, SuccessPermissionAc.class));
            finish();
        }

        // Set up button click to show permission dialog
        checkPermissionButton.setOnClickListener(v -> {
            DialogConfig config = new DialogConfig(
                "Allow Access",
                "This app needs storage permissions to play videos and access media.",
                "Yes",
                "No"
            );
            permissionManager.requestStoragePermissionsWithDialog(config);
        });
    }
}
```

### Key Classes
- **`PermissionManager`**: Manages permission requests and state.
  - Constructor: `PermissionManager(Activity activity, PermissionCallback callback, boolean showDialog)`
  - Methods:
    - `requestStoragePermissionsWithDialog(DialogConfig config)`: Shows a dialog and requests permissions.
    - `isFirstTime()`: Checks if permissions were previously granted.
- **`DialogConfig`**: Configures the permission dialog (title, message, positive/negative buttons).
- **`PermissionCallback`**: Interface for handling permission results.

### Permissions Handled
- `READ_EXTERNAL_STORAGE` (pre-Android 13)
- `WRITE_EXTERNAL_STORAGE` (pre-Android 13)
- `READ_MEDIA_AUDIO` (Android 13+)
- `READ_MEDIA_VIDEO` (Android 13+)
- `READ_MEDIA_IMAGES` (Android 13+)

Ensure these permissions are declared in your app's `AndroidManifest.xml`:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
    <uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
    <!-- ... -->
</manifest>
```

## Example Layout
Your `activity_main.xml` might look like this:

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
- **Min SDK**: 23 (Android 6.0 Marshmallow)
- **Compile SDK**: 35 (Android 16 Baklava)
- **Java**: 17 (required by AGP 8.x)

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
For questions or support, reach out to [Foysal Tech](mailto:your-email@example.com) or open an issue on GitHub.

---

Happy coding! 🚀
```

---

### 2. `LICENSE`
This is a standard MIT License file. While it’s not typically in Markdown, GitHub renders it nicely as plain text.

```markdown
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

### 3. `CHANGELOG.md`
This file tracks version history and changes in your library.

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

---
```

---

### How to Add These Files to Your GitHub Repository

1. **Create the Files Locally:**
   - In your project root (`C:\Users\Foysal Tech\AndroidStudioProjects\MediaPermissionLib`):
     - Create `README.md` and paste the first content.
     - Create `LICENSE` and paste the second content.
     - Create `CHANGELOG.md` and paste the third content.

2. **Customize (Optional):**
   - In `README.md`, replace `your-email@example.com` with your actual email.
   - Update dates or version details in `CHANGELOG.md` if they differ from your actual timeline.

3. **Commit and Push:**
   ```bash
   git add README.md LICENSE CHANGELOG.md
   git commit -m "Add README, LICENSE, and CHANGELOG for MediaPermissionLib"
   git push origin main
   ```

4. **Tag the Latest Version:**
   - If you’re on version `3.0`, tag it:
     ```bash
     git tag 3.0
     git push origin 3.0
     ```

5. **Verify on GitHub:**
   - Visit `https://github.com/Foysalofficial/MediaPermissionLibAndroid/` to see the rendered files.
   - The `README.md` will appear on the main page, `LICENSE` will be linked, and `CHANGELOG.md` will be accessible as a separate file.

---

### Additional Notes
- **Markdown Formatting:** These files use GitHub-flavored Markdown, which supports headers (`#`), code blocks (```), links, and badges.
- **Badges:** The `README.md` includes badges for release version and license. They’ll work once you’ve tagged a release and added the `LICENSE` file.
- **Enhancements:** You could add screenshots (upload images to the repo and link them in `README.md` with `![Screenshot](path/to/image.png)`) or a "Getting Started" section with more examples.

This set of files provides a professional and informative setup for your GitHub repository. Let me know if you need further adjustments!
