package com.example.pet_finder_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class HomepageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Toolbar arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.homepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homepage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<AdoptingCategoryDomain> AdoptingList = new ArrayList<AdoptingCategoryDomain>();
        AdoptingList.add(new AdoptingCategoryDomain(R.drawable.cat_category1, "Samantha", R.drawable.favorate, "Thu Duc ( 2,5 km )", R.drawable.male, 0));
        AdoptingList.add(new AdoptingCategoryDomain(R.drawable.cat_category2, "Tigri", R.drawable.non_favorate, "Thu Duc ( 2,5 km )", R.drawable.female, 0));
        AdoptingList.add(new AdoptingCategoryDomain(R.drawable.cat_category1, "Samantha", R.drawable.favorate, "Thu Duc ( 2,5 km )", R.drawable.male, 0));
        AdoptingList.add(new AdoptingCategoryDomain(R.drawable.cat_category1, "Samantha", R.drawable.non_favorate, "Thu Duc ( 2,5 km )", R.drawable.male, 0));
        AdoptingList.add(new AdoptingCategoryDomain(R.drawable.cat_category2, "Tigri", R.drawable.non_favorate, "Thu Duc ( 2,5 km )", R.drawable.female, 0));

        recyclerView = findViewById(R.id.adoptCategoryView);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 40,40));

        AdoptingCategoryAdapter petAdapter = new AdoptingCategoryAdapter(AdoptingList, R.layout.adopting_pet_category);
        recyclerView.setAdapter(petAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));

        List<AdoptingCategoryDomain> MissingList = new ArrayList<AdoptingCategoryDomain>();
        MissingList.add(new AdoptingCategoryDomain(R.drawable.dog_category1, "Samantha", R.drawable.favorate, "Thu Duc ( 2,5 km )", R.drawable.male, R.drawable.mising));
        MissingList.add(new AdoptingCategoryDomain(R.drawable.dog_category2, "Tigri", R.drawable.non_favorate, "Thu Duc ( 2,5 km )", R.drawable.female, R.drawable.seen));
        MissingList.add(new AdoptingCategoryDomain(R.drawable.dog_category1, "Samantha", R.drawable.favorate, "Thu Duc ( 2,5 km )", R.drawable.male, R.drawable.mising));
        MissingList.add(new AdoptingCategoryDomain(R.drawable.dog_category2, "Samantha", R.drawable.non_favorate, "Thu Duc ( 2,5 km )", R.drawable.male, R.drawable.seen));
        MissingList.add(new AdoptingCategoryDomain(R.drawable.dog_category1, "Tigri", R.drawable.non_favorate, "Thu Duc ( 2,5 km )", R.drawable.female, R.drawable.seen));

        recyclerView = findViewById(R.id.missingCategoryView);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 40,40));
        AdoptingCategoryAdapter missingAdapter = new AdoptingCategoryAdapter(MissingList, R.layout.adopting_pet_category);
        recyclerView.setAdapter(missingAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
    }
}
