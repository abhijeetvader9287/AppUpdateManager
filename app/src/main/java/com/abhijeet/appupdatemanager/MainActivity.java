package com.abhijeet.appupdatemanager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abhijeet.appupdatemanager.DbWork.ObjectApp;
import com.abhijeet.appupdatemanager.DbWork.TableControllerUninstalledApps;

import java.util.List;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readRecords();
        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setMessage("Do You Want Update For This App Later ?")
                .setTitle("AppUpdateManager")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ObjectApp objectApp = new ObjectApp();
                        objectApp.AppName= "aaa";
                        objectApp.Version= "bbb";

                        boolean createSuccessful = new TableControllerUninstalledApps(MainActivity.this).create(objectApp);

                        if(createSuccessful){
                            Toast.makeText(MainActivity.this, "App information was saved.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Unable to save App information.", Toast.LENGTH_SHORT).show();
                        }

                        readRecords();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
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
        }
        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            if (linearLayoutRecords != null) {
                linearLayoutRecords.addView(locationItem);
            }
        }

    }
}
