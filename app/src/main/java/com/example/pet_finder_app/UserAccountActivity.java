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

import com.example.pet_finder_app.Class.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccountActivity extends AppCompatActivity {
    Toolbar arrowBack;


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

}
