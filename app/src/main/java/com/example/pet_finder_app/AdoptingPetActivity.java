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

public class AdoptingPetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Toolbar arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.adopting_pet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pet), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<AdoptingPetItem> petList = new ArrayList<AdoptingPetItem>();
        petList.add(new AdoptingPetItem(R.drawable.cat1, "Yellow cat", "Was Castrated", "Yellow, White, Black", "08-09-2023", R.drawable.female, 6));
        petList.add(new AdoptingPetItem(R.drawable.cat2, "White cat", "Was Castrated", "White, Blue eyes", "08-09-2023", R.drawable.male, 6));
        petList.add(new AdoptingPetItem(R.drawable.cat1, "Yellow cat", "Was Castrated", "Yellow, White, Black", "08-09-2023", R.drawable.female, 6));
        petList.add(new AdoptingPetItem(R.drawable.cat1, "Yellow cat", "Was Castrated", "Yellow, White, Black", "08-09-2023", R.drawable.male, 6));
        petList.add(new AdoptingPetItem(R.drawable.cat1, "Yellow cat", "Was Castrated", "Yellow, White, Black", "08-09-2023", R.drawable.female, 6));
        petList.add(new AdoptingPetItem(R.drawable.cat1, "Yellow cat", "Was Castrated", "Yellow, White, Black", "08-09-2023", R.drawable.male, 6));
        petList.add(new AdoptingPetItem(R.drawable.cat1, "Yellow cat", "Was Castrated", "Yellow, White, Black", "08-09-2023", R.drawable.female, 6));
        petList.add(new AdoptingPetItem(R.drawable.cat1, "Yellow cat", "Was Castrated", "Yellow, White, Black", "08-09-2023", R.drawable.male, 6));
        petList.add(new AdoptingPetItem(R.drawable.cat1, "Yellow cat", "Was Castrated", "Yellow, White, Black", "08-09-2023", R.drawable.female, 6));

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomepageActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recycleView);
        AdoptingPetAdapter petAdapter = new AdoptingPetAdapter(petList);
        recyclerView.setAdapter(petAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }
}
