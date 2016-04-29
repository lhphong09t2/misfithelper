package com.example.luongt.misfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.example.luongt.misfit.control.EnableButton;
import com.example.luongt.misfit.control.MisfitHelperControl;
import com.example.luongt.misfit.misfithelper.BaseMisfitHelper;
import com.example.luongt.misfit.misfithelper.LockHelper;
import com.example.luongt.misfit.model.setting.BaseSetting;
import com.example.luongt.misfit.model.setting.LockSetting;
import com.example.luongt.misfit.service.HelloService;
import com.misfit.misfitlinksdk.MFLSession;
import com.misfit.misfitlinksdk.publish.MFLCallBack;
import com.misfit.misfitlinksdk.publish.MFLCommand;
import com.misfit.misfitlinksdk.publish.MFLError;
import com.misfit.misfitlinksdk.publish.MFLGestureType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EnableButton _enableButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        _enableButton.setChecked(MFLSession.sharedInstance().isEnabled());
    }

    @Override
    public void onClick(final View v) {
        final Animation inAnimation = AnimationUtils.loadAnimation(this, R.anim.dynamic_scale_in);
        inAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if (v == _enableButton) {
                    if (_enableButton.isChecked()) {
                        MFLSession.sharedInstance().enable("100", "6hbIEM2QbKtOasV4E7b3BvSc6fpId4Cj", new MFLCallBack() {
                            @Override
                            public void onResponse(final Map<String, Map<MFLGestureType, MFLCommand>> commandMapping, final List<MFLCommand> supportedCommands, final MFLError error) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (error != null) {
                                            _enableButton.setChecked(false);
                                            return;
                                        }
                                        _enableButton.setChecked(MFLSession.sharedInstance().isEnabled());
                                    }
                                });
                            }
                        });
                    } else {
                        if(MFLSession.sharedInstance() != null) {
                            MFLSession.sharedInstance().disable();
                        }
                    }
                } else if (v instanceof MisfitHelperControl) {
                    final MisfitHelperControl MFControl = (MisfitHelperControl) v;
                    new Thread() {
                        @Override
                        public void run() {
                            switch (MFControl.getName()) {
                                case "Alarm":
                                    startActivity(new Intent(MainActivity.this, AlarmActivity.class));
                                    break;
                                case "Call":
                                    startActivity(new Intent(MainActivity.this, CallSettingActivity.class));
                                    break;
                                case "Lock":
                                    LockHelper lockHelper = HelloService.getInstance().getLockHelper();
                                    if (((LockSetting) lockHelper.getSetting()).getPasscode().equals(lockHelper.getPasscode())) {
                                        startActivity(new Intent(MainActivity.this, LockSettingActivity.class));
                                    } else {
                                        startActivity(new Intent(MainActivity.this, LockConfirmPinActivity.class));
                                    }
                                    break;
                                case "Money":
                                    startActivity(new Intent(MainActivity.this, MoneyActivity.class));
                                    break;
                                case "Slide":
                                    startActivity(new Intent(MainActivity.this, ControlSlideActivity.class));
                                    break;
                            }
                        }
                    }.start();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}

        });

        v.startAnimation(inAnimation);
    }

    private void initView() {
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.leftLayout);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.rightLayout);

        _enableButton = new EnableButton(this, MFLSession.sharedInstance().isEnabled());
        _enableButton.setOnClickListener(this);

        Animation enableAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate);
        enableAnimation.setInterpolator(new OvershootInterpolator(0.8f));
        enableAnimation.setStartOffset(1500 + 300);
        _enableButton.startAnimation(enableAnimation);
        linearLayout1.addView(_enableButton);

        ArrayList<BaseMisfitHelper> misfitHelpers = HelloService.getInstance().getMisfitHelpers();
        for (int i = 0; i < misfitHelpers.size(); i++) {
            BaseMisfitHelper<BaseSetting> misfitHelper = misfitHelpers.get(i);
            final MisfitHelperControl MFControl = createMFControl(misfitHelper);

            Animation inAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate);
            inAnimation.setInterpolator(new OvershootInterpolator(0.8f));
            inAnimation.setStartOffset(1500 - i * 300);
            MFControl.startAnimation(inAnimation);

            if (i > misfitHelpers.size() / 2 - 1) {
                linearLayout2.addView(MFControl);
            } else {
                linearLayout1.addView(MFControl);
            }
        }
    }

    private MisfitHelperControl createMFControl(BaseMisfitHelper<BaseSetting> misfitHelper) {
        MisfitHelperControl MFControl = new MisfitHelperControl(this);
        MFControl.setOnClickListener(this);
        MFControl.setIconId(misfitHelper.getIconId());
        MFControl.setName(misfitHelper.getName());

        return MFControl;
    }
}
