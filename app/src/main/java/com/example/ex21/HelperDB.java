package com.example.ex21;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version 1.5
 * @since 5/2/2022
 * Helper to the data base class.
 */
public class HelperDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbexam.db";
    private static final int DATABASE_VERSION = 1;
    String strCreate, strDelete;

    public HelperDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate="CREATE TABLE "+ com.example.ex21.Users.TABLE_USERS;
        strCreate+=" ("+ com.example.ex21.Users.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+ com.example.ex21.Users.LNAME+" TEXT,";
        strCreate+=" "+ com.example.ex21.Users.FNAME+" TEXT,";
        strCreate+=" "+ com.example.ex21.Users.COMPANY+" TEXT,";
        strCreate+=" "+ com.example.ex21.Users.ID+" TEXT,";
        strCreate+=" "+ com.example.ex21.Users.PHONE+" TEXT,";
        strCreate+=" "+ com.example.ex21.Users.ACTIVE+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+ com.example.ex21.Companies.TABLE_COMPANIES;
        strCreate+=" ("+ com.example.ex21.Companies.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+ com.example.ex21.Companies.NAME+" TEXT,";
        strCreate+=" "+ com.example.ex21.Companies.TAX_ID+" TEXT,";
        strCreate+=" "+ com.example.ex21.Companies.MAIN_PHONE+" TEXT,";
        strCreate+=" "+ com.example.ex21.Companies.SECONDARY_PHONE+" TEXT,";
        strCreate+=" "+ com.example.ex21.Companies.ACTIVE+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);


        strCreate="CREATE TABLE "+ com.example.ex21.Orders.TABLE_ORDERS;
        strCreate+=" ("+ com.example.ex21.Orders.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+ com.example.ex21.Orders.RESTAURANT_ID+" TEXT,";
        strCreate+=" "+ com.example.ex21.Orders.RESTAURANT_NAME+" TEXT,";
        strCreate+=" "+ com.example.ex21.Orders.USER_ID+" TEXT,";
        strCreate+=" "+ com.example.ex21.Orders.USER_NAME+" TEXT,";
        strCreate+=" "+ com.example.ex21.Orders.DATE+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+ com.example.ex21.Meals.TABLE_MEALS;
        strCreate+=" ("+ com.example.ex21.Meals.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+ com.example.ex21.Meals.APPETIZER+" TEXT,";
        strCreate+=" "+ com.example.ex21.Meals.MAIN+" TEXT,";
        strCreate+=" "+ com.example.ex21.Meals.SIDE+" TEXT,";
        strCreate+=" "+ com.example.ex21.Meals.DESSERT+" TEXT,";
        strCreate+=" "+ com.example.ex21.Meals.DRINK+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete="DROP TABLE IF EXISTS "+ com.example.ex21.Users.TABLE_USERS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+ com.example.ex21.Companies.TABLE_COMPANIES;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+ com.example.ex21.Meals.TABLE_MEALS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+ com.example.ex21.Orders.TABLE_ORDERS;
        db.execSQL(strDelete);

        onCreate(db);
    }
}
