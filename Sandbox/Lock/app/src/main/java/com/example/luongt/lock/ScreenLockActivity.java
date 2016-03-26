package com.example.luongt.lock;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class ScreenLockActivity extends AppCompatActivity implements View.OnClickListener {

    WindowManager manager;
    FrameLayout viewStatusBar;
    FrameLayout viewNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullScreen();
        //DisableSystemBar();
        showWhenLocked();

        setContentView(R.layout.activity_screen_lock);

        Button unlockButton = (Button)findViewById(R.id.unlockButton);
        unlockButton.setOnClickListener(this);
    }

    @Override
    public void onAttachedToWindow() {
        Log.e("Home Button", "onAttachedToWindow");
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onAttachedToWindow();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("Home Button", "Clicked");
        if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            Log.e("Home Button", "Clicked");
        }
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        //manager.removeView(viewStatusBar);
        //manager.removeView(viewNavBar);

        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
        lock.disableKeyguard();

        this.finish();
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
}
