package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pet_finder_app.Class.MissingPet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchingLostPetActivity extends AppCompatActivity {
    Toolbar arrowBack;
    FloatingActionButton addBtn, add_missing_btn, favorite_btn;
    ListView lv;
    SearchingLostPetAdapter adapter;
    ArrayList<MissingPet> arrayList;
    List<String> imageUrls = new ArrayList<>();
    ImageView filterMissing;
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
        filterMissing = findViewById(R.id.filterMissing);
        ImageView notifiImg = findViewById(R.id.notification_homepage);
        notifiImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
            }
        });
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
        arrayList = new ArrayList<MissingPet>();

        lv = findViewById(R.id.lv);
        adapter = new SearchingLostPetAdapter(this,R.layout.searching_lost_pet_item,arrayList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), PetDetailActivity.class));
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(clicked);
                setAnimation(clicked);
                clicked = !clicked;
            }
        });
        filterMissing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FilterMissingPet.class));
            }
        });
        renderAllMissingPost();

    }
    private void renderAllMissingPost(){
        Log.d("MissingPetData", "2");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference missingPetRef = firebaseDatabase.getReference().child("Missing pet");
        missingPetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("MissingPetData", "3");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MissingPet missingPet = snapshot.getValue(MissingPet.class);
//                    Log.d("MissingPetData", "4");
//
//                    if (snapshot.exists()) { // Check if the snapshot has any children
//                        String gender = snapshot.child("gender").getValue(String.class);
//                        String id = snapshot.child("id").getValue(String.class);
//                        String breed = snapshot.child("breed").getValue(String.class);
//
//                        String idPet = snapshot.child("idPet").getValue(String.class);
//                        List<String> imgUrl = snapshot.child("imgUrl").getValue(List.class);
//                        String name = snapshot.child("name").getValue(String.class);
//                        String registerDate = snapshot.child("registerDate").getValue(String.class);
//                        String size = snapshot.child("size").getValue(String.class);
//                        String typeId = snapshot.child("typeId").getValue(String.class);
//                        String typeMissing = snapshot.child("typeMissing").getValue(String.class);
//                        String weight = snapshot.child("weight").getValue(String.class);
//
//                        Log.d("MissingPetData", "Gender: " + gender);
//                        Log.d("MissingPetData", "ID: " + id);
//                        Log.d("MissingPetData", "Pet ID: " + idPet);
//                        Log.d("MissingPetData", "Image URL: " + imgUrl);
//                        Log.d("MissingPetData", "Name: " + name);
//                        Log.d("MissingPetData", "Registration Date: " + registerDate);
//                        Log.d("MissingPetData", "Size: " + size);
//                        Log.d("MissingPetData", "Type ID: " + typeId);
//                        Log.d("MissingPetData", "Missing Type: " + typeMissing);
//                        Log.d("MissingPetData", "Weight: " + weight);
//                    } else {
//                        Log.d("MissingPetData", "Snapshot does not contain any data");
//                    }
//                    String gender = snapshot.child("gender").getValue(String.class);
//                    String name = snapshot.child("name").getValue(String.class);
//                    String color = snapshot.child("color").getValue(String.class);
//                    String registerDate = snapshot.child("registerDate").getValue(String.class);
//                    String imageUrl = snapshot.child("imgUrl").getValue(String.class);
//                    String typeMissing = snapshot.child("typeMissing").getValue(String.class);
//                    String age = snapshot.child("age").getValue(String.class);
//                    String breed = snapshot.child("breed").getValue(String.class);
//                    String description = snapshot.child("description").getValue(String.class);
//                    String addressMissing = snapshot.child("addressMissing").getValue(String.class);
//                    String statusMissing = snapshot.child("status").getValue(String.class);
//
//                    String dateMissing = snapshot.child("dateMissing").getValue(String.class);
//                    String requestPoster = snapshot.child("requestPoster").getValue(String.class);
//
//                    String size = snapshot.child("size").getValue(String.class);
//                    String postUserId = snapshot.child("postUserId").getValue(String.class);
//                    imageUrls.add(imageUrl);
//                    // Create a MissingPet object and add it to the list

//                    MissingPet pet = new MissingPet(age,breed, "categoryId", color, description, gender, "idPet", imageUrls, name, registerDate, size, "typeId", "weight", "id", typeMissing,addressMissing, dateMissing, requestPoster,postUserId,statusMissing);
                    arrayList.add(missingPet);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("MissingPetData", "Failed to read value.", error.toException());

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
