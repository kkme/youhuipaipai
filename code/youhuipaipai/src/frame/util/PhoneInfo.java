package frame.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * 
 * @项目名:
 * 
 * @类名:PhoneInfo.java
 * 
 * @创建人:cuijianzhi
 * 
 * @类描述:获取手机信息
 * 
 * @date:2013-9-13
 * 
 * @Version:1.0
 */
public class PhoneInfo {

    public static String getPhoneInfo(Activity mActivity) {
        StringBuffer stringBuffer = new StringBuffer();
        /** 手机制造商 */
        String brand;
        /** 手机型号 */
        String model;
        /** imei */
        String imei;
        /** 系统版本 */
        String release;
        /** 网络名称（中国移动 联通 电信） */
        String networkoperatorname;
        /** 屏幕像素 */
        String screensize;

        try {
            brand = Build.BRAND; // 手机制造商
        } catch (Exception e) {
            brand = "NULL";
        }

        try {
            model = Build.MODEL; // 手机型号
        } catch (Exception e) {
            model = "NULL";
        }
        TelephonyManager tm = (TelephonyManager) mActivity
                .getSystemService(Context.TELEPHONY_SERVICE);
        try {
            imei = tm.getDeviceId(); // imei
        } catch (Exception e) {
            imei = "NULL";
        }
        try {
            networkoperatorname = tm.getNetworkOperatorName();
        } catch (Exception e) {
            networkoperatorname = "NULL";
        }
        try {
            release = Build.VERSION.RELEASE; // 手机系统版本
        } catch (Exception e) {
            release = "NULL";
        }
        try {
            DisplayMetrics dm = new DisplayMetrics();
            mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int widthPixels = dm.widthPixels;
            int heightPixels = dm.heightPixels;
            screensize = Integer.toString(widthPixels) + "*"
                    + Integer.toString(heightPixels); // 屏幕分配率
        } catch (Exception e) {
            screensize = "NULL";
        }

        stringBuffer.append("手机制造商:" + brand + "手机型号:" + model + "系统版本:"
                + release + "imei:" + imei + "网络名称:" + networkoperatorname
                + "屏幕像素:" + screensize);
        return stringBuffer.toString();
    }
}
