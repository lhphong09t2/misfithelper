package com.example.luongt.misfithelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.misfit.misfitlinksdk.MFLSession;
import com.misfit.misfitlinksdk.publish.MFLCallBack;
import com.misfit.misfitlinksdk.publish.MFLCommand;
import com.misfit.misfitlinksdk.publish.MFLError;
import com.misfit.misfitlinksdk.publish.MFLGestureType;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DeviceMonitorDelegate deviceMonitorDelegate = new DeviceMonitorDelegate();
        MFLSession.sharedInstance().setGestureCommandDelegate(deviceMonitorDelegate);

        DeviceStateTrackingDelegate deviceStateTrackingDelegate = new DeviceStateTrackingDelegate();
        MFLSession.sharedInstance().setStateTrackingDelegate(deviceStateTrackingDelegate);

        ((Switch) findViewById(R.id.switch1)).setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final Switch switchEnable = (Switch) buttonView;

        if (switchEnable.isChecked()) {
            MFLSession.sharedInstance().enable("100", "6hbIEM2QbKtOasV4E7b3BvSc6fpId4Cj", new MFLCallBack() {
                @Override
                public void onResponse(final Map<String, Map<MFLGestureType, MFLCommand>> commandMapping, final List<MFLCommand> supportedCommands, final MFLError error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (error != null) {
                                Log.e(TAG, error.getLocalizedMessage());
                                switchEnable.setChecked(false);
                                return;
                            }
                            switchEnable.setChecked(MFLSession.sharedInstance().isEnabled());
                        }
                    });
                }
            });
        } else {
            MFLSession.sharedInstance().disable();
        }
    }
}
