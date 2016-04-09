package com.example.luongt.misfithelper;

import android.app.Instrumentation;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.luongt.misfithelper.control.DynamicView;
import com.misfit.misfitlinksdk.MFLSession;
import com.misfit.misfitlinksdk.publish.MFLCallBack;
import com.misfit.misfitlinksdk.publish.MFLCommand;
import com.misfit.misfitlinksdk.publish.MFLError;
import com.misfit.misfitlinksdk.publish.MFLGestureType;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener,
        OnMisfitButtonClickListener
{

    private static final String TAG = "MainActivity";

    private DynamicView _styleOneView;
    private DynamicView _styleTwoView;
    private DynamicView _styleThreeView;
    private DynamicView _styleFourView;

    private Button _styleOneButton;
    private Button _styleTwoButton;
    private Button _styleThreeButton;
    private Button _styleFourButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Switch) findViewById(R.id.switch1)).setOnCheckedChangeListener(this);

        _styleOneView = (DynamicView)findViewById(R.id.styleOneView);
        _styleTwoView = (DynamicView)findViewById(R.id.styleTwoView);
        _styleThreeView = (DynamicView)findViewById(R.id.styleThreeView);
        _styleFourView = (DynamicView)findViewById(R.id.styleFourView);

        _styleOneButton = (Button)findViewById(R.id.singlePressButton);
        _styleTwoButton = (Button)findViewById(R.id.doublePressButton);
        _styleThreeButton = (Button)findViewById(R.id.triplePressButton);
        _styleFourButton = (Button)findViewById(R.id.longPressButton);

        _styleOneButton.setOnClickListener(this);
        _styleTwoButton.setOnClickListener(this);
        _styleThreeButton.setOnClickListener(this);
        _styleFourButton.setOnClickListener(this);

        DeviceMonitorDelegate deviceMonitorDelegate = new DeviceMonitorDelegate();
        MFLSession.sharedInstance().setGestureCommandDelegate(deviceMonitorDelegate);
        deviceMonitorDelegate.setOnMisfitButtonClickListener(this);

        DeviceStateTrackingDelegate deviceStateTrackingDelegate = new DeviceStateTrackingDelegate();
        MFLSession.sharedInstance().setStateTrackingDelegate(deviceStateTrackingDelegate);
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

    @Override
    public void onClick(View v) {
        if (v == _styleOneButton)
        {
            _styleOneView.blink();
        }
    }

    Instrumentation m_Instrumentation;
    @Override
    public void onClick(MFLGestureType type) {

        final MFLGestureType temp = type;

        m_Instrumentation = new Instrumentation();
        m_Instrumentation.sendKeyDownUpSync( KeyEvent.KEYCODE_B );

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (temp)
                {
                    case SINGLE_PRESS:
                        _styleOneView.blink();
//                        Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
//                        KeyEvent upEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
//                        upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
//                        sendBroadcast(upIntent);

//                        m_Instrumentation.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
//                                SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 100, 0));
//
//                        m_Instrumentation.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
//                                SystemClock.uptimeMillis(),MotionEvent.ACTION_UP,0, 100, 0));

//                        InputConnection ic = getCurrentInputConnection();
//                        long eventTime = SystemClock.uptimeMillis();
//                        ic.sendKeyEvent(new KeyEvent(eventTime, eventTime,
//                                KeyEvent.ACTION_DOWN, keyEventCode, 0, 0, 0, 0,
//                                KeyEvent.FLAG_SOFT_KEYBOARD|KeyEvent.FLAG_KEEP_TOUCH_MODE));
//
//                        ic.sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), eventTime,
//                                KeyEvent.ACTION_UP, keyEventCode, 0, 0, 0, 0,
//                                KeyEvent.FLAG_SOFT_KEYBOARD|KeyEvent.FLAG_KEEP_TOUCH_MODE));

                        new InputMethodService().sendDownUpKeyEvents(KeyEvent.KEYCODE_CAMERA);

                        break;
                    case DOUBLE_PRESS:
                        _styleTwoView.blink();
                        break;
                    case TRIPLE_PRESS:
                        _styleThreeView.blink();
                        break;
                    default:
                        _styleFourView.blink();
                        break;
                }
            }
        });
    }
}
