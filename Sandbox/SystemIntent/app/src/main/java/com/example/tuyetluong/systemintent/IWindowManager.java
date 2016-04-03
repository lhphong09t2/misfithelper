package com.example.tuyetluong.systemintent;

import android.os.Binder;
import android.os.IBinder;
import android.view.KeyEvent;

public interface IWindowManager {
    public static class Stub {
        public static IWindowManager asInterface( IBinder binder ) {
            return null;
        }
    }

    public boolean injectKeyEvent(KeyEvent keyEvent, boolean f);
}