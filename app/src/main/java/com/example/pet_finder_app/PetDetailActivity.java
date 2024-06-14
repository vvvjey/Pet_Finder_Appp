package com.example.pet_finder_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class    PetDetailActivity extends AppCompatActivity {

    Toolbar arrowBack;
    private ImageView petImageView;
    private TextView petNameTextView;
    private TextView petAgeTextView;
    private TextView petSizeTextView;
    private TextView petBreedTextView;
    private ImageView petGenderImageView;
    private TextView petColorTextView;
    private TextView petMissingDateTextView;
    private TextView petTypeMissingTextView;
    private TextView postUserIdTextView;
    private TextView requestPosterTextView;
    private TextView descriptionTextView;
    private TextView addressMissingTextView;
    private TextView statusMissingTextView;
    private Button chatBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pet_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pet_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        petImageView = findViewById(R.id.imagePetDetailMissing);
        petNameTextView = findViewById(R.id.namePetDetailMissing);
        petAgeTextView = findViewById(R.id.agePetDetailMissing);
        petSizeTextView = findViewById(R.id.sizePetDetailMissing);
        petBreedTextView = findViewById(R.id.breedPetDetailMissing);
        petGenderImageView = findViewById(R.id.genderPetDetailMissing);
        petColorTextView = findViewById(R.id.colorPetDetailMissing);
        petMissingDateTextView = findViewById(R.id.postTimeDetailMissing);
        petTypeMissingTextView = findViewById(R.id.typeMissingPetDetailMissing);
        requestPosterTextView = findViewById(R.id.requestPosterPetDetailMissing);
        descriptionTextView = findViewById(R.id.descriptionPetDetailMissing);
        addressMissingTextView = findViewById(R.id.addressPetDetailMissing);
        statusMissingTextView = findViewById(R.id.petStatusDetailMissing);
        chatBtn = findViewById(R.id.chatBtn);
//        Take data
        Intent intent = getIntent();
        String idPet = intent.getStringExtra("idPet");
        String petName = intent.getStringExtra("petName");
        String petAge = intent.getStringExtra("petAge");
        String petSize = intent.getStringExtra("petSize");
        String petBreed = intent.getStringExtra("petBreed");
        String addressMissing = intent.getStringExtra("addressMissing");
        String petGender = intent.getStringExtra("petGender");
        String petColor = intent.getStringExtra("petColor");
        String petRegisterDate = intent.getStringExtra("petRegisterDate");
        String petMissingDate = intent.getStringExtra("petMissingDate");
        String petTypeMissing = intent.getStringExtra("petTypeMissing");
        String petImageUrl = intent.getStringExtra("petImageUrl");
        String requestPoster = intent.getStringExtra("requestPoster");
        String description = intent.getStringExtra("desciptionPet");
        String statusMissing = intent.getStringExtra("statusMissing");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String postUserId = intent.getStringExtra("postUserId");


        petNameTextView.setText(petName);
        petAgeTextView.setText(petAge);
        petSizeTextView.setText(petSize);
        petBreedTextView.setText(petBreed);
        if(petGender.equals("Male")){
            petGenderImageView.setImageResource(R.drawable.male);
        } else {
            petGenderImageView.setImageResource(R.drawable.female);
        }
        petColorTextView.setText(petColor);
        petMissingDateTextView.setText(petMissingDate);
        petTypeMissingTextView.setText(petTypeMissing);
        requestPosterTextView.setText(requestPoster);
        descriptionTextView.setText(description);
        addressMissingTextView.setText(addressMissing);
        Picasso.get().load(petImageUrl).into(petImageView);
        statusMissingTextView.setText(statusMissing);

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentUserId = currentUser.getUid();
                List<String> userIds = Arrays.asList(currentUserId, postUserId);
                Intent intent = new Intent(getApplicationContext(), ChatPageChatBoxActivity.class);
                intent.putExtra("userIds", userIds.toArray(new String[0]));
                startActivity(intent);
            }
        });

    }
}
