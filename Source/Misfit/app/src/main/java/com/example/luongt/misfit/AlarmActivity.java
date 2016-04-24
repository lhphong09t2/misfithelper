package com.example.luongt.misfit;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.luongt.misfit.misfithelper.AlarmHelper;
import com.example.luongt.misfit.model.setting.AlarmSetting;
import com.example.luongt.misfit.service.HelloService;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener
{
    private AlarmHelper _alarmHelper;
    private AlarmSetting _alarmSetting;

    private Button _enableButton;
    private CheckBox _repeatCB;
    private TimePicker _alarmTimePicker;

    private static AlarmActivity alarmActivity;
    public static AlarmActivity getInstance(){
        return alarmActivity;
    }

    private boolean _isEnable;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarmActivity = this;

        _enableButton = (Button)findViewById(R.id.enableButton);
        _enableButton.setOnClickListener(this);

        _alarmTimePicker = (TimePicker)findViewById(R.id.alarmTimePicker);

        _repeatCB = (CheckBox)findViewById(R.id.repeatCB);

        _alarmHelper = HelloService.getInstance().getAlarmHelper();
        if(_alarmHelper != null){
            _alarmSetting = _alarmHelper.getSetting();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                _alarmTimePicker.setHour(_alarmSetting.getHour());
                _alarmTimePicker.setMinute(_alarmSetting.getMinute());
            }else {
                _alarmTimePicker.setCurrentHour(_alarmSetting.getHour());
                _alarmTimePicker.setCurrentMinute(_alarmSetting.getMinute());
            }
            _repeatCB.setChecked(_alarmSetting.isRepeat());
            _isEnable = _alarmSetting.isEnable();
            setEnableButton();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == _enableButton){
            _isEnable = !_isEnable;
            setState(_isEnable);
        }
    }

    @SuppressWarnings("deprecation")
    public void setState(boolean isEnable){
        _isEnable = isEnable;
        int hour;
        int minute;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = _alarmTimePicker.getHour();
            minute = _alarmTimePicker.getMinute();
        }
        else{
            hour = _alarmTimePicker.getCurrentHour();
            minute = _alarmTimePicker.getCurrentMinute();
        }
        _alarmHelper.saveSetting(new AlarmSetting(hour, minute, _repeatCB.isChecked(), _isEnable));

        setEnableButton();
        if(_isEnable) {
            Toast.makeText(this, "Set alarm at " + hour + ":" + minute, Toast.LENGTH_LONG).show();
            _alarmHelper.setAlarm(hour, minute, _repeatCB.isChecked());
        }
        else {
            _alarmHelper.removeAlarm();
        }
    }

    @SuppressWarnings("deprecation")
    public void setEnableButton(){
        if(_isEnable) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                _enableButton.setTextColor(getResources().getColor(R.color.disableColor, null));
            }
            else {
                _enableButton.setTextColor(getResources().getColor(R.color.disableColor));
            }
            _enableButton.setText("TURN OFF ALARM");
        }
        else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                _enableButton.setTextColor(getResources().getColor(R.color.enableColor, null));
            }
            else {
                _enableButton.setTextColor(getResources().getColor(R.color.enableColor));
            }
            _enableButton.setText("TURN ON ALARM");
        }
    }
}
