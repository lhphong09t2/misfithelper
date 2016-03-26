package com.example.luongt.lock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.example.luongt.lock.service.ServiceLock;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(getBaseContext(), ServiceLock.class));
    }

    @Override
    public void onAttachedToWindow() {
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG|WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
    protected void onDestroy() {
        startService(new Intent(getBaseContext(), ServiceLock.class));
        super.onDestroy();
    }



}
