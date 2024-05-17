package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class RescueCategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Toolbar arrowBack;
    ConstraintLayout gomap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.rescue_pet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rescue_pet), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomepageActivity.class));
            }
        });

        List<RescueCategoryDomain> RescueList = new ArrayList<RescueCategoryDomain>();
        RescueList.add(new RescueCategoryDomain(R.drawable.rescue_station1, "SaiGon Pet Adoption","Quan 9, TPHCM", "0.4 kms"));
        RescueList.add(new RescueCategoryDomain(R.drawable.rescue_station1, "SaiGon Pet Adoption","Quan 9, TPHCM", "0.4 kms"));
        RescueList.add(new RescueCategoryDomain(R.drawable.rescue_station1, "SaiGon Pet Adoption","Quan 9, TPHCM", "0.4 kms"));
        RescueList.add(new RescueCategoryDomain(R.drawable.rescue_station1, "SaiGon Pet Adoption","Quan 9, TPHCM", "0.4 kms"));
        RescueList.add(new RescueCategoryDomain(R.drawable.rescue_station1, "SaiGon Pet Adoption","Quan 9, TPHCM", "0.4 kms"));

        gomap = findViewById(R.id.gomap);

        gomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RescueCategoryActivity.this, MapActivity.class));
            }
        });

        recyclerView = findViewById(R.id.rescue_view);
        RescueCategoryAdapter rescueAdapter = new RescueCategoryAdapter(RescueList,this);
        recyclerView.setAdapter(rescueAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));

    }
}
