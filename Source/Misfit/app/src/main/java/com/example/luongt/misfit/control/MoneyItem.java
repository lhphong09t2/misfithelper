package com.example.luongt.misfit.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.luongt.misfit.R;

/**
 * Created by luongt on 4/7/2016.
 */
public class MoneyItem extends RelativeLayout {
    public MoneyItem(Context context) {
        super(context);
    }

    public MoneyItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoneyItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.pass_code, this, true);
    }
}
