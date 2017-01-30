package com.abhijeet.appupdatemanager;
import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhijeet.appupdatemanager.adapters.ApplicationAdapter;

import java.util.ArrayList;
import java.util.List;
public class AddFromInstalledList extends AppCompatActivity {
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    ListView list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_installed_list);
        list = (ListView) findViewById(R.id.list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.apptitle);
        mTitle.setText("Save To App List");
        Button saveBtn = (Button) toolbar.findViewById(R.id.savebutton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddFromInstalledList.this, "Save Btn Clicked", Toast.LENGTH_LONG).show();
            }
        });
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.getContentInsetEnd();
        toolbar.setPadding(0, 0, 0, 0);
        setSupportActionBar(toolbar);
        packageManager = getPackageManager();
        new LoadApplications().execute();
    }
  /*  public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;
        switch (item.getItemId()) {
            case R.id.menu_about: {
                displayAboutDialog();
                break;
            }
            default: {
                result = super.onOptionsItemSelected(item);
                break;
            }
        }
        return result;
    }
    private void displayAboutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.about_title));
        builder.setMessage(getString(R.string.about_desc));
        builder.setPositiveButton("Know More", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://javatechig.com"));
                startActivity(browserIntent);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No Thanks!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.show();
    }*/
    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        applist.add(info);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return applist;
    }
    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;
        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(AddFromInstalledList.this,
                    R.layout.snippet_list_row, applist);
            return null;
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
        @Override
        protected void onPostExecute(Void result) {
            list.setAdapter(listadaptor);
            progress.dismiss();
            super.onPostExecute(result);
        }
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(AddFromInstalledList.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
