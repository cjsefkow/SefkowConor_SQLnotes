package com.example.sefkowc1167.mycontactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Contact2018.db";
    public static final String TABLE_NAME = "Contact2018_table";
    public static final String ID = "ID";
    public static final String COLUMN_NAME_CONTACT = "contact";
    public static final String COLUMN_NAME_PHONE = "phone";
    public static final String COLUMN_NAME_ADDRESS = "address";


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_CONTACT + " TEXT, " +
                    COLUMN_NAME_PHONE + " TEXT, " +
                    COLUMN_NAME_ADDRESS + " TEXT)";



    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase db = this.getWritableDatabase();
        Log.d("MyContactApp", "Databasehelper: constructedDatabaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MyContactApp", "Databasehelper: creating database");
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MyContactApp", "Databasehelper: upgrading database");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insertData(String name, String address, String phone) {
        Log.d("MyContactApp", "Databasehelper: inserting data");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("MyContactApp", SQL_CREATE_ENTRIES);
        ContentValues contentValue = new ContentValues();
        Log.d("MyContactApp", name + " " + address + " " + phone);
        contentValue.put(COLUMN_NAME_CONTACT, name);
        contentValue.put(COLUMN_NAME_ADDRESS, address);
        contentValue.put(COLUMN_NAME_PHONE, phone);

        long result = db.insert(TABLE_NAME, null, contentValue);
        Log.d("MyContactApp", "created result");
        if (result == -1) {
            Log.d("MyContactApp", "Databasehelper: contact insert failed");
            return false;
        } else {
            Log.d("MyContactApp", "Databasehelper: contact inserted");
            return true;
        }
    }

    public Cursor getAllData() {
        Log.d("MyContactApp", "MainActivity: calling getAllData");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
}

