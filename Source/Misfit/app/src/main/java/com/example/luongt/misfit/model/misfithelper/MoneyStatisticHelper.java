package com.example.luongt.misfit.model.misfithelper;

import android.content.Context;

import com.example.luongt.misfit.R;

/**
 * Created by luongt on 3/24/2016.
 */
public class MoneyStatisticHelper extends BaseMisfitHelper {

    public MoneyStatisticHelper(Context context) {
        super(context);
    }

    @Override
    Object createDefaultSetting() {
        return null;
    }

    @Override
    public String getKey() {
        return "money";
    }

    @Override
    public int getIconId() {
        return R.drawable.money;
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
        return "Money";
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

