package com.example.luongt.misfit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.misfit.misfitlinksdk.MFLSession;

public class MisfitEventNotifierApplication extends Application implements Application.ActivityLifecycleCallbacks  {
    public static Context _context;
    public  static  Context getContext()
    {
        return  _context;
    }

    public MisfitEventNotifierApplication() {
        super();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
    }

    @Override
    public void onTerminate() {
        _context = null;
        super.onTerminate();
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