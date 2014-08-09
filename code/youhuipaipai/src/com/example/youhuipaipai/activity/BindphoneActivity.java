package com.example.youhuipaipai.activity;

import java.util.HashMap;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;

import frame.http.bean.FailureResult;
import frame.util.CheckUtil;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.ToastUtil;

public class BindphoneActivity extends BaseActivity {

    String userid = "";
    EditText et_phone, et_code;
    Button getvalidatecode_btn, btn_sure;
    private int time = 60;
    private Handler handler;

    @Override
    protected int getContentViewID() {
        return R.layout.bindphone;
    }

    @Override
    protected void initView() {
        // TODO Auto-generated method stub
        super.initView();
        et_code = getView(R.id.et_code);
        et_phone = getView(R.id.et_phone);
        getvalidatecode_btn = getView(R.id.getvalidatecode_btn);
        btn_sure = getView(R.id.btn_sure);
        processHandler();
    }

    @Override
    protected void getDatas() {
        // TODO Auto-generated method stub
        super.getDatas();
        userid = getIntent().getStringExtra("id");
    }

    private void processHandler() {
        // TODO Auto-generated method stub
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                case 8:
                    if (time > 0) {
                        time--;
                        getvalidatecode_btn.setText("获取验证码（" + time + "s）");
                        handler.sendEmptyMessageDelayed(8, 1000);
                    } else {
                        getvalidatecode_btn.setEnabled(true);
                        time = 60;
                        getvalidatecode_btn.setText("获取验证码");
                        // btn_getCode.setTextColor(getResources().getColor(
                        // R.color.black));
                    }

                    break;
                }
            }
        };
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub
        super.setListener();
        btn_sure.setOnClickListener(this);
        getvalidatecode_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        super.onClick(v);
        switch (v.getId()) {
        case R.id.btn_sure:// 确定
            if ("".equals(et_code.getText().toString().trim())
                    || "".equals(et_phone.getText().toString().trim())) {
                ToastUtil.showShort(this, "请完善信息");
            } else {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("requestCommand", "BindPhone");
                hm.put("userid", userid);
                hm.put("phone", et_phone.getText().toString().trim());
                getDataFromServer(hm, 2);
            }
            break;
        case R.id.getvalidatecode_btn:// 获取验证码
            if ("".equals(et_phone.getText().toString().trim())) {
                ToastUtil.showLong(this, "请输入手机号码");
                return;
            }
            if (!CheckUtil.checkPhoneNumber(et_phone.getText().toString()
                    .toLowerCase())) {
                ToastUtil.showLong(this, "手机号码格式不正确");
                return;
            }
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("requestCommand", "GetMessage");
            hm.put("phone", et_phone.getText().toString().trim());
            getDataFromServer(hm, 2);
            break;
        }
    }

    private void getDataFromServer(HashMap<String, String> hm, final int type) {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                switch (type) {
                case 1:// 确定
                    finish();
                    break;
                case 2:// 获取验证码
                    handler.sendEmptyMessageDelayed(8, 1000);
                    getvalidatecode_btn.setEnabled(false);
                    break;
                }
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(BindphoneActivity.this, result.errorString);
            }
        });
    }

}
