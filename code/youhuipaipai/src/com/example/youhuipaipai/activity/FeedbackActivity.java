package com.example.youhuipaipai.activity;

import java.util.HashMap;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;

import frame.http.bean.FailureResult;
import frame.util.CheckUtil;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.MyPreference;
import frame.util.ToastUtil;

public class FeedbackActivity extends BaseActivity {

    private TextView tv_back, tv_number;
    private EditText edit_content, edit_contact;
    private Button btn_submit;
    private int number = 128;

    @Override
    protected int getContentViewID() {

        return R.layout.feedback;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_back = getView(R.id.tv_back);
        edit_content = getView(R.id.edit_content);
        edit_contact = getView(R.id.edit_contact);
        btn_submit = getView(R.id.btn_submit);
        tv_number = getView(R.id.tv_number);
    }

    @Override
    protected void setListener() {
        super.setListener();
        tv_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        // 下面为EditText文本框添加监听
        edit_content.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                temp = s;
            }

            public void afterTextChanged(Editable s) {
                int num = number - s.length();
                tv_number.setText("剩余" + num + "字");
                selectionStart = edit_content.getSelectionStart();
                selectionEnd = edit_content.getSelectionEnd();
                if (temp.length() > number) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    edit_content.setText(s);
                    edit_content.setSelection(tempSelection);// 设置光标在最后
                }
            }
        });

    }

    @Override
    protected void getDatas() {
        super.getDatas();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.tv_back:
            finish();

            break;

        case R.id.btn_submit:
            if (isCanCommit()) {
                getFeedBack();
            }
            break;
        }
    }

    /**
     * 是否可以提交
     * 
     * @return
     */
    private boolean isCanCommit() {
        if ("".equals(edit_contact.getText().toString().trim())) {
            ToastUtil.showShort(this, "联系方式为必填项！");
            return false;
        }
        if (!CheckUtil.checkPhoneNumber(edit_contact.getText().toString()
                .trim())) {
            ToastUtil.showShort(this, "手机号码格式不正确！");
            return false;
        }
        return true;
    }

    private void getFeedBack() {
        HttpUtil ut = new HttpUtil();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("requestCommand", "Feedback");
        map.put("userid", MyPreference.getString(this, Constant.USERID));
        map.put("content", isNull(edit_content.getText().toString()));
        // TODO 判断是否为合法的电话号码
        map.put("contact", edit_contact.getText().toString());
        ut.post(this, map, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                // TODO 将返回的数据显示
                ToastUtil.showShort(FeedbackActivity.this, "提交成功");
                finish();
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(FeedbackActivity.this, result.errorString);
            }
        });
    }

    private String isNull(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

}
