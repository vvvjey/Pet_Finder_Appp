package com.example.pet_finder_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button btn;
    Button btnAdoptForm;
    Button btnLostPetForm;
    Button btn_login;

    Button btnFavorite;
    Button btn_adopt, btn_homepage, btn_rescue;
    Button btn_filter_missing_pet, btn_filter_adopt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchingLostPetActivity.class));
            }
        });

        btn_adopt = findViewById(R.id.buttonAdopt);
        btn_adopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdoptingPetActivity.class));
            }
        });

        btnAdoptForm = findViewById(R.id.buttonAdoptForm);
        btnAdoptForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FillInforToAdoptActivity.class));
            }
        });
        btnLostPetForm = findViewById(R.id.buttonFormLostPet);
        btnLostPetForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FillInforAboutLostPet.class));
            }
        });

        btn_homepage = findViewById(R.id.buttonHomepage);
        btn_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));

            }
        });

        btn_rescue = findViewById(R.id.buttonRescue);
        btn_rescue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RescueCategoryActivity.class));

            }
        });

        btn_login = findViewById(R.id.login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        btnFavorite = findViewById(R.id.buttonFavorite);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FavoritePetActivity.class));

            }
        });

        btn_filter_missing_pet = findViewById(R.id.buttonFilterMissingPet);
        btn_filter_missing_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FilterMissingPet.class));

            }
        });

        btn_filter_adopt = findViewById(R.id.buttonFilterAdopt);
        btn_filter_adopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FilterAdopt.class));

            }
        });
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

    }
}
