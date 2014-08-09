package frame.util;

import java.util.ArrayList;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

public class ImageDetailThread extends MyThread {

	private Handler handler;
	private String imgUrl;

	public ImageDetailThread(Handler handler, String imgUrl) {
		this.handler = handler;
		this.imgUrl = imgUrl;
	}

	@Override
	public void run() {

		try {
			if (isStop)
				return;
			String filePath = ImgUtil.getFileURL(imgUrl);
			Message msg = new Message();
			msg.obj = filePath;
			msg.what = 888;
			handler.sendMessage(msg);
		} catch (Exception e) {
			Log.e("出错", "出错" + e.getMessage());
		}
	}
}
