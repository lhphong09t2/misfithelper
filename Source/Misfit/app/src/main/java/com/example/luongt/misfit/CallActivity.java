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
import android.util.Log;
import android.view.WindowManager;

import com.example.luongt.misfit.helper.CallSetting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
            if(intent.getAction().equals("END_CALL")){
                endCall();
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
        mIntentFilter.addAction("END_CALL");
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
        am.setStreamMute(AudioManager.STREAM_RING, true);
    }

    public void sendMessage(String phoneNumber, String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        }
        catch (Exception e){}
    }

    public void endCall(){
        Executor eS = Executors.newSingleThreadExecutor();
        eS.execute(new Runnable() {
            @Override
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("service call phone 5 \n");
                } catch (Exception exc) {}
            }
        });
    }
}
