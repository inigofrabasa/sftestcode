package com.inigo.servicefusiontestcode.contact.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inigo on 24/09/17.
 */

public class Addresses {   private List<String> addresses;

    public Addresses(){
        addresses = new ArrayList<>();
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public String getAddress(int index){
        return addresses.get(index);
    }

    public void deleteAddress(int index){
        addresses.remove(index);
    }

    public void add(String address){
        addresses.add(address);
    }

    public void setAddress(Integer index, String address){
        addresses.set(index, address);
    }
}
