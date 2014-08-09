package frame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;

public class FileUtil {

	public static String getRealPath(Context mContext, Uri fileUrl) {
		String fileName = null;
		Uri filePathUri = fileUrl;
		
		File f = new File( Environment.getExternalStorageDirectory() +"");
		
		if (fileUrl != null) {
			if (fileUrl.getScheme().toString().compareTo("content") == 0) // content://开头的uri
			{
				Cursor cursor = mContext.getContentResolver().query(fileUrl,
						null, null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					int column_index = cursor
							.getColumnIndexOrThrow(MediaColumns.DATA);
					fileName = cursor.getString(column_index); // 取出文件路径
					
					
					if (f.getAbsolutePath().startsWith("/mnt")&&!fileName.startsWith("/mnt")) {
						// 检查是否有”/mnt“前缀

						fileName = "/mnt" + fileName;
					}
					cursor.close();
				}
			} else if (fileUrl.getScheme().compareTo("file") == 0) // file:///开头的uri
			{
				fileName = filePathUri.toString();
				fileName = filePathUri.toString().replace("file://", "");
				// 替换file://
				if (f.getAbsolutePath().startsWith("/mnt")&&!fileName.startsWith("/mnt")) {
					// 加上"/mnt"头
					fileName = "/mnt" + fileName;
				}
			}
		}
		return fileName;
	}
	
	
	
	public static void writeSerializable(Object obj,String path) {
		ObjectOutputStream out =null;
		try {
			File f = new File(path);
			f.getParentFile().mkdirs();
			if (f.exists())
				f.delete();
			f.createNewFile();
			out= new ObjectOutputStream( new FileOutputStream(f));
			out.writeObject(obj);
		} catch (Exception e) {
			Log.e("序列化出错", "write序列化出错："+e.getMessage());
			e.printStackTrace();
		}finally{
			if(out !=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Object readSerializable(String path) {
		ObjectInputStream in = null;
		try {
			File f = new File(path);
			if (f.exists()) {
				in = new ObjectInputStream(new FileInputStream(f));
				Object mul = in.readObject();
				return mul;
			}
		} catch (Exception e) {
			Log.e("序列化出错", "read序列化出错："+e.getMessage());
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
}
