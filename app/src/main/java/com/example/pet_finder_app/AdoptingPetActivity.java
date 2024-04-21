package com.example.pet_finder_app;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AdoptingPetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton addBtn, edit_btn, favorite_btn;
    private Toolbar arrowBack;
    private boolean clicked;

    private Animation getFromBottom() {
        return AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
    }

    private Animation getToBottom() {
        return AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
    }

    private Animation getRotateClose() {
        return AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
    }

    private Animation getRotateOpen() {
        return AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recycleView);
        AdoptingPetAdapter petAdapter = new AdoptingPetAdapter(petList);
        recyclerView.setAdapter(petAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        addBtn = findViewById(R.id.add_btn);
        edit_btn = findViewById(R.id.edit_btn);
        favorite_btn = findViewById(R.id.favorite_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(clicked);
                setAnimation(clicked);
                clicked = !clicked;
            }
        });

        favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FavoritePetActivity.class));
            }
        });
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FillInforToAdoptActivity.class));
            }
        });
    }

    private void setVisibility(boolean clicked) {
        if (!clicked) {
            edit_btn.setVisibility(View.VISIBLE);
            favorite_btn.setVisibility(View.VISIBLE);
        } else {
            edit_btn.setVisibility(View.INVISIBLE);
            favorite_btn.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(boolean clicked) {
        if (!clicked) {
            edit_btn.startAnimation(getFromBottom());
            favorite_btn.startAnimation(getFromBottom());
            addBtn.startAnimation(getRotateOpen());
        } else {
            edit_btn.startAnimation(getToBottom());
            favorite_btn.startAnimation(getToBottom());
            addBtn.startAnimation(getRotateClose());
        }
    }
}
