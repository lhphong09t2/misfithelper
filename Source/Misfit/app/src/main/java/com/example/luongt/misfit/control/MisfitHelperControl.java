package com.example.luongt.misfit.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.luongt.misfit.R;
import com.example.luongt.misfit.helper.ScreenHelper;

/**
 * Created by luongt on 4/5/2016.
 */
public class MisfitHelperControl extends RelativeLayout {
    public MisfitHelperControl(Context context) {
        super(context);
        intView(context);
    }

    private ImageView _misfitIcon;
    private String _misfitName;

    private void intView(Context context)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.misfit_helper_control, this, true);

        _misfitIcon = (ImageView)findViewById(R.id.misfitIcon);

        setLayoutParams(new LinearLayout.LayoutParams(ScreenHelper.toPixel(130), ScreenHelper.toPixel(130), 1f));
    }

    public void setIconId(int id)
    {
        _misfitIcon.setImageResource(id);
    }

    public void setName(String name)
    {
        _misfitName = name;
    }

    public String getName()
    {
        return _misfitName;
    }
}
