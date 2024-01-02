package com.example.freelanceapp;

//all code from https://www.youtube.com/watch?v=MnJg1f25h_g&list=PLnisUReSm0-kNhfXJHymlIFeyGYHj87tP&index=14

import android.widget.TextView;

public class ReadWriteUserDetails {
    public String DOB;
    public String address;
    public String phone;

    //constructor
    public ReadWriteUserDetails(){
    }

    public ReadWriteUserDetails(String textDOB, String textPhone, String textAddress){
        this.DOB = textDOB;
        this.address = textAddress;
        this.phone = textPhone;

    }
}
