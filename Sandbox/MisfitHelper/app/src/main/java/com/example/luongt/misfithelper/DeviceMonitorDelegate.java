package com.example.luongt.misfithelper;

import android.util.Log;

import com.misfit.misfitlinksdk.publish.MFLCommand;
import com.misfit.misfitlinksdk.publish.MFLGestureCommandDelegate;

/**
 * Created by luongt on 3/16/2016.
 */
public class DeviceMonitorDelegate implements MFLGestureCommandDelegate {

    private static final String TAG = "DeviceMonitorDelegate";

    @Override
    public void performActionByCommand(MFLCommand command, String serialNumber) {
        Log.i(TAG, "performActionByCommand " + command.getName() + " " + serialNumber);
        // add your code here
    }
}