package com.example.luongt.lock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.luongt.lock.ScreenLockActivity;

/**
 * Created by luongt on 3/21/2016.
 */
public class ScreenReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
        {
            Intent i = new Intent();
            i.setClass(context, ScreenLockActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
