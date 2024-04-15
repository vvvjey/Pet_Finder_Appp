package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SearchingLostPetActivity extends AppCompatActivity {
    Toolbar arrowBack;
    ListView lv;
    SearchingLostPetAdapter adapter;
    ArrayList<String> arrayList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_lost_pet);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        arrayList = new ArrayList<String>();
        arrayList.add("ABC");
        arrayList.add("ABC");
        lv = findViewById(R.id.lv);
        adapter = new SearchingLostPetAdapter(this,R.layout.searching_lost_pet_item,arrayList);
        lv.setAdapter(adapter);

    }

}
