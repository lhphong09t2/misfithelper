package com.example.luongt.misfit.model.data;

/**
 * Created by luongt on 4/6/2016.
 */
public class MoneyPayment extends BaseData {
    private String time;
    private double _amountMoney;
    private String _content;

    public MoneyPayment(String time, double _amountMoney, String _content) {
        this.time = time;
        this._amountMoney = _amountMoney;
        this._content = _content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAmountMoney() {
        return _amountMoney;
    }

    public void setAmountMoney(double _amountMoney) {
        this._amountMoney = _amountMoney;
    }

    public String getContent() {
        return _content;
    }

    public void setContent(String _content) {
        this._content = _content;
    }


}
