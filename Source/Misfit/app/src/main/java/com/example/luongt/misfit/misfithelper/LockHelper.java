package com.example.luongt.misfit.misfithelper;

import android.content.Context;

import com.example.luongt.misfit.R;

/**
 * Created by luongt on 3/24/2016.
 */
public class LockHelper extends BaseMisfitHelper {

    public LockHelper(Context context) {
        super(context);
    }

    @Override
    Object createDefaultSetting() {
        return null;
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
        return null;
    }

    @Override
    Object parseJsonSetting(String settingJson) {
        return null;
    }

    @Override
    public String getName() {
        return "Lock";
    }

    @Override
    public void onSinglePress() {

    }

    @Override
    public String getSinglePressTitle() {
        return null;
    }

    @Override
    public void onDoublePress() {

    }

    @Override
    public String getDoublePressTitle() {
        return null;
    }

    @Override
    public void onTripplePress() {

    }

    @Override
    public String getTriplePressTitle() {
        return null;
    }
}

