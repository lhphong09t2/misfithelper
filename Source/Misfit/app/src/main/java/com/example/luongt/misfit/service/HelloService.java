package com.example.luongt.misfit.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.luongt.misfit.LockActivity;
import com.example.luongt.misfit.MFContants;
import com.example.luongt.misfit.MisfitEventNotifierApplication;
import com.example.luongt.misfit.R;
import com.example.luongt.misfit.misfithelper.AlarmHelper;
import com.example.luongt.misfit.misfithelper.BaseMisfitHelper;
import com.example.luongt.misfit.misfithelper.CallHelper;
import com.example.luongt.misfit.misfithelper.ControlSlideHelper;
import com.example.luongt.misfit.misfithelper.LockHelper;
import com.example.luongt.misfit.misfithelper.MoneyStatisticHelper;
import com.example.luongt.misfit.model.setting.LockSetting;
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

    private ArrayList<BaseMisfitHelper> _misfitHelpers;

    public ArrayList<BaseMisfitHelper> getMisfitHelpers() {
        return _misfitHelpers;
    }

    private BaseMisfitHelper _currentMisfitHelper;

    private CallHelper _callHelper;

    public CallHelper getCallHelper() {
        return _callHelper;
    }

    private AlarmHelper _alarmHelper;

    public AlarmHelper getAlarmHelper() {
        return _alarmHelper;
    }

    private ControlSlideHelper _controlSlideHelper;

    public ControlSlideHelper getControlSlideHelper() {
        return _controlSlideHelper;
    }

    private MoneyStatisticHelper _moneyStatisticHelper;

    public MoneyStatisticHelper get_moneyStatisticHelper() {
        return _moneyStatisticHelper;
    }

    private LockHelper _lockHelper;

    public LockHelper getLockHelper() {
        return _lockHelper;
    }

    private String _commandMisfit;

    private static HelloService _instance;

    public static HelloService getInstance() {
        return _instance;
    }

    public HelloService() {
        _instance = this;
        MFLSession.build(MisfitEventNotifierApplication.getContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (MFLSession.sharedInstance() != null) {
            MFLSession.sharedInstance().setGestureCommandDelegate(this);
            MFLSession.sharedInstance().setStateTrackingDelegate(this);
        }

        InitHelpers();

        if(((LockSetting)_lockHelper.getSetting()).isEnable()) {
            startService(new Intent(this, LockService.class));
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean _isSettingMode = false;

    @Override
    public void performActionByCommand(MFLCommand command, String serialNumber) {
        _commandMisfit = command.getName();
        int _currentIndex = _misfitHelpers.indexOf(_currentMisfitHelper);
        int maxIndex = 1;

        if (CallHelper.inCall) {
            HandleIncomingCall();
        } else if (AlarmHelper.isAlarming) {
            HandleAlarm();
        } else if (_isSettingMode) {
            switch (_commandMisfit) {
                case "sp":
                    if (_currentIndex != maxIndex) {
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
                        _currentMisfitHelper = _misfitHelpers.get(maxIndex);
                    }
                    speak(_currentMisfitHelper.getName());
                    break;
                case "tp":
                    if (LockActivity.getInstance() == null) {
                        startActivity(new Intent(this, LockActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    } else {
                        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MFContants.FINISH_LOCK));
                    }
                    _isSettingMode = false;
                    break;
                default:
                    _isSettingMode = false;
                    speak(getString(R.string.exit_setting_mode));
                    break;
            }
        } else {
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
                if (MoneyStatisticHelper.isInMoney == true) {
                    _currentMisfitHelper.onLongPress();
                    speak(_currentMisfitHelper.getLongPressTitle());
                } else {
                    _isSettingMode = true;
                    speak(getString(R.string.setting_mode));
                }
                break;
        }
    }

    private void InitHelpers() {
        Context context = MisfitEventNotifierApplication.getContext();
        getSharedPreferences(MFContants.CALL_SETTING_KEY, MODE_PRIVATE);

        _callHelper = new CallHelper(context);
        _alarmHelper = new AlarmHelper(context);
        _controlSlideHelper = new ControlSlideHelper(context);
        _lockHelper = new LockHelper(context);
        _moneyStatisticHelper = new MoneyStatisticHelper(context);

        _misfitHelpers = new ArrayList<BaseMisfitHelper>();
        _misfitHelpers.add(_moneyStatisticHelper);
        _misfitHelpers.add(_controlSlideHelper);
        _misfitHelpers.add(_lockHelper);
        _misfitHelpers.add(_alarmHelper);
        _misfitHelpers.add(_callHelper);

        _currentMisfitHelper = _misfitHelpers.get(0);
    }
}
