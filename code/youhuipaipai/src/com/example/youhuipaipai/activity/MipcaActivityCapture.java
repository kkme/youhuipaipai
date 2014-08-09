package com.example.youhuipaipai.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.mining.app.zxing.camera.CameraManager;
import com.mining.app.zxing.decoding.CaptureActivityHandler;
import com.mining.app.zxing.decoding.InactivityTimer;
import com.mining.app.zxing.view.ViewfinderView;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.MyPreference;
import frame.util.HttpUtil.CallbackListener;
import frame.util.ToastUtil;

//二维码扫描
public class MipcaActivityCapture extends BaseActivity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	// private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;


	private void init() {
		CameraManager.init(this);
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		// hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();

		// if (hasSurface) {
		// initCamera(surfaceHolder);
		// } else {
		surfaceHolder.addCallback(this);
		// surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// }
		decodeFormats = null;
		characterSet = null;
		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	/*
	 * @Override protected void onResume() { super.onResume(); }
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * 
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (resultString.equals("")) {
			Toast.makeText(MipcaActivityCapture.this, "二维码扫描失败!",
					Toast.LENGTH_SHORT).show();
		} else {
			sign(result);
		}
	}
	
	private void sign(final Result res){
		String userId = MyPreference.getString(this, Constant.USERID);
		if (TextUtils.isEmpty(userId)) {
            startActivity(new Intent(this, LogActivity.class));
            ToastUtil.showLong(CTX, "请先登录");
            return;
        }
		showProgressDialog("正在请求", "请稍候...");
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "UserSignIn");
		hm.put("userid",userId);
		hm.put("key",res.getText());
		hu.post(this, hm, new CallbackListener() {
			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				
				JSONObject obj=JSON.parseObject(result.toJSONString());
				String score = obj.getString("message");
				String msg = null;
				if("0".equals(score)){
					msg = "您今天已经签到，明天再来吧";
				}else{
					msg = "恭喜您获取" + score + "积分";
				}
				ToastUtil.showLong(CTX, msg);
				Intent intent = new Intent(MipcaActivityCapture.this, ShopDetailActivity.class);
				intent.putExtra("merchantid", res.getText());
				startActivity(intent);
			}
			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(MipcaActivityCapture.this, result.message);
			}
		});
	}
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceCreated(final SurfaceHolder holder) {
		// if (!hasSurface) {
		// hasSurface = true;
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				initCamera(holder);
			}
		}, 100);
		
		// }

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// hasSurface = false;
		
	}
	
	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	@Override
	protected int getContentViewID() {
		// TODO Auto-generated method stub
		return R.layout.signin;
	}
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		init();
	}
}