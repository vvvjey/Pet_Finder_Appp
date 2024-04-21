package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    TextView toRegisterTv;
    EditText password;
    boolean isPasswordVisible = true;
    Button registerBtn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        toRegisterTv = findViewById(R.id.toRegisterTv);
        registerBtn = findViewById(R.id.btnLogin);
        password = findViewById(R.id.edtPassword);

        toRegisterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Splash1Activity.class));

            }
        });
        password.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (password.getRight()-password.getCompoundDrawables()[Right].getBounds().width())) {
                        int selection = password.getSelectionEnd();
                        if (isPasswordVisible) {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds( 0, 0, R.drawable.ic_eye,  0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisible = false;
                        } else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
