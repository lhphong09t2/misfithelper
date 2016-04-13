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

    public TextView get_contentPayment() {
        return _contentPayment;
    }

    public TextView get_amountPayment() {
        return _amountPayment;
    }

    public TextView get_idPayment() {
        return _idPayment;
    }

    public MoneyItem(Context context, MoneyPayment moneyPayment) {
        super(context);

        initView(context);

        _contentPayment = (TextView) findViewById(R.id.contentPayment);
        _amountPayment = (TextView)findViewById(R.id.amountPayment);
        _idPayment = (TextView) findViewById(R.id.idPayment);

        _contentPayment.setText(moneyPayment.getContent());
        _amountPayment.setText(moneyPayment.getAmountMoney()+"");
        _idPayment.setText(moneyPayment.getId()+"");
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.money_item, this, true);
    }
}
