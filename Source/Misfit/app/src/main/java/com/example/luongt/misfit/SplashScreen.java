package com.example.luongt.misfit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.luongt.misfit.helper.ScreenHelper;
import com.example.luongt.misfit.service.HelloService;

/**
 * Created by luongt on 4/5/2016.
 */
public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenHelper.initVariables(this);

        if (HelloService.getInstance() == null) {
            startService(new Intent(this, HelloService.class));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashScreen.this.startActivity(new Intent(SplashScreen.this, MainActivity.class));
            }
        }, 2000);
    }
}
