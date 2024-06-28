package com.example.pet_finder_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import com.example.pet_finder_app.Class.FavoritePet;
import com.example.pet_finder_app.Class.Pet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoritePetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView imageView;
    List<Pet> petList = new ArrayList<>();
    List<String> listIdFavorite = new ArrayList<>();
    String idUser = "1";
    Toolbar arrowBack;

    private boolean isGrid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.favorite);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.favorite), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("FavoritePet").child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FavoritePet favoritePet = snapshot.getValue(FavoritePet.class);
                if (favoritePet != null) {
                    listIdFavorite = favoritePet.getFavoritePet();
                    fetchFavoritePets(databaseReference);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void fetchFavoritePets(DatabaseReference databaseReference) {
        databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                petList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Pet pet = snap.getValue(Pet.class);
                    if (pet != null && listIdFavorite.contains(pet.getIdPet())) {
                        petList.add(pet);
                        Log.d("Show FavoriteidPet", "Yes you are in" + pet.getIdPet());
                    }
                }
                populateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void populateRecyclerView() {
        List<AdoptingCategoryDomain> FavoriteList = new ArrayList<>();
        for (Pet pet : petList) {
            Log.d("Show FavoriteidPet", "Yes you are in2" + pet.getIdPet());
            FavoriteList.add(new AdoptingCategoryDomain(
                    pet.getImgUrl().get(0),
                    pet.getName(),
                    pet.getGender(),
                    pet.getBreed(),
                    pet.getAge(),
                    pet.getPostUserId(),
                    pet.getIdPet()
            ));
        }

        recyclerView = findViewById(R.id.favoriteView);
//        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 40, 40));
        AdoptingCategoryAdapter favoriteAdapter = new AdoptingCategoryAdapter(FavoriteList, R.layout.favorite_item, "Adopt");
        recyclerView.setAdapter(favoriteAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(FavoritePetActivity.this, 2));

        imageView = findViewById(R.id.grid_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGrid) {
                    recyclerView.setLayoutManager(new GridLayoutManager(FavoritePetActivity.this, 1, RecyclerView.VERTICAL, false));
                    AdoptingCategoryAdapter horizontalAdapter = new AdoptingCategoryAdapter(FavoriteList, R.layout.favorite_item_horz, "Adopt");
                    recyclerView.setAdapter(horizontalAdapter);
                    imageView.setImageResource(R.drawable.list_view);
                    isGrid = false;
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(FavoritePetActivity.this, 2, RecyclerView.VERTICAL, false));
                    AdoptingCategoryAdapter horizontalAdapter = new AdoptingCategoryAdapter(FavoriteList, R.layout.favorite_item, "Adopt");
                    recyclerView.setAdapter(horizontalAdapter);
                    imageView.setImageResource(R.drawable.grid_black);
                    isGrid = true;
                }

            }
        });
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
