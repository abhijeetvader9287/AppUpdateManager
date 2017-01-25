package com.abhijeet.appupdatemanager.DbWork;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Admin on 1/25/2017.
 */
public class TableControllerUninstalledApps extends DatabaseHandler {
    public TableControllerUninstalledApps(Context context) {
        super(context);
    }
    public boolean create(ObjectApp objectapp) {
        ContentValues values = new ContentValues();
        values.put("AppName", objectapp.AppName);
        values.put("Version", objectapp.Version);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("uninstalledapps", null, values) > 0;
        db.close();
        return createSuccessful;
    }
    public List<ObjectApp> read() {
        List<ObjectApp> recordsList = new ArrayList<ObjectApp>();
        String sql = "SELECT * FROM uninstalledapps ORDER BY id DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String AppName = cursor.getString(cursor.getColumnIndex("AppName"));
                String Version = cursor.getString(cursor.getColumnIndex("Version"));
                ObjectApp objectapp = new ObjectApp();
                objectapp.id = id;
                objectapp.AppName = AppName;
                objectapp.Version = Version;
                recordsList.add(objectapp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recordsList;
    }
    public ObjectApp readSingleRecord(int studentId) {
        ObjectApp objectapp = null;
        String sql = "SELECT * FROM uninstalledapps WHERE id = " + studentId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String AppName = cursor.getString(cursor.getColumnIndex("AppName"));
            String Version = cursor.getString(cursor.getColumnIndex("Version"));
            objectapp = new ObjectApp();
            objectapp.id = id;
            objectapp.AppName = AppName;
            objectapp.Version = Version;
        }
        cursor.close();
        db.close();
        return objectapp;
    }
    public int count() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM uninstalledapps";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();
        return recordCount;
    }
    public boolean update(ObjectApp objectapp) {
        ContentValues values = new ContentValues();
        values.put("AppName", objectapp.AppName);
        values.put("Version", objectapp.Version);
        String where = "id = ?";
        String[] whereArgs = {Integer.toString(objectapp.id)};
        SQLiteDatabase db = this.getWritableDatabase();
        boolean updateSuccessful = db.update("uninstalledapps", values, where, whereArgs) > 0;
        db.close();
        return updateSuccessful;
    }
    public boolean delete(int id) {
        boolean deleteSuccessfull = false;
        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessfull = db.delete("uninstalledapps", "id = " + id, null) > 0;
        db.close();
        return deleteSuccessfull;
    }
}