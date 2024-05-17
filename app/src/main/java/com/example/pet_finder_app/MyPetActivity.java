package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class MyPetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<Pet> petList = new ArrayList<>();
    List<AdoptPet> adoptPets = new ArrayList<>();
    List<AdoptOrder> adoptOrders = new ArrayList<>();
    List<User> users = new ArrayList<>();
    Button addNewPet;
    Toolbar arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.my_pet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mypet), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addNewPet = findViewById(R.id.btn_add_pet);
        arrowBack = findViewById(R.id.toolbarArrowBack);

        addNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPetActivity.this, AddingPetActivity.class);
                intent.putExtra("activity", "add");
                startActivity(intent);
            }
        });

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPetActivity.this, MyShopActivity.class);
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
                }
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

    }

    private void populateRecyclerView() {
        List<AdoptingCategoryDomain> PetList = new ArrayList<AdoptingCategoryDomain>();
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
                    Log.d("check", "orderPet");
                    User user = userHash.get(orderPet.getUserId());
                    if(user != null){
                        PetList.add(new AdoptingCategoryDomain(
                                pet.getIdPet(),
                                pet.getImgUrl(),
                                pet.getName(),
                                adoptPet.getPrice(),
                                pet.getGender(),
                                pet.getCategoryId(),
                                pet.getAge(),
                                adoptPet.getStatus()
                        ));
                    }
                }
            }

        }

        recyclerView = findViewById(R.id.mypet_view);
        MyPetAdapter petAdapter = new MyPetAdapter(MyPetActivity.this, PetList);
        recyclerView.setAdapter(petAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
    }
}
