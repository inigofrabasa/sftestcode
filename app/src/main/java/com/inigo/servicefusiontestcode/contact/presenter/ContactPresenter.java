package com.inigo.servicefusiontestcode.contact.presenter;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.inigo.servicefusiontestcode.contact.interactor.DeleteContactInteractor;
import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contact.view.ContactActivity;
import com.inigo.servicefusiontestcode.contacts.database.ContactSQLiteHelper;
import com.inigo.servicefusiontestcode.contacts.interactor.ObtainContactInteractor;
import com.inigo.servicefusiontestcode.contacts.view.MainActivity;

import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by Inigo on 22/09/17.
 */

public class ContactPresenter {

    private ContactActivity contactActivity;

    private SQLiteDatabase db;
    private ContactSQLiteHelper contactHelper;

    private ObtainContactInteractor obtainContactInteractor;

    private Contact contact;

    public ContactPresenter(ContactActivity contactActivity){
        this.contactActivity = contactActivity;

        initDB();
    }

    private void initDB(){
        //Open Data Base in readable and writable mode
        contactHelper = new ContactSQLiteHelper(contactActivity, "ContactsDB", null, 1);
        db = contactHelper.getWritableDatabase();
    }

    public void obtainContact(Bundle bundle){
        if(bundle != null){
            obtainContactInteractor = new ObtainContactInteractor(db);

            try {
                contact = obtainContactInteractor.execute(bundle.getString(MainActivity.CONTACT_ID)).get();
                contactActivity.bindData(contact);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean deleteContact(){
        try {
            return new DeleteContactInteractor(db).execute(contact).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public interface View{
        public void bindData(Contact contact);
    }
}
