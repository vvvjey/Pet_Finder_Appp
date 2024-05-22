package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserChangePasswordActivity extends AppCompatActivity {
    Toolbar arrowBack;
    EditText currentPW, newPW, confirmPW;
    boolean isPasswordVisible = false;
    Button btnChangePW;
    private FirebaseAuth auth;
    FirebaseUser user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_change_password);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        currentPW = findViewById(R.id.editCurrentPW);
        newPW = findViewById(R.id.editNewPW);
        confirmPW = findViewById(R.id.editConfirmPW);
        btnChangePW = findViewById(R.id.btn_change_password);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserSecurityActivity.class));
            }
        });
        currentPW.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (currentPW.getRight()-currentPW.getCompoundDrawables()[Right].getBounds().width())) {
                        int selection = currentPW.getSelectionEnd();
                        if (isPasswordVisible) {
                            currentPW.setCompoundDrawablesRelativeWithIntrinsicBounds( 0, 0, R.drawable.ic_eye,  0);
                            currentPW.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisible = false;
                        } else {
                            currentPW.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
                            currentPW.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisible = true;
                        }
                        currentPW.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        newPW.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (newPW.getRight()-newPW.getCompoundDrawables()[Right].getBounds().width())) {
                        int selection = newPW.getSelectionEnd();
                        if (isPasswordVisible) {
                            newPW.setCompoundDrawablesRelativeWithIntrinsicBounds( 0, 0, R.drawable.ic_eye,  0);
                            newPW.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisible = false;
                        } else {
                            newPW.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
                            newPW.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisible = true;
                        }
                        newPW.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        confirmPW.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (confirmPW.getRight()-confirmPW.getCompoundDrawables()[Right].getBounds().width())) {
                        int selection = confirmPW.getSelectionEnd();
                        if (isPasswordVisible) {
                            confirmPW.setCompoundDrawablesRelativeWithIntrinsicBounds( 0, 0, R.drawable.ic_eye,  0);
                            confirmPW.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isPasswordVisible = false;
                        } else {
                            confirmPW.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
                            confirmPW.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isPasswordVisible = true;
                        }
                        confirmPW.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        currentPW.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    checkCurrentPassword();
                }
            }
        });


        btnChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_currentPW = currentPW.getText().toString();
                String txt_newPW = newPW.getText().toString();
                String txt_ConfirmPW = confirmPW.getText().toString();

                // Kiểm tra xem tất cả các trường có được điền đầy đủ không
                if (TextUtils.isEmpty(txt_currentPW)) {
                    Toast.makeText(UserChangePasswordActivity.this, "Current Password is needed", Toast.LENGTH_SHORT) .show();
                    newPW.setError("Please enter your current password");
                    newPW.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(txt_newPW)) {
                    Toast.makeText(UserChangePasswordActivity.this, "New Password is needed", Toast.LENGTH_SHORT) .show();
                    newPW.setError("Please enter your new password");
                    newPW.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(txt_ConfirmPW)) {
                    Toast.makeText(UserChangePasswordActivity.this, "Please confirm your new password", Toast.LENGTH_SHORT).show();
                    confirmPW.setError("Please re-enter your confirmation password");
                    confirmPW.requestFocus();
                    return;
                }

                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), txt_currentPW);

                // Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    changePW(user);
                                } else {
                                    currentPW.setError("Incorrect current password");
                                }
                            }
                        });
            }
        });


    }
    private void checkCurrentPassword() {
        String txt_currentPW = currentPW.getText().toString();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), txt_currentPW);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            // Show error message for incorrect current password
                            currentPW.setError("Incorrect current password");
                        }
                    }
                });
    }
    //-------------Click button change password---------------
    private void changePW(FirebaseUser user){
        String txt_currentPW = currentPW.getText().toString();
        String txt_newPW = newPW.getText().toString();
        String txt_ConfirmPW = confirmPW.getText().toString();

        if (txt_newPW.length() < 6) {
            Toast.makeText(UserChangePasswordActivity.this, "Password should be at least 6 digits!", Toast.LENGTH_SHORT).show();
            newPW.setError("Password too short!");
            newPW.requestFocus();
        }
        else if (!txt_newPW.equals(txt_ConfirmPW)) {
            Toast.makeText(UserChangePasswordActivity.this,"Password did not match", Toast.LENGTH_SHORT).show();
            confirmPW.setError("Please re-enter same password");
            confirmPW.requestFocus();
        }
        else if (txt_currentPW.equals(txt_newPW)) {
            Toast.makeText(UserChangePasswordActivity.this, "Please enter a new password", Toast.LENGTH_SHORT).show();
            newPW.setError("New Password cannot be same as old one");
            newPW.requestFocus();
        } else {
            user.updatePassword(txt_newPW).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Save to Firebase
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(user.getUid());
                        userRef.child("password").setValue(txt_newPW);

                        Toast.makeText(UserChangePasswordActivity.this,"Password has been changed", Toast.LENGTH_SHORT) .show();
                        Intent intent = new Intent(UserChangePasswordActivity.this, UserSecurityActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try{
                            throw task.getException();
                        } catch (Exception e){
                            Toast.makeText(UserChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            });
        }
    }
}
