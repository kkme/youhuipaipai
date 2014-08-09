package com.example.youhuipaipai.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import cn.sharesdk.framework.ShareSDK;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import frame.util.ToastUtil;

public class MyApplication extends Application {
	public static BMapManager mBMapManager;
    public MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
       initManager(instance);
        ShareSDK.initSDK(this);
    }
    public  void initManager(Context context){
	    if(null==mBMapManager){
	        mBMapManager=new BMapManager(context);
	    }
	    
//	    mBMapManager.init("vyk6oANDxLfahRQnKbWsgkxU", new MyGeneralListener()); // 外包给的key
	    mBMapManager.init("18oropywaGF3QZPOR4HvGIAH", new MyGeneralListener());	// 公司给的key
//	    mBMapManager.init("rv0v4dNLORN8KyHEZRN9OvWP", new MyGeneralListener());	// 我的自己的key
	    mBMapManager.start();
	}

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
//                ToastUtil.showShort(getApplicationContext(), "您的网络出错啦！");
            } else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                ToastUtil.showShort(getApplicationContext(), "输入正确的检索条件！");
            }
        }

        @Override
        public void onGetPermissionState(int iError) {// key验证
            /*
             * if (iError != 0) { //授权Key错误：
             * ToastUtil.showShort(getApplicationContext(),
             * "请在 DemoApplication.java文件输入正确的授权Key,并检查您的网络连接是否正常！error: "
             * +iError); } else{ ToastUtil.showShort(getApplicationContext(),
             * "key认证成功"); }
             */
        }
    }
}
