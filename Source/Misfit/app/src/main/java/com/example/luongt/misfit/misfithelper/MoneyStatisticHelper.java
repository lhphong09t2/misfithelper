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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        return "" + ((MoneySetting)getSetting()).getSPMoney();
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
                        MoneyPayment moneyPayment = new MoneyPayment(0, getDateTime(), _moneyInput, "N/A");
                        addNewToDB(moneyPayment);
                        _moneyInput = 0;
                        _isMoneyPaymentCreating = false;
                    }
                }.start();
            }
        });
    }

    public void addNewToDB(MoneyPayment moneyPayment){
        _moneyDBHelper.createNew(moneyPayment);

        if(_moneyPaymentAddedListener != null) {
            _moneyPaymentAddedListener.onChanged();
        }
    }

    public void updatePayment(MoneyPayment moneyPayment){
        _moneyDBHelper.updateData(moneyPayment);

        if(_moneyPaymentAddedListener != null) {
            _moneyPaymentAddedListener.onChanged();
        }
    }

    public void deletePayment(int id){
        _moneyDBHelper.deleteData(id);

        if(_moneyPaymentAddedListener != null) {
            _moneyPaymentAddedListener.onChanged();
        }
    }

    @Override
    public String getDoublePressTitle() {
        return "" + ((MoneySetting)getSetting()).getDPMoney();
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
            //TODO:Remove recent add
        }
    }

    @Override
    public String getTriplePressTitle() {
        return null;
    }

    private static OnMoneyPaymentChangedListener _moneyPaymentAddedListener;
    public void setOnMoneyPaymentAddedListener(OnMoneyPaymentChangedListener moneyPaymentAddedListener){
        _moneyPaymentAddedListener = moneyPaymentAddedListener;
    }

    public String getDateTime(){
        Date currentLocalTime = Calendar.getInstance().getTime();
        DateFormat date = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
        Log.i(TAG, date.format(currentLocalTime));
        return date.format(currentLocalTime);
    }
}

