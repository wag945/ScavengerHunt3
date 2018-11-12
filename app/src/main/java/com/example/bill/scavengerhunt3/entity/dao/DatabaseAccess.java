package com.example.bill.scavengerhunt3.entity.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAccess extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "userprofile.db";

    public DatabaseAccess(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("DatabaseAccess", "context constructor");
    }

    public DatabaseAccess(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("DatabaseAccess", "multi parameter constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Create the UserProfile table once the database is instantiated.
        db.execSQL(UserProfileTable.create());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //In case the database is a cache for online data, you can discard the existing data
        //once the app is gathering information from an external data source.
        db.execSQL(UserProfileTable.delete());
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }


}
