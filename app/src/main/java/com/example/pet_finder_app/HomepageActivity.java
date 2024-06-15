package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_finder_app.Class.AdoptOrder;
import com.example.pet_finder_app.Class.AdoptPet;
import com.example.pet_finder_app.Class.MissingPet;
import com.example.pet_finder_app.Class.Pet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HomepageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ImageView icon_chat;
    TextView seeAllAdopt,seeAllMissing, AdoptCount, MissingCount;
    ImageView icon_user;
    List<Pet> petList = new ArrayList<>();
    List<AdoptPet> adoptPets = new ArrayList<>();
    List<MissingPet> missingPets = new ArrayList<>();
    private static final int REQUEST_NOTIFICATION = 1;
    private int countAdopt = 0;
    private int countCompleted = 0;
    private int countMissing = 0;
    Toolbar arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.homepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homepage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        NAVIGATION
        icon_user = findViewById(R.id.imageView5);
        icon_chat = findViewById(R.id.imageView3);
        seeAllAdopt = findViewById(R.id.seeAllAdopt);
        seeAllMissing = findViewById(R.id.seeAllMissing);
        LinearLayout homeSection = findViewById(R.id.home_sec);
        LinearLayout missingSection = findViewById(R.id.missing_sec);
        LinearLayout adoptSection = findViewById(R.id.adopt_sec);
        LinearLayout rescueSection = findViewById(R.id.rescue_sec);
        LinearLayout profileSection = findViewById(R.id.profile_sec);
        ImageView notifiImg = findViewById(R.id.notification_homepage);
        ImageView chatBot = findViewById(R.id.faq);
        seeAllAdopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdoptingPetActivity.class));
            }
        });
        seeAllMissing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchingLostPetActivity.class));
            }
        });
        missingSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchingLostPetActivity.class));
            }
        });
        adoptSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdoptingPetActivity.class));
            }
        });
        rescueSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RescueCategoryActivity.class));
            }
        });
        profileSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        notifiImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, NotificationActivity.class));
            }
        });
        icon_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, ProfileActivity.class));
            }
        });
        icon_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, ChatPageActivity.class));
            }
        });
        //CHATBOT
        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the chat bot activity or show a chat bot dialog
                Intent intent = new Intent(HomepageActivity.this, ChatBotActivity.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    Pet pet = snap.getValue(Pet.class);
                    petList.add(pet);
                }
                populateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("AdoptPet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    AdoptPet adoptPet = snap.getValue(AdoptPet.class);
                    adoptPets.add(adoptPet);
                    countAdopt += 1;
                }
                Log.d("ADOPT_PETS_LIST", "Adopt Pets List: " + adoptPets);
                populateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("AdoptOrder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    AdoptOrder adoptOrder = snap.getValue(AdoptOrder.class);
                    if(adoptOrder.getStatus().equals("Completed")){
                        countCompleted += 1;
                    }
                    populateRecyclerView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("Missing pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    MissingPet missingPet = snap.getValue(MissingPet.class);
                    missingPets.add(missingPet);
                    countMissing += 1;
                }
                populateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void populateRecyclerView() {
        List<AdoptingCategoryDomain> petItems = new ArrayList<>();
        List<AdoptingCategoryDomain> missingItems = new ArrayList<>();
        HashMap<String, AdoptPet> hashMap = new HashMap<>();
        for(AdoptPet adoptPet : adoptPets){
            hashMap.put(adoptPet.getIdPet(), adoptPet);
        }
        for(Pet pet : petList){
            AdoptPet adoptPet = hashMap.get(pet.getIdPet());
            if(adoptPet != null){
                petItems.add(new AdoptingCategoryDomain(
                        pet.getImgUrl().get(0),
                        pet.getName(),
                        adoptPet.getPrice(),
                        pet.getGender(),
                        pet.getBreed(),
                        pet.getAge(),
                        pet.getPostUserId(),
                        pet.getIdPet()
                ));
            }
        }
        for(MissingPet missingPet : missingPets){
            missingItems.add(new AdoptingCategoryDomain(
                    missingPet.getImgUrl().get(0),
                    missingPet.getName(),
                    missingPet.getGender(),
                    missingPet.getBreed(),
                    missingPet.getAge(),
                    missingPet.getPostUserId(),
                    missingPet.getIdPet()
            ));
        }
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(currentTime);
        TextView date = findViewById(R.id.date);
        TextView CompletedCount = findViewById(R.id.rescued_value);
        CompletedCount.setText(Integer.toString(countCompleted));
        date.setText(formattedDate);
        AdoptCount = findViewById(R.id.adopt_value);
        MissingCount = findViewById(R.id.missing_value);
        AdoptCount.setText(Integer.toString(countAdopt));
        MissingCount.setText(Integer.toString(countMissing));
        recyclerView = findViewById(R.id.adoptCategoryView);
        AdoptingCategoryAdapter petAdapter = new AdoptingCategoryAdapter(petItems, R.layout.adopting_pet_category, "Adopt");
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 5,5));
        recyclerView.setAdapter(petAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));

        recyclerView = findViewById(R.id.missingCategoryView);
        AdoptingCategoryAdapter missingAdapter = new AdoptingCategoryAdapter(missingItems, R.layout.adopting_pet_category, "Missing");
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 5,5));
        recyclerView.setAdapter(missingAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
    }
}
