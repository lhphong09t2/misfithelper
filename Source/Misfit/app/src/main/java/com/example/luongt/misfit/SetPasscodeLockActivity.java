package com.example.luongt.misfit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luongt.misfit.misfithelper.LockHelper;
import com.example.luongt.misfit.model.setting.LockSetting;
import com.example.luongt.misfit.service.HelloService;

public class SetPasscodeLockActivity extends AppCompatActivity {

    private TextView _confirmTV;

    private EditText _passcodeET;

    private String _passcode;
    private boolean _isConfirm;

    LockHelper _lockHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_passcode);

        _lockHelper = HelloService.getInstance().getLockHelper();

        initView();
    }

    private void initView(){
        _confirmTV = (TextView) findViewById(R.id.confirmTV);

        _passcodeET = (EditText) findViewById(R.id.passcodeET);
    }

    public void enterPasscode(View v){
        if(_isConfirm){
            if(_passcodeET.getText().toString().equals(_passcode)){
                _lockHelper.saveSetting(new LockSetting(_passcodeET.getText().toString(),((LockSetting)_lockHelper.getSetting()).isEnable()));
                finish();
            }
            else {
                _passcodeET.setText("");
                _confirmTV.setText("Incorrect, try again");
            }
        }
        else{
            _passcode = _passcodeET.getText().toString();
            _passcodeET.setText("");
            _confirmTV.setText("Confirm passcode");
        }
        _isConfirm = !_isConfirm;
    }

    public void cancel(View v){
        finish();
    }

}
