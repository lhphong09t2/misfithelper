package com.example.luongt.misfit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.luongt.misfit.control.PassCode;
import com.example.luongt.misfit.misfithelper.LockHelper;
import com.example.luongt.misfit.model.setting.LockSetting;
import com.example.luongt.misfit.receiver.LockReceiver;
import com.example.luongt.misfit.service.HelloService;

public class LockActivity extends AppCompatActivity implements View.OnClickListener{

    private FrameLayout _frameLayout;

    private Button _passCodeButton;

    private PassCode _passcode;

    private TextView _textView;
    private TextView _passcodeEditText;

    private WindowManager _manager;

    private LockHelper _lockHelper;

    private LocalBroadcastManager _localBroadcastManager;
    private BroadcastReceiver _broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MFContants.FINISH_LOCK)){
                Unlock();
                LockReceiver.isScreenOn = false;
            }
        }
    };

    private  static LockActivity _instance;
    public static LockActivity getInstance()
    {
        return  _instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showWhenLocked();
        setFullScreen();

        _instance = this;
        _lockHelper = HelloService.getInstance().getLockHelper();

        _manager = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE));

        _frameLayout = new FrameLayout(this);
        _manager.addView(LayoutInflater.from(this).inflate(R.layout.activity_lock, _frameLayout, true), getLayoutParams());

         _localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter mIntentFilter = new IntentFilter(MFContants.FINISH_LOCK);
        _localBroadcastManager.registerReceiver(_broadcastReceiver, mIntentFilter);

        _passCodeButton = (Button) _frameLayout.findViewById(R.id.passCodeButton);
        _passCodeButton.setOnClickListener(this);

        _passcode = (PassCode) _frameLayout.findViewById(R.id.passCode);
        _passcode.setVisibility(View.GONE);

        _textView = (TextView) _frameLayout.findViewById(R.id.textView);

        _passcodeEditText = (TextView) _frameLayout.findViewById(R.id.passcodeEditText);
    }

    public void showWhenLocked(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public void setFullScreen(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public WindowManager.LayoutParams getLayoutParams(){
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        return localLayoutParams;
    }

    @Override
    public void onClick(View v) {
        if(v == _passCodeButton){
            _passCodeButton.setVisibility(View.GONE);
            _textView.setText("Enter PIN to unlock phone");
            _passcode.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _instance = null;
        _manager.removeView(_frameLayout);
        _localBroadcastManager.unregisterReceiver(_broadcastReceiver);
    }

    public void onNumberClick(View v) {
        if(_passcodeEditText.getText().toString().length() < 4) {
            _passcodeEditText.setText(_passcodeEditText.getText() + ((Button) v).getText().toString());
        }
        if(_passcodeEditText.getText().toString().length() == 4){
            onOKClick(v);
        }
    }

    public void onBackClick(View v) {
        String passcode = _passcodeEditText.getText().toString();
        int n = passcode.length();
        if(_passcodeEditText.length() > 0) {
            _passcodeEditText.setText(passcode.substring(0, n-1));
        }
    }

    public void onOKClick(View v) {
        if(_passcodeEditText.getText().toString().equals(((LockSetting)_lockHelper.getSetting()).getPasscode())){
            Unlock();
        }
        else{
            _textView.setText("PIN is incorrect");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    _passcodeEditText.setText("");
                }
            }, 100);
        }
    }

    public void Unlock(){
        finish();
        LockReceiver.isScreenOn = false;
    }
}