package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.pet_finder_app.Class.MissingPet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class    PetDetailActivity extends AppCompatActivity {

    ImageView arrowBack;
    private ImageView petImageView;
    private TextView petNameTextView;
    private TextView petAgeTextView;
    FirebaseDatabase firebaseDatabase;
    MissingPet pet;
    DatabaseReference databaseReference;
    private TextView petSizeTextView;
    private TextView petBreedTextView;
    ImageSlider image_slider;
    private ImageView petGenderImageView;
    private TextView petColorTextView;
    private TextView petMissingDateTextView;
    private TextView petTypeMissingTextView;
    private TextView postUserIdTextView;
    private TextView requestPosterTextView;
    private TextView descriptionTextView;
    private TextView addressMissingTextView;
    private TextView statusMissingTextView;
    private Button chatBtn,contactBtn;
    String petName;
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
//        ImageView imageView = findViewById(R.id.image_view_pet);
        petNameTextView = findViewById(R.id.namePetDetailMissing);
        petAgeTextView = findViewById(R.id.agePetDetailMissing);
        petSizeTextView = findViewById(R.id.sizePetDetailMissing);
        petBreedTextView = findViewById(R.id.breedPetDetailMissing);
        petGenderImageView = findViewById(R.id.genderPetDetailMissing);
        petColorTextView = findViewById(R.id.colorPetDetailMissing);
//        petMissingDateTextView = findViewById(R.id.postTimeDetailMissing);
        petTypeMissingTextView = findViewById(R.id.typeMissingPetDetailMissing);
        requestPosterTextView = findViewById(R.id.requestPosterPetDetailMissing);
        descriptionTextView = findViewById(R.id.descriptionPetDetailMissing);
        addressMissingTextView = findViewById(R.id.addressPetDetailMissing);
//        statusMissingTextView = findViewById(R.id.petStatusDetailMissing);
        chatBtn = findViewById(R.id.chatBtn);
        contactBtn = findViewById(R.id.contactBtn);
        image_slider = findViewById(R.id.image_view_pet);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
//        Take data
        Intent intent = getIntent();
        String idPet = intent.getStringExtra("idPet");
        Log.d("Image URL", idPet);
        petName = intent.getStringExtra("petName");
        String petAge = intent.getStringExtra("petAge");
        String petSize = intent.getStringExtra("petSize");
        String petBreed = intent.getStringExtra("petBreed");
        String addressMissing = intent.getStringExtra("addressMissing");
        String petGender = intent.getStringExtra("petGender");
        String petColor = intent.getStringExtra("petColor");
        String petRegisterDate = intent.getStringExtra("petRegisterDate");
        String petMissingDate = intent.getStringExtra("petMissingDate");
        String petTypeMissing = intent.getStringExtra("petTypeMissing");
//        String petImageUrl = intent.getStringExtra("petImageUrl");
        String requestPoster = intent.getStringExtra("requestPoster");
        String description = intent.getStringExtra("desciptionPet");
        String statusMissing = intent.getStringExtra("statusMissing");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String postUserId = intent.getStringExtra("postUserId");

//        Log.d("Request" , requestPoster);

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
//        petMissingDateTextView.setText(petMissingDate);
        petTypeMissingTextView.setText(petTypeMissing);
        requestPosterTextView.setText(requestPoster);
        descriptionTextView.setText(description);
        addressMissingTextView.setText(addressMissing);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Missing pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    if(idPet.equals(snap.getValue(MissingPet.class).getIdPet())){
                        pet = snap.getValue(MissingPet.class);
                        for (int i = 0; i < pet.getImgUrl().size(); i++){
                            String imageUrl = pet.getImgUrl().get(i);
                            Log.d("Image URL", imageUrl);
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                slideModels.add(new SlideModel(imageUrl, ScaleTypes.CENTER_CROP));
                            } else {
                            }
                        }
                        image_slider.setImageList(slideModels, ScaleTypes.CENTER_CROP);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        arrowBack = findViewById(R.id.back_btn);
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
        contactBtn.setOnClickListener(new View.OnClickListener() {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference notificationsRef = database.getReference("Notification");
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM d 'at' h:mm a", Locale.getDefault());
                String formattedDate = sdf.format(new Date());
                // Create a new contact record
                Map<String, Object> contact = new HashMap<>();
                contact.put("fromUserId", currentUser.getUid());
                contact.put("toUserId", postUserId);
                contact.put("notifi_descrip", "You have a missing notification about " + getIntent().getStringExtra("petName") + " post");
                contact.put("notifi_time", formattedDate);
                contact.put("notifi_type", "Missing");



                // Add a new record
                notificationsRef.push().setValue(contact)
                        .addOnSuccessListener(aVoid -> {
                            Log.d("PetDetailActivity", "Notification record added successfully.");
                            // You can show a success message or perform other actions
                        })
                        .addOnFailureListener(e -> {
                            Log.w("PetDetailActivity", "Error adding notification record", e);
                            // You can show an error message or perform other actions
                        });
            }
        });
    }

}
