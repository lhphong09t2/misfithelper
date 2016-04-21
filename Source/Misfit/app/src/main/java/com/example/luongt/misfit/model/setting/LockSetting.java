package com.example.luongt.misfit.model.setting;

/**
 * Created by luongt on 4/20/2016.
 */
public class LockSetting extends BaseSetting {
    private String _passcode;

    public LockSetting(String _passcode) {
        this._passcode = _passcode;
    }

    public String getPasscode() {
        return _passcode;
    }

    public void setPasscode(String _passcode) {
        this._passcode = _passcode;
    }
}
