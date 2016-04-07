package com.example.luongt.misfit.fragment;


import android.support.v4.app.Fragment;

/**
 * Created by luongt on 4/7/2016.
 */
public abstract class BaseFragment<T> extends Fragment {
    protected T _misfitHelper;

    public void SetMisfitHelper(T misfitHelper)
    {
        _misfitHelper = misfitHelper;
    }
}
