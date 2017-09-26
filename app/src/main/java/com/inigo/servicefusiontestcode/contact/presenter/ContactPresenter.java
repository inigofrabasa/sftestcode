package com.inigo.servicefusiontestcode.contact.presenter;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.inigo.servicefusiontestcode.contact.interactor.DeleteContactAddressesInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.DeleteContactEmailsInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.DeleteContactInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.DeleteContactPhonesInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.ObtainAddressesInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.ObtainEmailsInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.ObtainPhonesInteractor;
import com.inigo.servicefusiontestcode.contact.model.Addresses;
import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contact.model.Emails;
import com.inigo.servicefusiontestcode.contact.model.Phones;
import com.inigo.servicefusiontestcode.contact.view.ContactActivity;
import com.inigo.servicefusiontestcode.contactslist.database.ContactSQLiteHelper;
import com.inigo.servicefusiontestcode.contact.interactor.ObtainContactInteractor;
import com.inigo.servicefusiontestcode.contactslist.view.MainActivity;

import java.util.concurrent.ExecutionException;

/**
 * Created by Inigo on 22/09/17.
 */

public class ContactPresenter {

    private ContactActivity contactActivity;

    private SQLiteDatabase db;
    private ContactSQLiteHelper contactHelper;

    private ObtainContactInteractor obtainContactInteractor;
    private ObtainPhonesInteractor obtainPhonesInteractor;
    private ObtainAddressesInteractor obtainAddressesInteractor;
    private ObtainEmailsInteractor obtainEmailsInteractor;

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

    public void obtainPhones(String id){
        obtainPhonesInteractor = new ObtainPhonesInteractor(db);
        try {
            contactActivity.bindPhones(obtainPhonesInteractor.execute(id).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void obtainAddresses(String id){
        obtainAddressesInteractor = new ObtainAddressesInteractor(db);
        try {
            contactActivity.bindAddresses(obtainAddressesInteractor.execute(id).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void obtainEmails(String id){
        obtainEmailsInteractor = new ObtainEmailsInteractor(db);
        try {
            contactActivity.bindEmails(obtainEmailsInteractor.execute(id).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Boolean deleteContact(){
        try {
            Integer idResult = new DeleteContactInteractor(db).execute(contact).get();
            if(idResult != -1){
                new DeleteContactPhonesInteractor(db).execute(idResult).get();
                new DeleteContactAddressesInteractor(db).execute(idResult).get();
                new DeleteContactEmailsInteractor(db).execute(idResult).get();
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public interface View{
        void bindData(Contact contact);

        void bindPhones(Phones phones);

        void bindAddresses(Addresses addresses);

        void bindEmails(Emails emails);
    }
}
