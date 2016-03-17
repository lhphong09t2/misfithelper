package com.example.luongt.misfithelper;

import android.util.Log;

import com.misfit.misfitlinksdk.publish.MFLCommand;
import com.misfit.misfitlinksdk.publish.MFLGestureCommandDelegate;
import com.misfit.misfitlinksdk.publish.MFLGestureType;

/**
 * Created by luongt on 3/16/2016.
 */
public class DeviceMonitorDelegate implements MFLGestureCommandDelegate {

    private static final String TAG = "DeviceMonitorDelegate";
    private static String commandMisfit = "";

    @Override
    public void performActionByCommand(MFLCommand command, String serialNumber) {
        Log.i(TAG, "performActionByCommand " + command.getName() + " " + serialNumber);
        commandMisfit = command.getName();

        switch (commandMisfit)
        {
            case "sp":
                iOnMisfitButtonClickListener.onClick(MFLGestureType.SINGLE_PRESS);
                break;
            case "dp":
                iOnMisfitButtonClickListener.onClick(MFLGestureType.DOUBLE_PRESS);
                break;
            case "tp":
                iOnMisfitButtonClickListener.onClick(MFLGestureType.TRIPLE_PRESS);
                break;
            default:
                iOnMisfitButtonClickListener.onClick(MFLGestureType.LONG_PRESS);
                break;
        }
    }

    private OnMisfitButtonClickListener iOnMisfitButtonClickListener;

    public void setOnMisfitButtonClickListener(OnMisfitButtonClickListener i)
    {
        iOnMisfitButtonClickListener = i;
    }
}