package com.example.luongt.misfit.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.luongt.misfit.service.HelloService;
import com.example.luongt.misfit.service.LockService;


/**
 * Created by luongt on 3/24/2016.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            context.startService(new Intent(context, HelloService.class));
            context.startService(new Intent(context, LockService.class));
        }
    }
