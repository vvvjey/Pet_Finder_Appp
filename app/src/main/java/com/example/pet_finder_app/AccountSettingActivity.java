package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class AccountSettingActivity extends AppCompatActivity {
    Toolbar arrowBack;
    ConstraintLayout itemAccount,itemNotification,itemPrivacy,itemSecurity;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account_setting);

        arrowBack = findViewById(R.id.toolbarArrowBack);
        itemAccount = findViewById(R.id.item_account);
        itemNotification = findViewById(R.id.item_notification);
        itemPrivacy = findViewById(R.id.item_privacy);
        itemSecurity = findViewById(R.id.item_security);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
        itemAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
            }
        });
        itemNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserNotificationActivity.class));
            }
        });
        itemPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserPrivacyActivity.class));
            }
        });
        itemSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserSecurityActivity.class));
            }
        });
    }
}
