package frame.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

public class ImgUtil {
	public static Bitmap fileToBitmap(Context context,String fileUrl,int w,int h) {

		if (fileUrl == null)
			return null;
		try {
			File f = new File(fileUrl);
			if (!f.exists())
				return null;
			Uri uri = Uri.fromFile(f);
			if (uri != null) {
				byte[] bt = getBytes(context.getContentResolver().openInputStream(uri));
 
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeByteArray(bt,0,bt.length,opts);
				int srcWidth = opts.outWidth;
				int srcHeight = opts.outHeight;
				int destWidth = 0;
				int destHeight = 0;
				//缩放的比例
				double ratio = 0.0;
				Log.e("原图片宽高 ","Width:" + srcWidth + " Height:" + srcHeight);;
				if(srcWidth<w){
					Bitmap bitMap = BitmapFactory.decodeByteArray(bt, 0, bt.length);
					return bitMap;
				}
				
				ratio = srcWidth / w;
				destWidth = w;
				destHeight = (int) (srcHeight / ratio);
				 
				
				BitmapFactory.Options newOpts = new BitmapFactory.Options();
				
				Log.e("ratio + 1", "ratio + 1:"+((int) ratio + 1));
				
				newOpts.inSampleSize = (int) ratio + 1;
				newOpts.inJustDecodeBounds = false;
				newOpts.outHeight = h;
				newOpts.outWidth = w;
				
				Bitmap bitMap =BitmapFactory.decodeByteArray(bt,0,bt.length,newOpts);
				Log.e("压缩后的高度", "压缩后的高度："+bitMap.getWidth()+"   压缩后的高度："+bitMap.getHeight());
				
				return bitMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static Drawable fileToDrawable(Context context,String fileUrl) {
		return fileToDrawable(context, fileUrl, true);
	}
	
	public static Drawable fileToDrawable(Context context,String fileUrl, boolean Compress) {
		
		if (fileUrl == null){
			return null;
		}
		File f = new File(fileUrl);
		try {
			if (!f.exists()){
				return null;
			}
			if(Compress)
				return new BitmapDrawable(decodeFile(f));
			else{
				Uri uri = Uri.fromFile(f);
				if (uri != null) {
					 return Drawable.createFromStream(context.getContentResolver().openInputStream(uri), null);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}catch (OutOfMemoryError e) {
			Log.d("", "------------OutOfMemoryError-------");
			return new BitmapDrawable(decodeFile(f));
		}
		return null;
	}
	
    private static Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(f);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();
            
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
            
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	private static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = is.read(b, 0, 1024)) != -1) {
			baos.write(b, 0, len);
			baos.flush();
		}
		byte[] bytes = baos.toByteArray();
		return bytes;
	}
 
	
	/**
	 * 通过URL 获取图片url
	 * @param ctx
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String getFileURL(String url)
			throws IOException {
		if (url == null || url.equals(""))
			return null;

		String urlPath =url;

		File cacheFile =new File(android.os.Environment.getExternalStorageDirectory(),"peugeot");
		cacheFile.mkdirs();
		File file = new File(cacheFile, md5(urlPath));
		try {
			if (file.exists()) {
				if (file.length() < 10) {
					file.delete();
				}else{
					return file.getPath();
				}
			} 
			Log.d("", "下载图片~~~~~~~~~~~" + url);
			FileOutputStream outStream = new FileOutputStream(file);
			HttpURLConnection conn = (HttpURLConnection) new URL(urlPath).openConnection();
			conn.setConnectTimeout(10 * 1000);
			if (conn.getResponseCode() == 200) {
				InputStream inStream = conn.getInputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = inStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				outStream.close();
				inStream.close();
				return file.getPath();
			} else
			return null;

		} 
		catch (Exception ex) {
			Log.d("getFileURL", "getFileURL error = " + ex.getMessage());
			ex.printStackTrace();
			file.delete();
			return null;
		}finally {
			if (file.length() < 10) {
				file.delete();
				return null;
			}
		}
  
	}
	

	/**
	 * md5加密
	 * @param s
	 * @return
	 */
	public static String md5(String s) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String toHexString(byte[] b) { // String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){
		
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
				.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
 
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
 
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
 
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
 
		return output;
	}
	
    /**
     * 转换图片成圆形
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
    		if(bitmap == null)
    			return null;
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float roundPx;
            float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
            if (width <= height) {
                    roundPx = width / 2;
                    top = 0;
                    bottom = width;
                    left = 0;
                    right = width;
                    height = width;
                    dst_left = 0;
                    dst_top = 0;
                    dst_right = width;
                    dst_bottom = width;
            } else {
                    roundPx = height / 2;
                    float clip = (width - height) / 2;
                    left = clip;
                    right = width - clip;
                    top = 0;
                    bottom = height;
                    width = height;
                    dst_left = 0;
                    dst_top = 0;
                    dst_right = height;
                    dst_bottom = height;
            }
             
            Bitmap output =null;
            Canvas canvas =null;
            try {
            	 output =Bitmap.createBitmap(width,  height, Config.ARGB_8888);
            	   canvas = new Canvas(output);
			} catch (OutOfMemoryError e) {
				return null;
			}
             
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
            final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
            final RectF rectF = new RectF(dst);

            paint.setAntiAlias(true);
             
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, src, dst, paint);
            bitmap.recycle();
            return output;
    }
}
