package com.example.luongt.misfit.model.setting;

/**
 * Created by luongt on 3/28/2016.
 */
public class AlarmSetting extends BaseSetting {
    private int hour;
    private int minute;
    private boolean isRepeat;
    private boolean isEnable;

    public AlarmSetting(int hour, int minute, boolean isRepeat, boolean isEnable) {
        this.hour = hour;
        this.minute = minute;
        this.isRepeat = isRepeat;
        this.isEnable = isEnable;
    }

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

    public boolean isRepeat() {
        return isRepeat;
    }

    public void isRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void isEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }
}
