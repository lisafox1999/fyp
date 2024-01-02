package com.example.freelanceapp;

import com.google.firebase.database.Exclude;
import java.io.Serializable;
import java.util.HashMap;

public class Freelancer implements Serializable

        //code from youtube video https://www.youtube.com/watch?v=741QCymuky4
{
    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public void setRateForCategory(String category, int rate) {
        if (Rates.containsKey(category)) {
            Rates.replace(category, rate);
        } else {
            Rates.put(category, rate);
        }
    }

    public HashMap<String, Integer> getRates() {
        return Rates;
    }

    public Integer getRateForCategory(String category) {
        return Rates.get(category);
    }



    @Exclude
    private String FullName;
    private String Id;
    private String DOB;
    private String Phone;
    private String Address;
    private HashMap<String, Integer> Rates;

    //empty public constructor defined to pass data from instant node of database to properties back and forth
    public Freelancer() {
        Rates = new HashMap<>();
    }

    //constructor to pass the value through
    public Freelancer(String FullName, String DOB, String Phone, String Address) {
        this.FullName = FullName;
        this.Id = Id;
        this.DOB = DOB;
        this.Phone = Address;
        this.Address = Phone;
    }





}
