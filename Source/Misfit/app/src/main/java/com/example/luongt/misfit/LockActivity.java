package com.example.luongt.misfit;

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
import android.widget.FrameLayout;

public class LockActivity extends AppCompatActivity {

    FrameLayout viewStatusBar;
    FrameLayout viewNavBar;

    WindowManager manager;
    private LocalBroadcastManager _localBroadcastManager;
    private BroadcastReceiver _broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("android.intent.action.FINISH")){
                manager.removeView(viewStatusBar);
                manager.removeView(viewNavBar);
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
        IntentFilter mIntentFilter = new IntentFilter("android.intent.action.FINISH");
        _localBroadcastManager.registerReceiver(_broadcastReceiver, mIntentFilter);
    }

    public void setFullScreen(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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
    protected void onDestroy() {
        super.onDestroy();
        _localBroadcastManager.unregisterReceiver(_broadcastReceiver);
    }
}
