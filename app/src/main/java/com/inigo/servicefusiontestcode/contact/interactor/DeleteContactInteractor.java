package com.inigo.servicefusiontestcode.contact.interactor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.inigo.servicefusiontestcode.contact.model.Contact;

/**
 * Created by Inigo on 23/09/17.
 */

public class DeleteContactInteractor extends AsyncTask<Contact, Void, Integer> {
    private SQLiteDatabase db;

    public DeleteContactInteractor(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    protected Integer doInBackground(Contact... contact) {
        if(contact != null && db != null){
            db.delete("Contact","id="+contact[0].getId(),null);
            return contact[0].getId();
        } else return -1;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
    }
}
