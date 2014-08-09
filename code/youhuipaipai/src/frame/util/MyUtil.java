package frame.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @项目名:TongChengHui
 * 
 * @类名:MyUtil.java
 * 
 * @创建人:pangerwei
 * 
 * @类描述:常用工具类
 * 
 * @date:2013-10-28
 * 
 * @Version:1.0
 */
public class MyUtil {

    /**
     * 获取时间差值
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getTime(String startTime, String endTime) {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");// 格式化时间
        try {
            int result = (int) ((d.parse(endTime).getTime() - d
                    .parse(startTime).getTime()) / 1000);
            return result;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static String getNowDate() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        return localTime.format("%Y-%m-%d");
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        return df.format(new Date());
    }

    /**
     * dp转换成px
     * 
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 从相机
     */
    public static void pickFromCamera(File file, Activity context) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri mImageCaptureUri = Uri.fromFile(file);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                mImageCaptureUri);
        try {
            intent.putExtra("return-data", true);
            context.startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从相册中选择
     */
    public static void pickFromFile(Activity context) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(
                Intent.createChooser(intent, "Complete action using"), 3);
    }

    /**
     * 获取中间有*号的手机号
     * 
     * @param phone
     * @return
     */
    public static String getPhonePass(String phone) {
        if (null == phone || "".equals(phone) || phone.length() < 11) {
            return "";
        }

        String passA = phone.substring(0, 3);
        String passB = phone.substring(phone.length() - 3, phone.length());
        return passA + "*****" + passB;
    }

    /**
     * 获取中间有*号的身份证号
     * 
     * @param phone
     * @return
     */
    public static String getPidPass(String pid) {
        if (null == pid || "".equals(pid) || pid.length() < 18) {
            return "";
        }

        String passA = pid.substring(0, 3);
        String passB = pid.substring(pid.length() - 3, pid.length());
        return passA + "*****" + passB;
    }

    /**
     * 动态设置listview的高度.
     * 
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 显示软键盘
     * 
     * @param etView
     */
    public static void showImm(EditText etView, Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etView, InputMethodManager.RESULT_SHOWN);
    }

    /**
     * 隐藏软键盘
     * 
     * @param etView
     */
    public static void closeImm(EditText etView, Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etView.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    /**
     * 获取字符串 排除null 或“null”
     * 
     * @param string
     * @return
     */
    public static String getStringWithoutNull(String string) {
        if (null != string && !"null".equals(string)) {
            return string;
        }
        return "";
    }
    public static String getStringWithoutNullInt(String string) {
        if (null != string && !"null".equals(string)) {
            return string;
        }
        return "0";
    }

}
