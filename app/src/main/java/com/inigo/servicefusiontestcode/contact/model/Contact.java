package com.inigo.servicefusiontestcode.contact.model;

import java.util.List;

/**
 * Created by Inigo on 21/09/17.
 */

public class Contact {

    private Integer id;
    private String name;
    private String lastName;
    private String dateOfBirth;
    private Addresses adresses;
    private Phones phones;
    private Emails emails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contact(){

    }

    public Contact(String inName, String inLastName){
        name = inName;
        lastName = inLastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Addresses getAdresses() {
        return adresses;
    }

    public void setAdresses(Addresses adresses) {
        this.adresses = adresses;
    }

    public Phones getPhones() {
        return phones;
    }

    public void setPhones(Phones phones) {
        this.phones = phones;
    }

    public Emails getEmails() {
        return emails;
    }

    public void setEmails(Emails emails) {
        this.emails = emails;
    }
}
