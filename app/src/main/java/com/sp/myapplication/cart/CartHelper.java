package com.sp.myapplication.cart;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CartHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cart.db";
    private static final int SCHEMA_VERSION = 1;

    public CartHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Will be called once when the database is not created
        db.execSQL("CREATE TABLE cart (" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, foodname TEXT UNIQUE, foodprice TEXT, qty INTEGER);"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Will not be called until SCHEMA_VERSION increases
        //add more tables by upgrading database
    }

    //Read all records from quickrep_table
    public Cursor getAll() {
        return (getReadableDatabase().rawQuery(
                "SELECT DISTINCT _id, foodname, foodprice, qty " +
                        "_id, foodname, foodprice, qty FROM cart ORDER BY _id ASC", null));
    }
    public Cursor getCount() {
        return (getReadableDatabase().rawQuery(
                "SELECT COUNT(*)  " +
                        "FROM cart", null));
    }

//    public Cursor getbyId(String id) {
//        String[] args = {id};
//
//        return (getReadableDatabase().rawQuery(
//                "SELECT _id, foodname, qty" +
//                        " FROM cart WHERE _ID = ?", args));
//    }

    //Write a record into cart
    public void insert(String foodname, String foodprice, Integer qty) {
        ContentValues cv = new ContentValues();

        cv.put("foodname",foodname);
        cv.put("foodprice",foodprice);
        cv.put("qty",qty);
        getWritableDatabase().insertOrThrow("cart", "foodname", cv);
    }

    public void update( String foodname, String foodprice, Integer qty) {
        ContentValues cv = new ContentValues();
        cv.put("foodname",foodname);
        cv.put("foodprice",foodprice);
        cv.put("qty",qty);
        getWritableDatabase().update("cart",cv,"foodname=?",new String[]{foodname});
    }

    public void delete(String foodname, String foodprice, Integer qty) {
        getWritableDatabase().delete("cart","foodname=?", new String[]{foodname});
    }

    public void deleteAll(){
        getWritableDatabase().execSQL("DELETE FROM "+ "cart");
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    public Integer getid(Cursor c) {return (c.getInt(0));}
    public String getfoodname(Cursor c) {return (c.getString(c.getColumnIndex("foodname")));}
    public String getfoodprice(Cursor c) {return (c.getString(c.getColumnIndex("foodprice")));}
    public Integer getqty(Cursor c) {return (c.getInt(c.getColumnIndex("qty")));}
}
