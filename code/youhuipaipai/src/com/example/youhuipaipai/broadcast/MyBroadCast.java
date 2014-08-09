package com.example.youhuipaipai.broadcast;

import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.application.PullService;

import frame.util.MyPreference;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i("info",
                "开机启动------------------------------------------------------------");
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            if (!MyPreference.getBoolean(context, Constant.IS_PUSH)){
                PullService.stopAction(context);
            }else {
                PullService.startAction(context);
            }
        }
    }

}
