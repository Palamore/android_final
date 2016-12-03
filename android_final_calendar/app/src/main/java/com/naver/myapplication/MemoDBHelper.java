package com.naver.myapplication;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDBHelper extends SQLiteOpenHelper {

    public MemoDBHelper(Context context, String name, CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL("CREATE TABLE media(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT, " + "media BLOB );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
   //     db.execSQL("DROP TABLE IF EXIST today;");
   //     onCreate(db);
    }

}

