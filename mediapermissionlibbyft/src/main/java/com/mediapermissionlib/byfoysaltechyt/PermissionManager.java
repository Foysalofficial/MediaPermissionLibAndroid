package com.mediapermissionlib.byfoysaltechyt;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class PermissionManager {
    public AppCompatActivity activity;
    private final PermissionCallback callback;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private CustomAlertDialog alertDialog;
    private FirstTimeStore firstTimeStore;
    private final boolean useFirstTimeStore;

    public PermissionManager(AppCompatActivity activity, PermissionCallback callback, boolean useFirstTimeStore) {
        this.activity = activity;
        this.callback = callback;
        this.useFirstTimeStore = useFirstTimeStore;
        if (useFirstTimeStore) {
            this.firstTimeStore = new FirstTimeStore(activity);
        }
        initializeLauncher();
    }

    private void initializeLauncher() {
        requestPermissionLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    boolean allGranted = true;
                    for (Boolean granted : result.values()) {
                        if (!granted) {
                            allGranted = false;
                            break;
                        }
                    }
                    if (allGranted) {
                        if (useFirstTimeStore && firstTimeStore != null) {
                            firstTimeStore.storeFirstTime(1);
                        }
                        callback.onPermissionGranted();
                    } else {
                        callback.onPermissionDenied();
                    }
                }
        );
    }

    public void requestStoragePermissions() {
        List<String> permissions = getRequiredPermissions();
        if (permissions.isEmpty() || arePermissionsGranted()) {
            if (useFirstTimeStore && firstTimeStore != null) {
                firstTimeStore.storeFirstTime(1);
            }
            callback.onPermissionGranted();
        } else {
            requestPermissionLauncher.launch(permissions.toArray(new String[0]));
        }
    }

    public void requestStoragePermissionsWithDialog(DialogConfig config) {
        if (arePermissionsGranted()) {
            if (useFirstTimeStore && firstTimeStore != null) {
                firstTimeStore.storeFirstTime(1);
            }
            callback.onPermissionGranted();
        } else {
            alertDialog = new CustomAlertDialog(activity, config, this);
            alertDialog.show();
        }
    }

    public boolean isFirstTime() {
        if (useFirstTimeStore && firstTimeStore != null) {
            return firstTimeStore.isFirstTime();
        }
        return true;
    }

    private List<String> getRequiredPermissions() {
        List<String> permissions = new ArrayList<>();
        int sdkVersion = Build.VERSION.SDK_INT;

        if (sdkVersion >= Build.VERSION_CODES.TIRAMISU) { // Android 13+ (API 33+)
            permissions.add(Manifest.permission.READ_MEDIA_AUDIO);
            permissions.add(Manifest.permission.READ_MEDIA_VIDEO);
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES);
        } else if (sdkVersion >= Build.VERSION_CODES.Q) { // Android 10–12 (API 29–31)
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            // WRITE_EXTERNAL_STORAGE is optional in scoped storage, only request if needed
            if (sdkVersion < Build.VERSION_CODES.R || !isLegacyStorageSupported()) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        } else if (sdkVersion >= Build.VERSION_CODES.M) { // Android 6.0–9 (API 23–28)
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        // Pre-Marshmallow (API < 23): No runtime permissions needed
        return permissions;
    }

    public boolean arePermissionsGranted() {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            return hasPermission(Manifest.permission.READ_MEDIA_AUDIO) &&
                    hasPermission(Manifest.permission.READ_MEDIA_VIDEO) &&
                    hasPermission(Manifest.permission.READ_MEDIA_IMAGES);
        } else if (sdkVersion >= Build.VERSION_CODES.Q) { // Android 10–12
            boolean readGranted = hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            boolean writeGranted = sdkVersion < Build.VERSION_CODES.R || !isLegacyStorageSupported() ?
                    hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) : true;
            return readGranted && writeGranted;
        } else if (sdkVersion >= Build.VERSION_CODES.M) { // Android 6.0–9
            return hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) &&
                    hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else { // Pre-Marshmallow
            return true;
        }
    }

    private boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isLegacyStorageSupported() {
        // Check if legacy storage is still viable (Android 10–11 with requestLegacyExternalStorage)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageLegacy();
        }
        return false; // Android 12+ enforces scoped storage unless MANAGE_EXTERNAL_STORAGE is used
    }

    public PermissionCallback getCallback() {
        return callback;
    }
}
