package com.smartlogic.saranga.reserveme.Models;

import java.io.Serializable;

/**
 * Created by sranga on 6/13/2016.
 */
public class Customer implements Serializable {
    private int id;
    private String name;
    private String phoneNumber;
    private String passWord;
    private String email;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getEmail() {
        return email;
    }
}
