package com.example.luongt.misfit.misfithelper;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.SmsManager;

import com.example.luongt.misfit.R;
import com.example.luongt.misfit.model.setting.CallSetting;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

;

/**
 * Created by luongt on 3/28/2016.
 */
public class CallHelper extends BaseMisfitHelper {

    private String _message = "I'm busy";

    public static boolean inCall = false;
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
        endCall();
    }

    @Override
    public String getSinglePressTitle() {
        return "Turn off ringtone";
    }

    @Override
    public String getDoublePressTitle() {
        return "End call";
    }

    @Override
    public String getTriplePressTitle() {
        return "Send a message";
    }

    @Override
    public void onTripplePress() {
        sendMessage(((CallSetting)getSetting()).getMessage());
    }

    @Override
    public String getName() {
        return "Call";
    }

    @Override
    public String getKey() {
        return "call";
    }

    @Override
    public int getIconId() {
        return R.drawable.call;
    }

    @Override
    Object createDefaultSetting() {
        return new CallSetting(_message);
    }

    @Override
    String getSettingJson(Object setting) {
        CallSetting callSetting = (CallSetting) setting;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message", callSetting.getMessage());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    Object parseJsonSetting(String settingJson) {
        try {
            JSONObject jsonObject = new JSONObject(settingJson);
            return new CallSetting(jsonObject.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void silenceRinger(){
        AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, AudioManager.FLAG_VIBRATE);
    }

    private void sendMessage(String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, ((CallSetting)getSetting()).getMessage(), null, null);
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
