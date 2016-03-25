package com.example.luongt.misfit.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.luongt.misfit.LockActivity;
import com.example.luongt.misfit.misfithelper.ControlSlideHelper;
import com.example.luongt.misfit.misfithelper.FuelMoneyStatisticHelper;
import com.example.luongt.misfit.misfithelper.MisfitHelper;
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
public class HelloService extends IntentService implements MFLGestureCommandDelegate, MFLStateTrackingDelegate {

    private static final String TAG = "HelloService";
    public static Boolean isServiceRunning = false;

    private ArrayList<MisfitHelper> _misfitHelpers;
    private MisfitHelper _currentMisfitHelper;

    public HelloService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isServiceRunning = true;

        MFLSession.sharedInstance().setGestureCommandDelegate(this);
        MFLSession.sharedInstance().setStateTrackingDelegate(this);

        _misfitHelpers = new ArrayList<MisfitHelper>();
        _misfitHelpers.add(new ControlSlideHelper());
        _misfitHelpers.add(new FuelMoneyStatisticHelper());
        _currentMisfitHelper = _misfitHelpers.get(0);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    private boolean _isSettingMode = false;

    @Override
    public void performActionByCommand(MFLCommand command, String serialNumber) {
        Log.i(TAG, "performActionByCommand " + command.getName() + " " + serialNumber);
        String commandMisfit = command.getName();

        int _currentIndex = _misfitHelpers.indexOf(_currentMisfitHelper);

        if (_isSettingMode) {
            switch (commandMisfit) {
                case "sp":
                    if (_currentIndex != _misfitHelpers.size() -1)
                    {
                        _currentMisfitHelper = _misfitHelpers.get(_currentIndex + 1);
                    }
                    else
                    {
                        _currentMisfitHelper =  _misfitHelpers.get(0);
                    }

                    Intent lockIntent = new Intent(this, LockActivity.class);
                    lockIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(lockIntent);
                    break;
                case "dp":
                    if (_currentIndex != 0)
                    {
                        _currentMisfitHelper = _misfitHelpers.get(_currentIndex - 1);
                    }
                    else
                    {
                        _currentMisfitHelper = _misfitHelpers.get(_misfitHelpers.size() - 1);
                    }
                    break;
                case "tp":
                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    localBroadcastManager.sendBroadcast(new Intent("android.intent.action.FINISH"));
                    break;
                default:
                    break;
            }
        } else {
            switch (commandMisfit) {
                case "sp":
                    _currentMisfitHelper.getSinglePressTitle();
                    break;
                case "dp":
                    _currentMisfitHelper.onDoublePress();
                    break;
                case "tp":
                    _currentMisfitHelper.onTripplePress();
                    break;

                default:
                    _isSettingMode = !_isSettingMode;
                    break;
            }
        }
    }

    @Override
    public void onDeviceStateChange(MFLDeviceState mflDeviceState, String s) {

    }

    @Override
    public void onServiceStateChange(MFLServiceState mflServiceState) {

    }
}
