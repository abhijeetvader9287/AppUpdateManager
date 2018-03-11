package com.abhijeet.appupdatemanager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhijeet.appupdatemanager.DbWork.ObjectApp;
import com.abhijeet.appupdatemanager.DbWork.TableControllerInstalledApps;
import com.abhijeet.appupdatemanager.adapters.UpdateReqListAdapter;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class UpdateReqList extends AppCompatActivity {
    private List<PackageInfo> applist = null;
    private UpdateReqListAdapter listadaptor = null;
    private ListView list;
    String newVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatereq_list);
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
                        UpdateReqList.this.finish();
                    }
                })
                .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ObjectApp objectApp = new ObjectApp();
                        objectApp.AppName = AppName;
                        objectApp.Version = Version;
                        boolean createSuccessful = new TableControllerInstalledApps(UpdateReqList.this).create(objectApp);
                        if (createSuccessful) {
                            Toast.makeText(UpdateReqList.this, "App information was saved.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UpdateReqList.this, "Unable to save App information.", Toast.LENGTH_SHORT).show();
                        }
                        readRecords();
                    }
                });
        if (AppName != null) {
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    private void readRecords() {
        list = (ListView) findViewById(R.id.linearLayoutRecords);
        List<ObjectApp> installedapp = new TableControllerInstalledApps(this).read();
        if (installedapp.size() > 0) {
            applist = new ArrayList<PackageInfo>();
            newVersion="";
            for (final ObjectApp obj : installedapp) {
                try {
                    final TextView textViewStudentItem = new TextView(UpdateReqList.this);
                    final PackageInfo packageInfo = new PackageInfo();
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            try {
                                newVersion="";
                                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + obj.AppName + "&hl=en")
                                        .timeout(30000)
                                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                                        .referrer("http://www.google.com")
                                        .get()
                                        .select("div[itemprop=softwareVersion]")
                                        .first()
                                        .ownText();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Object o) {
                            try {
                                int id = obj.id;
                                String AppName = obj.AppName;
                                String Version = obj.Version;
                                String textViewContents = Version + "*" + newVersion;
                                textViewStudentItem.setPadding(0, 10, 0, 10);
                                textViewStudentItem.setText(textViewContents);
                                textViewStudentItem.setTag(Integer.toString(id));
                                packageInfo.packageName = obj.AppName;
                                packageInfo.versionName = textViewContents;
                                applist.add(packageInfo);
                                listadaptor = new UpdateReqListAdapter(UpdateReqList.this,
                                        R.layout.snippet_list_row, applist);
                                list.setAdapter(listadaptor);
                            } catch (Exception ex) {
                            }

               /* textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());*/
                        }
                    };
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                        asyncTask.execute();
                    }
                } catch (Exception ex) {
                }
            }

        } else {
            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");
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
