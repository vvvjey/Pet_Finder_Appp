package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ProfileActivity extends AppCompatActivity {
    Toolbar arrowBack;
    ConstraintLayout profileMyShop,profileMissingAnimalsPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        ConstraintLayout myShop = findViewById(R.id.item_account);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        profileMyShop = findViewById(R.id.profileMyShop);
        profileMissingAnimalsPost = findViewById(R.id.profileMissingAnimalsPost);

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
//        myShop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ProfileActivity.this, MyShopActivity.class));
//            }
//        });
    }
}
