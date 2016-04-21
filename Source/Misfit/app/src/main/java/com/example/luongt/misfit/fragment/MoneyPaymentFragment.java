package com.example.luongt.misfit.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luongt.misfit.R;
import com.example.luongt.misfit.adapter.CustomAdapter;
import com.example.luongt.misfit.databasehelper.MoneyPaymentHelper;
import com.example.luongt.misfit.misfithelper.MoneyStatisticHelper;
import com.example.luongt.misfit.misfithelper.OnMoneyPaymentChangedListener;
import com.example.luongt.misfit.model.table.MoneyPayment;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by luongt on 4/7/2016.
 */
public class MoneyPaymentFragment extends BaseFragment<MoneyStatisticHelper> implements View.OnClickListener,
        OnMoneyPaymentChangedListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    View view;
    Calendar calendar;

    private Context _context;

    private MoneyStatisticHelper _moneyStatisticHelper;

    private Button _okButton;
    private Button _cancelButton;
    private View _addButton;

    private EditText _amountMoneyET;
    private EditText _contentET;
    private EditText _timeET;

    private ListView _paymentListView;
    private CustomAdapter _paymentAdapter;
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_money_payment, container, false);

        _context = view.getContext();

        initView(view);
        refreshUI();

        _moneyStatisticHelper = new MoneyStatisticHelper(view.getContext());
        _moneyStatisticHelper.setOnMoneyPaymentChangedListener(this);

        return view;
    }

    private void initView(View view)
    {
        _addButton = view.findViewById(R.id.addButton);
        _addButton.setOnClickListener(this);

        calendar = Calendar.getInstance();

        dialog = new Dialog(_context);
        dialog.setContentView(R.layout.money_payment_input);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = (int)(getResources().getDisplayMetrics().widthPixels*0.95);
        layoutParams.height = (int)(getResources().getDisplayMetrics().heightPixels*0.70);
        dialog.getWindow().setAttributes(layoutParams);

        _okButton = (Button)dialog.findViewById(R.id.okButton);
        _okButton.setOnClickListener(this);
        _cancelButton = (Button)dialog.findViewById(R.id.cancelButton);
        _cancelButton.setOnClickListener(this);

        _amountMoneyET = (EditText)dialog.findViewById(R.id.amountMoneyET);
        _contentET = (EditText)dialog.findViewById(R.id.contentET);
        _timeET = (EditText)dialog.findViewById(R.id.timeET);
    }

    private void refreshUI(){
        ArrayList<MoneyPayment> moneyPayments = new MoneyPaymentHelper(_context).getData();
        _paymentListView = (ListView)view.findViewById(R.id.paymentListView);
        _paymentListView.setOnItemClickListener(this);
        _paymentListView.setOnItemLongClickListener(this);

        _paymentAdapter = new CustomAdapter(_context,R.layout.money_item, moneyPayments);
        _paymentListView.setAdapter(_paymentAdapter);
    }

    private boolean isEdit;
    @Override
    public void onClick(View v) {
        if(v == _addButton)
        {
            dialog.setTitle("Add money payment");
            dialog.show();
        }
        if(v == _okButton){
            if(isEdit){
                MoneyPayment moneyPayment = new MoneyPayment(_idPayment,_timeET.getText().toString(), Double.parseDouble(_amountMoneyET.getText().toString()), _contentET.getText().toString());
                _moneyStatisticHelper.updatePayment(moneyPayment);
                isEdit = false;
            }
            else{
                MoneyPayment moneyPayment = new MoneyPayment(0,_timeET.getText().toString(), Double.parseDouble(_amountMoneyET.getText().toString()), _contentET.getText().toString());
                _moneyStatisticHelper.addNewToDB(moneyPayment);
            }

            dialog.dismiss();
        }
        if(v == _cancelButton){
            dialog.dismiss();
        }
    }

    @Override
    public void onChanged() {
        refreshUI();
    }

    private int _idPayment;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView contentPayment = (TextView)view.findViewById(R.id.contentPayment);
        TextView amountPayment = (TextView)view.findViewById(R.id.amountPayment);
        TextView timePayment = (TextView)view.findViewById(R.id.timePayment);

        _amountMoneyET.setText(amountPayment.getText().toString());
        _contentET.setText(contentPayment.getText().toString());
        _timeET.setText(timePayment.getText().toString());
        dialog.setTitle("Edit money payment");
        dialog.show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final MoneyPayment payment = _paymentAdapter.getItem(position);

        new AlertDialog.Builder(_context)
                .setTitle("Delete")
                .setMessage("Do you want to delete?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _moneyStatisticHelper.deletePayment(payment.getId());
                    }
                }).create().show();

        return true;
    }
}
