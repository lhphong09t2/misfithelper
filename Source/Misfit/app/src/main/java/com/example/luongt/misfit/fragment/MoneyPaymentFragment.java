package com.example.luongt.misfit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luongt.misfit.R;
import com.example.luongt.misfit.misfithelper.MoneyStatisticHelper;

/**
 * Created by luongt on 4/7/2016.
 */
public class MoneyPaymentFragment extends BaseFragment<MoneyStatisticHelper> {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_money_payment, container, false);
    }
}
