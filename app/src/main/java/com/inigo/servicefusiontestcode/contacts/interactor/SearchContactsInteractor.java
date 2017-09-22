package com.inigo.servicefusiontestcode.contacts.interactor;

import android.os.AsyncTask;
import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contacts.model.Contacts;

/**
 * Created by Inigo on 21/09/17.
 */

public class SearchContactsInteractor extends AsyncTask<String, Void, Contacts> {

    private Contacts contacts;
    private Contacts resultContacts;

    public SearchContactsInteractor(){
        resultContacts = new Contacts();
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    @Override
    protected Contacts doInBackground(String... text) {
        if(contacts != null ){
            for(Contact item : contacts.getContacts()){
                if((item.getName() + " " + item.getLastName()).
                        toLowerCase().contains(text[0].toLowerCase())){
                    resultContacts.addContact(item);
                }
            }
            return resultContacts;
        } else return null;
    }

    @Override
    protected void onPostExecute(Contacts contacts) {
        super.onPostExecute(contacts);
    }
}

