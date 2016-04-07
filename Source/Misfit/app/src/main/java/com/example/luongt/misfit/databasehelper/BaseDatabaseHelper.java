package com.example.luongt.misfit.databasehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luongt on 4/6/2016.
 */
public abstract class BaseDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    protected static String SQL_CREATE_ENTRIES = null;
    protected static String SQL_DELETE_ENTRIES = null;

    protected SQLiteDatabase db;

    public BaseDatabaseHelper(Context context, String name) {
        super(context, name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
