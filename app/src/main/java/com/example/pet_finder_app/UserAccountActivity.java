package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    TextView editUsername, editGender, editAddress, editPhoneNumber;
    TextView editEmail;
    String password ;
    private FirebaseAuth auth;
    String testName = "user1";



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account);
        auth = FirebaseAuth.getInstance();
        arrowBack = findViewById(R.id.toolbarArrowBack);
        Button saveChangesButton = findViewById(R.id.btn_save_change);
        editUsername = findViewById(R.id.editUserName);
        editEmail = findViewById(R.id.editEmail);
        editAddress = findViewById(R.id.editAddress);
        editGender = findViewById(R.id.editGender);
        editPhoneNumber= findViewById(R.id.editPhoneNumber);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AccountSettingActivity.class));
            }
        });
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfoToFirebase();
            }
        });
        defaultUser();
        getUser();



    }
    private void saveUserInfoToFirebase(){
        String newEmail = editEmail.getText().toString();
        String newName = editUsername.getText().toString();
        String newGender = editGender.getText().toString();
        String newAddress = editAddress.getText().toString();
        String newPhoneNumber = editPhoneNumber.getText().toString();

        // Create a Map to store user information
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", newEmail);
        userInfo.put("fullname", newName);
        userInfo.put("gender", newGender);
        userInfo.put("address", newAddress);
        userInfo.put("phonenumber", newPhoneNumber);
        // Get a reference to the user node
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(uid);

        // Update user information in Firebase
        userRef.setValue(userInfo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserAccountActivity.this, "User information saved!", Toast.LENGTH_SHORT).show();
                            // Optionally, reload user data or navigate back to another activity
                        } else {
                            Log.w("TAG", "Error saving user information", task.getException());
                            Toast.makeText(UserAccountActivity.this, "Error saving user information!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void defaultUser(){
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference dtbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = dtbRef.child("User").child(uid);
        userRef.child("address").setValue("Ho Chi Minh City");
        userRef.child("gender").setValue("Female");
        userRef.child("phonenumber").setValue("XXX");
    }
    private void getUser(){
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference dtbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = dtbRef.child("User").child(uid);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Extract the fullname from the snapshot
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String name  = dataSnapshot.child("fullname").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);
                    String phonenumber = dataSnapshot.child("phonenumber").getValue(String.class);
                    editEmail.setText(email);
                    editUsername.setText(name);
                    editGender.setText(gender);
                    editAddress.setText(address);
                    editPhoneNumber.setText(phonenumber);

                } else {
                    // Handle the case where fullname doesn't exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors during database read operation
                Log.w("TAG", "Failed to read value", error.toException());
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
