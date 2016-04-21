package com.example.luongt.misfit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.luongt.misfit.R;
import com.example.luongt.misfit.model.table.MoneyPayment;

import java.util.List;

/**
 * Created by luongt on 4/19/2016.
 */
public class CustomAdapter extends ArrayAdapter<MoneyPayment> {

    public CustomAdapter(Context context, int resource, List<MoneyPayment> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MoneyPayment payment = getItem(position);

        TextView _contentPayment;
        TextView _amountPayment;
        TextView _timePayment;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.money_item, parent, false);
        }

        _contentPayment = (TextView)convertView.findViewById(R.id.contentPayment);
        _amountPayment = (TextView)convertView.findViewById(R.id.amountPayment);
        _timePayment = (TextView)convertView.findViewById(R.id.timePayment);

        _contentPayment.setText(payment.getContent());
        _amountPayment.setText((int)payment.getAmountMoney()+"");
        _timePayment.setText(payment.getTime());

        return convertView;
    }
}
