package com.university.hafsa.controller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;

public class Chef {
    private DatabaseReference chefRef;

    public Chef() {
        // Initialize the Firebase Realtime Database
        chefRef = FirebaseDatabase.getInstance().getReference("Chef");
    }

    // Method to save two strings to the database
    public void saveChefs(String Chef1, String Chef2) {
        chefRef.child("data").child("Chef1").setValue(Chef1);
        chefRef.child("data").child("Chef2").setValue(Chef2);
    }

    public void getChef1(final ChefCallback<String> callback) {
        chefRef.child("data").child("Chef1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    String chef1 = task.getResult().getValue(String.class);
                    callback.onSuccess(chef1);
                } else {
                    callback.onError(task.getException());
                }
            }
        });
    }

    // Method to get the second string from the database
    public void getChef2(final ChefCallback<String> callback) {
        chefRef.child("data").child("Chef2").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    String chef2 = task.getResult().getValue(String.class);
                    callback.onSuccess(chef2);
                } else {
                    callback.onError(task.getException());
                }
            }
        });
    }

    // Callback interface for getting Chef data
    public interface ChefCallback<T> {
        void onSuccess(T data);
        void onError(Exception e);
    }
}
