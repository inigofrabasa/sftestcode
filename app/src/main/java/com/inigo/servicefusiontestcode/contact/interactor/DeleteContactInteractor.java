package com.inigo.servicefusiontestcode.contact.interactor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.inigo.servicefusiontestcode.contact.model.Contact;

/**
 * Created by Inigo on 23/09/17.
 */

public class DeleteContactInteractor extends AsyncTask<Contact, Void, Boolean> {
    private SQLiteDatabase db;

    public DeleteContactInteractor(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    protected Boolean doInBackground(Contact... contact) {
        if(contact != null && db != null){
            db.delete("Contact","id="+contact[0].getId(),null);
            return true;
        } else return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
