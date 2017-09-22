package com.inigo.servicefusiontestcode.contact.presenter;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.inigo.servicefusiontestcode.contact.interactor.CreateContactInteractor;
import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contact.view.CreateContactActivity;
import com.inigo.servicefusiontestcode.contacts.database.ContactSQLiteHelper;
import com.inigo.servicefusiontestcode.contacts.model.Contacts;

import java.util.concurrent.ExecutionException;

/**
 * Created by Inigo on 22/09/17.
 */

public class CreateContactPresenter {

    private CreateContactActivity createContactActivity;

    public CreateContactPresenter(CreateContactActivity createContactActivity){
        this.createContactActivity = createContactActivity;
    }

    private SQLiteDatabase db;
    private ContactSQLiteHelper contactHelper;

    private Contacts dataBaseContacts;
    private CreateContactInteractor createContactInteractor;

    public Boolean tryCreateContact(Bundle bundle){

        if(bundle == null)
            return false;

        if(bundle.getString(CreateContactActivity.CONTACT_NAME).equals("") &&
                bundle.getString(CreateContactActivity.CONTACT_LASTNAME).equals("")){
            return false;
        }

        initDB();

        createContact(
                new Contact(
                bundle.getString(CreateContactActivity.CONTACT_NAME),
                bundle.getString(CreateContactActivity.CONTACT_LASTNAME)));

        return true;
    }

    private void initDB(){
        //Open Data Base in readable and writable mode
        contactHelper = new ContactSQLiteHelper(createContactActivity, "ContactsDB", null, 1);
        db = contactHelper.getWritableDatabase();
    }

    private void createContact(Contact inContact) {
        if (inContact != null) {
            try {
                createContactInteractor = new CreateContactInteractor(db);
                Boolean result = createContactInteractor.execute(inContact).get();
            } catch (ExecutionException e) {
            } catch (InterruptedException f) {}
        }
    }

    public interface View {
    }
}
