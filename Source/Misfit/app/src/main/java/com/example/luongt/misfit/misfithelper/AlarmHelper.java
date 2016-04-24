package com.example.luongt.misfit.misfithelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.luongt.misfit.MFContants;
import com.example.luongt.misfit.R;
import com.example.luongt.misfit.model.setting.AlarmSetting;
import com.example.luongt.misfit.receiver.AlarmReceiver;

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

    private int currentVolume;

    public AlarmHelper(Context context) {
        super(context);
        _context = context;

        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
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
    public void onSinglePress() {
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(MFContants.SNOOZE));
    }

    @Override
    public void onDoublePress() {
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(MFContants.DISMISS));
    }

    AudioManager audioManager;
    @Override
    public void onTripplePress() {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        playAudio(R.raw.terible_song);
    }

    static MediaPlayer _mediaPlayer;
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

    private AlarmManager _alarmMgr;
    private PendingIntent _alarmIntent;
    public void setAlarm(int hour, int minute, Boolean repeat){
        _alarmMgr = (AlarmManager)_context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(_context, AlarmReceiver.class);
        _alarmIntent = PendingIntent.getBroadcast(_context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        if(repeat){
            _alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, _alarmIntent);
        }
        else {
            _alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), _alarmIntent);
        }
    }

    public void removeAlarm(){
        if(_alarmMgr != null) {
            _alarmMgr.cancel(_alarmIntent);
        }
    }

    public void resetVolume(){
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
    }
}
