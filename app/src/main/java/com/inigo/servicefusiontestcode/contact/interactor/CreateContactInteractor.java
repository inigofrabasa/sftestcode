package com.inigo.servicefusiontestcode.contact.interactor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.inigo.servicefusiontestcode.contact.model.Contact;

/**
 * Created by Inigo on 21/09/17.
 */

public class CreateContactInteractor extends AsyncTask<Contact, Void, Integer> {

    private SQLiteDatabase db;

    public CreateContactInteractor(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    protected Integer doInBackground(Contact... contact) {
        if(contact != null && db != null){
            ContentValues newContact = new ContentValues();

            newContact.put("name",contact[0].getName().toString());
            newContact.put("lastname",contact[0].getLastName().toString());
            newContact.put("datebirth",contact[0].getDateOfBirth().toString());
            return (int) (long)db.insert("Contact",null,newContact);
        } else return -1;
    }

    @Override
    protected void onPostExecute(Integer _id) {
        super.onPostExecute(_id);
    }
}
