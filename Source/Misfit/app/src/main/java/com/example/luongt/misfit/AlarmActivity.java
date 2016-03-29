package com.example.luongt.misfit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.luongt.misfit.receiver.AlarmReceiver;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener
{
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private TextView timeTextView;
    private Button saveButton;
    private CheckBox repeatCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        timeTextView = (TextView)findViewById(R.id.timeTextView);
        timeTextView.setOnClickListener(this);

        repeatCheckbox = (CheckBox)findViewById(R.id.repeatCheckbox);

    }

    private void setAlarm(int hour, int minute, Boolean repeat){
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        if(repeat){
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }
        else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()-60*1000, alarmIntent);
        }
    }

    private int hourOfDay;
    public int getHourOfDay() {
        return hourOfDay;
    }

    private int minute;
    public int getMinute() {
        return minute;
    }

    @Override
    public void onClick(View v) {
        if(v == timeTextView) {
            new TimePickerDialog(this, TimePickerDialog.BUTTON_NEGATIVE, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    timeTextView.setText(hourOfDay+" : "+String.format("%02d", minute));
                    AlarmActivity.this.hourOfDay = hourOfDay;
                    AlarmActivity.this.minute = minute;
                }
            }, 12, 00, false).show();
        }
        if(v == saveButton){
            Intent intent = new Intent("ACTION_SAVE_SETTING");
            intent.putExtra("HOUR_OF_DATE", hourOfDay);
            intent.putExtra("MINUTE", minute);
            intent.putExtra("IS_REPEAT", repeatCheckbox.isChecked());
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

            Toast.makeText(this, "Set alarm at "+hourOfDay+":"+String.format("%02d", minute), Toast.LENGTH_LONG).show();
            setAlarm(hourOfDay, minute, repeatCheckbox.isChecked());
        }
    }
}
