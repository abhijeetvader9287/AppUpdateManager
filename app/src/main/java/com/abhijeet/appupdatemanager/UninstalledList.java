package com.abhijeet.appupdatemanager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abhijeet.appupdatemanager.DbWork.ObjectApp;
import com.abhijeet.appupdatemanager.DbWork.TableControllerUninstalledApps;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.util.List;
public class UninstalledList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uninstalled_list);
        checkForUpdates();
        final String AppName;
        final String Version;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                AppName = null;
                Version = null;
            } else {
                AppName = extras.getString("AppName");
                Version = extras.getString("Version");

            }
        } else {
            AppName = (String) savedInstanceState.getSerializable("AppName");
            Version = (String) savedInstanceState.getSerializable("Version");
        }
        readRecords();
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Do You Want Update For This App Later ?")
                .setTitle("AppUpdateManager")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UninstalledList.this.finish();
                    }
                })
                .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ObjectApp objectApp = new ObjectApp();
                        objectApp.AppName = AppName;
                        objectApp.Version = Version;
                        boolean createSuccessful = new TableControllerUninstalledApps(UninstalledList.this).create(objectApp);
                        if (createSuccessful) {
                            Toast.makeText(UninstalledList.this, "App information was saved.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UninstalledList.this, "Unable to save App information.", Toast.LENGTH_SHORT).show();
                        }
                        readRecords();
                    }
                });
        if (AppName != null) {
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    public void readRecords() {
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        if (linearLayoutRecords != null) {
            linearLayoutRecords.removeAllViews();
        }
        List<ObjectApp> uninstalledapp = new TableControllerUninstalledApps(this).read();
        if (uninstalledapp.size() > 0) {
            for (ObjectApp obj : uninstalledapp) {
                int id = obj.id;
                String AppName = obj.AppName;
                String Version = obj.Version;
                String textViewContents = AppName + " - " + Version;
                TextView textViewStudentItem = new TextView(this);
                textViewStudentItem.setPadding(0, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Integer.toString(id));
               /* textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());*/
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
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // ... your own onResume implementation
        checkForCrashes();
    }
    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }
    private void checkForCrashes() {
        CrashManager.register(this);
    }
    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }
    private void unregisterManagers() {
        UpdateManager.unregister();
    }
}
