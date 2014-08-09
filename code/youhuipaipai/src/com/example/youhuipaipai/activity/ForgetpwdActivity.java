package com.example.youhuipaipai.activity;

import java.util.HashMap;

import org.json.JSONException;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.ToastUtil;

public class ForgetpwdActivity extends BaseActivity {

    String sign = "1";
    String content = "";

    @Override
    protected int getContentViewID() {
        return R.layout.forgetpwd;
    }

    Button btn_sure;
    EditText editText;
    RadioButton rbLeft, rbRight;
    boolean flag = true;// TRUE 表示手机找回 FALSE 表示邮箱找回

    @Override
    protected void initView() {
        // TODO Auto-generated method stub
        super.initView();
        btn_sure = getView(R.id.btn_commit);
        editText = getView(R.id.et_content);
        rbLeft = getView(R.id.rb_left);
        rbRight = getView(R.id.rb_right);
        setCurrentPosition(0);
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub
        super.setListener();
        getView(R.id.left_tv).setOnClickListener(this);
        btn_sure.setOnClickListener(this);
        rbLeft.setOnClickListener(this);
        rbRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        super.onClick(v);
        switch (v.getId()) {
        case R.id.left_tv:
            finish();
            break;
        case R.id.btn_commit:
            content = editText.getText().toString().trim();
            if (!"".equals(content)) {
                getMycomments();
            } else {
                ToastUtil.showShort(this, "请完善信息！");
            }
            break;
        case R.id.rb_left:// 手机找回
            flag = true;
            editText.setHint("请输入您的绑定手机号");
            sign = "1";
            setCurrentPosition(0);
            break;
        case R.id.rb_right:// 邮箱找回
            flag = false;
            editText.setHint("请输入您的绑定邮箱");
            sign = "2";
            setCurrentPosition(1);
            break;
        }
    }

    private void setCurrentPosition(int position) {
        switch (position) {
        case 0:
            rbLeft.setTextColor(getResources().getColor(R.color.white));
            rbRight.setTextColor(getResources().getColor(R.color.red));
            break;
        case 1:
            rbRight.setTextColor(getResources().getColor(R.color.white));
            rbLeft.setTextColor(getResources().getColor(R.color.red));
            break;
        }
    }

    /**
     * userid string 用户Id phone string 手机号（手机号和邮箱必选其中一个） email string 邮箱 sign
     * string 1 手机找回 2 邮箱找回
     */
    private void getMycomments() {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "FindPassword");
        hm.put("userid", MyPreference.getString(this, Constant.USERID));
        hm.put("phone", content);
        hm.put("email", content);
        hm.put("sign", sign);
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                try {
                    ToastUtil.showShort(ForgetpwdActivity.this,
                            JsonUtil.jsonToErrorMessage(result.toJSONString()));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }// 解析数据
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(ForgetpwdActivity.this, result.errorString);
            }
        });
    }
}
