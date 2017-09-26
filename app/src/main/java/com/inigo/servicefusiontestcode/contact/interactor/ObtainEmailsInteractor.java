package com.inigo.servicefusiontestcode.contact.interactor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.inigo.servicefusiontestcode.contact.model.Emails;

/**
 * Created by Inigo on 25/09/17.
 */

public class ObtainEmailsInteractor extends AsyncTask<String, Void, Emails> {

    private SQLiteDatabase db;

    public ObtainEmailsInteractor(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    protected Emails doInBackground(String... id ) {
        if (db != null){
            String contactsQuery ="select * from Emails where " + id[0] + " = id_contact" ;
            Cursor cursor = db.rawQuery(contactsQuery, null);
            Emails emails = new Emails();

            if (cursor.moveToFirst()){
                while(cursor.isAfterLast() == false){
                    emails.add(cursor.getString(cursor.getColumnIndex("email")));
                    cursor.moveToNext();
                }
            }
            return emails;
        }
        return null;
    }
}
