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
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.pet_finder_app.Class.AdoptPet;
import com.example.pet_finder_app.Class.Pet;
import com.example.pet_finder_app.Class.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdoptingPetDetailActivity extends AppCompatActivity {
    Toolbar arrowBack;
    ImageView backBtn;
    String idPet;
    Pet pet;
    AdoptPet adopt;
    User user;
    Button adoptBtn;
    ImageSlider image_slider;
    ConstraintLayout owner_info;
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

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdoptingPetActivity.class));
            }
        });
        TextView petAge = findViewById(R.id.age_value);
        TextView petBreed = findViewById(R.id.breed_value);
//        TextView registerDay = findViewById(R.id.postTimeDetailAdopt);
        TextView petSize = findViewById(R.id.size_value);
//        ImageView imageView = findViewById(R.id.image_view);
        TextView petName = findViewById(R.id.pet_name);
        TextView petPrice = findViewById(R.id.price);
        TextView petGender = findViewById(R.id.gender_value);
        TextView petColor = findViewById(R.id.color_value);
        TextView petWeight = findViewById(R.id.weight_value);
        TextView petDescription = findViewById(R.id.pet_describe);

        TextView nameUser = findViewById(R.id.owner_name);
        TextView phoneNumber = findViewById(R.id.user_phone);
        TextView emailAddress = findViewById(R.id.user_email);
        TextView addressUser = findViewById(R.id.user_location);
        ImageView user_img = findViewById(R.id.owner_img);

        image_slider = findViewById(R.id.image_view);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

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
                        for (int i = 0; i < pet.getImgUrl().size(); i++){
                            String imageUrl = pet.getImgUrl().get(i);
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                slideModels.add(new SlideModel(imageUrl, ScaleTypes.CENTER_CROP));
                            } else {

//                                slideModels.add(new SlideModel(imageUrl, ScaleTypes.CENTER_CROP));
                            }
                        }
                        image_slider.setImageList(slideModels, ScaleTypes.CENTER_CROP);
                        petAge.setText(pet.getAge());
                        petBreed.setText(pet.getBreed());
//                        registerDay.setText(pet.getRegisterDate());
                        petSize.setText(pet.getSize());
                        petName.setText(pet.getName());
                        petGender.setText(pet.getGender());
//                        Picasso.get().load(pet.getGender()).into(petGender);
                        petColor.setText(pet.getColor());
                        petWeight.setText(pet.getWeight());
                        petDescription.setText(pet.getDescription());

                        Log.d("ShowIdUserAdD", "Pet: " + pet.getPostUserId());

                        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snap: snapshot.getChildren()){
                                    if(pet.getPostUserId().equals(snap.getValue(User.class).getUserId())) {
                                        user = snap.getValue(User.class);
                                        Log.d("ShowIdUserAdD", user.getUserId());
                                        nameUser.setText(user.getName());
                                        phoneNumber.setText(user.getPhoneNumber());
                                        emailAddress.setText(user.getEmail());
                                        addressUser.setText(user.getAddress());
                                        Picasso.get().load(user.getImgUser()).into(user_img);
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


        owner_info = findViewById(R.id.owner_info);
        owner_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OwnerpageActivity.class);
                intent.putExtra("idUserPost", pet.getPostUserId());
                startActivity(intent);
            }
        });

        adoptBtn = findViewById(R.id.adopt_btn);
        adoptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FillInforToAdoptActivity.class);
                intent.putExtra("idPet", pet.getIdPet());
                intent.putExtra("idPostUser", pet.getPostUserId());
                intent.putExtra("namePet", pet.getName());
                startActivity(intent);
            }
        });
    }
}
