package com.inigo.servicefusiontestcode.contacts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Inigo on 21/09/17.
 */

public class ContactSQLiteHelper extends SQLiteOpenHelper {

    private final String CREATE_TABLE_CONTACT
            = "CREATE TABLE Contact (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, lastname TEXT)";

    public ContactSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Contact");

        onCreate(db);
    }
}
