package com.inigo.servicefusiontestcode.contact.presenter;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.inigo.servicefusiontestcode.contact.adapter.AddressesCreateAdapterRecyclerView;
import com.inigo.servicefusiontestcode.contact.adapter.EmailsCreateAdapterRecyclerView;
import com.inigo.servicefusiontestcode.contact.adapter.PhonesCreateAdapterRecyclerView;
import com.inigo.servicefusiontestcode.contact.interactor.CreateAddressesInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.CreateContactInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.CreateEmailsInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.CreatePhonesInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.ObtainAddressesInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.ObtainEmailsInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.ObtainPhonesInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.UpdateAddressesInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.UpdateContactInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.UpdateEmailsInteractor;
import com.inigo.servicefusiontestcode.contact.interactor.UpdatePhonesInteractor;
import com.inigo.servicefusiontestcode.contact.model.Addresses;
import com.inigo.servicefusiontestcode.contact.model.Contact;
import com.inigo.servicefusiontestcode.contact.model.Emails;
import com.inigo.servicefusiontestcode.contact.model.Phones;
import com.inigo.servicefusiontestcode.contact.view.CreateUpdateContactActivity;
import com.inigo.servicefusiontestcode.contactslist.database.ContactSQLiteHelper;
import com.inigo.servicefusiontestcode.contact.interactor.ObtainContactInteractor;

import java.util.concurrent.ExecutionException;

/**
 * Created by Inigo on 22/09/17.
 */

public class CreateUpdateContactPresenter implements
                    PhonesCreateAdapterRecyclerView.OnPhonesDataChange,
                    AddressesCreateAdapterRecyclerView.OnAddressesDataChange,
                    EmailsCreateAdapterRecyclerView.OnEmailsDataChange{

    private CreateUpdateContactActivity createUpdateContactActivity;

    private SQLiteDatabase db;
    private ContactSQLiteHelper contactHelper;

    private CreateContactInteractor createContactInteractor;
    private UpdateContactInteractor updateContactInteractor;

    private CreatePhonesInteractor createPhonesInteractor;
    private UpdatePhonesInteractor updatePhonesInteractor;

    private CreateAddressesInteractor createAddressesInteractor;
    private UpdateAddressesInteractor updateAddressesInteractor;

    private CreateEmailsInteractor createEmailsInteractor;
    private UpdateEmailsInteractor updateEmailsInteractor;

    private Contact contact;
    private Phones phones;
    private Addresses addresses;
    private Emails emails;

    public CreateUpdateContactPresenter(CreateUpdateContactActivity createUpdateContactActivity){
        this.createUpdateContactActivity = createUpdateContactActivity;
        initDB();
        phones = new Phones();
        addresses = new Addresses();
        emails = new Emails();
    }

    private void initDB(){
        //Open Data Base in readable and writable mode
        contactHelper = new ContactSQLiteHelper(createUpdateContactActivity, "ContactsDB", null, 1);
        db = contactHelper.getWritableDatabase();
    }

    public void obtainContact(String id){
        try {
            contact = (new ObtainContactInteractor(db)).execute(id).get();
            createUpdateContactActivity.bindData(contact);

            phones = (new ObtainPhonesInteractor(db)).execute(id).get();
            contact.setPhones(phones);
            createUpdateContactActivity.bindPhones(phones);

            addresses = (new ObtainAddressesInteractor(db)).execute(id).get();
            contact.setAdresses(addresses);
            createUpdateContactActivity.bindAddresses(addresses);

            emails = (new ObtainEmailsInteractor(db)).execute(id).get();
            contact.setEmails(emails);
            createUpdateContactActivity.bindEmails(emails);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Boolean tryCreateContact(Bundle bundle){

        if(bundle == null)
            return false;

        if( bundle.getString(CreateUpdateContactActivity.CONTACT_NAME).equals("") ||
            bundle.getString(CreateUpdateContactActivity.CONTACT_LASTNAME).equals("") ||
            bundle.getString(CreateUpdateContactActivity.CONTACT_MONTH).equals("") ||
            bundle.getString(CreateUpdateContactActivity.CONTACT_DAY).equals("") ||
            bundle.getString(CreateUpdateContactActivity.CONTACT_YEAR).equals("")){
            return false;
        }

        if(phones.getPhones().size() == 0)
            return false;

        if(phones.getPhones().size() == 1)
            if(phones.getPhone(0).equals(""))
                return false;

        if(emails.getEmails().size() == 0)
            return false;

        if(emails.getEmails().size() == 1)
            if(emails.getEmail(0).equals(""))
                return false;

        initDB();

        contact = new Contact(
                bundle.getString(CreateUpdateContactActivity.CONTACT_NAME),
                bundle.getString(CreateUpdateContactActivity.CONTACT_LASTNAME));
        contact.setDateOfBirth(
                bundle.getString(CreateUpdateContactActivity.CONTACT_MONTH) + "." +
                bundle.getString(CreateUpdateContactActivity.CONTACT_DAY) + "." +
                bundle.getString(CreateUpdateContactActivity.CONTACT_YEAR));
        contact.setPhones(phones);
        contact.setAdresses(addresses);
        contact.setEmails(emails);
        createContact(contact);
        return true;
    }

    public Boolean tryUpdateContact(Bundle bundle){

        if(bundle == null)
            return false;

        if( bundle.getString(CreateUpdateContactActivity.CONTACT_NAME).equals("") ||
            bundle.getString(CreateUpdateContactActivity.CONTACT_LASTNAME).equals("") ||
            bundle.getString(CreateUpdateContactActivity.CONTACT_MONTH).equals("") ||
            bundle.getString(CreateUpdateContactActivity.CONTACT_DAY).equals("") ||
            bundle.getString(CreateUpdateContactActivity.CONTACT_YEAR).equals("")){
            return false;
        }

        if(phones.getPhones().size() == 0)
            return false;

        if(phones.getPhones().size() == 1)
            if(phones.getPhone(0).equals(""))
                return false;

        if(emails.getEmails().size() == 0)
            return false;

        if(emails.getEmails().size() == 1)
            if(emails.getEmail(0).equals(""))
                return false;

        initDB();

        contact = new Contact(
                bundle.getString(CreateUpdateContactActivity.CONTACT_NAME),
                bundle.getString(CreateUpdateContactActivity.CONTACT_LASTNAME));
        contact.setId(Integer.parseInt(bundle.getString(CreateUpdateContactActivity.CONTACT_ID)));
        contact.setDateOfBirth(
                bundle.getString(CreateUpdateContactActivity.CONTACT_MONTH) + "." +
                bundle.getString(CreateUpdateContactActivity.CONTACT_DAY) + "." +
                bundle.getString(CreateUpdateContactActivity.CONTACT_YEAR));
        contact.setPhones(phones);
        contact.setAdresses(addresses);
        contact.setEmails(emails);
        updateContact(contact);
        return true;
    }

    private void createContact(Contact inContact) {
        if (inContact != null) {
            try {
                createContactInteractor = new CreateContactInteractor(db);
                Integer result = createContactInteractor.execute(inContact).get();
                if(result != -1){
                    createPhonesInteractor = new CreatePhonesInteractor(db,result);
                    createPhonesInteractor.execute(phones).get();

                    createAddressesInteractor = new CreateAddressesInteractor(db,result);
                    createAddressesInteractor.execute(addresses).get();

                    createEmailsInteractor = new CreateEmailsInteractor(db,result);
                    createEmailsInteractor.execute(emails).get();
                }
            } catch (ExecutionException e) {
            } catch (InterruptedException f) {}
        }
    }

    private void updateContact(Contact inContact) {
        if (inContact != null) {
            try {
                updateContactInteractor = new UpdateContactInteractor(db);
                Integer result = updateContactInteractor.execute(inContact).get();
                if(result != -1){
                    updatePhonesInteractor = new UpdatePhonesInteractor(db,result);
                    updatePhonesInteractor.execute(phones).get();

                    updateAddressesInteractor = new UpdateAddressesInteractor(db,result);
                    updateAddressesInteractor.execute(addresses).get();

                    updateEmailsInteractor = new UpdateEmailsInteractor(db,result);
                    updateEmailsInteractor.execute(emails).get();
                }
            } catch (ExecutionException e) {
            } catch (InterruptedException f) {}
        }
    }

    public void initPhone(){
        phones.add("");
        createUpdateContactActivity.bindPhones(phones);
    }

    public void initAddress(){
        addresses.add("");
        createUpdateContactActivity.bindAddresses(addresses);
    }

    public void initEmail(){
        emails.add("");
        createUpdateContactActivity.bindEmails(emails);
    }

    @Override
    public void phonesDataChange(Phones phones) {
        createUpdateContactActivity.bindPhones(phones);
    }

    @Override
    public void addressesDataChange(Addresses addresses) {
        createUpdateContactActivity.bindAddresses(addresses);
    }

    @Override
    public void emailsDataChange(Emails emails) {
        createUpdateContactActivity.bindEmails(emails);
    }

    public interface View {
        void bindData(Contact contact);

        void bindPhones(Phones phones);

        void bindAddresses(Addresses addresses);

        void bindEmails(Emails emails);
    }
}
