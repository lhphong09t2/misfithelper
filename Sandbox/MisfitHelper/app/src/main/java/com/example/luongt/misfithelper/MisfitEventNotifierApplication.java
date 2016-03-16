package com.example.luongt.misfithelper;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.misfit.misfitlinksdk.MFLSession;

public class MisfitEventNotifierApplication extends Application implements Application.ActivityLifecycleCallbacks  {

    public static final String TAG = "my_tag";

    public MisfitEventNotifierApplication() {
        super();

        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        MFLSession.build(this.getApplicationContext());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.i(TAG, "onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i(TAG, "onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.i(TAG, "onActivityResumed");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.i(TAG, "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.i(TAG, "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.i(TAG, "onActivityDestroyed");
    }
}