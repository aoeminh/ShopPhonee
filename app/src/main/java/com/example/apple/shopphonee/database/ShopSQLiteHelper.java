package com.example.apple.shopphonee.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShopSQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "shop_db";
    public static final String TABLE_NAME = "shop1";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN__SHOP_ID = "shopID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QUANTILY = "quantily";
    public static final int DATABASE_VERSION = 3;
    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN__SHOP_ID + " INTEGER not null,"
            + COLUMN_NAME + " text not null,"
            + COLUMN_IMAGE + " text not null,"
            + COLUMN_PRICE + " INTEGER not null,"
            + COLUMN_QUANTILY + " INTEGER not null)";

    // create database
    public ShopSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
