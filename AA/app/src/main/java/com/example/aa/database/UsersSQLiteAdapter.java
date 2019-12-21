package com.example.aa.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UsersSQLiteAdapter {
    private SQLiteHelper sqLiteHelper;

    public UsersSQLiteAdapter(Context context) {
        this.sqLiteHelper = new SQLiteHelper(context);
    }

    public ArrayList<String> getData() {
        SQLiteDatabase writableDatabase = sqLiteHelper.getWritableDatabase();
        String[] columns = {
                SQLiteHelper.NAME,
        };
        @SuppressLint("Recycle")
        Cursor cursor = writableDatabase.query(
                SQLiteHelper.USERS_TABLE_NAME,
                columns,
                null, null, null, null, null);
        ArrayList<String> userNames = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(SQLiteHelper.NAME));
            userNames.add(name);
        }
        return userNames;
    }

    public long insertData(String name){
        SQLiteDatabase writableDatabase = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.NAME, name);
        return writableDatabase.insert(SQLiteHelper.USERS_TABLE_NAME, null, contentValues);
    }

    public int deleteAll(){
        SQLiteDatabase writableDatabase = sqLiteHelper.getWritableDatabase();
        return writableDatabase.delete(SQLiteHelper.USERS_TABLE_NAME, null, null);
    }
}
