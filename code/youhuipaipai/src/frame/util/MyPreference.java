package frame.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class MyPreference {
	private static SharedPreferences preference;
	private static Editor editor;

	@SuppressLint("CommitPrefEdits")
	public static SharedPreferences getPreference(Context context) {
		if (preference == null) {
			preference = PreferenceManager.getDefaultSharedPreferences(context);
			editor=preference.edit();
		}
		return preference;
	}
	public static  void putString(Context context,String key, String value) {
		getPreference(context);
		editor.putString(key, value);
		editor.commit();
	}
	public static  void putBoolean(Context context,String key,boolean value) {
		getPreference(context);
		editor.putBoolean(key, value);
		editor.commit();
	}
	public static String getString(Context context,String key){
		getPreference(context);
		return preference.getString(key, "");
	}
	public static boolean getBoolean(Context context,String key){
		getPreference(context);
		return preference.getBoolean(key, false);
	}
	public static void putLong(Context context,String key, long value) {
		getPreference(context);
		editor.putLong(key, value);
		editor.commit();
	}
	public static long getLong(Context context,String key){
		getPreference(context);
		return preference.getLong(key, 0);
	}
	public static void putInt(Context context,String key, int value) {
		getPreference(context);
		editor.putInt(key, value);
		editor.commit();
	}
	public static int getInt(Context context,String key){
		getPreference(context);
		return preference.getInt(key, 0);
	}
}
