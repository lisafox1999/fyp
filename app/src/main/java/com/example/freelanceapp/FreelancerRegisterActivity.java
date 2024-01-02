package com.example.freelanceapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FreelancerRegisterActivity extends AppCompatActivity {

    //all code in this activity found in youtube video https://www.youtube.com/watch?v=iSsa9OlQJms

    // declare variables
    EditText RegEmail;
    EditText RegPassword;
    Button btnRegister;
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Imports the ui from  activity_register_user
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_register);

        getSupportActionBar().setTitle("Freelancer registration");

        //Hooks to all xml elements in register_user.xml
        RegEmail = findViewById(R.id.RegEmail);
        RegPassword = findViewById(R.id.RegPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);


        mAuth = FirebaseAuth.getInstance();

        //password code from video https://www.youtube.com/watch?v=vxnjJJkFPCI&list=PLnisUReSm0-kNhfXJHymlIFeyGYHj87tP&index=11
        //show hide password
        ImageView imageViewShowHidePwd = findViewById(R.id.imageView_show_hide_password);
        imageViewShowHidePwd.setImageResource(R.drawable.show_hide_password);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if pw visible then hide it
                if (RegPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    //if password visible then hide it
                    RegPassword.setTransformationMethod((PasswordTransformationMethod.getInstance()));//change icon
                    //change icon
                    imageViewShowHidePwd.setImageResource(R.drawable.show_hide_password);
                } else {
                    RegPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource((R.drawable.ic_show_pwd));
                }
            }
        });


        //method create user when register button clicked
        btnRegister.setOnClickListener(view ->{
            createUser();
        });

    }

    private void createUser(){
        // receive data entered by user
        String email = RegEmail.getText().toString();
        String password = RegPassword.getText().toString();

        //if statement of conditions to be met
        if (TextUtils.isEmpty(email)){
            RegEmail.setError("Email cannot be empty");
            RegEmail.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(FreelancerRegisterActivity.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
            RegEmail.setError("email is invalid");
            RegEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            RegPassword.setError("Password cannot be empty");
            RegPassword.requestFocus();
        }else if (password.length() < 6) {
            Toast.makeText(FreelancerRegisterActivity.this, "Password should be over 6 digits", Toast.LENGTH_LONG).show();
            RegPassword.setError("password is too weak");
            RegPassword.requestFocus();
        }else{
            progressBar.setVisibility(View.VISIBLE);
            //use firebase to register user through authentication
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(FreelancerRegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        //open profile activity when user registered and not let user return to registration page
                        Intent intent = new Intent(FreelancerRegisterActivity.this, FreelancerCreateProfileActivity.class);
                        intent.setFlags((intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK));
                        startActivity(intent);
                        finish();

                    }else{
                        //otherwise show error
                        Toast.makeText(FreelancerRegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }


}