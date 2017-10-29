package com.example.andrew.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andrew on 10/25/2017.
 */

public class DataBase extends SQLiteOpenHelper {
    public static String databasename = "data.db";

    public DataBase(Context context) {
        super( context, databasename, null, 1 );
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + DataContract.DataEntry.Table_name +
                "(" + DataContract.DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DataContract.DataEntry.COLUMN_NAME + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_QUANTITY + " INTEGER NOT NULL,"
                + DataContract.DataEntry.COLUMN_PRICE + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_IMAGE + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_EMAIL + " TEXT NOT NULL" + ");";
        db.execSQL( CREATE_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insert(String name, String price, String quantity, String email, String image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( DataContract.DataEntry.COLUMN_NAME, name );
        values.put( DataContract.DataEntry.COLUMN_PRICE, price );
        values.put( DataContract.DataEntry.COLUMN_QUANTITY, quantity );
        values.put( DataContract.DataEntry.COLUMN_EMAIL, email );
        values.put( DataContract.DataEntry.COLUMN_IMAGE, image );
        int id = (int) db.insert( DataContract.DataEntry.Table_name, null, values );
        return id != 0;
    }

    public void deleteitem(int item) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "DELETE FROM " + DataContract.DataEntry.Table_name + " WHERE " + DataContract.DataEntry._ID + "=" + item;
        db.execSQL( query );
    }

    public Cursor readData() {
        SQLiteDatabase readbd = getReadableDatabase();
        String[] output = {DataContract.DataEntry._ID, DataContract.DataEntry.COLUMN_NAME, DataContract.DataEntry.COLUMN_PRICE, DataContract.DataEntry.COLUMN_QUANTITY, DataContract.DataEntry.COLUMN_IMAGE, DataContract.DataEntry.COLUMN_EMAIL};
        Cursor cursor = readbd.query( DataContract.DataEntry.Table_name, output, null, null, null, null, null );
        return cursor;
    }

    public Cursor select(int ID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        String query = "SELECT * FROM " + DataContract.DataEntry.Table_name + " WHERE " + DataContract.DataEntry._ID + "=" + ID;
        cursor = db.rawQuery( query, null );
        return cursor;
    }

    public void update(int update, int ID) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE " + DataContract.DataEntry.Table_name + " SET quantity = " + update + " WHERE  " + DataContract.DataEntry._ID + "=" + ID;
        db.execSQL( query );
    }

    public boolean updatequantity(int update, int ID) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE " + DataContract.DataEntry.Table_name + " SET quantity = " + update + " WHERE  " + DataContract.DataEntry._ID + "=" + ID;
        db.execSQL( query );
        return update != 0;
    }
}
