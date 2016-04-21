package com.example.luongt.misfit.model.setting;

/**
 * Created by luongt on 3/28/2016.
 */
public class CallSetting extends BaseSetting {
    String message;

    public CallSetting(String passcode) {
        this.message = passcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
