package com.abhijeet.appupdatemanager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
public class AddFromInstalledList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_installed_list);
        readRecords();
    }
    public void readRecords() {
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        if (linearLayoutRecords != null) {
            linearLayoutRecords.removeAllViews();
        }
        PackageManager pm = getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(0);
        for (PackageInfo pi : list) {
            ApplicationInfo ai = null;
            try {
                ai = pm.getApplicationInfo(pi.packageName, 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if ((ai.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String AppName = pi.packageName;
                String Version = Integer.toString(pi.versionCode);
                String textViewContents = AppName + " - " + Version;
                TextView textViewStudentItem = new TextView(this);
                textViewStudentItem.setPadding(0, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);
                //* textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());*//*
                if (linearLayoutRecords != null) {
                    linearLayoutRecords.addView(textViewStudentItem);
                }
            }
        }
       /* List<ObjectApp> installedapp = new TableControllerInstalledApps(this).read();
        if (installedapp.size() > 0) {
            for (ObjectApp obj : installedapp) {
                int id = obj.id;
                String AppName = obj.AppName;
                String Version = obj.Version;
                String textViewContents = AppName + " - " + Version;
                TextView textViewStudentItem = new TextView(this);
                textViewStudentItem.setPadding(0, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Integer.toString(id));
               *//* textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());*//*
                if (linearLayoutRecords != null) {
                    linearLayoutRecords.addView(textViewStudentItem);
                }
            }
        } else {
            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");
            if (linearLayoutRecords != null) {
                linearLayoutRecords.addView(locationItem);
            }
        }*/
    }
}
