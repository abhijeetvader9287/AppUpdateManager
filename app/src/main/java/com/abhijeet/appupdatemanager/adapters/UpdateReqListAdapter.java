package com.abhijeet.appupdatemanager.adapters;
/**
 * Created by Admin on 1/28/2017.
 */

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhijeet.appupdatemanager.R;

import java.util.List;
public class UpdateReqListAdapter extends ArrayAdapter<PackageInfo> {
    private List<PackageInfo> appsList = null;
    private Context context;
    private PackageManager packageManager;


    public UpdateReqListAdapter(Context context, int resource,List<PackageInfo> appsList) {
        super(context, resource);        this.context = context;
        this.appsList = appsList;
        packageManager = context.getPackageManager();
    }
    @Override
    public int getCount() {
        return ((null != appsList) ? appsList.size() : 0);
    }

    @Override
    public PackageInfo getItem(int position) {
        return ((null != appsList) ? appsList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.snippet_list_row, null);
        }
        PackageInfo applicationInfo = appsList.get(position);
        if (null != applicationInfo) {
            TextView appName = (TextView) view.findViewById(R.id.app_name);
            TextView packageName = (TextView) view.findViewById(R.id.app_paackage);
            ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);
            appName.setText(applicationInfo.packageName);
            packageName.setText(applicationInfo.versionName);
            //iconview.setImageDrawable(applicationInfo.loadIcon(packageManager));
        }
        return view;
    }
}