package com.example.luongt.lock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by luongt on 3/21/2016.
 */
public class ScreenReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
        {
            Log.i("ABC", "ScreenReceive onReceive");
            Intent intentLock = new Intent();
            intentLock.setClass(context, ScreenLockActivity.class);
            context.startActivity(intentLock);
        }
    }
}
