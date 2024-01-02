package com.example.freelanceapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

//all code in this activity found in youtube video https://www.youtube.com/watch?v=iSsa9OlQJms
public class FreelancerLoginActivity extends AppCompatActivity {

    //declare variables
    EditText LoginEmail;
    EditText LoginPassword;
    TextView RegisterHere;
    Button btnLogin;
    ProgressBar progressBar;
    String TAG = "LoginActivity";


    FirebaseAuth authProfile;
    FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Imports the ui from  activity_register_user
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_login);

        getSupportActionBar().setTitle("Freelancer login");

        //Hooks to all xml elements in register_user.xml
        LoginEmail = findViewById(R.id.LoginEmailFreelancer);
        LoginPassword = findViewById(R.id.LoginPassword1);
        RegisterHere = findViewById(R.id.RegisterHere);
        btnLogin = findViewById(R.id.btnLoginFreelancer);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();


        //password code from https://www.youtube.com/watch?v=vxnjJJkFPCI&list=PLnisUReSm0-kNhfXJHymlIFeyGYHj87tP&index=11
        //show hide password
        ImageView imageViewShowHidePwd = findViewById(R.id.imageView_show_hide_password);
        imageViewShowHidePwd.setImageResource(R.drawable.show_hide_password);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if pw visible then hide it
                if (LoginPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    //if password visible then hide it
                    LoginPassword.setTransformationMethod((PasswordTransformationMethod.getInstance()));
                    //change icon
                    imageViewShowHidePwd.setImageResource(R.drawable.show_hide_password);
                } else {
                    LoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource((R.drawable.ic_show_pwd));
                }
            }
        });

        //method loginUser called when log in button clicked
        btnLogin.setOnClickListener(view -> {
            loginUser();
        });
        //bring user to register activity
        RegisterHere.setOnClickListener(view ->{
            startActivity(new Intent(FreelancerLoginActivity.this, FreelancerRegisterActivity.class));
        });
    }

    //loginUser method
    private void loginUser(){
        // receive data entered by user
        String email = LoginEmail.getText().toString();
        String password = LoginPassword.getText().toString();

        //if statement of conditions to be met
        if (TextUtils.isEmpty(email)){
            LoginEmail.setError("Email cannot be empty");
            LoginEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            LoginPassword.setError("Password cannot be empty");
            LoginPassword.requestFocus();
        }else{
            progressBar.setVisibility(View.VISIBLE);
            //use firebase to register user through authentication
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
//
//                        //get instance of current user
//                        FirebaseUser firebaseUser = authProfile.getCurrentUser();

                        //open profile activity
                        Toast.makeText(FreelancerLoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FreelancerLoginActivity.this, FreelancerProfileActivity.class));

                    }else{
                        //show error
                        try {
                            throw task.getException();
                        } catch(FirebaseAuthInvalidUserException e) {
                            LoginEmail.setError("User does not exist or is no longer valid. Please try again");
                            LoginEmail.requestFocus();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            LoginPassword.setError("Invalid credentials, please re-enter");
                            LoginPassword.requestFocus();
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                            Toast.makeText(FreelancerLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

}
