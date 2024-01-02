package com.example.freelanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.freelanceapp.ui.login.Category;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FreelancerCategoriesDAO {

    private DatabaseReference databaseReferenceCategory;
    private DatabaseReference databaseReferenceFreelancer;
    private FreelancerDAO freelancerDAO;

    FreelancerCategoriesDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://freelanceapp-5a1e8-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReferenceCategory = db.getReference(Category.class.getSimpleName());
        databaseReferenceFreelancer = db.getReference(Freelancer.class.getSimpleName());
        freelancerDAO = new FreelancerDAO();
    }

    public Task<Void> updateCategory(String category, String userId, String rate) {
        updateUserWithRate1(category, userId, rate);
        return databaseReferenceCategory.child(category).setValue(userId);
    }

    public Task<Void> updateUserWithRate(String category, String userId, String rate) {
        Freelancer freelancer = freelancerDAO.getUser(userId);
        freelancer.setRateForCategory(category, Integer.valueOf(rate));
        return freelancerDAO.updateUserRates(userId, freelancer.getRates());
    }

    public void updateUserWithRate1(String category, String userId, String rate) {

        databaseReferenceFreelancer.child(userId).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                Freelancer freelancer = snapshot.getValue(Freelancer.class);
                freelancer.setRateForCategory(category, Integer.valueOf(rate));
                databaseReferenceFreelancer.child(userId).child("Rates").setValue(freelancer.getRates());
            }

            //triggered in event of failure due to firebase rules
            @Override
            public void onCancelled(@NonNull DatabaseError error){
            }

        });
    }
}