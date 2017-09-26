package com.inigo.servicefusiontestcode.contact.interactor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.inigo.servicefusiontestcode.contact.model.Emails;

/**
 * Created by Inigo on 25/09/17.
 */

public class CreateEmailsInteractor extends AsyncTask<Emails, Void, Boolean> {

    private SQLiteDatabase db;
    private Integer contactId;

    public CreateEmailsInteractor(SQLiteDatabase db, Integer contactId) {
        this.contactId = contactId;
        this.db = db;
    }

    @Override
    protected Boolean doInBackground(Emails... emails) {
        if(emails != null && db != null){
            for(String email : emails[0].getEmails()){
                ContentValues newEmail = new ContentValues();

                newEmail.put("email", email.toString());
                newEmail.put("id_contact",contactId);

                db.insert("Emails",null,newEmail);
            }
            return true;
        } else return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }
}
