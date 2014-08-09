package frame.util;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import baidu.LocationUtil;
import baidu.LocationUtil.OnLocationGetListener;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.example.youhuipaipai.application.Constant;

import frame.http.HttpImpl;
import frame.http.bean.FailureResult;
import frame.http.bean.HttpResultBean;

public class HttpUtil {
    private Context context;
    public static final int DID_SUCCEED = 20;
    private CallbackListener mL;
    private static final int SUCCESS = 1;
    private static final String URL = Constant.HOST_URL;
    private JSONObject json;
    private double longitude, latitude;

    private boolean flag = false;// 主URL还是修改用户头像URL
    
    public HttpUtil(){
    	
    }

    private void setNormalParams(Map<String, String> hm) {
    	String userId = MyPreference.getString(context, Constant.USERID);
		if (TextUtils.isEmpty(userId))
			userId = "0";
		if(!hm.containsKey("userid")){
			hm.put("userid", userId);
		}
//		hm.put("longitude", 87.620867 + "");
//		hm.put("latitude", 43.820633+ "");
		if(!hm.containsKey("longitude"))
			hm.put("longitude", longitude + "");
		if(!hm.containsKey("latitude"))
			hm.put("latitude", latitude + "");
		//07-08 10:31:21.468: E/info(338): 传入参数 ---------->  {"userid":"56","requestCommand":"GetCouponsDetail","longitude":"87.620867","latitude":"43.820633","couponsid":"18378"}

        JSONObject obj = new JSONObject();
        Iterator iter = hm.entrySet().iterator();
        while (iter.hasNext()) {
            try {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                obj.put(key, URLEncoder.encode(value, "utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.json = obj;
    }

    public void post(Context context, final Map<String, String> params,
            final CallbackListener l) {
        this.context = context;
        LocationUtil.getInstance(context).getLocation(new OnLocationGetListener() {

            @Override
            public void onGetLocation(BDLocation location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                setNormalParams(params);
                mL = l;
                run();
            }
        });
    }

    public void postWithFile(Context context, String url, Map params, CallbackListener l) {
        this.context = context;
        mL = l;
        runWithFile(params, url);
        Log.e("info", "传入参数 ---------->  " + params);
    }

    public void run() {
        new Thread() {
            public void run() {
                HttpImpl imp = new HttpImpl();
                HttpResultBean bean;
                bean = imp.doPostJSON(URL, json);
                sendMessage(bean);
            };
        }.start();
    }

    public void runWithFile(final Map map, final String url) {
        new Thread() {
            public void run() {
                HttpImpl imp = new HttpImpl();
                HttpResultBean bean;
                bean = imp.doPostMapWithFile(url, map);
                sendMessage(bean);
            };
        }.start();
    }

    private void sendMessage(HttpResultBean result) {
        Message message = Message.obtain(handler, DID_SUCCEED);
        message.obj = result;
        handler.sendMessage(message);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            HttpResultBean bean = (HttpResultBean) message.obj;
            if (bean == null) {
                ToastUtil.showShort(context, "网络出错了");
                FailureResult result = new FailureResult();
                result.errorString = "网络出错了";
                if(mL != null)
                	mL.onFailed(result);
            } else {
                // Log.e("info", "返回结果 ---------------->  " + bean.getString());
                com.alibaba.fastjson.JSONObject obj = null;
                try {
                    obj = bean.getJSONObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (obj == null) {
                    ToastUtil.showShort(context, "网络出错了");
                    FailureResult result = new FailureResult();
                    result.errorString = "服务器返回异常";
                    if(mL != null)
                    	mL.onFailed(result);
                } else {
                    try {
                        int result = obj.getIntValue("result");
                        if (result == SUCCESS) {
                        	if(mL != null)
                        		mL.onSuccess(obj);
                        } else {
                            // mL.onFailed(JSON.parseObject(obj.toString(),
                            // FailureResult.class));
                        	
                            FailureResult result1 = JSON.parseObject(obj.toString(), FailureResult.class);
                            result1.errorString = "服务器返回异常";
                            if(mL != null)
                            	mL.onFailed(result1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        FailureResult result = new FailureResult();
                        result.errorString = "服务器返回异常";
                        if(mL != null)
                        	mL.onFailed(result);
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    public interface CallbackListener {
        public void onSuccess(com.alibaba.fastjson.JSONObject result);

        public void onFailed(FailureResult result);
    }
}