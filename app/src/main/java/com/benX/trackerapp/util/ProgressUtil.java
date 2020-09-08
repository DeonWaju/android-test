package com.benX.trackerapp.util;

import android.app.AlertDialog;
import android.content.Context;

import com.benX.trackerapp.R;

import dmax.dialog.SpotsDialog;

public class ProgressUtil {

    private AlertDialog dialog;

    public void displayProgress(Context context) {
        if (dialog == null) {
            dialog = new SpotsDialog(context, R.style.CustomDialog);
            dialog.setCancelable(false);
        }
        dialog.show();
    }

    public void closeProgress() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public AlertDialog getDialog() {
        return dialog;
    }

}
