package com.example.pet_finder_app;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritePetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView imageView;
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

//        List<AdoptingCategoryDomain> FavoriteList = new ArrayList<AdoptingCategoryDomain>();
//        FavoriteList.add(new AdoptingCategoryDomain(R.drawable.dog_category1, "Samantha", R.drawable.favorate, "Thu Duc ( 2,5 km )", R.drawable.male, R.drawable.mising));
//        FavoriteList.add(new AdoptingCategoryDomain(R.drawable.dog_category2, "Tigri", R.drawable.non_favorate, "Thu Duc ( 2,5 km )", R.drawable.female, R.drawable.seen));
//        FavoriteList.add(new AdoptingCategoryDomain(R.drawable.dog_category1, "Samantha", R.drawable.favorate, "Thu Duc ( 2,5 km )", R.drawable.male, R.drawable.mising));
//        FavoriteList.add(new AdoptingCategoryDomain(R.drawable.dog_category2, "Samantha", R.drawable.non_favorate, "Thu Duc ( 2,5 km )", R.drawable.male, R.drawable.seen));
//        FavoriteList.add(new AdoptingCategoryDomain(R.drawable.dog_category1, "Tigri", R.drawable.non_favorate, "Thu Duc ( 2,5 km )", R.drawable.female, R.drawable.seen));
//        recyclerView = findViewById(R.id.favoriteView);
//        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 20, 40,40));
//        AdoptingCategoryAdapter favoriteAdapter = new AdoptingCategoryAdapter(FavoriteList, R.layout.favorite_item);
//        recyclerView.setAdapter(favoriteAdapter);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
//
//        imageView = findViewById(R.id.grid_view);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isGrid){
//                    recyclerView.setLayoutManager(new GridLayoutManager(FavoritePetActivity.this,1, RecyclerView.VERTICAL, false));
//                    AdoptingCategoryAdapter horizontalAdapter = new AdoptingCategoryAdapter(FavoriteList, R.layout.favorite_item_horz);
//                    recyclerView.setAdapter(horizontalAdapter);
//                    imageView.setImageResource(R.drawable.list_view);
//                    isGrid = false;
//                }else{
//                    recyclerView.setLayoutManager(new GridLayoutManager(FavoritePetActivity.this,2, RecyclerView.VERTICAL, false));
//                    AdoptingCategoryAdapter horizontalAdapter = new AdoptingCategoryAdapter(FavoriteList, R.layout.favorite_item);
//                    recyclerView.setAdapter(horizontalAdapter);
//                    imageView.setImageResource(R.drawable.grid_black);
//                    isGrid = true;
//                }
//
//            }
//        });
//
//        arrowBack = findViewById(R.id.toolbarArrowBack);
//        arrowBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
    }
}
