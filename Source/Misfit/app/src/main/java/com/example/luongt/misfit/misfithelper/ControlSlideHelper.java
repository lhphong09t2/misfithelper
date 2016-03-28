package com.example.luongt.misfit.misfithelper;

import android.content.Context;

import com.example.luongt.misfit.R;

/**
 * Created by luongt on 3/24/2016.
 */
public class ControlSlideHelper extends BaseMisfitHelper implements MisfitHelper {

    public ControlSlideHelper(Context context) {
        super(context);
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
    public String getName() {
        return getContext().getString(R.string.control_slide);
    }

    @Override
    public void onSinglePress() {

    }

    @Override
    public String getSinglePressTitle() {
        return getContext().getString(R.string.next_slide);
    }

    @Override
    public void onDoublePress() {

    }

    @Override
    public String getDoublePressTitle() {
        return getContext().getString(R.string.back_slide);
    }

    @Override
    public void onTripplePress() {

    }

    @Override
    public String getTriplePressTitle() {
        return null;
    }
}
