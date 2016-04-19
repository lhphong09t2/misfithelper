package com.example.luongt.misfit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.luongt.misfit.AlarmReachedActivity;
import com.example.luongt.misfit.misfithelper.AlarmHelper;

/**
 * Created by luongt on 3/29/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmHelper.isAlarming = true;
        context.startActivity(new Intent(context, AlarmReachedActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
