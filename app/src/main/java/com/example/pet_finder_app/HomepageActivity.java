package com.example.pet_finder_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
//        NAVIGATION
        LinearLayout homeSection = findViewById(R.id.home_sec);
        LinearLayout missingSection = findViewById(R.id.missing_sec);
        LinearLayout adoptSection = findViewById(R.id.adopt_sec);
        LinearLayout rescueSection = findViewById(R.id.rescue_sec);
        LinearLayout profileSection = findViewById(R.id.profile_sec);
        ImageView notifiImg = findViewById(R.id.notification_homepage);
        missingSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchingLostPetActivity.class));
            }
        });
        adoptSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdoptingPetActivity.class));
            }
        });
        rescueSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RescueCategoryActivity.class));
            }
        });
        profileSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        notifiImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
            }
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
