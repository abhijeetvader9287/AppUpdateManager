package com.abhijeet.appupdatemanager.receivers;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
/**
 * Created by Admin on 1/2/2017.
 */
public class PeriodicTaskReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!(intent.getAction().isEmpty())) {
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
                Toast.makeText(context.getApplicationContext(), "App uninstalled " + intent.getData().toString(), Toast.LENGTH_LONG).show();
            }
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                Toast.makeText(context.getApplicationContext(), "App installed " + intent.getData().toString(), Toast.LENGTH_LONG).show();
            }
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
                Toast.makeText(context.getApplicationContext(), "App replaced " + intent.getData().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}

