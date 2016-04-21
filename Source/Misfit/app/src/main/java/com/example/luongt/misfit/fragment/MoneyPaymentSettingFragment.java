package com.example.luongt.misfit.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.luongt.misfit.R;
import com.example.luongt.misfit.misfithelper.MoneyStatisticHelper;
import com.example.luongt.misfit.model.setting.MoneySetting;

/**
 * Created by luongt on 4/20/2016.
 */
public class MoneyPaymentSettingFragment extends BaseFragment implements View.OnClickListener, TextView.OnEditorActionListener{
    private View _view;
    private Context _context;
    private MoneySetting _moneySetting;

    private ImageButton _singleButton;
    private ImageButton _doubleButton;
    private ImageButton _tripleButton;
    private ImageButton _longButton;

    private EditText _moneyEditText;
    private TextView _pressStateTV;

    private MoneyStatisticHelper _moneyStatisticHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_money_setting, container, false);
        _context = _view.getContext();
        _moneyStatisticHelper = new MoneyStatisticHelper(_view.getContext());

        _moneySetting = (MoneySetting)_moneyStatisticHelper.getSetting();

        initView();

        return _view;
    }

    private void initView(){
        _singleButton = (ImageButton)_view.findViewById(R.id.singleButton);
        _doubleButton = (ImageButton)_view.findViewById(R.id.doubleButton);
        _tripleButton = (ImageButton)_view.findViewById(R.id.tripleButton);
        _longButton = (ImageButton)_view.findViewById(R.id.longButton);

        _singleButton.setOnClickListener(this);
        _doubleButton.setOnClickListener(this);
        _tripleButton.setOnClickListener(this);
        _longButton.setOnClickListener(this);

        _moneyEditText = (EditText)_view.findViewById(R.id.moneyEditText);
        _moneyEditText.setText((int) _moneySetting.getSPMoney() + "");
        _moneyEditText.setOnEditorActionListener(this);

        _pressStateTV = (TextView) _view.findViewById(R.id.pressStateTV);
    }

    private void changeSetting(double money){
        _moneySetting = (MoneySetting)_moneyStatisticHelper.getSetting();
        switch(buttonMode){
            case 0:
                _moneySetting.setSPMoney(money);
                break;
            case 1:
                _moneySetting.setDPMoney(money);
                break;
            case 2:
                _moneySetting.setTPMoney(money);
                break;
        }
        _moneyStatisticHelper.saveSetting(_moneySetting);
    }

    int buttonMode;
    @Override
    public void onClick(View v) {
        if(v == _singleButton){
            buttonMode = 0;
            _moneyEditText.setText((int) _moneySetting.getSPMoney() + "");
            _pressStateTV.setText("Single press");
            //TODO:animation abc xyz
        }
        if(v == _doubleButton){
            buttonMode = 1;
            _moneyEditText.setText((int)_moneySetting.getDPMoney()+"");
            _pressStateTV.setText("Double press");
            //TODO:animation abc xyz
        }
        if(v == _tripleButton){
            buttonMode = 2;
            _moneyEditText.setText((int)_moneySetting.getTPMoney()+"");
            _pressStateTV.setText("Triple press");
            //TODO:animation abc xyz
        }
        if(v == _longButton){
            _moneyEditText.setText("x10");
            _pressStateTV.setText("Long press");
            //TODO:animation abc xyz
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //TODO: validate
        changeSetting(Integer.parseInt(v.getText().toString()));
        return true;
    }

}
