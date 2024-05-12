package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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
import com.example.pet_finder_app.Class.Pet;
import com.example.pet_finder_app.Class.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class AdoptStatusActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private Toolbar arrowBack;
    private boolean clicked;
    private ImageView filterAdopt;
    List<Pet> petList = new ArrayList<>();
    List<AdoptPet> adoptPets = new ArrayList<>();
    List<AdoptOrder> adoptOrders = new ArrayList<>();
    List<User> users = new ArrayList<>();

    private Animation getFromBottom() {
        return AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
    }

    private Animation getToBottom() {
        return AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
    }

    private Animation getRotateClose() {
        return AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
    }

    private Animation getRotateOpen() {
        return AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adopt_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adopt_status), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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
                }
                populateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("Appointment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    AdoptOrder adoptOrder = snap.getValue(AdoptOrder.class);
                    adoptOrders.add(adoptOrder);
                }
                populateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    User user = snap.getValue(User.class);
                    users.add(user);
                }
                populateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyShopActivity.class));
            }
        });

    }

    private void populateRecyclerView() {
        List<AdoptingPetItem> petItems = new ArrayList<>();
        HashMap<String, AdoptPet> adoptHash = new HashMap<>();
        HashMap<String, AdoptOrder> orderHash = new HashMap<>();
        HashMap<String, User> userHash = new HashMap<>();

        for(AdoptPet adoptPet : adoptPets){
            adoptHash.put(adoptPet.getIdPet(), adoptPet);
        }
        for(AdoptOrder adoptOrder : adoptOrders){
            orderHash.put(adoptOrder.getIdPet(), adoptOrder);
        }
        for(User user : users){
            userHash.put(user.getUserId(), user);
        }

        for(Pet pet : petList){
            AdoptPet adoptPet = adoptHash.get(pet.getIdPet());
            if(adoptPet != null){
                AdoptOrder orderPet = orderHash.get(adoptPet.getIdPet());
                if(orderPet != null){
                    User user = userHash.get(orderPet.getIdPet());
                    if(user != null){
                        petItems.add(new AdoptingPetItem(
                                pet.getAge(),
                                pet.getCategoryId(),
                                pet.getColor(),
                                pet.getGender(),
                                pet.getIdPet(),
                                pet.getImgUrl(),
                                pet.getName(),
                                pet.getRegisterDate(),
                                adoptPet.getStatus()
                        ));
                    }
                }
            }

        }
        recyclerView = findViewById(R.id.recycle_adopt_view);
        AdoptingPetAdapter petAdapter = new AdoptingPetAdapter(petItems, this);
        recyclerView.setAdapter(petAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }
}