package com.example.luongt.misfit.control.custom.listview;

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
public class CustomAdapter extends ArrayAdapter{
    Context context;
    List<MoneyPayment> moneyPayments;

    public CustomAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        moneyPayments = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.money_item, parent, false);

        TextView _contentPayment = (TextView)rowView.findViewById(R.id.contentPayment);
        TextView _amountPayment = (TextView)rowView.findViewById(R.id.amountPayment);
        TextView _timePayment = (TextView)rowView.findViewById(R.id.timePayment);
        TextView _idPayment = (TextView)rowView.findViewById(R.id.idPayment);

        _contentPayment.setText(moneyPayments.get(position).getContent());
        _amountPayment.setText((int)moneyPayments.get(position).getAmountMoney()+"");
        _timePayment.setText(moneyPayments.get(position). getTime());
        _idPayment.setText(moneyPayments.get(position).getId()+"");

        return rowView;

    }
}
