package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google. firebase.auth. FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import javax.annotation.Nullable;

public class RegisterActivity extends AppCompatActivity {
    TextView toLoginTv;
    Toolbar toolbar;
    EditText fullName, email, password;
    private FirebaseAuth auth;

    boolean isPasswordVisible = true;
    Button registerBtn, RegisterGGBtn;
    private GoogleSignInClient client ;
    private static final String TAG = "RegisterActivity";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        toolbar = findViewById(R.id.toolbarArrowBack);
        setSupportActionBar(toolbar);
        // register button
        registerBtn = findViewById(R.id.btnRegister);
        RegisterGGBtn = findViewById(R.id.btnLoginGG);
        fullName = findViewById(R.id.edtFullName);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        auth = FirebaseAuth.getInstance();
        //----------back to Login page-----------
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_fullname = fullName.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_fullname)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your full name!", Toast.LENGTH_SHORT).show();
                    fullName.setError("Full Name is required!");
                    fullName.requestFocus();
                }
                else if (TextUtils.isEmpty(txt_email)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your email!", Toast.LENGTH_SHORT).show();
                    email.setError("Email is required!");
                    email.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please re-enter your email!", Toast.LENGTH_LONG).show();
                    email.setError("Valid email is required!");
                    email.requestFocus();
                }
                else if (TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your password!", Toast.LENGTH_SHORT).show();
                    password.setError("Password is required!");
                    password.requestFocus();
                }
                else if (txt_password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password should be at least 6 digits!", Toast.LENGTH_SHORT).show();
                    password.setError("Password too short!");
                    password.requestFocus();
                } else {
                    registerUser(txt_fullname, txt_email, txt_password);
                }
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
    private void registerUser(String textFullName, String textEmail, String textPassword) {
        auth.createUserWithEmailAndPassword(textEmail, textPassword)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            // Enter user data into the Firebase Realtime Database
                            ReadwriteUserDetails writeUserDetails = new ReadwriteUserDetails(textFullName, textEmail, textPassword);
                            // Extracting user reference from DB for table "User"
                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("User");
                            referenceProfile.child(user.getUid()).setValue(writeUserDetails)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                // Send email to verify
                                                user.sendEmailVerification();
                                                Toast.makeText(RegisterActivity.this, "Registering user successful!", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(RegisterActivity.this, Splash1Activity.class);
                                                // To stop user from returning back to RegisterActivity on pressing back button after registration
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else {
                                                Toast.makeText(RegisterActivity.this, "Registering user failed!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                email.setError("Your email is invalid or already in use. Kindly re-enter!");
                                email.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                email.setError("User is already registered with this email. Use another email!");
                                email.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }


}
