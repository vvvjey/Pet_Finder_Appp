package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    Toolbar arrowBack;
    private FirebaseAuth auth;
    ConstraintLayout profileMyShop,profileMissingAnimalsPost,profileAdoptHistory,profileAccountSetting, item_logout;

    TextView user_name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.user_profile);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        profileMyShop = findViewById(R.id.profileMyShop);
        profileMissingAnimalsPost = findViewById(R.id.profileMissingAnimalsPost);
        profileAdoptHistory = findViewById(R.id.profileAdoptHistory);
        profileAccountSetting = findViewById(R.id.profileSetting);
        item_logout = findViewById(R.id.item_logOut);
        user_name =findViewById(R.id.user_name);
        getUser();

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, HomepageActivity.class));
            }
        });
        profileMyShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MyShopActivity.class));
            }
        });

        profileMissingAnimalsPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MissingAnimalsPostActivity.class));
            }
        });
        profileAdoptHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, HistoryAdoptActivity.class));
            }
        });
        profileAccountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, AccountSettingActivity.class));
            }
        });
        item_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
    private void checkUser(){
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
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
                    String fullname = dataSnapshot.child("fullname").getValue(String.class);
                    user_name.setText(fullname);
                } else {
                    // Handle the case where fullname doesn't exist
                    Log.d("TAG", "Fullname not found");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors during database read operation
                Log.w("TAG", "Failed to read value", error.toException());
            }
        });
    }
}
