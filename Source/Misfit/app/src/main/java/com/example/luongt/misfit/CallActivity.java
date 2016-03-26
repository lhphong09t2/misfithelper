package com.example.luongt.misfit;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.WindowManager;

import com.example.luongt.misfit.helper.CallSetting;

public class CallActivity extends Activity {

    WindowManager manager;
    private LocalBroadcastManager _localBroadcastManager;

    private BroadcastReceiver _broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("SILENCE_RINGER")){
                silenceRinger(context);
            }
            if(intent.getAction().equals("SEND_MESSAGE")){
                sendMessage(CallSetting.phoneNumber, CallSetting.message);
            }
            if(intent.getAction().equals("FINISH")){
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter mIntentFilter = new IntentFilter("SILENCE_RINGER");
        mIntentFilter.addAction("SEND_MESSAGE");
        mIntentFilter.addAction("FINISH");
        _localBroadcastManager.registerReceiver(_broadcastReceiver, mIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CallSetting.phoneNumber = null;
        _localBroadcastManager.unregisterReceiver(_broadcastReceiver);
    }

    public void silenceRinger(Context context){
        AudioManager am= (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    public void sendMessage(String phoneNumber, String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        }
        catch (Exception e){}
    }
}
