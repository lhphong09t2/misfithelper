package com.example.luongt.misfit.model.setting;

/**
 * Created by luongt on 4/6/2016.
 */
public class MoneySetting {
    private double _moneySP;
    private double _moneyDP;
    private int _delayTime;

    public MoneySetting(double _moneySP, double _moneyDP, int _delayTime) {
        this._moneySP = _moneySP;
        this._moneyDP = _moneyDP;
        this._delayTime = _delayTime;
    }

    public double getSPMoney() {

        return _moneySP;
    }

    public void setMoneySP(int _moneySP) {
        this._moneySP = _moneySP;
    }

    public double getDPMoney() {
        return _moneyDP;
    }

    public void setMoneyDP(int _moneyDP) {
        this._moneyDP = _moneyDP;
    }

    public int getDelayTime() {
        return _delayTime;
    }

    public void setDelayTime(int _delayTime) {
        this._delayTime = _delayTime;
    }
}
