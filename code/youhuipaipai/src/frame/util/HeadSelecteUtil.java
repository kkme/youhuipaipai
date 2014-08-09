package frame.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

/**
 * 头像选取工具：在onActivityResult中编写：
if(requestCode == 0 && resultCode == RESULT_OK){
    		Log.d("从相机获取 uri = " + mImageCaptureUri);
    		headUtil.doCrop(mImageCaptureUri);
    	}
    	if(requestCode == 1 && resultCode == RESULT_OK){
    		mImageCaptureUri = data.getData();
	    	Log.d("从相册选取 uri = " + mImageCaptureUri);
	    	headUtil.doCrop(mImageCaptureUri);
    	}
    	if(requestCode == 2 && resultCode == RESULT_OK){
    		Bundle extras = data.getExtras();
	    	
	        if (extras != null) {	        	
	            Bitmap photo = extras.getParcelable("data");
	            try {
					File f = new File(MyStorageUtil.FILE_ROOT, "avatar.png");
					FileOutputStream out = new FileOutputStream(f);
					photo.compress(Bitmap.CompressFormat.PNG, 80, out);
					
					String imgPath = f.getAbsolutePath();
					Log.d("裁剪后的头像路径：" + imgPath);
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	        
    	}
 */
public class HeadSelecteUtil {

	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_FILE = 1;
	private static final int CROP_REQUEST_CODE = 2;
	
	private Context context;
	
	public HeadSelecteUtil(Context context){
		this.context = context;
	}
	
	/**
	 * <p>Description: 从相册中选择</p>
	 */
	public void pickFromFile() {
		
		Intent intent = new Intent();
		
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        
        ((Activity) context).startActivityForResult(Intent.createChooser(intent, "选择图片打开工具"), PICK_FROM_FILE);
		
	}
	
	/**
	 * <p>Description:从相机 </p>
	 * @param cacheDirRoot
	 * @return
	 */
	public Uri pickFromCamera(String cacheDirRoot) {
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File tmpFile = new File(cacheDirRoot, "original_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
		Uri mImageCaptureUri = Uri.fromFile(tmpFile);

		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

		try {
			intent.putExtra("return-data", true);
			((Activity) context).startActivityForResult(intent, PICK_FROM_CAMERA);
			return mImageCaptureUri;
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
		return mImageCaptureUri;
		
	}

	/**
	 * <p>Description: 执行裁剪操作</p>
	 * @param mImageCaptureUri
	 */
	public void doCrop(final Uri mImageCaptureUri) {

		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, 0);

		int size = list.size();

		if (size == 0) {
			Toast.makeText(context, "Can not find image crop app",Toast.LENGTH_SHORT).show();
			return;
		} else {
			intent.setData(mImageCaptureUri);
			intent.putExtra("outputX", 100);
			intent.putExtra("outputY", 100);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
				((Activity) context).startActivityForResult(i, CROP_REQUEST_CODE);
			} else {
				for (ResolveInfo res : list) {
					final CropOption co = new CropOption();

					co.title = context.getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
					co.icon = context.getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);

					co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

					cropOptions.add(co);
				}

				CropOptionAdapter adapter = new CropOptionAdapter(context.getApplicationContext(), cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Choose Crop App");
				builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								((Activity) context).startActivityForResult(cropOptions.get(item).appIntent, CROP_REQUEST_CODE);
							}
						});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {

						if (mImageCaptureUri != null) {
							context.getContentResolver().delete(mImageCaptureUri, null, null);
						}
					}
				});

				AlertDialog alert = builder.create();

				alert.show();
			}
		}
	}
}
