package com.example.luongt.misfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.luongt.misfit.service.HelloService;
import com.misfit.misfitlinksdk.MFLSession;
import com.misfit.misfitlinksdk.publish.MFLCallBack;
import com.misfit.misfitlinksdk.publish.MFLCommand;
import com.misfit.misfitlinksdk.publish.MFLError;
import com.misfit.misfitlinksdk.publish.MFLGestureType;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch misfitSwitch = ((Switch) findViewById(R.id.enableMisfit));
        misfitSwitch.setOnCheckedChangeListener(this);
        misfitSwitch.setChecked(MFLSession.sharedInstance().isEnabled());

        if (HelloService.getInstance() == null)
        {
            startService(new Intent(this, HelloService.class));
        }
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
            startService(new Intent(getBaseContext(), HelloService.class));
        } else {
            MFLSession.sharedInstance().disable();
        }
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, AlarmActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
