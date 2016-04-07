package com.example.luongt.misfit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.luongt.misfit.misfithelper.AlarmHelper;
import com.example.luongt.misfit.receiver.AlarmReceiver;
import com.example.luongt.misfit.receiver.LockReceiver;

import java.util.Calendar;

public class AlarmReachedActivity extends AppCompatActivity implements View.OnClickListener{

    private Button snoozeButton;
    private Button dismissButton;

    private MediaPlayer _mediaPlayer;

    int timeSnooze = 5;

    private LocalBroadcastManager _localBroadcastManager;
    private BroadcastReceiver _broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MFContants.DISMISS)){
                finish();
            }
            if(intent.getAction().equals(MFContants.SNOOZE)){
                Snooze();
            }
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_reached);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        snoozeButton =(Button)findViewById(R.id.snoozeButton);
        snoozeButton.setOnClickListener(this);
        dismissButton = (Button)findViewById(R.id.dismissButton);
        dismissButton.setOnClickListener(this);

        snoozeButton.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BRIGHTNESS_DOWN));

        _localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter mIntentFilter = new IntentFilter(MFContants.DISMISS);
        mIntentFilter.addAction(MFContants.SNOOZE);
        _localBroadcastManager.registerReceiver(_broadcastReceiver, mIntentFilter);

        AlarmHelper.isAlarming = true;

        _mediaPlayer = MediaPlayer.create(this, R.raw.cuckoo);
        _mediaPlayer.setLooping(true);
        _mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _localBroadcastManager.unregisterReceiver(_broadcastReceiver);
        AlarmHelper.isAlarming = false;
        _mediaPlayer.stop();
        if(LockReceiver.isScreenOn) {
            startActivity(new Intent(this, LockActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            LockReceiver.isScreenOn = false;
        }
    }

    @Override
    public void onClick(View v) {
        if(v == snoozeButton){
            Snooze();
        }
        if(v == dismissButton){
            this.finish();
        }
    }

    public void Snooze(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, timeSnooze);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        this.finish();
    }
}
