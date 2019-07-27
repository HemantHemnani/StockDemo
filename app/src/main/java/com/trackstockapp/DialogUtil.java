package com.trackstockapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class DialogUtil {

    public static Dialog showLoader(Context context/*, boolean textStatus, int currentDoc, int totalDoc*/) {
        final Dialog networkDialogLoader = new Dialog(context, R.style.AppTheme);
        String fromTime = "";
        // networkDialogLoader.requestWindowFeature(Window.FEATURE_NO_TITLE);
        networkDialogLoader.setContentView(R.layout.progress_loader);
        networkDialogLoader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        networkDialogLoader.setCancelable(false);
        networkDialogLoader.setCanceledOnTouchOutside(false);


        networkDialogLoader.show();
        return networkDialogLoader;

    }


    public static void hideLoader(final Dialog dialog) {


        dialog.dismiss();
    }
}
