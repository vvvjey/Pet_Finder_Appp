package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

public class UserSecurityActivity extends AppCompatActivity {
    Toolbar arrowBack;
    ConstraintLayout itemChangePassword,itemChangeEmail;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_security);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        itemChangePassword = findViewById(R.id.item_changePassword);
        itemChangeEmail = findViewById(R.id.item_changeEmail);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AccountSettingActivity.class));
            }
        });
        itemChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserChangePasswordActivity.class));
            }
        });
        itemChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserChangeEmailActivity.class));
            }
        });
    }
}
