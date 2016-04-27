package com.example.luongt.misfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.luongt.misfit.misfithelper.LockHelper;
import com.example.luongt.misfit.model.setting.LockSetting;
import com.example.luongt.misfit.service.HelloService;
import com.example.luongt.misfit.service.LockService;

public class LockSettingActivity extends AppCompatActivity {

    Switch _enableButton;

    LinearLayout _changePasscodeButton;

    LockHelper _lockHelper;

    LockSetting _LockSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_setting);

        _lockHelper = HelloService.getInstance().getLockHelper();
        _LockSetting = (LockSetting)_lockHelper.getSetting();

        initView();
    }

    private void initView(){
        _enableButton = (Switch) findViewById(R.id.enableButton);
        _enableButton.setChecked(_LockSetting.isEnable());

    }

    public void enableButtonClick(View v){
        if(((Switch)v).isChecked()){
            startService(new Intent(this, LockService.class));
        }
        else{
            stopService(new Intent(HelloService.getInstance(), LockService.class));
        }

        _LockSetting.setEnable(((Switch)v).isChecked());
        _lockHelper.saveSetting(_LockSetting);
    }

    public void changePassButtonClick(View v){
        startActivity(new Intent(this, SetPasscodeLockActivity.class));
    }
}
