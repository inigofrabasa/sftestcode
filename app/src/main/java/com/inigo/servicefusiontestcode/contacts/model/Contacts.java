package com.inigo.servicefusiontestcode.contacts.model;
import com.inigo.servicefusiontestcode.contact.model.Contact;
import java.util.ArrayList;

/**
 * Created by Inigo on 22/09/17.
 */

public class Contacts {
    private ArrayList<Contact> contacts;

    public Contacts(){
        contacts = new ArrayList<>();
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact contact){
        if(contacts == null)
            contacts = new ArrayList<>();

        contacts.add(contact);
    }

    public void clearContacts(){
        if(contacts == null)
            return;

        contacts.clear();
    }

    public Contact getContact(int index){
        if(contacts!=null)
            return contacts.get(index);
        return null;
    }
}
