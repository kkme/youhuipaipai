package com.example.youhuipaipai.activity;

import android.app.Activity;
import android.app.ProgressDialog;

public class CameraBaseActivity extends Activity {

    private CameraBaseActivity CTX = CameraBaseActivity.this;
    /**
     * 等待框是否显示
     */
    protected boolean isProgressShowing = false;

    protected ProgressDialog progressDialog;

    /**
     * 显示等待框
     * 
     * @param title
     * @param message
     */
    public void showProgressDialog(String title, String message) {
        if (!isProgressShowing) {
            progressDialog = ProgressDialog.show(CTX, title, message);
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            isProgressShowing = true;

        } else {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
    }

    /**
     * 取消等待框
     */
    public void dismissProgressDialog() {
        if (progressDialog != null) {
            try {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    isProgressShowing = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
