package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MissingAnimalsPostActivity extends AppCompatActivity {
    Toolbar arrowBack;
    ListView lv;
    SearchingLostPetAdapter adapter;
    ArrayList<String> arrayList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missing_animal_post);

        arrowBack = findViewById(R.id.toolbarArrowBack);
//        arrayList = new ArrayList<String>();
//        arrayList.add("ABC");
//        arrayList.add("ABC");
//        arrayList.add("ABC");
//
//        arrayList.add("ABC");
//
//        lv = findViewById(R.id.lvMissingPost);
//        adapter = new SearchingLostPetAdapter(this,R.layout.searching_lost_pet_item,arrayList);
//        lv.setAdapter(adapter);



        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        });
    }

}
