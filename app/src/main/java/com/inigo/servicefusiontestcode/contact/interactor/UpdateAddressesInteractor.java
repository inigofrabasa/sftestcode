package com.inigo.servicefusiontestcode.contact.interactor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.inigo.servicefusiontestcode.contact.model.Addresses;

/**
 * Created by Inigo on 25/09/17.
 */

public class UpdateAddressesInteractor extends AsyncTask<Addresses, Void, Boolean> {
    private SQLiteDatabase db;
    private Integer contactId;

    public UpdateAddressesInteractor(SQLiteDatabase db, Integer contactId) {
        this.contactId = contactId;
        this.db = db;
    }

    @Override
    protected Boolean doInBackground(Addresses... addresses) {
        if(addresses != null && db != null){
            db.delete("Addresses", "id_contact = " + contactId, null);

            for(String address : addresses[0].getAddresses()){
                if(!address.equals("")) {
                    ContentValues newAddress = new ContentValues();

                    newAddress.put("address", address.toString());
                    newAddress.put("id_contact", contactId);

                    db.insert("Addresses", null, newAddress);
                }
            }
            return true;
        } else return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }
}