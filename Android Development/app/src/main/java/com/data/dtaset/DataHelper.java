package com.data.dtaset;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;


public abstract class DataHelper extends SQLiteOpenHelper {


    public DataHelper(Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table User(name TEXT,roll TEXT primary key, contact TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exits User");
    }

    //    Insert Data
    public Boolean insertdata(String name, String roll, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues = new ContentValues();
        ContentValues.put("name", name);
        ContentValues.put("roll", roll);
        ContentValues.put("content", content);
        long result = db.insert("User", null, ContentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    //    Update Data
    public Boolean updatedata(String name, String roll, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues = new ContentValues();
        ContentValues.put("name", name);
        ContentValues.put("roll", roll);
        ContentValues.put("content", content);

        Cursor cursor = db.rawQuery("Select * from User where roll=?", new String[]{roll});

        long result = db.update("User", ContentValues, "roll=?", new String[]{roll});
        if (cursor.getCount() > 0) {
            if (result == -1) {
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }


    }

    //    Delete Data
    public Boolean deletedata(String roll) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where roll=?", new String[]{roll});
        long result = db.delete("User", "roll=?", new String[]{roll});
        if (cursor.getCount() > 0) {
            if (result == -1) {
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }


    }
}
