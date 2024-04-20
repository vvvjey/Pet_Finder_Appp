package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SearchingLostPetActivity extends AppCompatActivity {
    Toolbar arrowBack;
    FloatingActionButton addBtn, add_missing_btn, favorite_btn;
    ListView lv;
    SearchingLostPetAdapter adapter;
    ArrayList<String> arrayList;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_lost_pet);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        addBtn = findViewById(R.id.add_btn);
        add_missing_btn = findViewById(R.id.add_missing_btn);
        favorite_btn = findViewById(R.id.favorite_btn);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomepageActivity.class));
            }
        });
        add_missing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FillInforAboutLostPet.class));
            }
        });
        favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FavoritePetActivity.class));
            }
        });
        arrayList = new ArrayList<String>();
        arrayList.add("ABC");
        arrayList.add("ABC");
        lv = findViewById(R.id.lv);
        adapter = new SearchingLostPetAdapter(this,R.layout.searching_lost_pet_item,arrayList);
        lv.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(clicked);
                setAnimation(clicked);
                clicked = !clicked;
            }
        });

    }

    private void setVisibility(boolean clicked) {
        if (!clicked) {
            add_missing_btn.setVisibility(View.VISIBLE);
            favorite_btn.setVisibility(View.VISIBLE);
        } else {
            add_missing_btn.setVisibility(View.INVISIBLE);
            favorite_btn.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(boolean clicked) {
        if (!clicked) {
            add_missing_btn.startAnimation(getFromBottom());
            favorite_btn.startAnimation(getFromBottom());
            addBtn.startAnimation(getRotateOpen());
        } else {
            add_missing_btn.startAnimation(getToBottom());
            favorite_btn.startAnimation(getToBottom());
            addBtn.startAnimation(getRotateClose());
        }
    }

}
