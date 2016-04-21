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
    private final double SPDefaultSetting = 1000;
    private final double DPDefaultSetting = 2000;
    private final double TPDefaultSetting = 5000;
    private final int DelayTime = 5000;

    private MoneyPaymentHelper _moneyDBHelper;
    private Context _context;

    public static boolean isInMoney = false;

    public MoneyStatisticHelper(Context context) {
        super(context);
        _context = context;
        _moneyDBHelper = new MoneyPaymentHelper(_context);
    }

    @Override
    Object createDefaultSetting() {
        return new MoneySetting(SPDefaultSetting, DPDefaultSetting, TPDefaultSetting, DelayTime);
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
            jsonObj.put("moneySP", moneySetting.getSPMoney());
            jsonObj.put("moneyDP", moneySetting.getDPMoney());
            jsonObj.put("moneyTP", moneySetting.getTPMoney());
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
            JSONObject jsonObj = new JSONObject(settingJson);
            double moneySP = jsonObj.getDouble("moneySP");
            double moneyDP = jsonObj.getDouble("moneyDP");
            double moneyTP = jsonObj.getDouble("moneyTP");
            int delayTime = jsonObj.getInt("delayTime");
            return new MoneySetting(moneySP, moneyDP, moneyTP, delayTime);
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
        normalPress(((MoneySetting) getSetting()).getSPMoney());
    }

    @Override
    public String getSinglePressTitle() {
        return "" + (int)_moneyInput;
    }

    @Override
    public void onDoublePress() {
        normalPress(((MoneySetting) getSetting()).getDPMoney());
    }


    @Override
    public String getDoublePressTitle() {
        return "" + (int)_moneyInput;
    }

    @Override
    public void onTripplePress() {
        normalPress(((MoneySetting) getSetting()).getTPMoney());
    }

    @Override
     public String getTriplePressTitle() {
        return "" + (int)_moneyInput;
    }

    private void normalPress(double money){
        if(_countDownTimer != null){
            _countDownTimer.cancel();
        }

        isInMoney = true;
        _currentInput = money;
        _moneyInput += _currentInput;
        addMoneyPayment();
    }

    @Override
    public String getLongPressTitle() {
        return "" + (int)_moneyInput;
    }

    @Override
    public void onLongPress(){
        if(_countDownTimer != null){
            _countDownTimer.cancel();
        }

        _moneyInput = _moneyInput - _currentInput + _currentInput*10;
        _currentInput *= 10;

        addMoneyPayment();
    }

    private double _currentInput = 0;
    private double _moneyInput = 0;

    private CountDownTimer _countDownTimer;
    private void addMoneyPayment(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                _countDownTimer = new CountDownTimer(((MoneySetting)getSetting()).getDelayTime(), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {}
                    public void onFinish() {
                        //TODO: Text to speech
                        MoneyPayment moneyPayment = new MoneyPayment(0, getDateTime(), _moneyInput, "N/A");
                        addNewToDB(moneyPayment);
                        _moneyInput = 0;
                        isInMoney = false;
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

    private static OnMoneyPaymentChangedListener _moneyPaymentAddedListener;
    public void setOnMoneyPaymentChangedListener(OnMoneyPaymentChangedListener moneyPaymentAddedListener){
        _moneyPaymentAddedListener = moneyPaymentAddedListener;
    }

    public String getDateTime(){
        Date currentLocalTime = Calendar.getInstance().getTime();
        DateFormat date = new SimpleDateFormat("HH:mm, dd/MM/yy");
        return date.format(currentLocalTime);
    }
}

