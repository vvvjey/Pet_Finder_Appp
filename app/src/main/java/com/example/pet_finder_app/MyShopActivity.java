package com.example.pet_finder_app;

import android.os.Bundle;

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

public class MyShopActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Toolbar arrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.my_shop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.myshop), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<AdoptingCategoryDomain> ShopList = new ArrayList<AdoptingCategoryDomain>();
        ShopList.add(new AdoptingCategoryDomain(R.drawable.dog_shop, "Gary", R.drawable.favorate, 95, R.drawable.male, "Dog", 4));
        ShopList.add(new AdoptingCategoryDomain(R.drawable.cat_shop, "Whitney", R.drawable.non_favorate, 20, R.drawable.female, "Cat", 4));
        ShopList.add(new AdoptingCategoryDomain(R.drawable.dog_shop2, "Willie", R.drawable.favorate, 120, R.drawable.male, "Dog", 4));
        ShopList.add(new AdoptingCategoryDomain(R.drawable.dog_shop, "Gary", R.drawable.favorate, 95, R.drawable.male, "Dog", 4));
        ShopList.add(new AdoptingCategoryDomain(R.drawable.cat_shop, "Whitney", R.drawable.non_favorate, 20, R.drawable.female, "Cat", 4));
        ShopList.add(new AdoptingCategoryDomain(R.drawable.dog_shop2, "Willie", R.drawable.favorate, 120, R.drawable.male, "Dog", 4));

        recyclerView = findViewById(R.id.myShopView);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 40,40));
        AdoptingCategoryAdapter shopAdapter = new AdoptingCategoryAdapter(ShopList, R.layout.my_shop_item);
        recyclerView.setAdapter(shopAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
    }
}
