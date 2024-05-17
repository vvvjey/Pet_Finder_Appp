package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet_finder_app.Class.FavouritePet;
import com.example.pet_finder_app.Class.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAccountActivity extends AppCompatActivity {
    Toolbar arrowBack;
    String[] petId= {"idpet1","idpet2"};
    String userId = "userTest";
    TextView editName ;
    TextView editUsername;
    TextView editEmail;
    String testName = "user1";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account);

        arrowBack = findViewById(R.id.toolbarArrowBack);
        editName = findViewById(R.id.editName);
        editUsername = findViewById(R.id.editUserName);
        editEmail = findViewById(R.id.editEmail);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AccountSettingActivity.class));
            }
        });
        testData();
        readData();

    }
    public   void readData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dtbRef = database.getReference("User");

        dtbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) { // Check if data exists
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String name = ds.child("name").getValue(String.class);
                        String email = ds.child("email").getValue(String.class);
                        String phone =  ds.child("phoneNumber").getValue(String.class);
                        Log.d("OK", "Retrieved name: " + name);

                        // Process the retrieved name here (e.g., update UI)

                        editName.setText(name);
                        editUsername.setText(phone);
                        editEmail.setText(email);

                    }
                } else {
                    Log.d("OK", "No data found at this location");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
                Log.d("OK", "Error reading data: " + error.getMessage());
            }
        });

    }
    private void testData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = "user123";
        String[] petIds = {"idpet1", "idpet2"};
        List<String> list = Arrays.asList(petIds);
// Get a reference to the "favouritePet" node
        DatabaseReference favoritePetRef = database.getReference("FavouritePet");

// Create a child node with the user ID
        DatabaseReference userRef = favoritePetRef.child(userId);
        userRef.setValue(list)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Firebase", "Pet IDs added successfully!");
                            // Handle successful addition (e.g., show a toast message)
                        } else {
                            Log.w("Firebase", "Error adding pet IDs", task.getException());
                            // Handle addition failure (e.g., show an error message)
                        }
                    }
                });

    }

}
