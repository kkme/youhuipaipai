package com.example.youhuipaipai.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.MemberCard;

public class MemberCardView extends BaseView {
	private RelativeLayout membercard_rl, haveChild, havenotChild;
	private TextView name_tv, memberCardCount_tv,memberCardCount_tv2,membercard_tv, name_tv2, membercard_tv2, cardnumber_tv, validdate_tv;
	private IOnClickListener listener;
	
	public MemberCardView(Context context, IOnClickListener listener) {
		super(context);
		this.listener = listener;
	}

	@Override
	protected int setLayout() {
		return R.layout.nearby_membercarditemview;
	}

	@Override
	protected void initViews() {
		super.initViews();
		membercard_rl = getView(R.id.membercard_rl);
		name_tv = getView(R.id.name_tv);
		membercard_tv = getView(R.id.membercard_tv);
		name_tv2 = getView(R.id.name_tv2);
		membercard_tv2 = getView(R.id.membercard_tv2);
		cardnumber_tv = getView(R.id.cardnumber_tv);
		validdate_tv = getView(R.id.validdate_tv);
		memberCardCount_tv=getView(R.id.validateCount_tv);
		memberCardCount_tv2=getView(R.id.validateCount_tv2);
		havenotChild = getView(R.id.membercard_havenot_child);
		haveChild = getView(R.id.membercard_have_child);
	}

	@Override
	protected void setListeners() {
		super.setListeners();
		membercard_rl.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.membercard_rl:
			if(listener != null)
				listener.click();
			break;

		default:
			break;
		}
	}

	public void setData(MemberCard mc, boolean mustDetail) {
		if("1".equals(mc.getState()) || mustDetail){
			cardnumber_tv.setText("NO:" + mc.getNo());
			validdate_tv.setText("有效期:" + mc.getStarttime().split(" ")[0] + "-" + mc.getEndtime().split(" ")[0]);
			memberCardCount_tv.setText("已领取人数："+mc.getReceiver());
			name_tv.setText(mc.getName());
			membercard_tv.setText(mc.getInfo());
			havenotChild.setVisibility(View.GONE);
			haveChild.setVisibility(View.VISIBLE);
			return;
		}
		if ("2".equals(mc.getState()) || "3".equals(mc.getState())) {
			name_tv2.setText(mc.getName());
			membercard_tv2.setText(mc.getInfo());
			memberCardCount_tv2.setText("已领取人数："+mc.getReceiver());
			havenotChild.setVisibility(View.VISIBLE);
			haveChild.setVisibility(View.GONE);
			membercard_rl.setBackgroundResource(R.drawable.membercard_3);
		}
	}

	public interface IOnClickListener {
		public void click();
	}

}
