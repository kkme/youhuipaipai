package frame.util;


import android.content.Context;
import android.widget.Toast;

/**
 * @类描述: Toast工具类
 * @文件名: ToastUtils.java
 * @包名路径: com.eastedge.heheguapp.utils
 * @创建时间 2013-3-19 下午2:20:25
 * @author lumeng	
 * @version V1.0
 */
public class ToastUtil extends Toast{
	
	public ToastUtil(Context context) {
		super(context);
	}
	
	/**
	 * 短时间显示Toast
	 * @param context
	 * @param msg
	 */
	public static void showShort(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 长时间显示Toast  
	 * @param context
	 * @param msg
	 */
	public static void showLong(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
}
