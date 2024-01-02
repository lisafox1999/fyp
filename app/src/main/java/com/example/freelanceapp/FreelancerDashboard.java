package com.example.freelanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FreelancerDashboard extends AppCompatActivity {

    TextView tvBlog, tvBrowse, tvPayments, tvCategories, tvProfile;
    Button btnLogout;
    ImageView blogImage, browseImage, paymentsImage, settingsImage, profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_dashboard);

        getSupportActionBar().setTitle("Freelancer Dashboard");

        //Hooks to all xml elements in register_user.xml
        tvBlog = findViewById(R.id.blog);
        tvBrowse = findViewById(R.id.browse);
        tvPayments = findViewById(R.id.payments);
        tvCategories = findViewById(R.id.hub);
        tvProfile = findViewById(R.id.profile);
        btnLogout = findViewById(R.id.btnLogoutdashboard);
        blogImage = findViewById(R.id.image_view1);
        browseImage = findViewById(R.id.image_view2);
        paymentsImage = findViewById(R.id.image_view3);
        settingsImage = findViewById(R.id.image_view4);
        profileImage = findViewById(R.id.image_view);

        tvBlog.setOnClickListener(view ->{
            startActivity(new Intent(FreelancerDashboard.this, FreelancerBlogActivity.class));
        });

        tvBrowse.setOnClickListener(view ->{
            startActivity(new Intent(FreelancerDashboard.this, FreelancerBrowseActivity.class));
        });

        tvPayments.setOnClickListener(view ->{
            startActivity(new Intent(FreelancerDashboard.this, FreelancerPaymentActivity.class));
        });

        tvCategories.setOnClickListener(view ->{
            startActivity(new Intent(FreelancerDashboard.this, FreelancerCreateCategoriesActivity.class));
        });

        tvProfile.setOnClickListener(view ->{
            startActivity(new Intent(FreelancerDashboard.this, FreelancerProfileActivity.class));
        });

        btnLogout.setOnClickListener(view ->{
            startActivity(new Intent(FreelancerDashboard.this, HomeActivity.class));
        });



    }
}