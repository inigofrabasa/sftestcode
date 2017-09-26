package com.inigo.servicefusiontestcode.contact.interactor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.inigo.servicefusiontestcode.contact.model.Addresses;
import com.inigo.servicefusiontestcode.contact.model.Phones;

/**
 * Created by Inigo on 25/09/17.
 */

public class ObtainAddressesInteractor  extends AsyncTask<String, Void, Addresses> {

    private SQLiteDatabase db;

    public ObtainAddressesInteractor(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    protected Addresses doInBackground(String... id ) {
        if (db != null){
            String contactsQuery ="select * from Addresses where " + id[0] + " = id_contact" ;
            Cursor cursor = db.rawQuery(contactsQuery, null);
            Addresses addresses = new Addresses();

            if (cursor.moveToFirst()){
                while(cursor.isAfterLast() == false){
                    addresses.add(cursor.getString(cursor.getColumnIndex("address")));
                    cursor.moveToNext();
                }
            }
            return addresses;
        }
        return null;
    }
}
