package com.example.luongt.misfit.misfithelper;

import android.content.Context;
import android.util.Log;

import com.example.luongt.misfit.R;
import com.example.luongt.misfit.model.setting.MoneySetting;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luongt on 3/24/2016.
 */
public class MoneyStatisticHelper extends BaseMisfitHelper {
    private String TAG = "MoneyStatisticHelper";
    private final double SPDefaultSetting = 10000;
    private final double DPDefaultSetting = 20000;
    private final int DelayTime = 5000;

    public MoneyStatisticHelper(Context context) {
        super(context);
    }

    @Override
    Object createDefaultSetting() {
        return new MoneySetting(SPDefaultSetting, DPDefaultSetting, DelayTime);
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
        MoneySetting moneySetting = (MoneySetting)setting;
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("moneySP", moneySetting.getMoneyDP());
            jsonObj.put("moneyDP", moneySetting.getMoneyDP());
            jsonObj.put("delayTime", moneySetting.getDelayTime());
            return jsonObj.toString();
        } catch (JSONException e) {
            Log.e(TAG, "Cannot convert to json");
        }
        return null;
    }

    @Override
    MoneySetting parseJsonSetting(String settingJson) {
        try {
            JSONObject jsonObj = new JSONObject();
            double moneySP = jsonObj.getDouble("moneySP");
            double moneyDP = jsonObj.getDouble("moneyDP");
            int delayTime = jsonObj.getInt("delayTime");
            return new MoneySetting(moneySP, moneyDP, delayTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

