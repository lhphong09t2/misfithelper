package com.example.luongt.misfit.misfithelper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by luongt on 3/28/2016.
 */
public abstract class BaseMisfitHelper<T> {

    private static final String TAG = "BaseMisfitHelper";

    private Context _context;
    private T _setting;

    protected Context getContext() {
        return _context;
    }

    public BaseMisfitHelper(Context context) {
        super();
        _context = context;
    }

    public abstract String getKey();

    public abstract int getIconId();

    abstract T createDefaultSetting();

    abstract String getSettingJson(T setting);

    abstract T parseJsonSetting(String settingJson);

    public void saveSetting(T setting) {
        if (setting != null) {
            _setting = setting;

            SharedPreferences sharedPref = _context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getKey(), getSettingJson(_setting));
            editor.commit();
        }
    }

    public T getSetting() {
        if (_setting != null) {
            return _setting;
        }

        SharedPreferences sharedPref = _context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String settingJson = sharedPref.getString(getKey(), null);

        if (settingJson == null || settingJson.equals("")) {
            _setting = createDefaultSetting();
        } else {
            _setting = parseJsonSetting(settingJson);
        }

        return _setting;
    }


    public String getSinglePressTitle() {
        return "Single press";
    }

    public String getDoublePressTitle() {
        return "Double press";
    }

    public String getTriplePressTitle() {
        return "Triple press";
    }

    public abstract String getName();

    public abstract void onSinglePress();

    public abstract void onDoublePress();

    public abstract void onTripplePress();
}
