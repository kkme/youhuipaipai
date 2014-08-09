package com.example.youhuipaipai.activity;

import java.util.HashMap;

import org.json.JSONException;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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
import frame.util.Log;
import frame.util.MyPreference;
import frame.util.ShareUtil;
import frame.util.ToastUtil;

public class LogActivity extends BaseActivity {
    private EditText username_et, pwd_et;
    private Button deleteusername_btn, login_btn, qqlogin_btn, sinalogin_btn;
    private TextView left_tv, right_tv, forgetpwd_tv, regist_tv;

    @Override
    protected int getContentViewID() {
        return R.layout.login;
    }

    int type = 0;

    @Override
    protected void initView() {
        super.initView();
        username_et = (EditText) findViewById(R.id.username_et);
        pwd_et = (EditText) findViewById(R.id.pwd_et);
        deleteusername_btn = (Button) findViewById(R.id.deleteusername_btn);
        login_btn = (Button) findViewById(R.id.login_btn);
        qqlogin_btn = (Button) findViewById(R.id.qqlogin_btn);
        sinalogin_btn = (Button) findViewById(R.id.sinalogin_btn);
        left_tv = (TextView) findViewById(R.id.left_tv);
        right_tv = (TextView) findViewById(R.id.right_tv);
        forgetpwd_tv = (TextView) findViewById(R.id.forgetpwd_tv);
        regist_tv = (TextView) findViewById(R.id.regist_tv);
    }

    @Override
    protected void setListener() {
        super.setListener();
        deleteusername_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        qqlogin_btn.setOnClickListener(this);
        sinalogin_btn.setOnClickListener(this);
        left_tv.setOnClickListener(this);
        forgetpwd_tv.setOnClickListener(this);
        regist_tv.setOnClickListener(this);
        right_tv.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    right_tv.setTextColor(Color.BLACK);
                    break;
                case MotionEvent.ACTION_UP:
                    right_tv.setTextColor(Color.WHITE);
                    goByIntent(MoreActivity.class, false);
                    break;
                }
                return true;
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case frame.util.Constant.SUCCESS:
                // switch (type) {
                // case 1:// qq
                //
                // break;
                // case 2:// sina
                //
                // break;
                // }
                loginThird("", "", "1", MyPreference.getString(
                        LogActivity.this, Constant.TOKEN));
                break;

            default:
                break;
            }
        };

    };

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.deleteusername_btn:
            username_et.getText().clear();
            break;
        case R.id.login_btn:
            login();
            break;
        case R.id.qqlogin_btn:// qq登录
            ShareUtil.QQAuthor(this, handler);
            break;
        case R.id.sinalogin_btn:// 新浪微博登录
            ShareUtil.sinaAuthor(this, handler);
            break;
        case R.id.left_tv:
            finish();
            break;
        case R.id.forgetpwd_tv:
            goByIntent(ForgetpwdActivity.class, false);
            break;
        case R.id.regist_tv:
            // goByIntent(RegisterActivity.class, false);
            startActivityForResult(new Intent(LogActivity.this,
                    RegisterActivity.class), 111);
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent arg2) {
        // TODO Auto-generated method stub
        Log.e("info", "rusultCode " + resultCode + "  arg2  "
                + (null != arg2 ? arg2.toString() : "kong "));
        if (resultCode != 2222) {
            return;
        }
        // if (null != arg2 && arg2.getBooleanExtra("flag", false)) {
        finish();
        // }
        super.onActivityResult(requestCode, resultCode, arg2);

    }

    protected void login() {
        String username = username_et.getText().toString().trim();
        String pwd = pwd_et.getText().toString().trim();
        if (!username.contains("@") && !CheckUtil.checkPhoneNumber(username)) {
            ToastUtil.showShort(this, "请输入正确格式的手机号");
            return;
        } else if (username.contains("@") && !CheckUtil.checkEmail(username)) {
            ToastUtil.showShort(this, "请输入正确格式的电子邮箱");
            return;
        }
        if (TextUtils.isEmpty(pwd) || pwd.length() < 6) {
            ToastUtil.showShort(this, "密码应为6~16字符，区分大小写");
            return;
        }
        loginThird(username, pwd, "0", "key");
    }

    private void loginThird(String name, final String pass, final String type,
            String thirdkey) {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "Login");
        hm.put("loginname", name);
        hm.put("password", pass);
        hm.put("isthird", type);
        hm.put("thirdkey", thirdkey);
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                User user;
                try {
                    user = JsonUtil.jsonToUser(result.toJSONString());
                    UserDB db = new UserDB(LogActivity.this);
                    db.insertValues(user);
                    MyPreference.putString(LogActivity.this, Constant.USERID,
                            user.getUserid());
                    MyPreference.putString(LogActivity.this, Constant.USERPASS,
                            pass);
                    MyPreference.putBoolean(LogActivity.this, Constant.ISLOGIN,
                            true);
                    if ("1".equals(type)) {
                        if ("1".equals(user.getIsFirst())) {// 第一次登录 需要绑定手机号码
                            goByIntent("id", user.getUserid(),
                                    BindphoneActivity.class, true);
                        } else {
                            finish();
                        }
                    } else {
                        finish();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(LogActivity.this, "用户名或密码错误！");
            }
        });
    }
}
