package com.example.freelanceapp;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.util.HashMap;

//code from youtube video https://www.youtube.com/watch?v=741QCymuky4

public class CustomerDAO {
    private DatabaseReference databaseReference;

    //
    public CustomerDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Customer.class.getSimpleName());
    }

    //push data onto database
    public Task<Void> add(Customer user) {
        return databaseReference.push().setValue(user);

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
