package com.example.freelanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FreelancerCreateCategoriesActivity extends AppCompatActivity {

    Button btnCreate;
    TextView tvProfile;
    EditText etRate;
    ProgressBar progressBar;
    Spinner categorySpinner;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FreelancerCategoriesDAO freelancerCategoriesDAO = new FreelancerCategoriesDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_create_categories);

        getSupportActionBar().setTitle("Freelancer Dashboard");

        //Hooks to all xml elements in register_user.xml
        tvProfile = findViewById(R.id.profile2);
        btnCreate = findViewById(R.id.btnCreateFreelance);
        progressBar = findViewById(R.id.progressBar6);
        etRate = findViewById(R.id.rate);
        categorySpinner = findViewById(R.id.categories);

        Spinner categoriesSpinner = (Spinner) findViewById(R.id.categories);

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(FreelancerCreateCategoriesActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(categoriesAdapter);

        tvProfile.setOnClickListener(view ->{
            startActivity(new Intent(FreelancerCreateCategoriesActivity.this, FreelancerProfileActivity.class));
        });

        btnCreate.setOnClickListener(view ->{
            startActivity(new Intent(FreelancerCreateCategoriesActivity.this, FreelancerDashboard.class));
        });

        btnCreate.setOnClickListener(view ->{
            updateUserWithCategory();
        });
    }

    private void updateUserWithCategory() {
        String rate = etRate.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();

        //if statement of conditions to be met
        if (TextUtils.isEmpty(rate)) {
            etRate.setError("Rate cannot be empty");
            etRate.requestFocus();
        } else {
            freelancerCategoriesDAO.updateCategory(category, currentFirebaseUser.getUid(), rate);
        }
    }
}