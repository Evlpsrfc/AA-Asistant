package com.example.aa.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AAA2.db";
    static final String BILL_TABLE_NAME = "BillTable";
    static final String USERS_TABLE_NAME = "UserTable";
    private static final int DATABASE_Version = 1;
    private static final String UID = "ID";
    static final String NAME = "Name";
    static final String MONEY = "Money";
    static final String DATE = "Date";
    static final String REMARK = "Remark";

    private static final String CREATE_BILL_TABLE = "CREATE TABLE IF NOT EXISTS " + BILL_TABLE_NAME +
            " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " VARCHAR(255), " +
            MONEY + " VARCHAR(255), " +
            DATE + " VARCHAR(255), " +
            REMARK + " VARCHAR(225))";
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE_NAME +
            " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " VARCHAR(225) UNIQUE)";
    private static final String DROP_BILL_TABLE = "DROP TABLE IF EXISTS " + BILL_TABLE_NAME;
    private static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS " + USERS_TABLE_NAME;
    private Context context;

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_BILL_TABLE);
            db.execSQL(CREATE_USERS_TABLE);
        } catch (Exception e) {
            Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Toast.makeText(context, "OnUpgrade", Toast.LENGTH_LONG).show();
            db.execSQL(DROP_BILL_TABLE);
            db.execSQL(DROP_USERS_TABLE);
            onCreate(db);
        } catch (Exception e) {
            Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
        }
    }
}