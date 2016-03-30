package com.example.luongt.misfit.misfithelper;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.luongt.misfit.MFContants;
import com.example.luongt.misfit.model.AlarmSetting;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by luongt on 3/28/2016.
 */
public class AlarmHelper extends BaseMisfitHelper<AlarmSetting>  {

    private static final String TAG = "AlarmHelper";

    public static boolean isAlarming = false;

    public AlarmHelper(Context context) {
        super(context);
    }

    @Override
    String getKey() {
        return "AlarmHelper";
    }

    @Override
    AlarmSetting createDefaultSetting() {
        Calendar c = Calendar.getInstance();
        return new AlarmSetting(c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false);
    }

    @Override
    String getSettingJson(AlarmSetting setting) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("hour", setting.getHour());
            jsonObj.put("minute", setting.getMinute());
            jsonObj.put("isRepeat", setting.getIsRepeat());
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
            boolean inRepeat = jsonObj.getBoolean("isRepeat");

            return  new AlarmSetting(hour, minute, inRepeat);
        } catch (JSONException e) {
            Log.e(TAG, "Cannot parse Json to object");
        }

        return  null;
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

    @Override
    public void onTripplePress() {

    }
}
