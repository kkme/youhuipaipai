package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.MyMembercardAdapter;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.MemberCard;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.HttpUtil.CallbackListener;
import frame.util.ToastUtil;

public class MyMembercardActivity extends BaseActivity {
    private TextView left_tv, edit_tv;
    private ListView lv;
    private String merchantid;
    private String userid;
    private MyMembercardAdapter adapter;
    ArrayList<MemberCard> memberCards;

    @Override
    protected int getContentViewID() {
        return R.layout.mymembercard;
    }

    @Override
    protected void initView() {
        super.initView();
        left_tv = getView(R.id.left_tv);
        edit_tv = getView(R.id.edit_tv);
        lv = getView(R.id.listview);
        adapter = new MyMembercardAdapter(memberCards, this);
        lv.setAdapter(adapter);
        userid = MyPreference.getString(this, Constant.USERID);
    }

    @Override
    protected void setListener() {
        super.setListener();
        left_tv.setOnClickListener(this);
        edit_tv.setOnClickListener(this);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                MemberCard mc = (MemberCard) parent.getAdapter().getItem(
                        position);
                Intent intent = new Intent(MyMembercardActivity.this,
                        MyMemberCardDetailActivity.class);
                intent.putExtra("memberid", mc.getId());
                startActivity(intent);
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
        case R.id.edit_tv:
            if ("删除".equals(edit_tv.getText().toString())) {
                edit_tv.setText("完成");
                adapter.edit(true);
            } else {
                edit_tv.setText("删除");
                adapter.edit(false);
                if (adapter.selectList.size() != 0) {
                    StringBuffer sb = new StringBuffer();
                    for (String s : adapter.selectList) {
                        sb.append(s).append(",");
                    }
                    ModifyDialog(sb.toString().substring(0,
                            sb.toString().length() - 1));
                }
            }
            break;
        }
    }

    /**
     * 确认修改的对话框
     * 
     * @param ctx
     * @param title
     * @param content
     */
    protected void ModifyDialog(final String string) {
        AlertDialog.Builder builder = new Builder(this);
        builder.setMessage("你要确认删除吗？");

        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // TODO
                        // 删除选中的条目
                        // deleteMsg();
                        deleteMembercard(string);
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
    protected void getDatas() {
        getMyMembercard();
    }

    private void getMyMembercard() {
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "GetMemberList2");
        hm.put("merchantid", merchantid);
        hm.put("userid", userid);
        hu.post(this, hm, new CallbackListener() {
            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                try {
                    memberCards = JsonUtil.jsonToMemberCards(result
                            .toJSONString());
                    adapter = new MyMembercardAdapter(memberCards,
                            MyMembercardActivity.this);
                    lv.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(MyMembercardActivity.this,
                        result.errorString);
            }
        });
    }

    public void deleteMembercard(String merchantid) {// 删除我的会员卡
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "DelMember");
        hm.put("memberid", merchantid);
        hm.put("userid", userid);
        hu.post(this, hm, new CallbackListener() {
            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                deleteItem();
                ToastUtil.showShort(MyMembercardActivity.this, "删除成功");
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(MyMembercardActivity.this,
                        result.errorString);
            }
        });
    }

    private void deleteItem() {
        if (null != memberCards) {
            ArrayList<MemberCard> cards = new ArrayList<MemberCard>();
            for (MemberCard comment : memberCards) {
                if (comment.isFlag()) {
                    // memberCards.remove(comment);
                    cards.add(comment);
                }
            }
            memberCards.removeAll(cards);
            adapter.onDataChanged(memberCards);
        }
    }
}
