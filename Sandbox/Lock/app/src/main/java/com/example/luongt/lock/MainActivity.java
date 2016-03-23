package com.example.luongt.lock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.luongt.lock.service.ServiceLock;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(getBaseContext(), ServiceLock.class));
    }

    @Override
    protected void onDestroy() {
        startService(new Intent(getBaseContext(), ServiceLock.class));
        super.onDestroy();
    }
}
