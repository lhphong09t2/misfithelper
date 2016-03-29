package com.example.luongt.misfit.model;

/**
 * Created by luongt on 3/28/2016.
 */
public class AlarmSetting {
    public AlarmSetting(int hour, int minute, boolean isRepeat){
        this.hour = hour;
        this.minute = minute;
        this.isRepeat = isRepeat;
    }

    private int hour;
    private int minute;
    private boolean isRepeat;

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean getIsRepeat() {
        return isRepeat;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setIsRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }
}
