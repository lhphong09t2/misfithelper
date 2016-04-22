package com.example.luongt.misfit.control;

import android.content.Context;

import com.example.luongt.misfit.R;

/**
 * Created by luongt on 4/22/2016.
 */
public class EnableButton extends MisfitHelperControl{
    private boolean _isChecked;

    public EnableButton(Context context, boolean isChecked) {
        super(context);
        _isChecked = isChecked;
        changeBackground(isChecked);
    }

    public boolean isChecked() {
        return _isChecked;
    }

    public void setChecked(boolean isChecked) {
        _isChecked = isChecked;
        changeBackground(isChecked);
    }

    @Override
    public boolean performClick() {
        setChecked(!_isChecked);
        return super.performClick();
    }

    private void changeBackground(boolean isChecked){
        if(isChecked){
            setIconId(R.drawable.on_misfit);
        }
        else{
            setIconId(R.drawable.off_misfit);
        }
    }
}
