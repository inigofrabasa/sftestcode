package com.inigo.servicefusiontestcode.contact.interactor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.inigo.servicefusiontestcode.contact.model.Contact;

/**
 * Created by Inigo on 23/09/17.
 */

public class UpdateContactInteractor extends AsyncTask<Contact, Void, Integer> {
    private SQLiteDatabase db;

    public UpdateContactInteractor(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    protected Integer doInBackground(Contact... contact) {
        if(contact != null && db != null){
            ContentValues updateContact = new ContentValues();

            updateContact.put("name",contact[0].getName().toString());
            updateContact.put("lastname",contact[0].getLastName().toString());
            updateContact.put("datebirth",contact[0].getDateOfBirth().toString());
            db.update("Contact",updateContact,"id="+contact[0].getId(),null);
            return contact[0].getId();
        } else return -1;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
    }
}
