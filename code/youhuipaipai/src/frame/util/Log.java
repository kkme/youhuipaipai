package frame.util;

public class Log {

	public static boolean isLog=true;
	
	public static void e(String tag,String msg){
		if(isLog)
		android.util.Log.e(tag, msg);
	}
	public static void d(String tag,String msg){
		if(isLog)
		android.util.Log.e(tag, msg);
	}
	public static void w(String tag,String msg){
		if(isLog)
		android.util.Log.e(tag, msg);
	}
	
	public static void i(String tag,String msg){
		if(isLog)
		android.util.Log.e(tag, msg);
	}
	
	public static void syso(String str){
		if(isLog)
		System.out.println(str);
	}
	
}
