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

    public MoneyItem(Context context, MoneyPayment moneyPayment) {
        super(context);

        initView(context);

        _contentPayment = (TextView) findViewById(R.id.contentPayment);
        _amountPayment = (TextView)findViewById(R.id.amountPayment);

        _contentPayment.setText(moneyPayment.getContent());
        _amountPayment.setText(moneyPayment.getAmountMoney()+"");
    }

    private void initView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.money_item, this, true);
    }
}
