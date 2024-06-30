package com.example.pet_finder_app.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_finder_app.AdoptStatusItem;
import com.example.pet_finder_app.Class.AdoptOrder;
import com.example.pet_finder_app.Class.AdoptPet;
import com.example.pet_finder_app.Class.Pet;
import com.example.pet_finder_app.HistoryAdapter;
import com.example.pet_finder_app.R;
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

public class SuccessRequest extends Fragment {

    private RecyclerView recyclerView;

    private Toolbar arrowBack;
    private boolean clicked;
    private ImageView filterAdopt;
    List<Pet> petList = new ArrayList<>();
    List<AdoptPet> adoptPets = new ArrayList<>();
    List<AdoptOrder> adoptOrders = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    String idUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_adopt, container, false);
        recyclerView = view.findViewById(R.id.recycle_adopt_view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        idUser = currentUser.getUid();

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
                        if(orderPet.getStatus().equals("Completed")){
                            petItems.add(new AdoptStatusItem(
                                    pet.getImgUrl().get(0),
                                    pet.getName(),
                                    "Medium",
                                    "Alaska",
                                    pet.getColor(),
                                    "Completed",
                                    pet.getIdPet(),
                                    orderPet.getIdOrder(),
                                    adoptPet.getId()
                            ));
                        }
                    }
                } else {
                    Log.d("No Orders Found", "No orders found for PetID: " + adoptPet.getIdPet());
                }
            }
        }


        HistoryAdapter petAdapter = new HistoryAdapter(petItems, getContext());
        recyclerView.setAdapter(petAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

}
