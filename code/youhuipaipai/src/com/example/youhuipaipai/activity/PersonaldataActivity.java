package com.example.youhuipaipai.activity;

import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.db.UserDB;
import com.example.youhuipaipai.entity.Area;
import com.example.youhuipaipai.entity.BaseArea;
import com.example.youhuipaipai.entity.User;
import com.example.youhuipaipai.view.OnChangedListener;
import com.example.youhuipaipai.view.SlipButton;
import com.example.youhuipaipai.view.WheelViewPop;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.MyPreference;
import frame.util.HttpUtil.CallbackListener;
import frame.util.ToastUtil;

public class PersonaldataActivity extends BaseActivity implements
        OnChangedListener {
    private TextView left_tv, city_tv, right_tv;
    private EditText nickname_et, mail_et;
    private Button save_btn;
    private SlipButton sex_sb;
    private User user;
    private String sex;
    private RelativeLayout city_rl;
    private String[]areaStrings;
    @Override
    protected int getContentViewID() {
        return R.layout.personaldata;
    }

    @Override
    protected void initView() {
        super.initView();
        city_rl = getView(R.id.city_rl);
        left_tv = getView(R.id.left_tv);
        right_tv = getView(R.id.right_tv);
        save_btn = getView(R.id.save_btn);
        user = (User) getIntent().getSerializableExtra("user");
        sex_sb = getView(R.id.sex_sb);
        nickname_et = getView(R.id.nickname_et);
        city_tv = getView(R.id.city_tv);
        mail_et = getView(R.id.mail_et);
        nickname_et.setText(user.getNickname());
        city_tv.setText(user.getThecity());
        mail_et.setText(user.getMail());
        if ("2".equals(user.getSex())) {
            sex_sb.NowChoose = false;
            sex = "2";
        } else {
            sex_sb.NowChoose = true;
            sex = "1";
        }
        sex_sb.SetOnChangedListener(this);

    }

    @Override
    protected void setListener() {
        super.setListener();
        left_tv.setOnClickListener(this);
        right_tv.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        city_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.left_tv:
            finish();
            break;
        case R.id.right_tv:
            goByIntent(ChangePwdActivity.class, false);
            break;
        case R.id.save_btn:
            saveData();
            break;
        case R.id.city_rl:
        	getArea();
            break;
        }
    }
    
    /**
	 * 获得地区列表
	 */
	private void getArea(){
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "GetAreaList");
		hu.post(this, hm, new CallbackListener() {
			@Override
			public void onSuccess(JSONObject result) {
				BaseArea area=JSON.parseObject(result.toJSONString(),BaseArea.class);
				List<Area> listAreas=area.getData();
				int count=listAreas.size();
				areaStrings=new String[count];
				for (int i=0;i<count;i++) {
					areaStrings[i]=listAreas.get(i).getName();
				}
				   WheelViewPop cvp = new WheelViewPop(PersonaldataActivity.this,areaStrings);
		           cvp.show(findViewById(R.id.all_rl));
		           cvp.setTextView(city_tv);
			}
			@Override
			public void onFailed(FailureResult result) {
				ToastUtil.showShort(PersonaldataActivity.this, result.errorString);
			}
		});
	}

    private void saveData() {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "EditUserInfo");
        hm.put("userid", user.getUserid());
        hm.put("nickname", nickname_et.getText().toString());
        hm.put("sex", sex);
        hm.put("thecity", city_tv.getText().toString());
        hm.put("email", mail_et.getText().toString());
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                UserDB ud = new UserDB(PersonaldataActivity.this);
                user.setMail(mail_et.getText().toString().trim());
                user.setNickname(nickname_et.getText().toString().trim());
                user.setSex(sex);
                user.setThecity(city_tv.getText().toString());
                ud.updateValue(user, user.getUserid());
                finish();
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(PersonaldataActivity.this,
                        result.errorString);
            }
        });
    }

    @Override
    public void onChanged(boolean checkState) {
        if (checkState) {
            sex = "1";
        } else {
            sex = "2";
        }
    }
}
