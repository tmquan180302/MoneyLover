package com.poly.moneylover.ui.category;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ConfirmDialog {

    public interface OnConfirmListener {
        void onConfirm();
        void onCancel();
    }

    public static void show(Context context, String title, String message, final OnConfirmListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onConfirm();
                        }
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (listener != null) {
                            listener.onCancel();
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
