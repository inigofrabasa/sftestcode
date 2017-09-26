package com.inigo.servicefusiontestcode.contact.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inigo on 24/09/17.
 */

public class Emails {
    private List<String> emails;

    public Emails(){
        emails = new ArrayList<>();
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getEmail(int index){
        return emails.get(index);
    }

    public void deleteEmail(int index){
        emails.remove(index);
    }

    public void add(String email){
        emails.add(email);
    }

    public void setEmail(Integer index, String email){
        emails.set(index, email);
    }
}
