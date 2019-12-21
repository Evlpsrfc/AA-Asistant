package com.example.aa.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aa.ui.bill.BillItem;

import java.util.ArrayList;

public class BillSQLiteAdapter {
    private SQLiteHelper sqLiteHelper;

    public BillSQLiteAdapter(Context context) {
        this.sqLiteHelper = new SQLiteHelper(context);
    }

    public long insertData(String name, double money, String date, String remark) {
        SQLiteDatabase writableDatabase = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.NAME, name);
        writableDatabase.insert(SQLiteHelper.USERS_TABLE_NAME, null, contentValues);
        contentValues.put(SQLiteHelper.MONEY, money);
        contentValues.put(SQLiteHelper.DATE, date);
        contentValues.put(SQLiteHelper.REMARK, remark);
        return writableDatabase.insert(SQLiteHelper.BILL_TABLE_NAME, null, contentValues);
    }

    public ArrayList<BillItem> getData() {
        SQLiteDatabase writableDatabase = sqLiteHelper.getWritableDatabase();
        String[] columns = {
                SQLiteHelper.NAME,
                SQLiteHelper.MONEY,
                SQLiteHelper.DATE,
                SQLiteHelper.REMARK
        };
        @SuppressLint("Recycle") Cursor cursor = writableDatabase.query(
                    SQLiteHelper.BILL_TABLE_NAME,
                    columns,
                    null, null, null, null, null);
        ArrayList<BillItem> billItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(SQLiteHelper.NAME));
            double money = cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.MONEY));
            String date = cursor.getString(cursor.getColumnIndex(SQLiteHelper.DATE));
            String remark = cursor.getString(cursor.getColumnIndex(SQLiteHelper.REMARK));
            billItems.add(new BillItem(name, money, date, remark));
        }
        return billItems;
    }

    public int deleteAll(){
        SQLiteDatabase writableDatabase = sqLiteHelper.getWritableDatabase();
        return writableDatabase.delete(SQLiteHelper.BILL_TABLE_NAME, null, null);
    }

    public int delete(String name) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String[] whereArgs = {name};
        return db.delete(SQLiteHelper.BILL_TABLE_NAME, SQLiteHelper.NAME + " = ?", whereArgs);
    }
}