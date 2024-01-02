package com.example.freelanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class FreelancerProfileActivity extends AppCompatActivity {

    //all code from https://www.youtube.com/watch?v=MnJg1f25h_g&list=PLnisUReSm0-kNhfXJHymlIFeyGYHj87tP&index=14

    private TextView Welcome, FullName, Email, DOB, Phone;
    private ProgressBar progressBar;
    private String fullName, phone, email, dob;
    private ImageView imageview;
    private FirebaseAuth authProfile;
    private Button btnLogOut, btnDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_profile);

        getSupportActionBar().setTitle("Home");

        Welcome = findViewById(R.id.welcomeUser);
        FullName = findViewById(R.id.FullName);
        Email = findViewById(R.id.Email);
        DOB = findViewById(R.id.DOB);
        Phone = findViewById(R.id.Phone);
        progressBar = findViewById(R.id.progressBar);
        btnLogOut = findViewById(R.id.btnLogout2);
        btnDashboard = findViewById(R.id.btnDashboard1);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(FreelancerProfileActivity.this, "Error occurred, Profile details unattainable at this moment", Toast.LENGTH_LONG).show();
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
                Freelancer freelancer = snapshot.getValue(Freelancer.class);
                if (freelancer != null){
                    fullName = freelancer.getFullName();
                    email = freelancer.getAddress();
                    dob = freelancer.getDOB();
                    phone = freelancer.getPhone();

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
                Toast.makeText(FreelancerProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }


        });

        //log out button functionality
        btnLogOut.setOnClickListener(view -> {
            startActivity(new Intent(FreelancerProfileActivity.this, HomeActivity.class));
        });

        btnDashboard.setOnClickListener(view -> {
            startActivity(new Intent(FreelancerProfileActivity.this, FreelancerDashboard.class));
        });




    }
}

