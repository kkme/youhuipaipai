package com.example.youhuipaipai.activity;

import java.util.HashMap;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.MyPreference;
import frame.util.ToastUtil;

public class ChangePwdActivity extends BaseActivity {

    private TextView password_back;
    private EditText edit_old, edit_password, edit_sure;
    Button exitlogin_btn;

    @Override
    protected int getContentViewID() {
        return R.layout.changepassword;
    }

    @Override
    protected void initView() {
        super.initView();
        password_back = getView(R.id.password_back);
        edit_old = getView(R.id.edit_old);
        edit_password = getView(R.id.edit_password);
        edit_sure = getView(R.id.edit_sure);
        exitlogin_btn = getView(R.id.exitlogin_btn);

    }

    @Override
    protected void setListener() {
        super.setListener();
        password_back.setOnClickListener(this);
        exitlogin_btn.setOnClickListener(this);

    }

    @Override
    protected void getDatas() {
        // TODO Auto-generated method stub
        super.getDatas();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.password_back:
            finish();
            break;

        case R.id.exitlogin_btn:
            changePwd();
            break;
        }
    }

    private void changePwd() {
        String old = edit_old.getText().toString().trim();
        String pwd = edit_password.getText().toString().trim();
        String pwdagain = edit_sure.getText().toString().trim();
        if (TextUtils.isEmpty(old) || old.length() < 6) {
            ToastUtil.showShort(this, "密码应为6~16字符，区分大小写");
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
        if (!old.equals(MyPreference.getString(this, Constant.USERPASS))) {
            ToastUtil.showShort(this, "原密码不正确！");
            return;
        }
        showProgressDialog("正在请求", "请稍后...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "ChangePassword");
        hm.put("userid", MyPreference.getString(this, Constant.USERID));
        hm.put("psw", pwd);
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                ToastUtil.showShort(ChangePwdActivity.this, "修改成功");
                finish();
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(ChangePwdActivity.this, result.errorString);
            }
        });
    }

}
