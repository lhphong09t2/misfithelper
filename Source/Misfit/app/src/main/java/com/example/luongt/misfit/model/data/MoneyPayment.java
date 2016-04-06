package com.example.luongt.misfit.model.data;

/**
 * Created by luongt on 4/6/2016.
 */
public class MoneyPayment extends BaseData {
    private int _id;
    private String _time;
    private double _amountMoney;
    private String _content;

    public MoneyPayment(int _id, String time, double _amountMoney, String _content) {
        this._id = _id;
        this._time = time;
        this._amountMoney = _amountMoney;
        this._content = _content;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getTime() {
        return _time;
    }

    public void setTime(String time) {
        this._time = time;
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
