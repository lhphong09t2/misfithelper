package com.example.luongt.misfit.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luongt.misfit.R;

/**
 * Created by luongt on 4/5/2016.
 */
public class MisfitHelperControl extends RelativeLayout {
    public MisfitHelperControl(Context context) {
        super(context);
        intView(context);
    }

    private ImageView _misfitIcon;
    private TextView _misfitName;

    private void intView(Context context)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.misfit_helper_control, this, true);

        _misfitIcon = (ImageView)findViewById(R.id.misfitIcon);
        _misfitName = (TextView)findViewById(R.id.misfitName);
    }

    public void setIconId(int id)
    {
        _misfitIcon.setImageResource(id);
    }

    public void setName(String name)
    {
        _misfitName.setText(name);
    }

    public String getName()
    {
        return _misfitName.getText().toString();
    }
}
