package com.example.luongt.misfit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.luongt.misfit.LockActivity;
import com.example.luongt.misfit.misfithelper.AlarmHelper;
import com.example.luongt.misfit.misfithelper.CallHelper;

/**
 * Created by luongt on 3/25/2016.
 */
public class LockReceiver extends BroadcastReceiver {

    public static boolean isScreenOn;

    @Override
    public void onReceive(Context context, Intent intent) {
        isScreenOn = true;
        if(!AlarmHelper.isAlarming && !CallHelper.inCall) {
            context.startActivity(new Intent(context, LockActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        else{
            LockActivity.getInstance().finish();
        }
    }
}
