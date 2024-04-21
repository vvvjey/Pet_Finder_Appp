package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdoptActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Toolbar arrowBack;
    Button detailPet;
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
                startActivity(new Intent(getApplicationContext(), MyShopActivity.class));
            }
        });

        List<AdoptingCategoryDomain> HistoryAdopt = new ArrayList<AdoptingCategoryDomain>();
        HistoryAdopt.add(new AdoptingCategoryDomain(R.drawable.dog_shop, "Gary", 95, R.drawable.male, "Dog", 4, "20/03/2024", "#3", "Was Castrated"));
        HistoryAdopt.add(new AdoptingCategoryDomain(R.drawable.cat_shop, "Gary", 95, R.drawable.male, "Dog", 4, "20/03/2024", "#2", "Was Castrated"));
        HistoryAdopt.add(new AdoptingCategoryDomain(R.drawable.dog_shop2, "Gary", 95, R.drawable.male, "Dog", 4, "20/03/2024", "#1", "Was Castrated"));
        HistoryAdopt.add(new AdoptingCategoryDomain(R.drawable.dog_shop, "Gary", 95, R.drawable.male, "Dog", 4, "20/03/2024", "#0", "Was Castrated"));
        HistoryAdopt.add(new AdoptingCategoryDomain(R.drawable.cat_shop, "Gary", 95, R.drawable.male, "Dog", 4, "20/03/2024", "#0", "Was Castrated"));

        recyclerView = findViewById(R.id.history_adopt_view);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 40,40));
        AdoptingCategoryAdapter shopAdapter = new AdoptingCategoryAdapter(HistoryAdopt, R.layout.history_adopt_item);
        // call detailButton here
        recyclerView.setAdapter(shopAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
    }
}
