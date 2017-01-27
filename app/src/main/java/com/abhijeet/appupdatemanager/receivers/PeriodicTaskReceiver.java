package com.abhijeet.appupdatemanager.receivers;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.abhijeet.appupdatemanager.InstalledList;
/**
 * Created by Admin on 1/2/2017.
 */
public class PeriodicTaskReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!(intent.getAction().isEmpty())) {
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
             /*   Intent i = new Intent(context.getApplicationContext(), UninstalledList.class);
           //   String pname=  intent.getData().toString() + getApplicationName(context, intent.getData().toString(), PackageManager.GET_UNINSTALLED_PACKAGES);
                i.putExtra("AppName", intent.getData().getSchemeSpecificPart());
                i.putExtra("Version", "1.0.1");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);*/
                //  Toast.makeText(context.getApplicationContext(), "App uninstalled " + intent.getData().toString(), Toast.LENGTH_LONG).show();
            }
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                Intent i = new Intent(context.getApplicationContext(), InstalledList.class);
                String pname = intent.getData().toString().replace("package:", "");
                PackageInfo pInfo = null;
                String version = "";
                try {
                    pInfo = context.getPackageManager().getPackageInfo(pname, 0);
                    version =Integer.toString( pInfo.versionCode);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                i.putExtra("AppName", pname);
                i.putExtra("Version", version);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);






             /*   Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);*/
                // Toast.makeText(context.getApplicationContext(), "App installed " + intent.getData().toString(), Toast.LENGTH_LONG).show();
            }
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
                Intent i = new Intent(context.getApplicationContext(), InstalledList.class);
                String pname = intent.getData().toString().replace("package:", "");
                PackageInfo pInfo = null;
                String version = "";
                try {
                    pInfo = context.getPackageManager().getPackageInfo(pname, 0);
                    version =Integer.toString( pInfo.versionCode);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                i.putExtra("AppName", pname);
                i.putExtra("Version", version);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }
}

