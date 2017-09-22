package com.inigo.servicefusiontestcode.contacts.interactor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contacts.model.Contacts;

/**
 * Created by Inigo on 21/09/17.
 */

public class ObtainContactsInteractor extends AsyncTask<Void, Void, Contacts> {

    private SQLiteDatabase db;

    public ObtainContactsInteractor(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    protected Contacts doInBackground(Void... voids ) {
        if (db != null){
            String contactsQuery ="select * from Contact order by name";
            Cursor cursor = db.rawQuery(contactsQuery, null);
            Contacts inContacts = new Contacts();

            if (cursor.moveToFirst()){
                while(cursor.isAfterLast() == false){
                    Contact contact = new Contact();
                    contact.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    contact.setName(cursor.getString(cursor.getColumnIndex("name")));
                    contact.setLastName(cursor.getString(cursor.getColumnIndex("lastname")));

                    inContacts.addContact(contact);
                    cursor.moveToNext();
                }
            }
            return inContacts;
        }
        return null;
    }
}
