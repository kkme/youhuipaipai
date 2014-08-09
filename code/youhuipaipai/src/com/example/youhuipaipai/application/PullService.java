package com.example.youhuipaipai.application;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract.Constants;

import com.alibaba.fastjson.JSON;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.Mymessage;

import frame.http.HttpImpl;
import frame.http.bean.HttpRequestBean;
import frame.http.bean.HttpResultBean;
import frame.util.JsonUtil;
import frame.util.Log;
import frame.util.MyPreference;

public class PullService extends Service {
    public static final String TAG = "PullService";
    public static final String ACTION_START = "action_start";
    public static final String ACTION_STOP = "action_stop";
    public static boolean isAvailable = true;
    public static int TYPE_DEFAULT = 100;
    private static Context context;
    /**
     * 轮训频率
     */
    private long interval;
    private Notification mNotification;
    private NotificationManager mManager;
    private NotifiThread mNotifiThread;

    private HttpRequestBean requestBean;
    private SharedPreferences preferences;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        initPreferences();
        initNotifiManager();
    }

    private void initPreferences() {
        preferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        interval = Constant.TEST;
    }

    private void initNotifiManager() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int icon = R.drawable.ic_launcher;
        mNotification = new Notification();
        mNotification.icon = icon;
        mNotification.tickerText = getResources().getString(R.string.newNotifi);
        mNotification.defaults |= Notification.DEFAULT_SOUND;
        // 点击后自动消失
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
    }

    private void launchNotification(String content) {
        mNotification.when = System.currentTimeMillis();

        // Intent i = new Intent("android.intent.action.MAIN");
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setComponent(new ComponentName("com.example.youhuipaipai",
                "com.example.youhuipaipai.activity.HomeActivity"));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        i.putExtra("code", 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i,
                Intent.FLAG_ACTIVITY_NEW_TASK);
        mNotification.contentIntent = pendingIntent;
        mNotification.setLatestEventInfo(context,
                getResources().getString(R.string.notifiTitle), content,
                pendingIntent);

        mManager.notify(TYPE_DEFAULT, mNotification);
    }

    /**
     * 开始
     * 
     * @param ctx
     */
    public static void startAction(Context ctx) {
        Intent i = new Intent();
        context = ctx;
        i.setClass(ctx, PullService.class);
        i.setAction(ACTION_START);
        ctx.startService(i);
    }

    /**
     * 停止
     * 
     * @param ctx
     */
    public static void stopAction(Context ctx) {
        Intent i = new Intent();
        context = ctx;
        i.setClass(ctx, PullService.class);
        i.setAction(ACTION_STOP);
        ctx.startService(i);
    }

    @Override
    public void onStart(Intent intent, int startId) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        getStart(intent);
        return START_STICKY;
    }

    private void getStart(Intent intent) {
        if (null != intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_START)) {
                // 开启服务
                if (mNotifiThread != null && mNotifiThread.isAlive()) {
                    isAvailable = false;
                    mNotifiThread.interrupt();
                    mNotifiThread = null;
                }
                isAvailable = true;
                mNotifiThread = new NotifiThread();
                mNotifiThread.start();
            } else if (action.equals(ACTION_STOP)) {
                // 关闭服务
                if (mNotifiThread != null && mNotifiThread.isAlive()) {
                    isAvailable = false;
                    mNotifiThread.interrupt();
                    mNotifiThread = null;
                }
            }
        }
    }

    class NotifiThread extends Thread {
        public void run() {
            while (isAvailable) {
                try {
                    // JSONObject object = new JSONObject();
                    JSONObject data = new JSONObject();
                    try {
                        data.put("requestCommand", "MyMessage");
                        data.put("userid", MyPreference.getString(
                                PullService.this, Constant.USERID));
                        data.put("index", "1");
                        data.put("sign", "20");
                        String json = new HttpImpl().doPostJSON(
                                Constant.HOST_URL, data).getString();
                        if (null != json && !"".equals(json)) {
                            ArrayList<Mymessage> mymessages = JsonUtil
                                    .jsonToMymessages(json);
                            if (null != mymessages && mymessages.size() > 0) {
                                String messageId = mymessages.get(0)
                                        .getMessageid();
                                String oldMessageId = MyPreference.getString(
                                        PullService.this, Constant.MESSAGEID);
                                if (!"".equals(messageId)
                                        && !"".equals(oldMessageId)
                                        && (Integer.valueOf(messageId) > Integer
                                                .valueOf(oldMessageId))) {
                                    MyPreference.putString(PullService.this,
                                            Constant.MESSAGEID, messageId);
                                    launchNotification("您有新的消息，请注意查收！");
                                }
                            }
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    };

}
