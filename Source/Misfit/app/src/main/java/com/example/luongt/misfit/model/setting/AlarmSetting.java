package com.example.luongt.misfit.model.setting;

/**
 * Created by luongt on 3/28/2016.
 */
public class AlarmSetting extends BaseSetting {
    public AlarmSetting(int hour, int minute, boolean isRepeat) {
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

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }
}