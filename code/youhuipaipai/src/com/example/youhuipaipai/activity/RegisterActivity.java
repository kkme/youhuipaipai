package com.example.youhuipaipai.activity;

import java.util.HashMap;

import org.json.JSONException;

import android.R.color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.db.UserDB;
import com.example.youhuipaipai.entity.User;

import frame.http.bean.FailureResult;
import frame.util.CheckUtil;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.ToastUtil;

public class RegisterActivity extends BaseActivity {

    private EditText phone_et, pwd_et, pwdagain_et, validatecode_et;
    private Button getvalidatecode_btn, regist_btn;
    private TextView left_tv;
    private int time = 60;
    private Handler handler;
    private String code = "";

    @Override
    protected int getContentViewID() {
        return R.layout.regist;
    }

    private void processHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                case 8:
                    if (time > 0) {
                        time--;
                        getvalidatecode_btn.setText("获取验证码（" + time + "s）");
                        getvalidatecode_btn.setTextColor(getResources()
                                .getColor(R.color.gainsboro));
                        handler.sendEmptyMessageDelayed(8, 1000);
                    } else {
                        getvalidatecode_btn.setEnabled(true);
                        time = 60;
                        getvalidatecode_btn.setText("获取验证码");
                        getvalidatecode_btn.setTextColor(getResources()
                                .getColor(R.color.white));
                        // btn_getCode.setTextColor(getResources().getColor(
                        // R.color.black));
                    }

                    break;
                }
            }
        };
    }

    @Override
    protected void initView() {
        super.initView();
        phone_et = getView(R.id.phone_et);
        pwd_et = getView(R.id.pwd_et);
        pwdagain_et = getView(R.id.pwdagain_et);
        validatecode_et = getView(R.id.validatecode_et);
        getvalidatecode_btn = getView(R.id.getvalidatecode_btn);
        regist_btn = getView(R.id.regist_btn);
        left_tv = (TextView) getView(R.id.left_tv);
        processHandler();
    }

    @Override
    protected void setListener() {
        super.setListener();
        getvalidatecode_btn.setOnClickListener(this);
        regist_btn.setOnClickListener(this);
        left_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.getvalidatecode_btn:
            if ("".equals(phone_et.getText().toString().trim())) {
                ToastUtil.showShort(this, "请输入手机号码");
                return;
            }
            if (!CheckUtil.checkPhoneNumber(phone_et.getText().toString()
                    .trim())) {
                ToastUtil.showShort(this, "手机号码格式不正确");
                return;
            }
            getvalidatecode();
            break;
        case R.id.regist_btn:
            regist();
            break;
        case R.id.left_tv:
            finish();
            break;
        default:
            break;
        }
    }

    private void getvalidatecode() {
        showProgressDialog("正在获取", "请稍后...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "GetMessage");
        hm.put("phone", phone_et.getText().toString().trim());
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                try {
                    org.json.JSONObject object = new org.json.JSONObject(result
                            .toJSONString());
                    code = object.optString("message");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                handler.sendEmptyMessageDelayed(8, 1000);
                getvalidatecode_btn.setEnabled(false);
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(RegisterActivity.this, result.errorString);
            }

        });
    }

    private void regist() {
        String username = phone_et.getText().toString().trim();
        String pwd = pwd_et.getText().toString().trim();
        String pwdagain = pwdagain_et.getText().toString().trim();
        if (!CheckUtil.checkPhoneNumber(username)) {
            ToastUtil.showShort(this, "请输入正确格式的手机号");
            return;
        }
        if (TextUtils.isEmpty(pwd) || pwd.length() < 6) {
            ToastUtil.showShort(this, "密码应为6~16字符，区分大小写");
            return;
        }
        if (TextUtils.isEmpty(pwdagain) || pwdagain.length() < 6) {
            ToastUtil.showShort(this, "密码应为6~16字符，区分大小写");
            return;
        }
        if (!pwd.equals(pwdagain)) {
            ToastUtil.showShort(this, "两次输入密码不一致");
            return;
        }

        if ("".equals(validatecode_et.getText().toString().trim())) {
            ToastUtil.showShort(this, "验证码不能为空");
            return;
        }
        if (!code.equals(validatecode_et.getText().toString().trim())) {
            ToastUtil.showShort(this, "验证码不正确");
            return;
        }

        showProgressDialog("正在请求", "请稍后...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "Register");
        hm.put("loginname", username);
        hm.put("password", pwd);
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                ToastUtil.showShort(RegisterActivity.this, "注册成功");
                User user;
                try {
                    user = JsonUtil.jsonToUser(result.toJSONString());
                    UserDB db = new UserDB(RegisterActivity.this);
                    db.insertValues(user);
                    MyPreference.putString(RegisterActivity.this,
                            Constant.USERID, user.getUserid());
                    MyPreference.putBoolean(RegisterActivity.this,
                            Constant.ISLOGIN, true);
                    setResult(2222);
                    finish();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(RegisterActivity.this, result.errorString);
            }
        });
    }
}
