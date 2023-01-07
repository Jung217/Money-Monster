package com.hyl.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(Context context,String name, CursorFactory factory,
                        int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table if not exists user_tb(_id integer primary key autoincrement," +
                "userID text not null," +
                "pwd text not null)");

        db.execSQL("create table if not exists refCode_tb(_id integer primary key autoincrement," +
                "CodeType text not null," +
                "CodeID text not null," +
                "CodeName text null)");

        db.execSQL("create table if not exists basicCode_tb(_id integer primary key autoincrement," +
                "userID text not null," +
                "Type integer not null," +
                "incomeWay text not null," +
                "incomeBy text not null," +
                "category text not null," +
                "item text not null," +
                "cost money not null," +
                "note text not null," +
                "makeDate text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

}
