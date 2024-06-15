package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.pet_finder_app.Class.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdoptingPetDetailActivity extends AppCompatActivity {
    Toolbar arrowBack;
    String idPet;
    Pet pet;
    AdoptPet adopt;
    User user;
    Button adoptBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pet_adopt_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adopt_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdoptingPetActivity.class));
            }
        });
        TextView petAge = findViewById(R.id.agePetDetailAdopt);
        TextView petBreed = findViewById(R.id.breedPetDetailAdopt);
        TextView registerDay = findViewById(R.id.postTimeDetailAdopt);
        TextView petSize = findViewById(R.id.sizePetDetailAdopt);
        ImageView imageView = findViewById(R.id.imagePetDetailAdopt);
        TextView petName = findViewById(R.id.namePetDetailAdopt);
        TextView petPrice = findViewById(R.id.petPrice);
        ImageView petGender = findViewById(R.id.genderPetDetailAdopt);
        TextView petColor = findViewById(R.id.colorPetDetailAdopt);
        TextView petWeight = findViewById(R.id.weightPetDetailAdopt);

        TextView nameUser = findViewById(R.id.namePosterPetDetailAdopt);
        TextView phoneNumber = findViewById(R.id.phonePosterPetDetailAdopt);
        TextView emailAddress = findViewById(R.id.emailPosterPetDetailAdopt);


        idPet = getIntent().getStringExtra("idPet");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();


        databaseReference.child("AdoptPet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    if(idPet.equals(snap.getValue(AdoptPet.class).getIdPet())) {
                        adopt = snap.getValue(AdoptPet.class);
                        petPrice.setText(adopt.getPrice());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    if(idPet.equals(snap.getValue(Pet.class).getIdPet())) {
                        pet = snap.getValue(Pet.class);
                        Picasso.get().load(pet.getImgUrl().get(0)).into(imageView);
                        petAge.setText(pet.getAge());
                        petBreed.setText(pet.getBreed());
                        registerDay.setText(pet.getRegisterDate());
                        petSize.setText(pet.getSize());
                        petName.setText(pet.getName());
                        Picasso.get().load(pet.getGender()).into(petGender);
                        petColor.setText(pet.getColor());
                        petWeight.setText(pet.getWeight());

                        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snap: snapshot.getChildren()){
                                    if(pet.getPostUserId().equals(snap.getValue(User.class).getUserId())) {
                                        user = snap.getValue(User.class);
                                        nameUser.setText(user.getName());
                                        phoneNumber.setText(user.getPhoneNumber());
                                        emailAddress.setText(user.getEmail());
                                        break;
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        TextView typeAdopt = findViewById(R.id.typeAdopt);
        typeAdopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OwnerpageActivity.class);
                startActivity(intent);
            }
        });

        adoptBtn = findViewById(R.id.fillInAdopt);
        adoptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FillInforToAdoptActivity.class);
                intent.putExtra("idPet", pet.getIdPet());
                intent.putExtra("namePet", pet.getName());
                startActivity(intent);
            }
        });
    }
}
