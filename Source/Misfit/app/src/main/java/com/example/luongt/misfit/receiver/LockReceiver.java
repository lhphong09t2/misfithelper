package com.example.luongt.misfit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.luongt.misfit.LockActivity;

/**
 * Created by luongt on 3/25/2016.
 */
public class LockReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentLock = new Intent(context, LockActivity.class);
        intentLock.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentLock);
    }
}
