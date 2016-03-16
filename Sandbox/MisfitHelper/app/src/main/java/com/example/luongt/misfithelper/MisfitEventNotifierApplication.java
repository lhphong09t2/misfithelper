package com.example.luongt.misfithelper;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.misfit.misfitlinksdk.MFLSession;

public class MisfitEventNotifierApplication extends Application implements Application.ActivityLifecycleCallbacks  {

    public MisfitEventNotifierApplication() {
        super();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        MFLSession.build(this.getApplicationContext());
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}