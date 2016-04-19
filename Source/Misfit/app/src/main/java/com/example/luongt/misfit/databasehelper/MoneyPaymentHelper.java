package com.example.luongt.misfit.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.luongt.misfit.model.table.MoneyPayment;

import java.util.ArrayList;

/**
 * Created by luongt on 4/6/2016.
 */
public class MoneyPaymentHelper extends  BaseDatabaseHelper{
    private static final String TABLE_NAME = "MoneyPaymentFragment";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_AMOUNT = "amount";
    private static final String COLUMN_NAME_CONTENT = "content";
    private static final String COLUMN_NAME_TIME = "time";

    public MoneyPaymentHelper(Context context) {
        super(context, "MoneyPaymentHelper.db");

        SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME_AMOUNT + " DOUBLE, " +
            COLUMN_NAME_CONTENT + " TEXT, " +
            COLUMN_NAME_TIME + " TEXT" + " )";

        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

        db = getWritableDatabase();
    }

    public void recreateTable(){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void createNew(MoneyPayment moneyPayment){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AMOUNT, moneyPayment.getAmountMoney());
        values.put(COLUMN_NAME_CONTENT, moneyPayment.getContent());
        values.put(COLUMN_NAME_TIME, moneyPayment.getTime().toString());

        db.insert(TABLE_NAME, null, values);
    }

    public ArrayList<MoneyPayment> getData(){
        ArrayList<MoneyPayment> moneyPayments = new ArrayList<MoneyPayment>();
        String[] projection = {
                COLUMN_NAME_ID,
                COLUMN_NAME_AMOUNT,
                COLUMN_NAME_CONTENT,
                COLUMN_NAME_TIME
        };

        Cursor cursor = db.query(TABLE_NAME, projection, null,null,null, null, null);

        while (cursor.moveToNext()){
            moneyPayments.add(new MoneyPayment(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TIME)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_NAME_AMOUNT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_CONTENT))));
        }

        return moneyPayments;
    }

    public void deleteData(int id){
        String selection = COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        db.delete(TABLE_NAME, selection, selectionArgs);
    }

    public void updateData(MoneyPayment moneyPayment){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AMOUNT, moneyPayment.getAmountMoney());
        values.put(COLUMN_NAME_CONTENT, moneyPayment.getContent());
        values.put(COLUMN_NAME_TIME, moneyPayment.getTime());

        String selection = COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(moneyPayment.getId()) };

        db.update(TABLE_NAME, values, selection, selectionArgs);
    }
}
