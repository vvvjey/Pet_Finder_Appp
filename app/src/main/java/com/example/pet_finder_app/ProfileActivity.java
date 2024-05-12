package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    Toolbar arrowBack;
    private FirebaseAuth auth;
    ConstraintLayout profileMyShop,profileMissingAnimalsPost,profileAdoptHistory,profileAccountSetting, item_logout;
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
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
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
}
