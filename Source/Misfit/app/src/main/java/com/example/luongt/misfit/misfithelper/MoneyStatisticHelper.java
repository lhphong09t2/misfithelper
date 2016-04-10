package com.example.luongt.misfit.misfithelper;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.luongt.misfit.R;
import com.example.luongt.misfit.databasehelper.MoneyPaymentHelper;
import com.example.luongt.misfit.model.setting.MoneySetting;
import com.example.luongt.misfit.model.table.MoneyPayment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by luongt on 3/24/2016.
 */
public class MoneyStatisticHelper extends BaseMisfitHelper {
    private String TAG = "MoneyStatisticHelper";
    private final double SPDefaultSetting = 10000;
    private final double DPDefaultSetting = 20000;
    private final int DelayTime = 5000;

    private MoneyPaymentHelper _moneyDBHelper;

    private Context _context;

    public MoneyStatisticHelper(Context context) {
        super(context);
        _context = context;
        _moneyDBHelper = new MoneyPaymentHelper(_context);
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
            jsonObj.put("moneySP", moneySetting.getDPMoney());
            jsonObj.put("moneyDP", moneySetting.getDPMoney());
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
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        addMoneyPayment(((MoneySetting)getSetting()).getSPMoney());
    }

    CountDownTimer countDownTimer;
    @Override
    public String getSinglePressTitle() {
        return "Add more " + ((MoneySetting)getSetting()).getSPMoney();
    }

    @Override
    public void onDoublePress() {
        if(countDownTimer != null){
            countDownTimer.cancel();
        }

        addMoneyPayment(((MoneySetting)getSetting()).getDPMoney());
    }

    private double _moneyInput = 0;
    private double _previouseMoney = 0;
    private boolean _isMoneyPaymentCreating = false;

    private void addMoneyPayment(double money){
        _previouseMoney = money;
        _moneyInput += money;

        _isMoneyPaymentCreating = true;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                countDownTimer = new CountDownTimer(((MoneySetting)getSetting()).getDelayTime(), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {}
                    public void onFinish() {
                        //TODO: Text to speech
                        Log.i(TAG, "onFinish");
                        //TODO: Review random
                        MoneyPayment moneyPayment = new MoneyPayment(new Random().nextInt(), Calendar.getInstance().toString(), _moneyInput, "");
                        _moneyDBHelper.createNew(moneyPayment);
                        _moneyInput = 0;
                        _isMoneyPaymentCreating = false;
                        if(_moneyPaymentAddedListener != null) {
                            _moneyPaymentAddedListener.onAdded();
                        }
                    }
                }.start();
            }
        });
    }

    @Override
    public String getDoublePressTitle() {
        return "Add more " + ((MoneySetting)getSetting()).getDPMoney();
    }

    @Override
    public void onTripplePress() {
        if (_isMoneyPaymentCreating )
        {
            addMoneyPayment(-_previouseMoney);

            if (_moneyInput < 0)
            {
                _moneyInput = 0;
            }
        }
        else
        {
            //TODO: remove recent money payment item from database
        }
    }

    @Override
    public String getTriplePressTitle() {
        return null;
    }

    private static OnMoneyPaymentAddedListener _moneyPaymentAddedListener;
    public void setOnMoneyPaymentAddedListener(OnMoneyPaymentAddedListener moneyPaymentAddedListener){
        _moneyPaymentAddedListener = moneyPaymentAddedListener;
    }

    public void removeOnMoneyPaymentAddedListener(){
        _moneyPaymentAddedListener = null;
    }
}

