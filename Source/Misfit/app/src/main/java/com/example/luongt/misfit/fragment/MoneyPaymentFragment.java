package com.example.luongt.misfit.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.luongt.misfit.R;
import com.example.luongt.misfit.control.MoneyItem;
import com.example.luongt.misfit.databasehelper.MoneyPaymentHelper;
import com.example.luongt.misfit.misfithelper.MoneyStatisticHelper;
import com.example.luongt.misfit.misfithelper.OnMoneyPaymentChangedListener;
import com.example.luongt.misfit.model.table.MoneyPayment;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by luongt on 4/7/2016.
 */
public class MoneyPaymentFragment extends BaseFragment<MoneyStatisticHelper> implements View.OnClickListener, OnMoneyPaymentChangedListener {
    View view;
    Calendar calendar;

    private static final String TAG = "MoneyPaymentFragment";

    private Context _context;

    MoneyStatisticHelper moneyStatisticHelper;

    private Button _okButton;
    private Button _cancelButton;

    private EditText _amountMoneyET;
    private EditText _contentET;
    private EditText _timeET;

    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_money_payment, container, false);

        _context = view.getContext();

        initView(view);
        refreshUI();

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
        _dateView.setText(String.format("%02d", calendar.get(Calendar.MONTH)) + "/" + String.format("%02d",
                calendar.get(Calendar.DAY_OF_MONTH)));

        dialog = new Dialog(_context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.money_payment_input);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = (int)(getResources().getDisplayMetrics().widthPixels*0.95);
        layoutParams.height = (int)(getResources().getDisplayMetrics().heightPixels*0.50);
        dialog.getWindow().setAttributes(layoutParams);

        _okButton = (Button)dialog.findViewById(R.id.okButton);
        _okButton.setOnClickListener(this);
        _cancelButton = (Button)dialog.findViewById(R.id.cancelButton);
        _cancelButton.setOnClickListener(this);

        _amountMoneyET = (EditText)dialog.findViewById(R.id.amountMoneyET);
        _contentET = (EditText)dialog.findViewById(R.id.contentET);
        _timeET = (EditText)dialog.findViewById(R.id.timeET);
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
            dialog.show();
        }
        if(v == _okButton){
            MoneyPayment moneyPayment = new MoneyPayment(0,_timeET.getText().toString(), Double.parseDouble(_amountMoneyET.getText().toString()), _contentET.getText().toString());
            moneyStatisticHelper.addNewToDB(moneyPayment);
            dialog.dismiss();
        }
        if(v == _cancelButton){
            dialog.dismiss();
        }
        if(v == _moneyItem){
            //TODO: Show info to view
        }
    }

    @Override
    public void onChanged() {
        refreshUI();
    }

    private MoneyItem _moneyItem;
    private void refreshUI(){
        ArrayList<MoneyPayment> moneyPayments = new MoneyPaymentHelper(_context).getData();

        if(moneyPayments.size()>0){
            for(MoneyPayment moneyPayment: moneyPayments){
                _moneyItem = new MoneyItem(_context, moneyPayment);
                _moneyItem.setOnClickListener(this);
                _payment.addView(_moneyItem);
            }
        }
    }
}
