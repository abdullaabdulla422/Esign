package com.ebs.rental.webutils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.Window;

import com.ebs.rental.general.R;

public class ProgressBar {

    private static Dialog commonProgressDialog;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void showCommonProgressDialog(Activity activity) {

        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // only for gingerbread and newer versions
            if (activity.isDestroyed()) { // or call isFinishing() if min sdk version < 17
                return;
            }
        } else {
            if (activity.isFinishing()) {
                return;
            }
        }

        commonProgressDialog = new Dialog(activity);
        commonProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        commonProgressDialog.setContentView(R.layout.activity_progress_bar);
        //noinspection ConstantConditions
        commonProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        commonProgressDialog.setCanceledOnTouchOutside(true);
        commonProgressDialog.setCancelable(false);

//        if (!activity.isDestroyed())
//            commonProgressDialog.show();

        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                // only for gingerbread and newer versions
                if (!activity.isDestroyed()) { // or call isFinishing() if min sdk version < 17
                    commonProgressDialog.show();

                }
            } else {
                if (!activity.isFinishing()) {
                    commonProgressDialog.show();
                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void dismiss() {


        if (commonProgressDialog != null) {
            commonProgressDialog.dismiss();
        }
    }


    public static boolean getDialogStatus() {

        return commonProgressDialog.isShowing();

    }

}
