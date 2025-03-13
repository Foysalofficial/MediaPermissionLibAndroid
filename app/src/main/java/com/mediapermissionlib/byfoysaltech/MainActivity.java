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

