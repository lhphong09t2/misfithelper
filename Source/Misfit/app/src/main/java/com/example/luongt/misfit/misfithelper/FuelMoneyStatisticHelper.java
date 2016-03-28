package com.example.luongt.misfit.misfithelper;

import android.content.Context;

/**
 * Created by luongt on 3/24/2016.
 */
public class FuelMoneyStatisticHelper extends BaseMisfitHelper implements MisfitHelper {

    public FuelMoneyStatisticHelper(Context context) {
        super(context);
    }

    @Override
    Object createDefaultSetting() {
        return null;
    }

    @Override
    String getKey() {
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
    public String getName() {
        return null;
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

