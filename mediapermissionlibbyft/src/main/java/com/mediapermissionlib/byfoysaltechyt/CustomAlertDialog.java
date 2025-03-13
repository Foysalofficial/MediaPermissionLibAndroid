package com.mediapermissionlib.byfoysaltechyt;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomAlertDialog {
    private final Dialog dialog;
    private final PermissionManager permissionManager;

    public CustomAlertDialog(Activity activity, DialogConfig config, PermissionManager manager) {
        this.permissionManager = manager;
        dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_permission);

        // Set custom text
        TextView titleView = dialog.findViewById(R.id.dialog_title);
        TextView messageView = dialog.findViewById(R.id.dialog_message);
        Button positiveButton = dialog.findViewById(R.id.btn_positive);
        Button negativeButton = dialog.findViewById(R.id.btn_negative);

        titleView.setText(config.getTitle());
        messageView.setText(config.getMessage());
        positiveButton.setText(config.getPositiveButtonText());
        negativeButton.setText(config.getNegativeButtonText());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                permissionManager.requestStoragePermissions();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                permissionManager.getCallback().onPermissionDenied();
            }
        });
    }

    public void show() {
        dialog.show();
    }
}
