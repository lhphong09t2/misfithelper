package com.example.luongt.misfit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.luongt.misfit.control.MisfitHelperControl;
import com.example.luongt.misfit.helper.ScreenHelper;
import com.example.luongt.misfit.model.data.MoneyPayment;
import com.example.luongt.misfit.model.databasehelper.MoneyDBHelper;
import com.example.luongt.misfit.model.misfithelper.BaseMisfitHelper;
import com.example.luongt.misfit.model.setting.BaseSetting;
import com.example.luongt.misfit.service.HelloService;
import com.misfit.misfitlinksdk.MFLSession;
import com.misfit.misfitlinksdk.publish.MFLCallBack;
import com.misfit.misfitlinksdk.publish.MFLCommand;
import com.misfit.misfitlinksdk.publish.MFLError;
import com.misfit.misfitlinksdk.publish.MFLGestureType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener {

    private Switch _misfitSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        runAnimation();

        MoneyDBHelper moneyDBHelper = new MoneyDBHelper(this);
        moneyDBHelper.RecreateTable();
        moneyDBHelper.createNewMoneyPayment(new MoneyPayment(new Random().nextInt(), new Date().toString(), 200, "kkkk"));
        ArrayList<MoneyPayment> moneyPayments = moneyDBHelper.getMoneyPayment();

//        _misfitSwitch = ((Switch) findViewById(R.id.enableMisfit));
//        _misfitSwitch.setOnCheckedChangeListener(this);
//        _misfitSwitch.setChecked(MFLSession.sharedInstance().isEnabled());
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

            Animation inAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
            MFControl.startAnimation(inAnimation);

            switch (MFControl.getName())
            {
                case "Alarm":
                    startActivity(new Intent(this, AlarmActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    //TODO open setting view
                    break;
                case "Call":
                    //TODO open setting view
                    break;
                case "Lock":
                    //TODO open setting view
                    break;
                case "Money":
                    //TODO open setting view
                    break;
                case "Slide":
                    //TODO open setting view
                    break;
            }
        }
    }

    ArrayList<MisfitHelperControl> MFControls = new ArrayList<MisfitHelperControl>();
    private void initView() {
        LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.linearLayout1);
        LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.linearLayout2);

        ArrayList<BaseMisfitHelper> misfitHelpers = HelloService.getInstance().getMisfitHelpers();
        for (int i = 0; i < misfitHelpers.size(); i++) {
            BaseMisfitHelper<BaseSetting> misfitHelper = misfitHelpers.get(i);
            final MisfitHelperControl MFControl = createMFControl(misfitHelper);
            MFControls.add(MFControl);
            MFControl.setVisibility(View.GONE);

            if(i > misfitHelpers.size()/2+0.5){
                linearLayout2.addView(MFControl);
            }
            else {
                linearLayout1.addView(MFControl);
            }
        }
    }

    private void runAnimation(){
        int n = 0;
        for (int i = MFControls.size()-1; i >= 0; i--){
            final MisfitHelperControl MFControl = MFControls.get(i);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MFControl.setVisibility(View.VISIBLE);
                    Animation inAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate);
                    MFControl.startAnimation(inAnimation);
                }
            }, n++*300);
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
