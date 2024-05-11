package com.example.pet_finder_app;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pet_finder_app.Class.AdoptPet;
import com.example.pet_finder_app.Class.Pet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class AdoptingPetDetailActivity extends AppCompatActivity {
    Toolbar arrowBack;
    ImageView back_btn;
    String idPet;
    Pet pet;
    AdoptPet adopt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.adopt_pet_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adoptDetail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdoptingPetActivity.class));
            }
        });
        ImageView imageView = findViewById(R.id.image_view);
        TextView petName = findViewById(R.id.pet_name);
        TextView petLocation = findViewById(R.id.location);
        TextView petPrice = findViewById(R.id.price);
        TextView petGender = findViewById(R.id.pet_sex);
        TextView petColor = findViewById(R.id.pet_color);
        TextView petCategory = findViewById(R.id.pet_breed);
        TextView petWeight = findViewById(R.id.pet_weight);

        idPet = getIntent().getStringExtra("idPet");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    if(idPet.equals(snap.getValue(Pet.class).getIdPet())) {
                        pet = snap.getValue(Pet.class);
                        Picasso.get().load(pet.getImgUrl()).into(imageView);
                        petName.setText(pet.getName());
                        petGender.setText(pet.getGender());
                        petColor.setText(pet.getColor());
                        petCategory.setText(pet.getCategoryId());
                        petWeight.setText(pet.getWeight());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("AdoptPet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    if(idPet.equals(snap.getValue(AdoptPet.class).getIdPet())) {
                        adopt = snap.getValue(AdoptPet.class);
                        petLocation.setText(adopt.getAddressAdopting());
                        petPrice.setText(adopt.getPrice());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
