package com.example.youhuipaipai.activity;

import java.io.Serializable;

import frame.commom.AppContext;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends FragmentActivity implements
        OnClickListener {

    protected int windowheight;
    protected int windowwidth;
    protected BaseActivity CTX = BaseActivity.this;
    protected String TAG = CTX.getClass().getSimpleName();
    protected View backView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewID());
        init();
        AppContext.curContext = this;
    }

    /**
     * 设置本界面 的Layout的id
     */
    protected abstract int getContentViewID();

    /**
     * 设置监听事件，如果不用设置，则不用设置
     */
    protected void setListener() {
        if (backView != null) {
            backView.setOnClickListener(this);
        }
    }

    /**
     * 初始化所有View和数据
     */
    private void init() {
        initView();
        setListener();
        getDatas();
    }

    /**
     * 获取数据
     */
    protected void getDatas() {
    }

    /**
     * 初始化所有控件
     */
    protected void initView() {
    }

    /**
     * 根据资源id 获取View ，不用强制转换
     * 
     * @param id
     *            资源id
     * @return 返回id所指向的View
     */
    protected <A extends View> A getView(int id) {
        return (A) findViewById(id);
    }

    /**
     * 判断sd卡是否存在
     * 
     * @return
     */
    protected boolean isSDCardExist() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 监听Click事件
     */
    public void onClick(View v) {
    }

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

    /**
     * 封装Intent跳转
     * 
     * @param clazz
     *            要跳向的界面的class
     * @param isCloseSelf
     *            是否关闭本界面
     */
    public void goByIntent(Class clazz, boolean isCloseSelf) {
        Intent intent = new Intent(CTX, clazz);
        startActivity(intent);
        if (isCloseSelf) {
            CTX.finish();
        }
    }

    /**
     * 封装Intent跳转
     * 
     * @param key
     * @param obj
     * @param clazz
     * @param isCloseSelf
     */
    protected void goByIntent(String key, Serializable obj, Class<?> clazz,
            boolean isCloseSelf) {
        Intent intent = new Intent();
        intent.setClass(CTX, clazz);
        if (!TextUtils.isEmpty(key)) {
            intent.putExtra(key, obj);
        }
        startActivity(intent);
        if (isCloseSelf) {
            CTX.finish();
        }
    }

    /**
     * 根据文件的绝对路径获取文件名
     * 
     * @param path
     * @return
     */
    protected String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1, path.length());
    }

    /**
     * 检测网络连接
     * 
     * @return
     */
    public boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * Wifi是否可用
     * 
     * @param mContext
     * @return
     */
    public boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getTypeName().equals("WIFI")) {
            return true;
        }
        return false;
    }

    /**
     * 退出确认
     */
    protected void exitConfirm() {
        AlertDialog.Builder builder = new Builder(BaseActivity.this);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    /**
     * 退出确认
     */
    protected void exitDialog(final Context ctx) {
        AlertDialog.Builder builder = new Builder(ctx);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.cancelAll();
                        // MApplication.exitAllActivity(ctx);
                    }
                });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }
}
