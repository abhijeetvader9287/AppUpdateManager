package com.abhijeet.appupdatemanager.receivers;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.abhijeet.appupdatemanager.MainActivity;

/**
 * Created by Admin on 1/2/2017.
 */
public class PeriodicTaskReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!(intent.getAction().isEmpty())) {
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
                Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                //  Toast.makeText(context.getApplicationContext(), "App uninstalled " + intent.getData().toString(), Toast.LENGTH_LONG).show();
            }
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
               // Toast.makeText(context.getApplicationContext(), "App installed " + intent.getData().toString(), Toast.LENGTH_LONG).show();
            }
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
                Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                //Toast.makeText(context.getApplicationContext(), "App replaced " + intent.getData().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}

