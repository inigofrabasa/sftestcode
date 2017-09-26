package com.inigo.servicefusiontestcode.contact.interactor;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

/**
 * Created by Inigo on 25/09/17.
 */

public class DeleteContactEmailsInteractor extends AsyncTask<Integer, Void, Boolean> {
    private SQLiteDatabase db;

    public DeleteContactEmailsInteractor(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    protected Boolean doInBackground(Integer... idContact) {
        if(idContact != null && db != null){
            db.delete("Emails","id_contact="+idContact[0],null);
            return true;
        } else return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }
}
