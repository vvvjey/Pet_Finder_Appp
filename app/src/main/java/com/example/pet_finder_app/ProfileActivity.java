package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pet_finder_app.Class.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    Toolbar arrowBack;
    private FirebaseAuth auth;
    ConstraintLayout profileMyShop, profileMissingAnimalsPost, profileAdoptHistory, profileAccountSetting, item_logout;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView user_name, user_location;
    ImageView user_img;
    User user;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        setContentView(R.layout.user_profile);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        profileMyShop = findViewById(R.id.profileMyShop);
        profileMissingAnimalsPost = findViewById(R.id.profileMissingAnimalsPost);
        profileAdoptHistory = findViewById(R.id.profileAdoptHistory);
        profileAccountSetting = findViewById(R.id.profileSetting);
        item_logout = findViewById(R.id.item_logOut);
        user_name = findViewById(R.id.user_name);
        user_location = findViewById(R.id.user_location);
        user_img = findViewById(R.id.user_img);
        idUser = currentUser.getUid();

        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    if (user != null && user.getUserId() != null && user.getUserId().equals(idUser)) {
                        user_name.setText(user.getFullname());
                        user_location.setText(user.getAddress());
                        Picasso.get().load(user.getImgUser()).into(user_img);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });

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

    private void checkUser() {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
