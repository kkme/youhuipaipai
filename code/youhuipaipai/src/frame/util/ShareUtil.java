package frame.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.example.youhuipaipai.R;

import frame.commom.AppContext;

/**
 * @类名:ShareUtil.java
 * 
 * @类描述:分享工具类
 * 
 * @Version:1.0
 */
public class ShareUtil {
	public static String contentString="我正在用生活系客户端 ，衣食住行一步到位，吃喝玩乐样样精通！你也试试哈！";
    public static String copyPicFromAssets(Context context, String fileName,
            String path) {
        try {
            /** 创建APK新的目录 */
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            InputStream is = context.getAssets().open(fileName + ".png");
            File file = new File(path + fileName + ".png");
            /** 如果此APK已存在不在写入,直接返回 */
            if (file.exists()) {
                return file.getPath();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            /** 写入数据 */
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            /** 返回新APK路劲 */
            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 新浪分享
     * 
     * @param content
     * @param image
     * @param handler
     */
    public static void sinaShare(final Context context, String content,
            String image, final Handler handler) {
        Platform.ShareParams sp = new SinaWeibo.ShareParams();
        sp.text = contentString;
        if ("".equals(image)) {
            String path = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/pic/";
            sp.imagePath = copyPicFromAssets(context, "ic_launcher", path);
        }

        Platform weibo = ShareSDK.getPlatform(AppContext.curContext,
                SinaWeibo.NAME);
        weibo.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                Log.e("info", "onError");
                Log.e("info", "onError" + arg2);
                handler.sendEmptyMessage(Constant.ERROR);
            }

            @Override
            public void onComplete(Platform arg0, int arg1,
                    HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                Log.e("info", "onComplete");
                handler.sendEmptyMessage(Constant.SUCCESS);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
                Log.e("info", "onCancel");
                handler.sendEmptyMessage(Constant.CANCLE);
            }
        }); // 设置分享事件回调
        // 执行图文分享
        weibo.share(sp);

    }

    /**
     * 腾讯微博
     * 
     * @param context
     * @param content
     * @param image
     * @param handler
     */
    public static void sinaTencent(final Context context, String content,
            String image, final Handler handler) {
        Platform.ShareParams sp = new SinaWeibo.ShareParams();
        sp.text = contentString;
        // sp.imagePath = image;
        if ("".equals(image)) {
            String path = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/pic/";
            sp.imagePath = copyPicFromAssets(context, "ic_launcher", path);
        }

        Platform weibo = ShareSDK.getPlatform(AppContext.curContext,
                TencentWeibo.NAME);
        weibo.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                Log.e("info", "onError");
                Log.e("info", "onError" + arg2);
                handler.sendEmptyMessage(Constant.ERROR);
            }

            @Override
            public void onComplete(Platform arg0, int arg1,
                    HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                Log.e("info", "onComplete");
                handler.sendEmptyMessage(Constant.SUCCESS);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
                Log.e("info", "onCancel");
                handler.sendEmptyMessage(Constant.CANCLE);
            }
        }); // 设置分享事件回调
        // 执行图文分享
        weibo.share(sp);

    }

    /**
     * 微信分享
     * 
     * @param contnet
     * @param url
     * @param handler
     */
    public static void weiXinShare1(final Context context, String url,
            String contnet, String pic, final Handler handler, boolean flag) {
        Platform weibo = ShareSDK.getPlatform(AppContext.curContext,
                WechatMoments.NAME);
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.title = "生活系";
        sp.text = contentString;
        sp.shareType = Platform.SHARE_IMAGE;
        if (null != url) {
            sp.url = url;
        } else
            sp.url = "";
        if (flag) {
            if (null != pic) {
                sp.imageUrl = pic;
            } else {
                sp.imageUrl = "";
            }
        } else {
            sp.imageData = BitmapFactory.decodeResource(null,
                    R.drawable.ic_launcher);
        }
        // sp.i
        weibo.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                Log.e("info", "onError");
                Log.e("info", "onError" + arg2);
                handler.sendEmptyMessage(Constant.ERROR);
            }

            @Override
            public void onComplete(Platform arg0, int arg1,
                    HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                Log.e("info", "onComplete");
                handler.sendEmptyMessage(Constant.SUCCESS);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
                Log.e("info", "onCancel");
                handler.sendEmptyMessage(Constant.CANCLE);
            }
        });
        weibo.share(sp);

    }

    /**
     * 微信分享
     * 
     * @param contnet
     * @param url
     * @param handler
     */
    public static void weiXinShare2(String url, String contnet, String pic,
            final Handler handler, final Context context) {
        Platform weibo = ShareSDK.getPlatform(AppContext.curContext,
                WechatMoments.NAME);
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.title = "生活系";
        sp.text = contentString;
        sp.shareType = Platform.SHARE_IMAGE;
        if (null != url) {
            sp.url = url;
        } else
            sp.url = "";
        sp.imageData = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_launcher);
        // sp.i
        weibo.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                Log.e("info", "onError");
                Log.e("info", "onError" + arg2);
                handler.sendEmptyMessage(Constant.ERROR);
            }

            @Override
            public void onComplete(Platform arg0, int arg1,
                    HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                Log.e("info", "onComplete");
                handler.sendEmptyMessage(Constant.CANCLE);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
                Log.e("info", "onCancel");
                ToastUtil.showShort(context, "分享取消");
                handler.sendEmptyMessage(Constant.SUCCESS);
            }
        });
        weibo.share(sp);

    }
    public static void weiXinShareFriend(String url, String contnet, String pic,
    		final Handler handler, final Context context) {
    	Platform weibo = ShareSDK.getPlatform(AppContext.curContext,
    			Wechat.NAME);
    	WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
    	sp.title = "生活系";
    	sp.text = contentString;
    	sp.shareType = Platform.SHARE_IMAGE;
    	if (null != url) {
    		sp.url = url;
    	} else
    		sp.url = "";
    	sp.imageData = BitmapFactory.decodeResource(context.getResources(),
    			R.drawable.ic_launcher);
    	// sp.i
    	weibo.setPlatformActionListener(new PlatformActionListener() {
    		
    		@Override
    		public void onError(Platform arg0, int arg1, Throwable arg2) {
    			// TODO Auto-generated method stub
    			Log.e("info", "onError");
    			Log.e("info", "onError" + arg2);
    			handler.sendEmptyMessage(Constant.ERROR);
    		}
    		
    		@Override
    		public void onComplete(Platform arg0, int arg1,
    				HashMap<String, Object> arg2) {
    			// TODO Auto-generated method stub
    			Log.e("info", "onComplete");
    			handler.sendEmptyMessage(Constant.CANCLE);
    		}
    		
    		@Override
    		public void onCancel(Platform arg0, int arg1) {
    			// TODO Auto-generated method stub
    			Log.e("info", "onCancel");
    			ToastUtil.showShort(context, "分享取消");
    			handler.sendEmptyMessage(Constant.SUCCESS);
    		}
    	});
    	weibo.share(sp);
    	
    }

    /**
     * 短信分享
     * 
     * @param content
     */
    public static void shareByMessage(String content) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        // sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        sendIntent.putExtra("sms_body", content);
        sendIntent.setType("vnd.android-dir/mms-sms");
        AppContext.curContext.startActivity(sendIntent);
    }

    /**
     * 邮件分享
     * 
     * @param content
     */
    public static void shareByEmail(String content) {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("plain/text");
        String emailSubject = "共享软件";

        // 设置邮件默认地址
        // email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);
        // 设置邮件默认标题
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);
        // 设置要默认发送的内容
        email.putExtra(android.content.Intent.EXTRA_TEXT, content);
        AppContext.curContext.startActivity(email);
    }

    /**
     * 
     * @Title: sinaAuthor
     * @Description: TODO(新浪登录)
     * @param @param context 上下文
     * @return void 返回类型
     * @throws
     * @author
     */
    public static void sinaAuthor(final Context context, final Handler handler) {
        Platform weibo = ShareSDK.getPlatform(context, SinaWeibo.NAME);
        if (weibo.isValid()) {
            weibo.removeAccount();
        }
        weibo.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable t) {
                // TODO Auto-generated method stub
                t.printStackTrace();
                handler.sendEmptyMessage(Constant.ERROR);
            }

            @Override
            public void onComplete(Platform weibo, int arg1,
                    HashMap<String, Object> res) {
                // TODO Auto-generated method stub
                // 操作成功的处理代码
                String id = weibo.getDb().getUserId();
                String token = weibo.getDb().getToken();
                Log.i("info", "userid --------------  " + id);
                Log.i("info", "token  --------------  " + token);
                // String name = weibo.getDb().get("nickname");
                // String name = (String) res.get("name");
                MyPreference.putString(context,
                        com.example.youhuipaipai.application.Constant.TOKEN,
                        token);
                MyPreference.putString(context,
                        com.example.youhuipaipai.application.Constant.SHARE_ID,
                        id);
                handler.sendEmptyMessage(Constant.SUCCESS);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
                handler.sendEmptyMessage(Constant.CANCLE);
            }
        });
        weibo.showUser(null);
    }

    /**
     * QQ登陆
     * 
     * @param context
     */
    public static void QQAuthor(final Context context, final Handler handler) {
        Platform weibo = ShareSDK.getPlatform(context, QZone.NAME);
        if (weibo.isValid()) {
            weibo.removeAccount();
        }
        weibo.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable t) {
                // TODO Auto-generated method stub
                t.printStackTrace();
                handler.sendEmptyMessage(Constant.ERROR);
            }

            @Override
            public void onComplete(Platform weibo, int arg1,
                    HashMap<String, Object> res) {
                // TODO Auto-generated method stub
                // 操作成功的处理代码
                String id = weibo.getDb().getUserId();
                // weibo.get
                String token = weibo.getDb().getToken();
                Log.i("info", "userid --------------  " + id);
                Log.i("info", "token  --------------  " + token);
                // String name = weibo.getDb().get("nickname");
                // String name = (String) res.get("name");
                MyPreference.putString(context,
                        com.example.youhuipaipai.application.Constant.TOKEN,
                        token);
                MyPreference.putString(context,
                        com.example.youhuipaipai.application.Constant.SHARE_ID,
                        id);
                handler.sendEmptyMessage(Constant.SUCCESS);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
                handler.sendEmptyMessage(Constant.CANCLE);
            }
        });
        weibo.showUser(null);
    }

}
