package com.example.pet_finder_app.fragments;

import android.os.Bundle;
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

import com.example.pet_finder_app.AdoptingCategoryAdapter;
import com.example.pet_finder_app.AdoptingCategoryDomain;
import com.example.pet_finder_app.Class.AdoptOrder;
import com.example.pet_finder_app.Class.AdoptPet;
import com.example.pet_finder_app.Class.Pet;
import com.example.pet_finder_app.R;
import com.example.pet_finder_app.SpaceItemDecoration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OwnerPet extends Fragment {
    private static final String ARG_USER_ID = "idUserPost";
    private String idUserPost;
    private RecyclerView recyclerView;

    private Toolbar arrowBack;
    private boolean clicked;
    private ImageView filterAdopt;
    List<Pet> petList = new ArrayList<>();

    List<AdoptPet> adoptPets = new ArrayList<>();
    List<AdoptOrder> adoptOrders = new ArrayList<>();
    String idUser;

    public static OwnerPet newInstance(String idUserPost) {
        OwnerPet fragment = new OwnerPet();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, idUserPost);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_adopt, container, false);
        recyclerView = view.findViewById(R.id.recycle_appointment_view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idUserPost = getArguments().getString(ARG_USER_ID);
//            Log.d("Show userPostId", idUserPost);
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
//        idUser = "1";

        databaseReference.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    Pet pet = snap.getValue(Pet.class);
                    assert pet != null;
                    if(pet.getPostUserId().equals(idUserPost)){
                        petList.add(pet);
                    }
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
    }

    private void populateRecyclerView() {
        List<AdoptingCategoryDomain> ShopList = new ArrayList<AdoptingCategoryDomain>();
        HashMap<String, AdoptPet> adoptHash = new HashMap<>();
        for(AdoptPet adoptPet : adoptPets){
            adoptHash.put(adoptPet.getIdPet(), adoptPet);
        }
        for(Pet pet : petList){
            AdoptPet adoptPet = adoptHash.get(pet.getIdPet());
            if(adoptPet != null && pet.getPostUserId().equals(idUserPost)){
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
            }
        }
        AdoptingCategoryAdapter shopAdapter = new AdoptingCategoryAdapter(ShopList, R.layout.onwer_pet_item, "Adopt");
        recyclerView.addItemDecoration(new SpaceItemDecoration(10, 10, 0,0));
        recyclerView.setAdapter(shopAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    }

}
