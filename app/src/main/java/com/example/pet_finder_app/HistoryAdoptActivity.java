package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryAdoptActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Toolbar arrowBack;
    Button detailPet;
    private boolean clicked;
    private ImageView filterAdopt;
    List<Pet> petList = new ArrayList<>();
    List<AdoptPet> adoptPets = new ArrayList<>();
    List<AdoptOrder> adoptOrders = new ArrayList<>();
    String idUser;
    public interface OnDetailButtonClickListener {
        void onDetailButtonClick(int position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.history_adopt);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.history_adopt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

            }
        });
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        idUser = "1";
        recyclerView = findViewById(R.id.history_adopt_view);

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

    }

    private void populateRecyclerView() {
        List<AdoptStatusItem> petItems = new ArrayList<>();
        HashMap<String, AdoptPet> adoptHash = new HashMap<>();
        HashMap<String, List<AdoptOrder>> orderHash = new HashMap<>();

        for (AdoptPet adoptPet : adoptPets) {
            adoptHash.put(adoptPet.getIdPet(), adoptPet);
        }

        for (AdoptOrder adoptOrder : adoptOrders) {
            if (!orderHash.containsKey(adoptOrder.getIdPet())) {
                orderHash.put(adoptOrder.getIdPet(), new ArrayList<>());
            }
            orderHash.get(adoptOrder.getIdPet()).add(adoptOrder);
        }

        for (Pet pet : petList) {
            AdoptPet adoptPet = adoptHash.get(pet.getIdPet());
            if (adoptPet != null && pet.getPostUserId().equals(idUser) ) {

                List<AdoptOrder> ordersForPet = orderHash.get(adoptPet.getIdPet());

                if (ordersForPet != null && !ordersForPet.isEmpty()) {
                    for (AdoptOrder orderPet : ordersForPet) {
                            petItems.add(new AdoptStatusItem(
                                    pet.getImgUrl(),
                                    pet.getName(),
                                    "Medium",
                                    "Alaska",
                                    pet.getColor(),
                                    orderPet.getStatus(),
                                    pet.getIdPet(),
                                    orderPet.getIdOrder(),
                                    adoptPet.getId()
                            ));
                    }
                } else {
                    Log.d("No Orders Found", "No orders found for PetID: " + adoptPet.getIdPet());
                }
            }
        }
        HistoryAdapter petAdapter = new HistoryAdapter(petItems, HistoryAdoptActivity.this);
        recyclerView.setAdapter(petAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(HistoryAdoptActivity.this, 1));
    }

}
