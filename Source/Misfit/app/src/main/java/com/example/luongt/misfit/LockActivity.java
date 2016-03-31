package com.example.luongt.misfit;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.luongt.misfit.control.PassCode;

public class LockActivity extends AppCompatActivity implements View.OnClickListener{

    FrameLayout viewStatusBar;
    FrameLayout viewNavBar;

    Button passCodeButton;

    PassCode passcode;

    TextView textView;
    EditText passcodeEditText;

    WindowManager manager;
    private LocalBroadcastManager _localBroadcastManager;
    private BroadcastReceiver _broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MFContants.FINISH_LOCK)){
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullScreen();
        DisableSystemBar();
        showWhenLocked();

        setContentView(R.layout.activity_lock);

         _localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter mIntentFilter = new IntentFilter(MFContants.FINISH_LOCK);
        _localBroadcastManager.registerReceiver(_broadcastReceiver, mIntentFilter);

        passCodeButton = (Button)findViewById(R.id.passCodeButton);
        passCodeButton.setOnClickListener(this);

        passcode = (PassCode)findViewById(R.id.passCode);
        passcode.setVisibility(View.GONE);

        textView = (TextView)findViewById(R.id.textView);
        passcodeEditText = (EditText)findViewById(R.id.passcodeEditText);
    }

    public void setFullScreen(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void showWhenLocked(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    public WindowManager.LayoutParams getLayoutParams(int position){
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = position;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = (int) (50 * getResources()
                .getDisplayMetrics().scaledDensity);
        localLayoutParams.format = PixelFormat.TRANSPARENT;
        return localLayoutParams;
    }

    public  void DisableSystemBar(){
        manager = ((WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));

        WindowManager.LayoutParams layoutTop = getLayoutParams(Gravity.TOP);
        viewStatusBar = new FrameLayout(this);
        manager.addView(viewStatusBar, layoutTop);

        WindowManager.LayoutParams layoutBottom = getLayoutParams(Gravity.BOTTOM);
        viewNavBar = new FrameLayout(this);
        manager.addView(viewNavBar, layoutBottom);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public void onClick(View v) {
        if(v == passCodeButton){
            passCodeButton.setVisibility(View.GONE);
            textView.setText("Enter PIN to unlock phone");
            passcode.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _localBroadcastManager.unregisterReceiver(_broadcastReceiver);

        manager.removeView(viewStatusBar);
        manager.removeView(viewNavBar);

        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
        lock.disableKeyguard();
    }

    public void on1Click(View v) {
        passcodeEditText.setText(passcodeEditText.getText()+"1");
    }

    public void on2Click(View v) {
        passcodeEditText.setText(passcodeEditText.getText()+"2");
    }

    public void on3Click(View v) {
        passcodeEditText.setText(passcodeEditText.getText()+"3");
    }

    public void on4Click(View v) {
        passcodeEditText.setText(passcodeEditText.getText()+"4");
    }

    public void on5Click(View v) {
        passcodeEditText.setText(passcodeEditText.getText()+"5");
    }

    public void on6Click(View v) {
        passcodeEditText.setText(passcodeEditText.getText()+"6");
    }

    public void on7Click(View v) {
        passcodeEditText.setText(passcodeEditText.getText()+"7");
    }

    public void on8Click(View v) {
        passcodeEditText.setText(passcodeEditText.getText()+"8");
    }

    public void on9Click(View v) {
        passcodeEditText.setText(passcodeEditText.getText()+"9");
    }

    public void on0Click(View v) {
        passcodeEditText.setText(passcodeEditText.getText()+"0");
    }

    public void onBackClick(View v) {
        int n = passcodeEditText.getText().length();
        if(passcodeEditText.length() > 0) {
            passcodeEditText.setText(passcodeEditText.getText().replace(n - 1, n, ""));
        }
    }

    public void onOKClick(View v) {
        if(passcodeEditText.getText().toString().equals("1235")){
            this.finish();
        }

        //TODO: The pin you enter is not correct
    }
}
