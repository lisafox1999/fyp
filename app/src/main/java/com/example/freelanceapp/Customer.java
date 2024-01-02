package com.example.freelanceapp;

import com.google.firebase.database.Exclude;
import java.io.Serializable;
import java.util.Map;

public class Customer implements Serializable

        //code from youtube video https://www.youtube.com/watch?v=741QCymuky4
{
    @Exclude
    private String FullName;
    private String DOB;
    private String Phone;
    private String Address;

    //empty public constructor defined to pass data from instant node of database to properties back and forth
    public Customer() {}

    //constructor to pass the value through
    public Customer(String FullName, String DOB, String Phone, String Address) {
        this.FullName = FullName;
        this.DOB = DOB;
        this.Phone = Address;
        this.Address = Phone;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }


}

