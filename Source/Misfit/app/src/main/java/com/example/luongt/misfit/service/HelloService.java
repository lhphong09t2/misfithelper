package com.example.luongt.misfit.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.luongt.misfit.MFContants;
import com.example.luongt.misfit.MisfitEventNotifierApplication;
import com.example.luongt.misfit.R;
import com.example.luongt.misfit.misfithelper.AlarmHelper;
import com.example.luongt.misfit.misfithelper.CallHelper;
import com.example.luongt.misfit.misfithelper.ControlSlideHelper;
import com.example.luongt.misfit.misfithelper.FuelMoneyStatisticHelper;
import com.example.luongt.misfit.misfithelper.MisfitHelper;
import com.example.luongt.misfit.model.AlarmSetting;
import com.misfit.misfitlinksdk.MFLSession;
import com.misfit.misfitlinksdk.publish.MFLCommand;
import com.misfit.misfitlinksdk.publish.MFLDeviceState;
import com.misfit.misfitlinksdk.publish.MFLGestureCommandDelegate;
import com.misfit.misfitlinksdk.publish.MFLServiceState;
import com.misfit.misfitlinksdk.publish.MFLStateTrackingDelegate;

import java.util.ArrayList;

/**
 * Created by luongt on 3/24/2016.
 */
public class HelloService extends TTSService implements MFLGestureCommandDelegate, MFLStateTrackingDelegate {

    private static final String TAG = "HelloService";

    private LocalBroadcastManager _localBroadcastManager;
    private BroadcastReceiver _broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "Service received");
            AlarmSetting alarmsetting = new AlarmSetting(intent.getIntExtra("HOUR_OF_DATE", 0), intent.getIntExtra("MINUTE", 0), intent.getBooleanExtra("IS_REPEAT", false));
            _alarmHelper.SaveSetting(alarmsetting);
        }
    };

    private ArrayList<MisfitHelper> _misfitHelpers;
    public ArrayList<MisfitHelper> getMisfitHelpers() {
        return _misfitHelpers;
    }

    private MisfitHelper _currentMisfitHelper;
    private CallHelper _callHelper;
    private AlarmHelper _alarmHelper;

    private String _commandMisfit;

    private  static  HelloService _instance;
    public static HelloService getInstance()
    {
        return  _instance;
    }

    public HelloService()
    {
        super(TAG);
        _instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MFLSession.sharedInstance().setGestureCommandDelegate(this);
        MFLSession.sharedInstance().setStateTrackingDelegate(this);

        startService(new Intent(this, LockService.class));
        InitHelpers();

        _localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter mIntentFilter = new IntentFilter("ACTION_SAVE_SETTING");
        _localBroadcastManager.registerReceiver(_broadcastReceiver, mIntentFilter);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
    }

    private boolean _isSettingMode = false;

    @Override
    public void performActionByCommand(MFLCommand command, String serialNumber) {
        _commandMisfit = command.getName();
        int _currentIndex = _misfitHelpers.indexOf(_currentMisfitHelper);

        if (_isSettingMode) {
            switch (_commandMisfit) {
                case "sp":
                    if (_currentIndex != _misfitHelpers.size() - 1) {
                        _currentMisfitHelper = _misfitHelpers.get(_currentIndex + 1);
                    } else {
                        _currentMisfitHelper = _misfitHelpers.get(0);
                    }
                    speak(_currentMisfitHelper.getName());
                    break;
                case "dp":
                    if (_currentIndex != 0) {
                        _currentMisfitHelper = _misfitHelpers.get(_currentIndex - 1);
                    } else {
                        _currentMisfitHelper = _misfitHelpers.get(_misfitHelpers.size() - 1);
                    }
                    speak(_currentMisfitHelper.getName());
                    break;
                case "tp":
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("FINISH_LOCK"));
                    break;
                default:
                    _isSettingMode = false;
                    speak(getString(R.string.exit_setting_mode));
                    break;
            }
        }
        else if (CallHelper.isInComingCall) {
            Log.e(TAG, "IsInComingCall");
            HandleIncomingCall();
        }
        else if (AlarmHelper.isAlarming)
        {
            Log.e(TAG, "IsAlarming");
            HandleAlarm();
        }
        else {
            Log.e(TAG, "HandleCurrentHelper");
            HandleCurrentHelper();
        }
    }

    @Override
    public void onDeviceStateChange(MFLDeviceState mflDeviceState, String s) {
        //TODO: handle device state change
    }

    @Override
    public void onServiceStateChange(MFLServiceState mflServiceState) {
        //TODO: handle service state change
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _localBroadcastManager.unregisterReceiver(_broadcastReceiver);
    }

    private void HandleIncomingCall() {
        switch (_commandMisfit) {
            case "sp":
                _callHelper.onSinglePress();
                break;
            case "dp":
                _callHelper.onDoublePress();
                break;
            case "tp":
                _callHelper.onTripplePress();
                break;
            default:
                break;
        }
    }

    private void HandleAlarm() {
        switch (_commandMisfit) {
            case "sp":
                _alarmHelper.onSinglePress();
                break;
            case "dp":
                _alarmHelper.onDoublePress();
                break;
            case "tp":
                _alarmHelper.onTripplePress();
                break;
            default:
                break;
        }
    }

    private void HandleCurrentHelper() {
        switch (_commandMisfit) {
            case "sp":
                _currentMisfitHelper.onSinglePress();
                speak(_currentMisfitHelper.getSinglePressTitle());
                break;
            case "dp":
                _currentMisfitHelper.onDoublePress();
                speak(_currentMisfitHelper.getDoublePressTitle());
                break;
            case "tp":
                _currentMisfitHelper.onTripplePress();
                speak(_currentMisfitHelper.getTriplePressTitle());
                break;
            default:
                _isSettingMode = true;
                speak(getString(R.string.setting_mode));
                break;
        }
    }

    private void InitHelpers()
    {
        Context context = MisfitEventNotifierApplication.getContext();
        getSharedPreferences(MFContants.CALL_SETTING_KEY, MODE_PRIVATE);

        _misfitHelpers = new ArrayList<MisfitHelper>();
        _misfitHelpers.add(new ControlSlideHelper(context));
        _misfitHelpers.add(new FuelMoneyStatisticHelper(context));
        _currentMisfitHelper = _misfitHelpers.get(0);

        _callHelper = new CallHelper(context);
        _alarmHelper = new AlarmHelper(context);
    }
}
