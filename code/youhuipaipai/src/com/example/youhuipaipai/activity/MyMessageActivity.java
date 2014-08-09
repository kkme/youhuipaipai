package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.MymessageAdapter;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.MyComment;
import com.example.youhuipaipai.entity.Mymessage;
import com.example.youhuipaipai.view.XListView;
import com.example.youhuipaipai.view.XListView.IXListViewListener;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.TimeUtil;
import frame.util.ToastUtil;

public class MyMessageActivity extends BaseActivity implements
        IXListViewListener {
    private TextView left_tv, right_tv;
    private XListView lv;
    private MymessageAdapter adapter;
    private boolean flag = true;// true 表示下拉刷新 FALSE 表示加载更多
    private int index = 1;
    ArrayList<Mymessage> list;

    @Override
    protected int getContentViewID() {
        return R.layout.mymessage;
    }

    @Override
    protected void initView() {
        super.initView();
        left_tv = getView(R.id.left_tv);
        right_tv = getView(R.id.right_tv);
        lv = getView(R.id.listview);
        // TODO
        list = new ArrayList<Mymessage>();
        adapter = new MymessageAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setXListViewListener(this);
        lv.setPullLoadEnable(true);
        lv.setPullRefreshEnable(true);
    }

    @Override
    protected void setListener() {
        super.setListener();
        left_tv.setOnClickListener(this);
        right_tv.setOnClickListener(this);
    }

    @Override
    protected void getDatas() {
        super.getDatas();
        // TODO
        getList();
    }

    private void onLoad() {
        lv.stopRefresh();
        lv.stopLoadMore();
        lv.setRefreshTime(TimeUtil.getNowTime());
    }

    private void getList() {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "MyMessage");
        hm.put("userid", MyPreference.getString(this, Constant.USERID));
        hm.put("index", index + "");
        hm.put("sign", "20");
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                try {
                    ArrayList<Mymessage> message = JsonUtil
                            .jsonToMymessages(result.toJSONString());
                    if (flag) {// 下拉刷新
                        if (null != message && message.size() > 0) {
                            MyPreference.putString(MyMessageActivity.this,
                                    Constant.MESSAGEID, message.get(0)
                                            .getMessageid());
                        }
                        list.clear();
                    }
                    list.addAll(message);
                    adapter.onDataChanged(list);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                onLoad();
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                onLoad();
                ToastUtil.showShort(MyMessageActivity.this, result.errorString);
            }
        });
    }

    public void deleteMsg() {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "DelMyMessage");
        StringBuffer sb = new StringBuffer();
        for (String s : adapter.msgids) {
            sb = sb.append(s).append(",");
        }
        sb = sb.deleteCharAt(sb.length() - 1);// 去掉最后一个”，“
        hm.put("messageid", sb.toString());
        hm.put("userid", MyPreference.getString(this, Constant.USERID));
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                deleteItem();
                adapter.notifyDataSetChanged();
                ToastUtil.showShort(MyMessageActivity.this, "操作成功！");
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(MyMessageActivity.this, result.errorString);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.left_tv:
            finish();
            break;
        case R.id.right_tv:
            if (right_tv.getText().equals("删除")) {
                adapter.setDelbtnVisible(true);
                right_tv.setText("完成");
            } else {
                adapter.setDelbtnVisible(false);
                right_tv.setText("删除");
                if (null != adapter.msgids && adapter.msgids.size() > 0) {
                    ModifyDialog();
                }
            }
            break;
        }
    }

    /**
     * 测试数据
     * 
     * @return
     */
    private List<Mymessage> TestData() {
        List<Mymessage> list = new ArrayList<Mymessage>();
        for (int i = 0; i < 10; i++) {
            Mymessage m = new Mymessage();
            m.setContent("2");
            m.setDatetime("20140321");
            m.setMessageid("id" + i);
            m.setTitle("2");
            list.add(m);

        }
        return list;
    }

    private void deleteItem() {
        if (null != list) {
            for (Mymessage comment : list) {
                if (comment.isFlag()) {
                    list.remove(comment);
                }
            }
        }
    }

    /**
     * 确认修改的对话框
     * 
     * @param ctx
     * @param title
     * @param content
     */
    protected void ModifyDialog() {
        AlertDialog.Builder builder = new Builder(this);
        builder.setMessage("你要确认删除吗？");

        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // TODO
                        // 删除选中的条目
                        deleteMsg();
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
    public void onRefresh() {
        // TODO Auto-generated method stub
        flag = true;
        index = 1;
        getList();
    }

    @Override
    public void onLoadMore() {
        // TODO Auto-generated method stub
        flag = false;
        index++;
        getList();
    }

}
