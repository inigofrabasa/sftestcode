package com.inigo.servicefusiontestcode.contacts.presenter;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.inigo.servicefusiontestcode.contacts.model.Contacts;
import com.inigo.servicefusiontestcode.contacts.view.MainActivity;
import com.inigo.servicefusiontestcode.contacts.database.ContactSQLiteHelper;
import com.inigo.servicefusiontestcode.contacts.interactor.ObtainContactsInteractor;
import com.inigo.servicefusiontestcode.contacts.interactor.SearchContactsInteractor;

import java.util.concurrent.ExecutionException;

/**
 * Created by Inigo on 21/09/17.
 */

public class MainPresenter {

    private MainActivity mainActivity;

    private SQLiteDatabase db;
    private ContactSQLiteHelper contactHelper;

    private Contacts dataBaseContacts;
    private ObtainContactsInteractor obtainContactsInteractor;

    private SearchContactsInteractor searchContact;

    public MainPresenter(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        //Init dataBase
        initDB();

        //Remove Contacts
        //removeAllContacts();

        //Load Contacts
        dataBaseContacts = getAllContacts();

        //Load Data
        mainActivity.bindData(dataBaseContacts);
    }

    private void initDB(){
        //Open Data Base in readable and writable mode
        contactHelper = new ContactSQLiteHelper(mainActivity, "ContactsDB", null, 1);
        db = contactHelper.getWritableDatabase();
    }

    private Contacts getAllContacts(){
        try {
            obtainContactsInteractor = new ObtainContactsInteractor(db);
            return obtainContactsInteractor.execute().get();
        } catch (ExecutionException e) {
        } catch (InterruptedException f) {}

        return null;
    }

    private void deleteDataBase(){
        mainActivity.deleteDatabase("ContactsDB");
    }

    private void removeAllContacts(){
        try {
            db.delete("Contact","",null);
        }
        catch(SQLException ex){}
    }

    public void searchContact(String text){
        searchContact = new SearchContactsInteractor();
        searchContact.setContacts(dataBaseContacts);

        try {
            mainActivity.bindData(searchContact.execute(text).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void bindAllContacts(){
        mainActivity.bindData(dataBaseContacts);
    }

    public void onDestroy(){
        db.close();
    }

    public interface View {
        void bindData(Contacts contact);
    }

}
