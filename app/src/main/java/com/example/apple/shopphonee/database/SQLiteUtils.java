package com.example.apple.shopphonee.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.apple.shopphonee.model.Cart;
import com.example.apple.shopphonee.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUtils  {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String [] allColumn = {ShopSQLiteHelper.COLUMN_ID,ShopSQLiteHelper.COLUMN__SHOP_ID,ShopSQLiteHelper.COLUMN_NAME,
                                    ShopSQLiteHelper.COLUMN_IMAGE,ShopSQLiteHelper.COLUMN_PRICE,
                                    ShopSQLiteHelper.COLUMN_QUANTILY};
    private Context context;

    public SQLiteUtils(Context context){
        this.context = context;
        this.sqLiteOpenHelper = new ShopSQLiteHelper(context);
    }

    public void open(){
        this.sqLiteDatabase =sqLiteOpenHelper.getWritableDatabase();
    }
    public void close(){
        sqLiteOpenHelper.close();
    }

    public void addCart(Cart cart){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShopSQLiteHelper.COLUMN__SHOP_ID,cart.getProductId());
        contentValues.put(ShopSQLiteHelper.COLUMN_NAME,cart.getProductName());
        contentValues.put(ShopSQLiteHelper.COLUMN_IMAGE,cart.getProductImage());
        contentValues.put(ShopSQLiteHelper.COLUMN_PRICE,cart.getProductPrice());
        contentValues.put(ShopSQLiteHelper.COLUMN_QUANTILY,cart.getQuantily());
        //insert to database

        sqLiteDatabase.insert(ShopSQLiteHelper.TABLE_NAME,"",contentValues);


    }
    public void deleteAll(){
        sqLiteDatabase.execSQL("DELETE FROM " + ShopSQLiteHelper.TABLE_NAME);
    }
    public List<Cart> getAll(){
        List<Cart> cartList
                = new ArrayList<Cart>();
        Cursor cursor = sqLiteDatabase.query(ShopSQLiteHelper.TABLE_NAME,allColumn,null,null, null,null,null);

        if(cursor==null){
            return null;
        }

        //if cusor not null
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            Cart cart = cusorModel(cursor);
            cartList.add(cart);
            cursor.moveToNext();
        }
        return cartList;
    }



    public Cart cusorModel(Cursor cursor){
        Cart cart = new Cart();
        cart.setProductId(cursor.getString(1));
        cart.setProductName(cursor.getString(2));
        cart.setProductImage(cursor.getString(3));
        cart.setProductPrice(cursor.getInt(4));
        cart.setQuantily(cursor.getInt(5));
        return cart;

    }
}
