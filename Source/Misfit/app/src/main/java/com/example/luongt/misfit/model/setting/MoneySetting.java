package com.example.luongt.misfit.model.setting;

/**
 * Created by luongt on 4/6/2016.
 */
public class MoneySetting {
    private double _moneySP;
    private double _moneyDP;
    private double _moneyTP;
    private int _delayTime;

    public MoneySetting(double _moneySP, double _moneyDP, double _moneyTP, int _delayTime) {
        this._moneySP = _moneySP;
        this._moneyDP = _moneyDP;
        this._moneyTP = _moneyTP;
        this._delayTime = _delayTime;
    }

    public double getSPMoney() {
        return _moneySP;
    }

    public void setSPMoney(int _moneySP) {
        this._moneySP = _moneySP;
    }

    public double getDPMoney() {
        return _moneyDP;
    }

    public void setDPMoney(int _moneyDP) {
        this._moneyDP = _moneyDP;
    }

    public int getDelayTime() {
        return _delayTime;
    }

    public void setDelayTime(int _delayTime) {
        this._delayTime = _delayTime;
    }

    public double getTPMoney() {
        return _moneyTP;
    }

    public void setTPMoney(double _moneyTP) {
        this._moneyTP = _moneyTP;
    }
}
