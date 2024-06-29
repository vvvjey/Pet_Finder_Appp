package com.example.pet_finder_app;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pet_finder_app.Class.MissingPet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MissingAnimalsPostActivity extends AppCompatActivity {
    Toolbar arrowBack;
    ListView lv;
    MissingAnimalPostAdapter adapter;
    ArrayList<MissingPet> arrayList;
    TextView missingPostSection, seenPostSection, protectedPostSection;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missing_animal_post);
        missingPostSection = findViewById(R.id.missingPostSection);
        seenPostSection = findViewById(R.id.seenPostSection);
        protectedPostSection = findViewById(R.id.protectedPostSection);

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrayList = new ArrayList<>();
        lv = findViewById(R.id.lvMissingPost);
        adapter = new MissingAnimalPostAdapter(this, R.layout.missing_animal_post_item, arrayList);
        lv.setAdapter(adapter);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        renderMissingPost("Missing");

        missingPostSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renderMissingPost("Missing");
                updateSectionStyle(missingPostSection);
            }
        });

        seenPostSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renderMissingPost("Seen");
                updateSectionStyle(seenPostSection);
            }
        });

        protectedPostSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renderMissingPost("Protected");
                updateSectionStyle(protectedPostSection);
            }
        });
    }

    private void renderMissingPost(String statusMissing) {
        Log.d("MissingPetData", "2");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference missingPetRef = firebaseDatabase.getReference().child("Missing pet");

        missingPetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("MissingPetData", "3");
                arrayList.clear();

                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("MissingPetData", "4");

                    if (snapshot.exists()) {
                        String postUserId = snapshot.child("postUserId").getValue(String.class);

                        // Check if the postUserId matches the current user's UID
                        if (currentUser != null && currentUser.getUid().equals(postUserId)) {
                            String gender = snapshot.child("gender").getValue(String.class);
                            String id = snapshot.child("id").getValue(String.class);
                            String breed = snapshot.child("breed").getValue(String.class);
                            String idPet = snapshot.child("idPet").getValue(String.class);
                            List<String> imageUrl = snapshot.child("imgUrl").getValue(t);
                            String name = snapshot.child("name").getValue(String.class);
                            String registerDate = snapshot.child("registerDate").getValue(String.class);
                            String size = snapshot.child("size").getValue(String.class);
                            String typeId = snapshot.child("typeId").getValue(String.class);
                            String typeMissing = snapshot.child("typeMissing").getValue(String.class);
                            String weight = snapshot.child("weight").getValue(String.class);

                            Log.d("MissingPetData", "Gender: " + gender);
                            Log.d("MissingPetData", "ID: " + id);
                            Log.d("MissingPetData", "Pet ID: " + idPet);
                            Log.d("MissingPetData", "Image URL: " + imageUrl);
                            Log.d("MissingPetData", "Name: " + name);
                            Log.d("MissingPetData", "Registration Date: " + registerDate);
                            Log.d("MissingPetData", "Size: " + size);
                            Log.d("MissingPetData", "Type ID: " + typeId);
                            Log.d("MissingPetData", "Missing Type: " + typeMissing);
                            Log.d("MissingPetData", "Weight: " + weight);

                            if (typeMissing.equals(statusMissing)) {
                                String color = snapshot.child("color").getValue(String.class);
                                String age = snapshot.child("age").getValue(String.class);
                                String description = snapshot.child("description").getValue(String.class);
                                String addressMissing = snapshot.child("addressMissing").getValue(String.class);
                                String status = snapshot.child("status").getValue(String.class);
                                String dateMissing = snapshot.child("dateMissing").getValue(String.class);
                                String requestPoster = snapshot.child("requestPoster").getValue(String.class);

                                // Create a MissingPet object and add it to the list
                                MissingPet pet = new MissingPet(age, breed, "categoryId", color, description, gender, idPet, imageUrl, name, registerDate, size, typeId, weight, id, typeMissing, addressMissing, dateMissing, requestPoster, postUserId, status);
                                arrayList.add(pet);
                            }
                        }
                    } else {
                        Log.d("MissingPetData", "Snapshot does not contain any data");
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("MissingPetData", "Failed to read value.", error.toException());
            }
        });
    }

    private void updateSectionStyle(TextView selectedSection) {
        missingPostSection.setTextSize(20);
        seenPostSection.setTextSize(20);
        protectedPostSection.setTextSize(20);

        missingPostSection.setTypeface(null, Typeface.NORMAL);
        seenPostSection.setTypeface(null, Typeface.NORMAL);
        protectedPostSection.setTypeface(null, Typeface.NORMAL);

        selectedSection.setTextSize(24);
        selectedSection.setTypeface(null, Typeface.BOLD);
    }
}
