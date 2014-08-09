package com.example.youhuipaipai.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.db.UserDB;
import com.example.youhuipaipai.entity.User;

import frame.http.bean.FailureResult;
import frame.imgtools.ImgShowUtil;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.BitmapUtil;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.ToastUtil;

public class MyActivity extends BaseActivity {
    private ImageView head_iv, sex_iv, modify_iv;
    private TextView right_tv, name_tv, jifen_tv;
    /** 我的积分 */
    private RelativeLayout membercard_rl, coupon_rl, attention_rl,
            jifenexchange_rl, jifenrecord_rl, mycomment_rl, mymessage_rl;
    private Button exitlogin_btn;

    private User user;
    private UserDB userDB;

    private boolean isLocal = false;

    @Override
    protected int getContentViewID() {
        return R.layout.my_home;
    }

    @Override
    protected void initView() {
        super.initView();
        head_iv = getView(R.id.head_iv);
        sex_iv = getView(R.id.sex_iv);
        modify_iv = getView(R.id.modify_iv);
        right_tv = getView(R.id.right_tv);
        name_tv = getView(R.id.name_tv);
        jifen_tv = getView(R.id.jifen_tv);
        membercard_rl = getView(R.id.membercard_rl);
        coupon_rl = getView(R.id.coupon_rl);
        attention_rl = getView(R.id.attention_rl);
        jifenexchange_rl = getView(R.id.jifenexchange_rl);
        jifenrecord_rl = getView(R.id.jifenrecord_rl);
        mycomment_rl = getView(R.id.mycomment_rl);
        mymessage_rl = getView(R.id.mymessage_rl);
        exitlogin_btn = getView(R.id.exitlogin_btn);

        right_tv.setOnClickListener(this);
        modify_iv.setOnClickListener(this);
        membercard_rl.setOnClickListener(this);
        coupon_rl.setOnClickListener(this);
        attention_rl.setOnClickListener(this);
        jifenexchange_rl.setOnClickListener(this);
        jifenrecord_rl.setOnClickListener(this);
        mycomment_rl.setOnClickListener(this);
        mymessage_rl.setOnClickListener(this);
        exitlogin_btn.setOnClickListener(this);

        initUserData();
    }

    @Override
    protected void setListener() {

        super.setListener();
        head_iv.setOnClickListener(this);
    }

    private void initUserData() {
        userDB = new UserDB(MyActivity.this);
        user = null != userDB.getUser() ? userDB.getUser() : new User();
    }

    /**
     * 初始化数据
     */
    private void initData(boolean flag, User user) {
        if (flag && null != user) {
            // if (isLocal) {
            // head_iv.setImageBitmap(BitmapUtil.getRoundedCornerBitmap(
            // BitmapFactory.decodeFile(fileName), 15));
            // } else {
            new ImgShowUtil(user.getHeadimg(), user.getUserid(), 500)
                    .setCoverDown(head_iv, R.drawable.wode_head_bg);
            // }
            name_tv.setText(user.getNickname());
            sex_iv.setVisibility(View.VISIBLE);
            sex_iv.setBackgroundResource("2".equals(user.getSex()) ? R.drawable.wode_male_icon
                    : R.drawable.wode_female_icon);
            jifen_tv.setText("我的积分：" + user.getIntegranum());

        } else {
            head_iv.setBackgroundResource(R.drawable.wode_head_bg);
            name_tv.setText("");
            jifen_tv.setText("");
            sex_iv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyPreference.getBoolean(MyActivity.this, Constant.ISLOGIN)) {
            exitlogin_btn.setText("退出登录");
            exitlogin_btn.setBackgroundResource(R.drawable.blue_btn_selector);
            // user = userDB.getUser();
            // initData(true, user);
            getUserInfo();
        } else {
            exitlogin_btn.setText("请登录");
            exitlogin_btn.setBackgroundResource(R.drawable.red_btn_selector);
            initData(false, user);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
        case R.id.head_iv:// 头像
            if (null != user
                    && MyPreference.getBoolean(MyActivity.this,
                            Constant.ISLOGIN)) {
                getPhoto();
            }
            return;
        case R.id.right_tv:// 更多
            intent.setClass(this, MoreActivity.class);
            // startActivity(intent);
            break;
        case R.id.modify_iv:// 设置
            if (!MyPreference.getBoolean(this, Constant.ISLOGIN)) {
                ToastUtil.showShort(this, "请先登录！");
                return;
            }
            intent.setClass(this, PersonaldataActivity.class);
            intent.putExtra("user", user);
            break;
        case R.id.membercard_rl:// 会员卡
            if (!MyPreference.getBoolean(this, Constant.ISLOGIN)) {
                ToastUtil.showShort(this, "请先登录！");
                return;
            }
            intent.setClass(this, MyMembercardActivity.class);
            break;
        // 我的-优惠券
        case R.id.coupon_rl:
            if (!MyPreference.getBoolean(this, Constant.ISLOGIN)) {
                ToastUtil.showShort(this, "请先登录！");
                return;
            }
            intent.setClass(this, MyCouponActivity.class);
            // startActivity(intent);
            break;

        // 我的-关注
        case R.id.attention_rl:
            if (!MyPreference.getBoolean(this, Constant.ISLOGIN)) {
                ToastUtil.showShort(this, "请先登录！");
                return;
            }
            intent.setClass(this, MyAttentionActivity.class);
            // startActivity(intent);
            break;
        case R.id.jifenexchange_rl:// 积分兑换
            if (!MyPreference.getBoolean(this, Constant.ISLOGIN)) {
                ToastUtil.showShort(this, "请先登录！");
                return;
            }
            intent.setClass(this, IntegralExchangeActivity.class);
            break;
        case R.id.jifenrecord_rl:// 积分记录
            if (!MyPreference.getBoolean(this, Constant.ISLOGIN)) {
                ToastUtil.showShort(this, "请先登录！");
                return;
            }
            intent.setClass(this, IntegralRecordActivity.class);
            break;
        case R.id.mycomment_rl:// 我的评论
            if (!MyPreference.getBoolean(this, Constant.ISLOGIN)) {
                ToastUtil.showShort(this, "请先登录！");
                return;
            }
            intent.setClass(this, MyCommentActivity.class);
            break;
        case R.id.mymessage_rl:// 我的消息
            if (!MyPreference.getBoolean(this, Constant.ISLOGIN)) {
                ToastUtil.showShort(this, "请先登录！");
                return;
            }
            intent.setClass(this, MyMessageActivity.class);
            break;
        case R.id.exitlogin_btn:// 退出
            if (MyPreference.getBoolean(MyActivity.this, Constant.ISLOGIN)) {
                userDB.deleteUser();
                head_iv.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.wode_head_bg));
                MyPreference.putBoolean(MyActivity.this, Constant.ISLOGIN,
                        false);
                MyPreference.putString(MyActivity.this, Constant.USERID, "");
                initData(false, user);
                exitlogin_btn.setText("请登录");
                exitlogin_btn
                        .setBackgroundResource(R.drawable.red_btn_selector);
                ToastUtil.showShort(MyActivity.this, "退出成功！");
            } else {// 登录
                goByIntent(LogActivity.class, false);
            }
            return;
        }
        startActivity(intent);
    }

    private void isCanGo() {
        if (!MyPreference.getBoolean(this, Constant.ISLOGIN)) {
            ToastUtil.showShort(this, "请先登录！");
            return;
        }
    }

    // ---------------------------拍照
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    private File tempFile;
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private String fileName = "";
    private String imageNameSmall;

    private void getPhoto() {
        Dialog alertDialog = new AlertDialog.Builder(this).setTitle("选择获取图片方式")
                .setPositiveButton("拍照", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        // 调用系统的拍照功能
                        Intent intentPaizhao = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        intentPaizhao.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(Environment
                                        .getExternalStorageDirectory(),
                                        "/imageName.jpg")));
                        startActivityForResult(intentPaizhao, PHOTOHRAPH);
                    }
                }).setNegativeButton("图库", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        // 调用系统的相册
                        Intent intentPhotoset = new Intent(Intent.ACTION_PICK,
                                null);
                        intentPhotoset.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                IMAGE_UNSPECIFIED);
                        // 调用剪切功能
                        startActivityForResult(intentPhotoset, PHOTOZOOM);
                    }
                }).create();
        alertDialog.show();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == NONE)
            return;
        // 拍照
        if (requestCode == PHOTOHRAPH) {
            // 设置文件保存路径这里放在跟目录下
            File filePaizhao = createFile(Environment
                    .getExternalStorageDirectory() + "/imageName.jpg");
            startPhotoZoom(Uri.fromFile(filePaizhao));
        }

        if (data == null)
            return;
        // 读取相册缩放图片
        if (requestCode == PHOTOZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                File file = createFile(Constant.IMGCACHE);
                FileOutputStream fileOutputStream;
                imageNameSmall = getStringToday();
                try {
                    fileName = file.getPath() + "/" + imageNameSmall + ".jpg";
                    // images.add(fileName);
                    fileOutputStream = new FileOutputStream(fileName);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100,
                            fileOutputStream);
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // head_iv.setBackground(null);
                //
                head_iv.setImageBitmap(BitmapUtil.getRoundedCornerBitmap(photo,
                        15));
                // head_iv.setBackground(new BitmapDrawable(photo));
                try {
                    // 上传图片
                    // StartHttp(HttpInterFace.uploadHeadImg(PreferenceUtil
                    // .getInstance(this).getUid(), MyUtil
                    // .encodeBase64File(fileName)), callBack,
                    // Constants.SUCCEED_ONE);
                    updateUserImage();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 缩放
     * 
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTORESOULT);
    }

    public File createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();// 创建目录，多级目录
        }
        return file;
    }

    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    private void updateUserImage() {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("userid", user.getUserid());
        hm.put("file", new File(fileName));
        hu.postWithFile(this, Constant.IMAGE_URL, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                ToastUtil.showShort(MyActivity.this, "头像上传成功！");
                isLocal = true;
                try {
                    org.json.JSONObject object = new org.json.JSONObject(result
                            .toJSONString());
                    String img = object.optString("message");
                    user.setHeadimg(img);
                    userDB.updateValue(user, user.getUserid());
                    initData(true, user);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                head_iv.setImageBitmap(BitmapUtil.getRoundedCornerBitmap(
                        BitmapFactory.decodeFile(fileName), 15));
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(MyActivity.this, result.errorString);
            }
        });
    }

    private void getUserInfo() {
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "GetUserInfo");
        hm.put("userid", MyPreference.getString(this, Constant.USERID));
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                try {
                    user = JsonUtil.jsonToUser(result.toJSONString());
                    UserDB db = new UserDB(MyActivity.this);
                    db.updateValue(user, user.getUserid());
                    initData(true, user);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(MyActivity.this, result.errorString);
            }
        });
    }

}
