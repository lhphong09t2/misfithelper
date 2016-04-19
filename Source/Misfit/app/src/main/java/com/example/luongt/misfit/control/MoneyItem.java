package com.example.luongt.misfit.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luongt.misfit.R;
import com.example.luongt.misfit.model.table.MoneyPayment;

/**
 * Created by luongt on 4/7/2016.
 */
public class MoneyItem extends RelativeLayout {

    private TextView _contentPayment;
    private TextView _amountPayment;
    private TextView _idPayment;

    private TextView _timePayment;

    public String getContentPayment() {
        return _contentPayment.getText().toString();
    }

    public String getAmountPayment() {
        return _amountPayment.getText().toString();
    }

    public int getIdPayment() {
        return Integer.parseInt(_idPayment.getText().toString());
    }

    public String getTimePayment() {
        return _timePayment.getText().toString();
    }

    public MoneyItem(Context context, MoneyPayment moneyPayment) {
        super(context);

        initView(context);
//
        _contentPayment = (TextView) findViewById(R.id.contentPayment);
        _amountPayment = (TextView)findViewById(R.id.amountPayment);
        _timePayment = (TextView) findViewById(R.id.timePayment);
        _idPayment = (TextView) findViewById(R.id.idPayment);

        _contentPayment.setText(moneyPayment.getContent());
        _amountPayment.setText((int)moneyPayment.getAmountMoney()+"");
        _timePayment.setText(moneyPayment.getTime());
        _idPayment.setText(moneyPayment.getId()+"");
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.money_item, this, true);
    }
}
