package com.example.cbp_manila_androidversion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "data.db";
    public static final String TABLE_NAME = "BusinessData";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "BusinessName";
    public static final String COL_3 = "BusinessDescription";
    public static final String COL_4 = "BusinessAddress";
    public static final String COL_5 = "BusinessHours";
    public static final String COL_6 = "ContactNumbers";
    public static final String COL_7 = "Website";
    public static final String COL_8 = "Facebook";
    public static final String COL_9 = "Instagram";
    public static final String COL_10 = "Requirement";
    public static final String COL_11= "Category";
    public static final String COL_12 = "Municipal";

    Cursor res;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, BusinessName TEXT, BusinessDescription TEXT, BusinessAddress TEXT, BusinessHours TEXT, ContactNumbers TEXT" +
                ", Website TEXT, Facebook TEXT, Instagram TEXT, Requirement TEXT, Category TEXT, Municipal TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String business_name, String business_description, String business_address, String business_hours, String business_numbers, String website
            , String facebook, String instagram, String requirement, String category, String municipal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, business_name);
        contentValues.put(COL_3, business_description);
        contentValues.put(COL_4, business_address);
        contentValues.put(COL_5, business_hours);
        contentValues.put(COL_6, business_numbers);
        contentValues.put(COL_7, website);
        contentValues.put(COL_8, facebook);
        contentValues.put(COL_9, instagram);
        contentValues.put(COL_10, requirement);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        res = db.rawQuery("select * from " + TABLE_NAME, null );
        return res;
    }

    public Cursor searchOption_Final_Result(String search){
        SQLiteDatabase db = this.getWritableDatabase();
        res = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_2 + " like " + "'%" + search + "%'", null );
        return res;
    }

    public Cursor searchOption(String search, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        res = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_11 + "=" + "'" + category + "'" + " and "  + COL_2 + " like " + "'%" + search + "%'", null );
        return res;
    }

    public Cursor searchOption_place(String search, String place){
        SQLiteDatabase db = this.getWritableDatabase();
        res = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_12 + "=" + "'" + place + "'" + " and "  + COL_2 + " like " + "'%" + search + "%'", null );
        return res;
    }

    public Cursor getFilteredData_Category(String filter_category){
        SQLiteDatabase db = this.getWritableDatabase();
        res = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_11 + " = " + "'" + filter_category + "'" , null );
        return res;
    }

    public Cursor getFilteredData_Places(String filter_place){
        SQLiteDatabase db = this.getWritableDatabase();
        res = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_12 + " = " + "'" + filter_place + "'" , null );
        return res;
    }

    public Cursor getFilteredData_Places_Category(String filter_place, String filter_category){
        SQLiteDatabase db = this.getWritableDatabase();
        res = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_12 + " = " + "'" + filter_place + "'" + " and " + COL_11 + "=" +  "'" + filter_category + "'", null );
        return res;
    }

    public Cursor getFilteredData_Category_Place(String filter_category, String filter_places){
        SQLiteDatabase db = this.getWritableDatabase();
        res = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_11 + " = " + "'" + filter_category + "'" + " and " +  COL_12 + " = " + "'" + filter_places + "'", null );
        return res;
    }
}
