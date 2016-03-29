package com.example.luongt.misfit.misfithelper;

import android.content.Context;

import com.example.luongt.misfit.model.AlarmSetting;

/**
 * Created by luongt on 3/28/2016.
 */
public class AlarmHelper extends BaseMisfitHelper<AlarmSetting> implements MisfitHelper {

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
//        AlarmSetting setting = new AlarmSetting();
//        setting.setAlarmTime(new Date());
//        setting.setAllowedDay(new boolean[7]);
//        setting.setEnableInteration(true);
        return null;
    }

    @Override
    String getSettingJson(AlarmSetting setting) {
        //TODO: convert setting to setting json
        return null;
    }


    @Override
    protected AlarmSetting parseJsonSetting(String settingJson) {
        //TODO: parse setting json to setting
        return  null;
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
