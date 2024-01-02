package com.example.freelanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class FreelancerCreateProfileActivity extends AppCompatActivity {

    private DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();

        //code found on youtube video https://www.youtube.com/watch?v=741QCymuky4
        // connect to xml file
        setContentView(R.layout.activity_freelancer_create_profile);

        getSupportActionBar().setTitle("Freelancer profile");

        //create profile button functionality
        Button btnCreateProfile = findViewById(R.id.btnCreateProfile);
        //create database connection
        FreelancerDAO dao = new FreelancerDAO();

        //set which ids relate to each variable
        final EditText RegFullName = findViewById(R.id.RegFullName);
        final EditText RegDOB = findViewById(R.id.RegDOB);
        final EditText RegAddress = findViewById(R.id.regAddress);
        final EditText RegPhone = findViewById(R.id.RegPhone);
        final Button btnLogout = findViewById(R.id.btnLogout);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

//setting date picker for edit text
        RegDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //date picker dialog
                picker = new DatePickerDialog(FreelancerCreateProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        RegDOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        //log out button functionality
        btnLogout.setOnClickListener(view -> {
            startActivity(new Intent(FreelancerCreateProfileActivity.this, HomeActivity.class));
        });

        btnCreateProfile.setOnClickListener(v ->
        {

            //create strings
            Freelancer user = new Freelancer(RegFullName.getText().toString(), RegDOB.getText().toString(), RegAddress.getText().toString(), RegPhone.getText().toString());
            String fullName = RegFullName.getText().toString();
            String address = RegAddress.getText().toString();
            

            //if successful, bring to profile activity
            dao.addUser(currentFirebaseUser.getUid(), user).addOnSuccessListener(suc ->
            {
                //conditions to be met in order to register user
                if  (RegPhone.length() != 10) {
                    RegPhone.setError("Phone number should be 10 digits");
                    RegPhone.requestFocus();
                }else if(TextUtils.isEmpty(address)) {
                    RegAddress.setError("Email cannot be empty");
                    RegAddress.requestFocus();
                }else if (TextUtils.isEmpty(fullName)) {
                    RegFullName.setError("Full name cannot be empty");
                    RegFullName.requestFocus();
                }else{ //if (RegDOB.length() != 10) {
                    //RegDOB.setError("Invalid date of birth");
                    // RegDOB.requestFocus();
                    //} else {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Your Freelancer profile has been created", Toast.LENGTH_SHORT).show();
                    // bring user to profile page
                    // prevent user from returning back to create profile activity once registration successful
                    Intent intent = new Intent(FreelancerCreateProfileActivity.this, FreelancerProfileActivity.class);
                    intent.setFlags((intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK));
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(er ->
            {
                Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
            progressBar.setVisibility(View.GONE);

        });
    }
}

