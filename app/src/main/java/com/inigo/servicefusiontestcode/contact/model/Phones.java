package com.inigo.servicefusiontestcode.contact.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inigo on 24/09/17.
 */

public class Phones {
    private List<String> phones;

    public Phones(){
        phones = new ArrayList<>();
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public String getPhone(int index){
        return phones.get(index);
    }

    public void deletePhone(int index){
        phones.remove(index);
    }

    public void add(String phone){
        phones.add(phone);
    }

    public void setPhone(Integer index, String phone){
        phones.set(index, phone);
    }
}
