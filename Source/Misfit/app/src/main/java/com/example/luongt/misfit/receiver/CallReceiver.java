package com.example.luongt.misfit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.luongt.misfit.CallActivity;
import com.example.luongt.misfit.helper.CallSetting;

/**
 * Created by luongt on 3/25/2016.
 */
public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.PHONE_STATE")){
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                CallSetting.phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                context.startActivity(new Intent(context, CallActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) || state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
                broadcastManager.sendBroadcast(new Intent("FINISH"));
            }
        }
    }

    public void silenceRinger(Context context){
        AudioManager am= (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }
}
