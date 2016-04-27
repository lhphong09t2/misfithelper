package com.example.luongt.misfit.model.setting;

/**
 * Created by luongt on 4/20/2016.
 */
public class LockSetting extends BaseSetting {
    private String _passcode;
    private boolean _isEnable;

    public LockSetting(String passcode, boolean isEnable) {
        this._passcode = passcode;
        this._isEnable = isEnable;
    }

    public String getPasscode() {
        return _passcode;
    }

    public void setPasscode(String _passcode) {
        this._passcode = _passcode;
    }

    public boolean isEnable() {
        return _isEnable;
    }

    public void setEnable(boolean enable) {
        _isEnable = enable;
    }
}
