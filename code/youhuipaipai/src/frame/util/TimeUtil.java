package frame.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.text.format.Time;
import android.widget.DatePicker;

/*******************************
 * 时间类工具
 * 
 * @author duanxinmeng
 * @version 1.0
 * @date 2013-8-22
 */
public class TimeUtil {

    /*****************************
     * 获取DatePickerDialog
     * 
     * @param listener
     * @return
     */
    public static DatePickerDialog getDatePicker(Context context,
            final TimeListener listener) {
        Time time = new Time();
        time.setToNow();
        DatePickerDialog picker = new DatePickerDialog(context,
                new OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                            int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat sdf = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        try {
                            String sendDate = sdf.format(sdf.parse("" + year
                                    + "-" + (monthOfYear + 1) + "-"
                                    + dayOfMonth));
                            if (listener != null)
                                listener.onCompleteDate(sendDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, time.year, time.month, time.monthDay);
        return picker;
    }

    /**************************
     * 获取当前时间
     * 
     * @return Time time
     */
    public static Time getCurrentTime() {
        Time time = new Time();
        time.setToNow();
        return time;
    }

    /************************
     * 获取当前年
     * 
     * @return int year
     */
    public static int getCurrentYear() {
        Time time = getCurrentTime();
        return time.year;
    }

    /**************************
     * 获取当前月
     * 
     * @return int month
     */
    public static int getCurrentMonth() {
        Time time = getCurrentTime();
        return time.month;
    }

    /************************
     * 获取当前日
     * 
     * @return int day
     */
    public static int getCurrentDay() {
        Time time = getCurrentTime();
        return time.monthDay;
    }

    /************************
     * 获取当前日期数组 分别是 年 月 日
     * 
     * @return Integer[] size = 3, year month monthday
     */
    public static Integer[] getCurrentDateStrs() {
        Integer[] strs = new Integer[3];
        strs[0] = getCurrentYear();
        strs[1] = getCurrentMonth();
        strs[2] = getCurrentDay();
        return strs;
    }

    /****************************
     * 获取标准格式时间字符串
     * 
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getFormatDate(int year, int month, int day) {
        String str = "";

        return str;
    }

    /****************************
     * 时间Dialog回调监听器
     * 
     * @author duanxinmeng
     * @version 1.0
     * @date 2013-8-22
     */
    public interface TimeListener {

        /**************************
         * 该方法将回传标准时间格式字符串
         * 
         * @param str
         *            xxxx-xx-xx
         */
        void onCompleteDate(String str);
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static String getNowTime() {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date());
    }

}
