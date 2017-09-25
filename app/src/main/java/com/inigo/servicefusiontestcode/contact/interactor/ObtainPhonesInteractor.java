package com.inigo.servicefusiontestcode.contact.interactor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.inigo.servicefusiontestcode.contact.model.Phones;

/**
 * Created by Inigo on 24/09/17.
 */

public class ObtainPhonesInteractor extends AsyncTask<String, Void, Phones> {

    private SQLiteDatabase db;

    public ObtainPhonesInteractor(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    protected Phones doInBackground(String... id ) {
        if (db != null){
            String contactsQuery ="select * from Phones where " + id[0] + " = id_contact" ;
            Cursor cursor = db.rawQuery(contactsQuery, null);
            Phones phones = new Phones();

            if (cursor.moveToFirst()){
                while(cursor.isAfterLast() == false){
                    phones.add(cursor.getString(cursor.getColumnIndex("phone")));
                    cursor.moveToNext();
                }
            }
            return phones;
        }
        return null;
    }

}
