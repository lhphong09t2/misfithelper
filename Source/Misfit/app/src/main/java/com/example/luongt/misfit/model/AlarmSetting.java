package com.example.luongt.misfit.model;

import java.util.Date;

/**
 * Created by luongt on 3/28/2016.
 */
public class AlarmSetting {
    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    private Date alarmTime;

    public boolean[] getAllowedDay() {
        return allowedDay;
    }

    public void setAllowedDay(boolean[] allowedDay) {
        this.allowedDay = allowedDay;
    }

    private boolean[] allowedDay;

    public boolean isEnableInteration() {
        return enableInteration;
    }

    public void setEnableInteration(boolean enableInteration) {
        this.enableInteration = enableInteration;
    }

    private boolean enableInteration;


}
