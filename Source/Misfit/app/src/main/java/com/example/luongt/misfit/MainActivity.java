package com.example.luongt.misfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.luongt.misfit.control.MisfitHelperControl;
import com.example.luongt.misfit.helper.ScreenHelper;
import com.example.luongt.misfit.misfithelper.BaseMisfitHelper;
import com.example.luongt.misfit.model.setting.BaseSetting;
import com.example.luongt.misfit.service.HelloService;
import com.misfit.misfitlinksdk.MFLSession;
import com.misfit.misfitlinksdk.publish.MFLCallBack;
import com.misfit.misfitlinksdk.publish.MFLCommand;
import com.misfit.misfitlinksdk.publish.MFLError;
import com.misfit.misfitlinksdk.publish.MFLGestureType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener {

    private Switch _misfitSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //TODO:delete this line
        //2342new MoneyPaymentHelper(this).recreateTable();

        _misfitSwitch = ((Switch) findViewById(R.id.enableMisfit));
        _misfitSwitch.setOnCheckedChangeListener(this);
        _misfitSwitch.setChecked(MFLSession.sharedInstance().isEnabled());
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
        if (v instanceof MisfitHelperControl)
        {
            final MisfitHelperControl MFControl = (MisfitHelperControl)v;

            Animation inAnimation = AnimationUtils.loadAnimation(this, R.anim.dynamic_scale_in);
            MFControl.startAnimation(inAnimation);

            switch (MFControl.getName())
            {
                case "Alarm":
                    startActivity(new Intent(this, AlarmActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case "Call":
                    //TODO open setting view
                    break;
                case "Lock":
                    //TODO open setting view
                    break;
                case "Money":
                    startActivity(new Intent(this, MoneyActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case "Slide":
                    //TODO open setting view
                    break;
            }
        }
    }

    private void initView() {
        LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.linearLayout1);
        LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.linearLayout2);

        ArrayList<BaseMisfitHelper> misfitHelpers = HelloService.getInstance().getMisfitHelpers();
        for (int i = 0; i < misfitHelpers.size(); i++) {
            BaseMisfitHelper<BaseSetting> misfitHelper = misfitHelpers.get(i);
            final MisfitHelperControl MFControl = createMFControl(misfitHelper);
            MFControl.setVisibility(View.INVISIBLE);

            Animation inAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate);
            inAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    MFControl.clearAnimation();
                    MFControl.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            inAnimation.setStartOffset(1500-i*300);
            MFControl.startAnimation(inAnimation);

            if(i > misfitHelpers.size()/2+0.5){
                linearLayout2.addView(MFControl);
            }
            else {
                linearLayout1.addView(MFControl);
            }
        }
    }

    private MisfitHelperControl createMFControl(BaseMisfitHelper<BaseSetting> misfitHelper){
        final int MFControlSize = ScreenHelper.toPixel(124);
        MisfitHelperControl MFControl = new MisfitHelperControl(this);
        MFControl.setOnClickListener(this);
        MFControl.setIconId(misfitHelper.getIconId());
        MFControl.setName(misfitHelper.getName());
        MFControl.setLayoutParams(new LinearLayout.LayoutParams(MFControlSize, MFControlSize, 1f));

        return MFControl;
    }

}
