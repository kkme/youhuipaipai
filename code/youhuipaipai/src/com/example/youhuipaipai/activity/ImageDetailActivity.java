package com.example.youhuipaipai.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.view.TouchImageView;

import frame.imgtools.ImgUtil;
import frame.util.ImageDetailThread;
import frame.util.Log;

public class ImageDetailActivity extends Activity {

	private TouchImageView img;
	private LinearLayout progress;
	
	private ImageDetailThread curImgThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_detail);
		img = (TouchImageView) findViewById(R.id.snoop);
		progress = (LinearLayout) findViewById(R.id.snoop_progress);
		progress.setVisibility(View.VISIBLE);

		Bundle extras = getIntent().getExtras();
		String imgUrl = extras.getString("picPath");
		Log.d("", "查看大图地址：" + imgUrl);
		doDownImg(imgUrl);

		img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 888:
				progress.setVisibility(View.GONE);

				String fileUrl = (String) msg.obj;
				Log.d("", "大图下载成功，图片地址：" + fileUrl);
				Drawable d = ImgUtil.fileToDrawable(ImageDetailActivity.this, fileUrl, false);
				img.setImageDrawable(d);
				img.setMaxZoom(4f);

				break;
			}
		};
	};
	
	private void doDownImg(String imgUrl) {
		if (TextUtils.isEmpty(imgUrl))
			return;
		if (curImgThread != null) {
			curImgThread.stopRun();
		}
		curImgThread = new ImageDetailThread(handler, imgUrl);
		curImgThread.start();
	}

}
