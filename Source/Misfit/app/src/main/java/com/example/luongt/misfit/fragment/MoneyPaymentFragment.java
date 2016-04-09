package com.example.luongt.misfit.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.luongt.misfit.R;
import com.example.luongt.misfit.control.MoneyItem;
import com.example.luongt.misfit.databasehelper.MoneyPaymentHelper;
import com.example.luongt.misfit.misfithelper.MoneyStatisticHelper;
import com.example.luongt.misfit.misfithelper.OnMoneyPaymentAddedListener;
import com.example.luongt.misfit.model.setting.MoneySetting;
import com.example.luongt.misfit.model.table.MoneyPayment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by luongt on 4/7/2016.
 */
public class MoneyPaymentFragment extends BaseFragment<MoneyStatisticHelper> implements View.OnClickListener, OnMoneyPaymentAddedListener{
    View view;
    Calendar calendar;

    private static final String TAG = "MoneyPaymentFragment";

    private MoneySetting _moneySetting;

    private Context _context;

    MoneyStatisticHelper moneyStatisticHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_money_payment, container, false);

        initView(view);

        _context = view.getContext();

        _moneySetting = (MoneySetting)new MoneyStatisticHelper(_context).getSetting();

        MoneyPaymentHelper moneyPaymentHelper = new MoneyPaymentHelper(_context);
        ArrayList<MoneyPayment> moneyPayments = moneyPaymentHelper.getData();

        if(moneyPayments.size()>0){
            for(MoneyPayment moneyPayment: moneyPayments){
                MoneyItem moneyItem = new MoneyItem(_context, moneyPayment);
                _payment.addView(moneyItem);
            }
        }

        moneyStatisticHelper = new MoneyStatisticHelper(view.getContext());
        moneyStatisticHelper.setOnMoneyPaymentAddedListener(this);

        return view;
    }

    private View _datePickerButton;
    private TextView _dateView;
    private View _addButton;

    private LinearLayout _payment;

    private void initView(View view)
    {
        _datePickerButton = view.findViewById(R.id.datePickerButton);
        _datePickerButton.setOnClickListener(this);

        _addButton = view.findViewById(R.id.addButton);
        _addButton.setOnClickListener(this);

        _dateView = (TextView)view.findViewById(R.id.dateView);

        _payment = (LinearLayout)view.findViewById(R.id.payment);

        calendar = Calendar.getInstance();
        String a = calendar.toString();
        _dateView.setText(String.format("%02d", calendar.get(Calendar.MONTH)) + "/" + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
    }

    @Override
    public void onClick(View v) {
        if (v == _datePickerButton)
        {
            new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    _dateView.setText(String.format("%02d",monthOfYear+1)+"/"+String.format("%02d",dayOfMonth));
                }
            },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        if(v == _addButton)
        {
            //TODO: get MoneyPayment input
            MoneyPayment moneyPayment = new MoneyPayment(new Random().nextInt(),calendar.toString(), 4232, "cc");
            addnewItem(moneyPayment);
        }
    }

    private void addnewItem(MoneyPayment moneyPayment){
        MoneyItem moneyItem = new MoneyItem(view.getContext(), moneyPayment);
        MoneyPaymentHelper moneyDBHelper = new MoneyPaymentHelper(_context);
        moneyDBHelper.createNew(moneyPayment);
        _payment.addView(moneyItem);
    }

    @Override
    public void onAdded() {
        Log.i("MoneyPaymentFragment", "onAdded");
        //TODO: Reload UI
    }
}
