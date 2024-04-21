package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ProfileActivity extends AppCompatActivity {
    Toolbar arrowBack;
    ConstraintLayout profileMyShop,profileMissingAnimalsPost,profileAdoptHistory,profileAccountSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        arrowBack = findViewById(R.id.toolbarArrowBack);
        profileMyShop = findViewById(R.id.profileMyShop);
        profileMissingAnimalsPost = findViewById(R.id.profileMissingAnimalsPost);
        profileAdoptHistory = findViewById(R.id.profileAdoptHistory);
        profileAccountSetting = findViewById(R.id.profileSetting);
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
        profileAccountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, AccountSettingActivity.class));
            }
        });

    }
}
