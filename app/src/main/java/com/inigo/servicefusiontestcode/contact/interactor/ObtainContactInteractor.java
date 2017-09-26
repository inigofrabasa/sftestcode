package com.inigo.servicefusiontestcode.contact.interactor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.inigo.servicefusiontestcode.contact.model.Contact;

/**
 * Created by Inigo on 22/09/17.
 */

public class ObtainContactInteractor extends AsyncTask<String, Void, Contact> {

    private SQLiteDatabase db;

    public ObtainContactInteractor(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    protected Contact doInBackground(String... id ) {
        if (db != null){
            String contactsQuery ="select * from Contact where " + id[0] + " = id" ;
            Cursor cursor = db.rawQuery(contactsQuery, null);
            Contact contact = new Contact();

            if (cursor.moveToFirst()){
                while(cursor.isAfterLast() == false){

                    contact.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    contact.setName(cursor.getString(cursor.getColumnIndex("name")));
                    contact.setLastName(cursor.getString(cursor.getColumnIndex("lastname")));
                    cursor.moveToNext();
                }
            }
            return contact;
        }
        return null;
    }
}
