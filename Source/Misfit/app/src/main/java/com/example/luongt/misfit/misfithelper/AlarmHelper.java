package com.example.luongt.misfit.misfithelper;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.luongt.misfit.MFContants;
import com.example.luongt.misfit.R;
import com.example.luongt.misfit.model.setting.AlarmSetting;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by luongt on 3/28/2016.
 */
public class AlarmHelper extends BaseMisfitHelper<AlarmSetting> {

    private static final String TAG = "AlarmHelper";

    private Context _context;

    public static boolean isAlarming = false;

    public AlarmHelper(Context context) {
        super(context);
        _context = context;
    }

    @Override
    public String getKey() {
        return "alarm";
    }

    @Override
    public int getIconId() {
        return R.drawable.alarm;
    }

    @Override
    AlarmSetting createDefaultSetting() {
        Calendar c = Calendar.getInstance();
        return new AlarmSetting(c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false, false);
    }

    @Override
    String getSettingJson(AlarmSetting setting) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("hour", setting.getHour());
            jsonObj.put("minute", setting.getMinute());
            jsonObj.put("isRepeat", setting.isRepeat());
            jsonObj.put("isEnable", setting.isEnable());
            return jsonObj.toString();
        } catch (JSONException e) {
            Log.e(TAG, "Cannot convert to json");
        }
        return null;
    }

    @Override
    protected AlarmSetting parseJsonSetting(String settingJson) {
        try {
            JSONObject jsonObj = new JSONObject(settingJson);
            int hour = jsonObj.getInt("hour");
            int minute = jsonObj.getInt("minute");
            boolean isRepeat = jsonObj.getBoolean("isRepeat");
            boolean isEnable = jsonObj.getBoolean("isEnable");

            return new AlarmSetting(hour, minute, isRepeat, isEnable);
        } catch (JSONException e) {
            Log.e(TAG, "Cannot parse Json to object");
        }

        return null;
    }

    @Override
    public String getName() {
        return "Alarm";
    }

    @Override
    public String getSinglePressTitle() {
        return "Snooze";
    }

    @Override
    public void onSinglePress() {
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(MFContants.SNOOZE));
    }

    @Override
    public String getDoublePressTitle() {
        return "Dismiss";
    }

    @Override
    public void onDoublePress() {
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(MFContants.DISMISS));
    }

    static MediaPlayer _mediaPlayer;
    @Override
    public void onTripplePress() {
        AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_VIBRATE);
        playAudio(R.raw.terible_song);
    }

    public void playAudio(int id){
        if(_mediaPlayer !=null){
            _mediaPlayer.stop();
        }
        _mediaPlayer = MediaPlayer.create(_context, id);
        _mediaPlayer.setLooping(true);
        _mediaPlayer.start();
    }

    public void stopAudio(){
        _mediaPlayer.stop();
    }
}
