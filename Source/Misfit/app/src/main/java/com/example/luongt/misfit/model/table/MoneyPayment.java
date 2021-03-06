package com.example.luongt.misfit.model.table;

/**
 * Created by luongt on 4/6/2016.
 */
public class MoneyPayment extends BaseTable {
    private int _id;
    private String _time;
    private double _amountMoney;
    private String _content;

    public MoneyPayment(int id, String time, double amountMoney, String content) {
        this._id = id;
        this._time = time;
        this._amountMoney = amountMoney;
        this._content = content;
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
