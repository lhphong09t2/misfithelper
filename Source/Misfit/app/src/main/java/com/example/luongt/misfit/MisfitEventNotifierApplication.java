package com.example.luongt.misfit;

import android.app.Application;
import android.content.Context;

public class MisfitEventNotifierApplication extends Application{
    public static Context _context;
    public  static  Context getContext()
    {
        return  _context;
    }

    public MisfitEventNotifierApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}