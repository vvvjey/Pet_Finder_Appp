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

import com.example.pet_finder_app.AdoptingCategoryDomain;
import com.example.pet_finder_app.HistoryAdapter;
import com.example.pet_finder_app.MyShopActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdoptActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Toolbar arrowBack;
    Button detailPet;
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
                startActivity(new Intent(getApplicationContext(), MyShopActivity.class));
            }
        });

        List<AdoptingCategoryDomain> historyAdopt = new ArrayList<>();
        historyAdopt.add(new AdoptingCategoryDomain(R.drawable.dog_shop, "Gary", 95, R.drawable.male, "Dog", 4, "20/03/2024", "#3", "Was Castrated"));
        historyAdopt.add(new AdoptingCategoryDomain(R.drawable.cat_shop, "Gary", 95, R.drawable.male, "Dog", 4, "20/03/2024", "#2", "Was Castrated"));
        historyAdopt.add(new AdoptingCategoryDomain(R.drawable.dog_shop2, "Gary", 95, R.drawable.male, "Dog", 4, "20/03/2024", "#1", "Was Castrated"));
        historyAdopt.add(new AdoptingCategoryDomain(R.drawable.dog_shop, "Gary", 95, R.drawable.male, "Dog", 4, "20/03/2024", "#0", "Was Castrated"));
        historyAdopt.add(new AdoptingCategoryDomain(R.drawable.cat_shop, "Gary", 95, R.drawable.male, "Dog", 4, "20/03/2024", "#0", "Was Castrated"));

        recyclerView = findViewById(R.id.history_adopt_view);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 40, 40));
        HistoryAdapter historyAdapter = new HistoryAdapter(this,historyAdopt, R.layout.history_adopt_item);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
    }
}
