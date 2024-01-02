package com.example.freelanceapp;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

//code from youtube video https://www.youtube.com/watch?v=741QCymuky4

public class FreelancerDAO {
    private DatabaseReference databaseReference;

    //
    public FreelancerDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://freelanceapp-5a1e8-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = db.getReference(Freelancer.class.getSimpleName());
    }

    //push data onto database
    public Task<Void> addUser(String userid, Freelancer user) {
        return databaseReference.child(userid).setValue(user);

    }

    public Freelancer getUser(String userId) {
        final Freelancer[] freelancer = {null};
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                freelancer[0] = snapshot.getValue(Freelancer.class);
            }

            //triggered in event of failure due to firebase rules
            @Override
            public void onCancelled(@NonNull DatabaseError error){
            }

        });

        return freelancer[0];
    }

    public Task<Void> updateUser(String userId, Freelancer freelancer) {
        return databaseReference.child(userId).setValue(freelancer);
    }

    public Task<Void> updateUserRates(String userId, HashMap<String, Integer> rates) {
        return databaseReference.child(userId).child("Rates").setValue(rates);
    }



    //for updating database
    public Task<Void> update(String key, HashMap<String ,Object> hashMap)
    {
        //call child from database
        return databaseReference.child(key).updateChildren(hashMap);
    }
    public Task<Void> remove(String key)
    {
        return databaseReference.child(key).removeValue();
    }
    public Query get()
    {
        return databaseReference;
    }
}
