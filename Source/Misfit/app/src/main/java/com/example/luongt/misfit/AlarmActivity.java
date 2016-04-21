package com.example.luongt.misfit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.luongt.misfit.misfithelper.AlarmHelper;
import com.example.luongt.misfit.model.setting.AlarmSetting;
import com.example.luongt.misfit.receiver.AlarmReceiver;
import com.example.luongt.misfit.service.HelloService;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener
{
    private AlarmManager _alarmMgr;
    private PendingIntent _alarmIntent;
    private AlarmHelper _alarmHelper;

    private Button _enableButton;
    private CheckBox _repeatCB;
    private TimePicker _alarmTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        _enableButton = (Button)findViewById(R.id.enableButton);
        _enableButton.setOnClickListener(this);

        _alarmTimePicker = (TimePicker)findViewById(R.id.alarmTimePicker);

        _repeatCB = (CheckBox)findViewById(R.id.repeatCB);

        _alarmHelper = HelloService.getInstance().getAlarmHelper();
        if(_alarmHelper != null){
            _alarmTimePicker.setCurrentHour(_alarmHelper.getSetting().getHour());
            _alarmTimePicker.setCurrentMinute(_alarmHelper.getSetting().getMinute());
            _repeatCB.setChecked(_alarmHelper.getSetting().isRepeat());
        }
    }

    private void setAlarm(int hour, int minute, Boolean repeat){
        _alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        _alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        if(repeat){
            _alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, _alarmIntent);
        }
        else {
            _alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), _alarmIntent);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == _enableButton){
            int hour = _alarmTimePicker.getCurrentHour(); //TODO: WTF? getHour() require API23 and getCurrentHour() in deprecated!!!
            int minute = _alarmTimePicker.getCurrentMinute();
            _alarmHelper.saveSetting(new AlarmSetting(hour, minute, _repeatCB.isChecked(), true));

            Toast.makeText(this, "Set alarm at "+hour+":" +minute, Toast.LENGTH_LONG).show();
            setAlarm(hour, minute, _repeatCB.isChecked());
        }
    }
}
