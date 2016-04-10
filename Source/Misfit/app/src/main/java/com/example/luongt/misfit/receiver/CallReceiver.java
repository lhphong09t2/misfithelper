package com.example.luongt.misfit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.example.luongt.misfit.LockActivity;
import com.example.luongt.misfit.misfithelper.CallHelper;

/**
 * Created by luongt on 3/25/2016.
 */
public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        CallHelper.inCall = true;
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state != null) {
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                CallHelper.phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                CallHelper.inCall = false;
                if (LockReceiver.isScreenOn) {
                    context.startActivity(new Intent(context, LockActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    LockReceiver.isScreenOn = false;
                }
            }
        }
    }
}
