package com.example.luongt.misfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luongt.misfit.misfithelper.LockHelper;
import com.example.luongt.misfit.model.setting.LockSetting;
import com.example.luongt.misfit.service.HelloService;

public class LockConfirmPinActivity extends AppCompatActivity implements TextWatcher {

    private TextView _confirmTV;

    private EditText _confirmPasscodeET;

    private LockHelper _lockHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_confirm_pin);

        _lockHelper = HelloService.getInstance().getLockHelper();

        initView();
    }

    private void initView(){
        _confirmTV = (TextView) findViewById(R.id.confirmTV);
        _confirmPasscodeET = (EditText) findViewById(R.id.confirmPasscodeET);
        _confirmPasscodeET.addTextChangedListener(this);
    }

    private void checkPasscode(String passcode){
        if(((LockSetting)_lockHelper.getSetting()).getPasscode().equals(passcode)){
            startActivity(new Intent(this, LockSettingActivity.class));
        }
        else {
            _confirmTV.setText("Incorrect, try again");
            _confirmPasscodeET.setText("");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().length() == 4){
            checkPasscode(s.toString());
        }
    }
}
