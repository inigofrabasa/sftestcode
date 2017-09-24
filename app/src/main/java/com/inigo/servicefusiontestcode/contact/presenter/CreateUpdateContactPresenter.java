package com.inigo.servicefusiontestcode.contact.presenter;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.inigo.servicefusiontestcode.contact.interactor.CreateContactInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.UpdateContactInteractor;
import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contact.view.CreateUpdateContactActivity;
import com.inigo.servicefusiontestcode.contacts.database.ContactSQLiteHelper;
import com.inigo.servicefusiontestcode.contacts.interactor.ObtainContactInteractor;

import java.util.concurrent.ExecutionException;

/**
 * Created by Inigo on 22/09/17.
 */

public class CreateUpdateContactPresenter {

    private CreateUpdateContactActivity createUpdateContactActivity;

    public CreateUpdateContactPresenter(CreateUpdateContactActivity createUpdateContactActivity){
        this.createUpdateContactActivity = createUpdateContactActivity;
        initDB();
    }

    private SQLiteDatabase db;
    private ContactSQLiteHelper contactHelper;

    private CreateContactInteractor createContactInteractor;
    private UpdateContactInteractor updateContactInteractor;

    public Boolean tryCreateContact(Bundle bundle){

        if(bundle == null)
            return false;

        if(bundle.getString(CreateUpdateContactActivity.CONTACT_NAME).equals("") ||
                bundle.getString(CreateUpdateContactActivity.CONTACT_LASTNAME).equals("")){
            return false;
        }

        initDB();

        createContact(
                new Contact(
                bundle.getString(CreateUpdateContactActivity.CONTACT_NAME),
                bundle.getString(CreateUpdateContactActivity.CONTACT_LASTNAME)));

        return true;
    }

    public Boolean tryUpdateContact(Bundle bundle){

        if(bundle == null)
            return false;

        if(bundle.getString(CreateUpdateContactActivity.CONTACT_NAME).equals("") ||
                bundle.getString(CreateUpdateContactActivity.CONTACT_LASTNAME).equals("")){
            return false;
        }

        initDB();

        Contact contact = new Contact(
                bundle.getString(CreateUpdateContactActivity.CONTACT_NAME),
                bundle.getString(CreateUpdateContactActivity.CONTACT_LASTNAME));
        contact.setId(Integer.parseInt(bundle.getString(CreateUpdateContactActivity.CONTACT_ID)));
        updateContact(contact);

        return true;
    }

    private void initDB(){
        //Open Data Base in readable and writable mode
        contactHelper = new ContactSQLiteHelper(createUpdateContactActivity, "ContactsDB", null, 1);
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

    private void updateContact(Contact inContact) {
        if (inContact != null) {
            try {
                updateContactInteractor = new UpdateContactInteractor(db);
                Boolean result = updateContactInteractor.execute(inContact).get();
            } catch (ExecutionException e) {
            } catch (InterruptedException f) {}
        }
    }

    public void obtainContact(String id){
        try {
            createUpdateContactActivity.bindData((new ObtainContactInteractor(db)).execute(id).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public interface View {
        void bindData(Contact contact);
    }
}
