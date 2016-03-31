package com.example.luongt.misfit.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.luongt.misfit.R;

/**
 * Created by luongt on 3/31/2016.
 */
public class PassCode extends LinearLayout {

    public PassCode(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitControl(context);
    }

    private void InitControl(Context context) {
        setOrientation(VERTICAL);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.pass_code, this, true);
    }
}
