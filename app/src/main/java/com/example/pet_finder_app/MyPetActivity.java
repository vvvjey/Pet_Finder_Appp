package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyPetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

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
                startActivity(new Intent(MyPetActivity.this, AddingPetActivity.class));
            }
        });

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPetActivity.this, MyShopActivity.class));
            }
        });

        List<AdoptingCategoryDomain> PetList = new ArrayList<AdoptingCategoryDomain>();
        PetList.add(new AdoptingCategoryDomain(R.drawable.dog_shop, "Gary", 95, R.drawable.male, "Dog", 4));
        PetList.add(new AdoptingCategoryDomain(R.drawable.cat_shop, "Whitney", 20, R.drawable.female, "Cat", 4));
        PetList.add(new AdoptingCategoryDomain(R.drawable.dog_shop2, "Willie", 120, R.drawable.male, "Dog", 4));
        PetList.add(new AdoptingCategoryDomain(R.drawable.dog_shop, "Gary", 95, R.drawable.male, "Dog", 4));
        PetList.add(new AdoptingCategoryDomain(R.drawable.cat_shop, "Whitney", 20, R.drawable.female, "Cat", 4));
        PetList.add(new AdoptingCategoryDomain(R.drawable.dog_shop2, "Willie", 120, R.drawable.male, "Dog", 4));

        recyclerView = findViewById(R.id.mypet_view);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 40,40));
        AdoptingCategoryAdapter shopAdapter = new AdoptingCategoryAdapter(PetList, R.layout.my_pet_item);
        recyclerView.setAdapter(shopAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
    }
}
