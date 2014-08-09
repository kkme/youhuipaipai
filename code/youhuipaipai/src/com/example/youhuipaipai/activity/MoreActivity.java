package com.example.youhuipaipai.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.application.PullService;
import com.example.youhuipaipai.application.UpdateManager;
import com.example.youhuipaipai.view.BgSelectPop;
import com.example.youhuipaipai.view.BgSelectPop.HeadPicListener;
import com.example.youhuipaipai.view.OnChangedListener;
import com.example.youhuipaipai.view.SlipButtonPush;

import frame.util.GetFileSizeUtil;
import frame.util.MyPreference;
import frame.util.ShareUtil;
import frame.util.ToastUtil;

public class MoreActivity extends BaseActivity {

    private TextView tv_back;
    private RelativeLayout passwordRelLay, clearRelLay, updateRelLay,
            sendRelLay, aboutRelLay, shareRelLay, suggestionRelLay;
    SlipButtonPush slipButtonPush;
    LinearLayout ll_more;
    boolean flag = false;
    BgSelectPop bgSelectPop;

    @Override
    protected int getContentViewID() {

        return R.layout.more_home;
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case frame.util.Constant.SUCCESS:
                ToastUtil.showShort(MoreActivity.this, "分享成功");
                break;
            case frame.util.Constant.ERROR:
                ToastUtil.showShort(MoreActivity.this, "分享失败");
                break;
            case frame.util.Constant.CANCLE:
                ToastUtil.showShort(MoreActivity.this, "分享取消");
                break;
            }
        };
    };

    @Override
    protected void initView() {
        super.initView();
        ll_more = getView(R.id.ll_more);
        tv_back = getView(R.id.tv_back);
        passwordRelLay = getView(R.id.passwordRelLay);
        clearRelLay = getView(R.id.clearRelLay);
        updateRelLay = getView(R.id.updateRelLay);
        sendRelLay = getView(R.id.sendRelLay);
        aboutRelLay = getView(R.id.aboutRelLay);
        shareRelLay = getView(R.id.shareRelLay);
        suggestionRelLay = getView(R.id.suggestionRelLay);
        slipButtonPush = (SlipButtonPush) findViewById(R.id.sli);
        if (MyPreference.getBoolean(this, Constant.IS_PUSH)) {
            slipButtonPush.NowChoose = false;
            flag = false;
        } else {
            slipButtonPush.NowChoose = true;
            flag = true;
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        tv_back.setOnClickListener(this);
        passwordRelLay.setOnClickListener(this);
        clearRelLay.setOnClickListener(this);
        updateRelLay.setOnClickListener(this);
        aboutRelLay.setOnClickListener(this);
        shareRelLay.setOnClickListener(this);
        suggestionRelLay.setOnClickListener(this);
        slipButtonPush.SetOnChangedListener(new OnChangedListener() {

            @Override
            public void onChanged(boolean checkState) {
                if (checkState) {
                    flag = true;
                    PullService.startAction(MoreActivity.this);
                } else {
                    flag = false;
                    PullService.stopAction(MoreActivity.this);
                }
                MyPreference.putBoolean(MoreActivity.this, Constant.IS_PUSH,
                        !flag);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
        case R.id.tv_back:
            finish();
            return;
        case R.id.passwordRelLay:
            if (!MyPreference.getBoolean(this, Constant.ISLOGIN)) {
                ToastUtil.showShort(this, "请先登录！");
                return;
            }
            intent.setClass(this, ChangePwdActivity.class);
            break;
        case R.id.clearRelLay:
            promptExit();
            return;
        case R.id.updateRelLay:
            // TODO 检查更新
            ToastUtil.showShort(this, "正在检测新版本，请稍后！");
            updateSoft();
            return;
        case R.id.sendRelLay:
            // TODO
            return;
        case R.id.aboutRelLay:
            intent.setClass(this, AboutusActivity.class);
            break;
        case R.id.shareRelLay:
            // TODO
            share();
            return;
        case R.id.suggestionRelLay:
            intent.setClass(this, FeedbackActivity.class);
            break;
        }
        startActivity(intent);

    }

    /**
     * 检测版本更新
     */
    private void updateSoft() {
        new Thread() {
            public void run() {
                Looper.prepare();
                try {
                    UpdateManager updateManager = new UpdateManager(
                            MoreActivity.this);
                    updateManager.checkUpdate();
                    Looper.loop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 清除缓存
     * 
     * @param con
     */
    public void promptExit() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("提示");
        ab.setMessage("确定清除缓存");
        ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                GetFileSizeUtil.getInstance().delFolder(
                        Environment.getExternalStorageDirectory()
                                + frame.util.Constant.IMG_URL);
                ToastUtil.showShort(MoreActivity.this, "已清除");
            }
        });
        ab.setNegativeButton("取消", null);
        ab.show();
    }

    /**
     * 分享
     */
    private void share() {
        bgSelectPop = new BgSelectPop(this,true);
        bgSelectPop.show(ll_more);
        bgSelectPop.setHeadActionListener(new HeadPicListener() {

            @Override
            public void sendByWeiXin() {
                // TODO Auto-generated method stub
                 ShareUtil.weiXinShare2("", "", "", handler, MoreActivity.this);
            }

            @Override
            public void sendBySina() {
                // TODO Auto-generated method stub
                // ToastUtil.show("新浪分享");
                ShareUtil.sinaShare(MoreActivity.this, "", "", handler);
            }

            @Override
            public void sendByMessage() {
                // TODO Auto-generated method stub
                ShareUtil.sinaTencent(MoreActivity.this, "", "", handler);
            }

            @Override
            public void sendByEmail() {
                // TODO Auto-generated method stub
                // ToastUtil.show("邮件分享");

            }

			@Override
			public void sendByWeiXinFriend() {
				// TODO Auto-generated method stub
				ShareUtil.weiXinShareFriend("", "", "", handler, MoreActivity.this);
			}

			@Override
			public void savePic() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void sendByMyMessage() {
//				 Uri smsToUri = Uri.parse( "smsto:" );  
//				 Intent intent =  new  Intent(Intent.ACTION_VIEW, smsToUri);  
//				 intent.setType( "vnd.android-dir/mms-sms" );
//				 intent.putExtra(Intent.EXTRA_TEXT, "生活系这个应用挺不错的");
//				 startActivity(intent);
				ShareUtil.shareByMessage("生活系这个应用挺不错的");
			}
        });
    }
   

}
