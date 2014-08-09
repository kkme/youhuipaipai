package com.example.youhuipaipai.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import baidu.LocationUtil;
import baidu.LocationUtil.OnLocationGetListener;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;

import frame.http.bean.FailureResult;
import frame.util.BitmapUtil;
import frame.util.HeadSelecteUtil;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.Log;
import frame.util.MyPreference;
import frame.util.ToastUtil;

//点评
public class RemarkActivity extends BaseActivity {
    private TextView left_tv, remainnumber_tv, remarkscore_tv;
    private EditText remark_et;
    private RatingBar ratingbar;
    SeekBar serve_seekbar, enviroment_seekbar;
    Button remark_btn;
    private Button addPic;

    private String merchantid;

    private int fw = 100, hj = 100;

    private HeadSelecteUtil headUtil;
    private Uri mImageCaptureUri;
    private List<String> pics = new ArrayList<String>();
    private ImageView pic1, pic2, pic3;

    private double longitude;
    private double latitude;

    @Override
    protected int getContentViewID() {
        return R.layout.signin_remark;
    }

    @Override
    protected void initView() {
        super.initView();
        merchantid = getIntent().getStringExtra("merchantid");
        left_tv = getView(R.id.left_tv);
        remark_et = getView(R.id.remark_et);
        remainnumber_tv = getView(R.id.remainnumber_tv);
        remarkscore_tv = getView(R.id.remarkscore_tv);
        remark_btn = getView(R.id.remark_btn);
        ratingbar = getView(R.id.ratingbar);
        serve_seekbar = getView(R.id.serve_seekbar);
        enviroment_seekbar = getView(R.id.enviroment_seekbar);
        serve_seekbar.setProgress(70);
        enviroment_seekbar.setProgress(70);
        addPic = getView(R.id.takepicture_btn);

        headUtil = new HeadSelecteUtil(CTX);
        pic1 = getView(R.id.iv_add_comment_pic1);
        pic2 = getView(R.id.iv_add_comment_pic2);
        pic3 = getView(R.id.iv_add_comment_pic3);

        changeNum(serve_seekbar, 70);
        changeNum(enviroment_seekbar, 70);
    }

    public void changeNum(SeekBar bar, int progress) {
        View cacheView = LayoutInflater.from(this).inflate(
                R.layout.custom_thumb, null);
        TextView text = (TextView) cacheView.findViewById(R.id.thumb_num);
        text.setText(progress + "");
        Bitmap b = BitmapUtil.getBitmapFromView(cacheView);
        Drawable d = BitmapUtil.bitmapToDrawableByBD(this, b);
        bar.setThumb(d);
    }

    private Handler handler = new Handler();

    private Runnable serviceR = new Runnable() {

        @Override
        public void run() {
            changeNum(serve_seekbar, fw);
        }
    };
    private Runnable enR = new Runnable() {

        @Override
        public void run() {
            changeNum(enviroment_seekbar, hj);
        }
    };

    @Override
    protected void setListener() {
        super.setListener();
        left_tv.setOnClickListener(this);
        remark_btn.setOnClickListener(this);
        addPic.setOnClickListener(this);
        remark_et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                remainnumber_tv.setText("还可以输入" + (140 - s.length()) + "个字");
            }
        });
        ratingbar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                    boolean fromUser) {
                remarkscore_tv.setText(((int) ratingBar.getRating()) + "星");

            }
        });
        serve_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar seekbar, int progress,
                    boolean arg2) {
                fw = progress;
                handler.post(serviceR);
            }
        });

        enviroment_seekbar
                .setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar arg0) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar arg0) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekbar,
                            int progress, boolean arg2) {
                        hj = progress;
                        handler.post(enR);
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
        case R.id.remark_btn:
            final String content = remark_et.getText().toString();
            if (TextUtils.isEmpty(content)) {
                ToastUtil.showLong(CTX, "请输入点评内容");
                return;
            }
            final String userId = MyPreference.getString(this, Constant.USERID);
            if (TextUtils.isEmpty(userId)) {
                startActivity(new Intent(RemarkActivity.this, LogActivity.class));
                ToastUtil.showLong(CTX, "请先登录");
                return;
            }
            final String star = ratingbar.getRating() + "";
            LocationUtil.getInstance(this).getLocation(
                    new OnLocationGetListener() {

                        @Override
                        public void onGetLocation(BDLocation location) {
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            startComment(content, star.substring(0, 1),
                                    fw + "", hj + "", userId);
                        }
                    });
            break;
        case R.id.takepicture_btn:
            getPhoto();
            break;
        }
    }

    private void startComment(String content, String star, String fw,
            String hj, String userId) {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("merchantid", merchantid);
        hm.put("content", content);
        hm.put("start", star);
        hm.put("fw", fw);
        hm.put("hj", hj);
        hm.put("longitude", longitude);
        hm.put("Latitude", latitude);
        hm.put("userid", userId);
        for (int i = 0; i < pics.size(); i++) {
            hm.put("file" + i, new File(pics.get(i)));
        }
        hu.postWithFile(this, Constant.COMMENTS_URL, hm,
                new CallbackListener() {

                    @Override
                    public void onSuccess(JSONObject result) {
                        dismissProgressDialog();
                        int r = result.getIntValue("result");
                        String msg = result.getString("message");
                        ToastUtil.showLong(CTX, msg);
                        if (r == 1) {
                        	setResult(RESULT_OK);
                            finish();
                        }
                    }

                    @Override
                    public void onFailed(FailureResult result) {
                        dismissProgressDialog();
                        ToastUtil
                                .showShort(RemarkActivity.this, result.message);
                    }
                });
    }

    private void getPhoto() {
        if (pics.size() >= 3) {
            ToastUtil.showLong(CTX, "最多添加三张图片");
            return;
        }
        Dialog alertDialog = new AlertDialog.Builder(this).setTitle("选择获取图片方式")
                .setPositiveButton("拍照", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File f = new File(Environment
                                .getExternalStorageDirectory()
                                + "/youhuipaipai");
                        if (!f.exists())
                            f.mkdirs();
                        mImageCaptureUri = headUtil.pickFromCamera(Environment
                                .getExternalStorageDirectory()
                                + "/youhuipaipai");
                    }
                }).setNegativeButton("图库", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        headUtil.pickFromFile();
                    }
                }).create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Log.d("", "从相机获取 uri = " + mImageCaptureUri);
            headUtil.doCrop(mImageCaptureUri);
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mImageCaptureUri = data.getData();
            Log.d("", "从相册选取 uri = " + mImageCaptureUri);
            headUtil.doCrop(mImageCaptureUri);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                try {
                    File file = new File(
                            Environment.getExternalStorageDirectory()
                                    + "/youhuipaipai");
                    if (!file.exists())
                        file.mkdirs();
                    File f = new File(Environment.getExternalStorageDirectory()
                            + "/youhuipaipai", "avatar_result"
                            + String.valueOf(System.currentTimeMillis())
                            + ".png");
                    FileOutputStream out = new FileOutputStream(f);
                    photo.compress(Bitmap.CompressFormat.PNG, 80, out);

                    String imgPath = f.getAbsolutePath();
                    Log.d("", "裁剪后的头像路径：" + imgPath);
                    pics.add(imgPath);
                    switch (pics.size()) {
                    case 1:
                        pic1.setVisibility(View.VISIBLE);
                        pic1.setImageBitmap(BitmapFactory.decodeFile(imgPath));
                        break;
                    case 2:
                        pic2.setVisibility(View.VISIBLE);
                        pic2.setImageBitmap(BitmapFactory.decodeFile(imgPath));
                        break;
                    case 3:
                        pic3.setVisibility(View.VISIBLE);
                        pic3.setImageBitmap(BitmapFactory.decodeFile(imgPath));
                        addPic.setVisibility(View.GONE);
                        break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
