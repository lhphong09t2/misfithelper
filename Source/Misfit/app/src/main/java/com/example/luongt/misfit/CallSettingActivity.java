package com.example.luongt.misfit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.luongt.misfit.misfithelper.CallHelper;
import com.example.luongt.misfit.model.setting.CallSetting;

public class CallSettingActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher{

    private ImageButton _singleButton;
    private ImageButton _doubleButton;
    private ImageButton _tripleButton;

    private TextView _pressStateTV;

    private EditText _messageET;

    private CallHelper _callHelper;

    private CallSetting _callSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_setting);

        _callHelper = new CallHelper(this);
        _callSetting = (CallSetting)_callHelper.getSetting();
        initView();
    }

    private void initView() {
        _singleButton = (ImageButton) findViewById(R.id.singleButton);
        _doubleButton = (ImageButton) findViewById(R.id.doubleButton);
        _tripleButton = (ImageButton) findViewById(R.id.tripleButton);

        _pressStateTV = (TextView) findViewById(R.id.pressStateTV);

        _singleButton.setOnClickListener(this);
        _doubleButton.setOnClickListener(this);
        _tripleButton.setOnClickListener(this);

        _messageET = (EditText) findViewById(R.id.messageET);
        _messageET.addTextChangedListener(this);
        _messageET.setText(_callSetting.getMessage());
    }

    @Override
    public void onClick(View v) {
        if (v == _singleButton) {
            _pressStateTV.setText(_callHelper.getSinglePressTitle());
            //TODO:animation abc xyz
        }
        if (v == _doubleButton) {
            _pressStateTV.setText(_callHelper.getDoublePressTitle());
            //TODO:animation abc xyz
        }
        if (v == _tripleButton) {
            _pressStateTV.setText(_callHelper.getTriplePressTitle());
            //TODO:animation abc xyz
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        _callSetting.setMessage(s.toString());
        _callHelper.saveSetting(_callSetting);
    }
}
