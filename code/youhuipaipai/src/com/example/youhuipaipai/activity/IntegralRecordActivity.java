package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.IntegralrecordlAdapter;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.IntegralRecord;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.ToastUtil;

/**
 * @项目名:youhuipaipai
 * 
 * @类名:IntegralRecordActivity.java
 * 
 * @类描述:积分记录
 * 
 * @Version:1.0
 * 
 ******************************************/
public class IntegralRecordActivity extends BaseActivity {
    private TextView left_tv;
    private ListView lv;
    private IntegralrecordlAdapter adapter;

    private String currentJifen = "";

    /**
     * 当前积分
     */
    private TextView tv_current_integral;

    @Override
    protected int getContentViewID() {
        return R.layout.integalrecord;
    }

    @Override
    protected void initView() {
        super.initView();
        left_tv = getView(R.id.left_tv);
        lv = getView(R.id.listview);
        adapter = new IntegralrecordlAdapter(null, this);
        lv.setAdapter(adapter);
        tv_current_integral = getView(R.id.tv_current_integral);
    }

    @Override
    protected void setListener() {
        super.setListener();
        left_tv.setOnClickListener(this);
    }

    @Override
    protected void getDatas() {
        super.getDatas();
        getIntegralRecord();
    }

    private void getIntegralRecord() {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "IntegralRecord");
        hm.put("userid", MyPreference.getString(this, Constant.USERID));
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                List<IntegralRecord> list;
                try {
                    list = JsonUtil.jsonToIntegralRecords(result.toJSONString());
                    currentJifen = JsonUtil.jsonToScore(result.toJSONString());
                    tv_current_integral.setText("当前积分：" + currentJifen);
                    adapter.onDataChanged(list);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(IntegralRecordActivity.this,
                        result.errorString);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        finish();
    }

    /**
     * 获取当前积分
     * 
     * @param list
     */
    private int getCurrentIntegral(List<IntegralRecord> list) {
        int sum = 0;
        for (IntegralRecord ir : list) {
            sum += Integer.parseInt(ir.getScore());
        }
        return sum;
    }

    private List<IntegralRecord> TestData() {
        List<IntegralRecord> list = new ArrayList<IntegralRecord>();
        for (int i = 0; i < 10; i++) {
            IntegralRecord ir = new IntegralRecord();
            ir.setDatetime("20130111");
            ir.setRecorded("0000");
            ir.setScore("-100");
            ir.setSource("积分来源");
            ir.setUserid("001");
            list.add(ir);
        }
        return list;
    }
}
