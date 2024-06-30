package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_finder_app.Class.AdoptOrder;
import com.example.pet_finder_app.Class.AdoptPet;
import com.example.pet_finder_app.Class.Pet;
import com.example.pet_finder_app.Class.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyShopActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ConstraintLayout historyView,adoptStatus, appointment, success, cancelled;
    Toolbar arrowBack;
    List<Pet> petList = new ArrayList<>();
    List<AdoptPet> adoptPets = new ArrayList<>();
    List<AdoptOrder> adoptOrders = new ArrayList<>();
    List<User> users = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private int countRequest = 0;
    private int countAppointment = 0;
    private int countSuccess = 0;
    private int countCancelled = 0;
    private TextView request_value, appointment_value, success_value, cancelled_value;
    String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.my_shop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.myshop), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ConstraintLayout myPet = findViewById(R.id.see_all_view);
        myPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyShopActivity.this, MyPetActivity.class));
            }
        });
        arrowBack = findViewById(R.id.toolbarArrowBack);
        historyView = findViewById(R.id.history_view);
        adoptStatus = findViewById(R.id.request);
        appointment = findViewById(R.id.appointment);
        success = findViewById(R.id.success);
        cancelled = findViewById(R.id.cancelled);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
        adoptStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdoptStatusActivity.class);
                intent.putExtra("TAB_POSITION", 0);
                startActivity(intent);
            }
        });
        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdoptStatusActivity.class);
                intent.putExtra("TAB_POSITION", 1);
                startActivity(intent);
            }
        });
        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdoptStatusActivity.class);
                intent.putExtra("TAB_POSITION", 2);
                startActivity(intent);
            }
        });
        cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdoptStatusActivity.class);
                intent.putExtra("TAB_POSITION", 3);
                startActivity(intent);
            }
        });

        historyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HistoryAdoptActivity.class));
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        appointment_value = findViewById(R.id.appointment_value);
        success_value = findViewById(R.id.success_value);
        cancelled_value = findViewById(R.id.cancelled_value);
        request_value = findViewById(R.id.request_value);
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

        idUser = currentUser.getUid();


    }

    private void populateRecyclerView() {
        List<AdoptingCategoryDomain> ShopList = new ArrayList<AdoptingCategoryDomain>();
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
            if(adoptPet != null && pet.getPostUserId().equals(idUser)){
                ShopList.add(new AdoptingCategoryDomain(
                        pet.getIdPet(),
                        pet.getImgUrl().get(0),
                        pet.getName(),
                        adoptPet.getPrice(),
                        pet.getGender(),
                        pet.getCategoryId(),
                        pet.getAge(),
                        adoptPet.getStatus(),
                        pet.getPostUserId()
                ));
                AdoptOrder adoptOrder = orderHash.get(pet.getIdPet());
                if(adoptOrder != null){
                    if(adoptOrder.getStatus().equals("Pretending")){
                        countRequest += 1;
                    }else if (adoptOrder.getStatus().equals("Accept")){
                        countAppointment += 1;
                    }else if (adoptOrder.getStatus().equals("Completed")){
                        countSuccess += 1;
                    }else {
                        countCancelled += 1;
                    }
                }
                }
        }
        request_value.setText(Integer.toString(countRequest));
        appointment_value.setText(Integer.toString(countAppointment));
        success_value.setText(Integer.toString(countSuccess));
        cancelled_value.setText(Integer.toString(countCancelled));
        recyclerView = findViewById(R.id.myShopView);
        AdoptingCategoryAdapter shopAdapter = new AdoptingCategoryAdapter(ShopList, R.layout.my_shop_item, "Adopt");
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 5,5));
        recyclerView.setAdapter(shopAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
    }
}
