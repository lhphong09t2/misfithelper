package com.example.luongt.misfit.misfithelper;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.SmsManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

;

/**
 * Created by luongt on 3/28/2016.
 */
public class CallHelper extends BaseMisfitHelper implements MisfitHelper {

    public static boolean isInComingCall = false;
    public static String phoneNumber;

    public CallHelper(Context context) {
        super(context);
    }

    @Override
    public void onSinglePress() {
        silenceRinger();
    }

    @Override
    public void onDoublePress() {
        //sendMessage("ABC XXX");
    }

    @Override
    public void onTripplePress() {
        endCall();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    String getKey() {
        return null;
    }

    @Override
    Object createDefaultSetting() {
        return null;
    }

    @Override
    String getSettingJson(Object setting) {
        return null;
    }

    @Override
    Object parseJsonSetting(String settingJson) {
        return null;
    }

    @Override
    public String getSinglePressTitle() {
        return null;
    }

    @Override
    public String getDoublePressTitle() {
        return null;
    }

    @Override
    public String getTriplePressTitle() {
        return null;
    }

    public void silenceRinger(){
        AudioManager am= (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        am.setStreamMute(AudioManager.STREAM_RING, true);
    }

    private void sendMessage(String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        }
        catch (Exception e){}
    }

    private void endCall(){
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
