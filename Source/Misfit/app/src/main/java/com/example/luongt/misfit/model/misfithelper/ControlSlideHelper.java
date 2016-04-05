package com.example.luongt.misfit.model.misfithelper;

import android.content.Context;

import com.example.luongt.misfit.R;

/**
 * Created by luongt on 3/24/2016.
 */
public class ControlSlideHelper extends BaseMisfitHelper {

    public ControlSlideHelper(Context context) {
        super(context);
    }

    @Override
    public String getKey() {
        return "slide";
    }

    @Override
    public int getIconId() {
        return R.drawable.slide;
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
        return "Slide";
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
