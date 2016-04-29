package com.example.luongt.misfit.misfithelper;

import android.content.Context;

import com.example.luongt.misfit.R;
import com.example.luongt.misfit.model.setting.LockSetting;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luongt on 3/24/2016.
 */
public class LockHelper extends BaseMisfitHelper {

    private String _passcode = "0000";
    private boolean isEnable = true;

    public String getPasscode() {
        return _passcode;
    }

    public LockHelper(Context context) {
        super(context);
    }

    @Override
    Object createDefaultSetting() {
        return new LockSetting(_passcode, isEnable);
    }

    @Override
    public String getKey() {
        return "lock";
    }

    @Override
    public int getIconId() {
        return R.drawable.lock;
    }

    @Override
    String getSettingJson(Object setting) {
        LockSetting lockSetting = (LockSetting)setting;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("passcode", lockSetting.getPasscode());
            jsonObject.put("isEnable", lockSetting.isEnable());
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
            return new LockSetting(jsonObject.getString("passcode"), jsonObject.getBoolean("isEnable"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getName() {
        return "Lock";
    }

    @Override
    public void onSinglePress() {}

    @Override
    public void onDoublePress() {}

    @Override
    public void onTripplePress() {}

}

