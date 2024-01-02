package com.example.freelanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerProfileActivity extends AppCompatActivity {

    //all code from https://www.youtube.com/watch?v=MnJg1f25h_g&list=PLnisUReSm0-kNhfXJHymlIFeyGYHj87tP&index=14

    private TextView Welcome, FullName, Email, DOB, Phone;
    private ProgressBar progressBar;
    private String fullName, phone, email, dob;
    private ImageView imageview;
    private FirebaseAuth authProfile;
    private Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile);

        getSupportActionBar().setTitle("Home");

        Welcome = findViewById(R.id.welcomeUser2);
        FullName = findViewById(R.id.fullName);
        Email = findViewById(R.id.email);
        DOB = findViewById(R.id.DOB2);
        Phone = findViewById(R.id.phone);
        progressBar = findViewById(R.id.progressBar3);
        btnLogOut = findViewById(R.id.btnLogout3);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(CustomerProfileActivity.this, "Error occurred, Profile details unattainable at this moment", Toast.LENGTH_LONG).show();
        } else {
            showUserProfile(firebaseUser);

        }
    }

    private void showUserProfile(FirebaseUser firebaseUser){
        progressBar.setVisibility(View.VISIBLE);
        String userID = firebaseUser.getUid();

        //extracting user reference from database
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance("https://freelanceapp-5a1e8-default-rtdb.europe-west1.firebasedatabase.app").getReference("Freelancer");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                //ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                Customer customer = snapshot.getValue(Customer.class);
                if (customer != null){
                    fullName = customer.getFullName();
                    email = customer.getAddress();
                    dob = customer.getDOB();
                    phone = customer.getPhone();

                    progressBar.setVisibility(View.INVISIBLE);

                    Welcome.setText("Welcome, " + fullName + "!");
                    FullName.setText(fullName);
                    Email.setText(email);
                    DOB.setText(dob);
                    Phone.setText(phone);
                }
            }

            //triggered in event of failure due to firebase rules
            @Override
            public void onCancelled(@NonNull DatabaseError error){
                Toast.makeText(CustomerProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }


        });

        //log out button functionality
        btnLogOut.setOnClickListener(view -> {
            startActivity(new Intent(CustomerProfileActivity.this, HomeActivity.class));
        });




    }
}

