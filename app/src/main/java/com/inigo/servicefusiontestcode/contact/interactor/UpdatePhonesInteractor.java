package com.inigo.servicefusiontestcode.contact.interactor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contact.model.Phones;

/**
 * Created by Inigo on 24/09/17.
 */

public class UpdatePhonesInteractor extends AsyncTask<Phones, Void, Boolean> {
    private SQLiteDatabase db;
    private Integer contactId;

    public UpdatePhonesInteractor(SQLiteDatabase db, Integer contactId) {
        this.contactId = contactId;
        this.db = db;
    }

    @Override
    protected Boolean doInBackground(Phones... phones) {
        if(phones != null && db != null){
            ContentValues updatePhones = new ContentValues();
            db.delete("Phones", "id_contact = " + contactId, null);

            for(String phone : phones[0].getPhones()){
                ContentValues newPhone = new ContentValues();

                newPhone.put("phone",phone.toString());
                newPhone.put("id_contact",contactId);

                db.insert("Phones",null,newPhone);
            }

            return true;
        } else return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }
}
