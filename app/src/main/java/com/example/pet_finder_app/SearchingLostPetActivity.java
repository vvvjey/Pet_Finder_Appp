package com.example.pet_finder_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.pet_finder_app.Class.MissingPet;
import com.example.pet_finder_app.Class.Recognition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchingLostPetActivity extends AppCompatActivity {
    Toolbar arrowBack;
    FloatingActionButton addBtn, add_missing_btn, favorite_btn;
    ListView lv;
    private Dialog dialog;
    SearchingLostPetAdapter adapter;
    ArrayList<MissingPet> arrayList;
    Spinner breed, color;
    private boolean isFilter = false;
    private boolean isCategory = false;
    private boolean isGender = false;
    private boolean isSize = false;
    private boolean isAge = false;
    private boolean isColor = false;
    private boolean isBreed = false;
    private boolean isTypePet = false;
    ArrayAdapter<CharSequence> breedAdapter;
    ArrayAdapter<CharSequence> colorAdapter;
    private String breedItem, colorItem, category, gender, size, age, typePet;

    private TextView cat, dog, turtle, hamster, rabbit, duck, others, male, female, small, medium, large, baby, young, adult, senior, missingBtn, seenBtn, protectedBtn;
    List<MissingPet> petListTemp = new ArrayList<>();
    List<String> imageUrls = new ArrayList<>();
    List<MissingPet> petList= new ArrayList<>();
    ImageView filterMissing, detect_image;
    private final int IMAGE_PICK = 100;
    Bitmap bitmap;
    DetectorActivity detectorActivity;
    Paint boxPaint = new Paint();
    Paint textPain = new Paint();
    float maxConf = 0.0f;
    private Button search_image;
    String highestConfLabel = "";
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
        search_image = findViewById(R.id.search_image);
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
                Intent intent = new Intent(getApplicationContext(), FavoritePetActivity.class);
                intent.putExtra("typeFunction", "Missing");
                startActivity(intent);
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
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        renderAllMissingPost();
        filterMissing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petList.clear();
                petList.addAll(petListTemp);
                Log.d("PET_LIST", "Pet ListBefore: " + petList);
                View dialogView = LayoutInflater.from(SearchingLostPetActivity.this).inflate(R.layout.filtter_missing_pet, null);
                dialog = new Dialog(SearchingLostPetActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchingLostPetActivity.this);
                builder.setView(dialogView);
//                adoptPets = new ArrayList<>();
                dialog = builder.create();
                dialog.show();
                TextView closeBtn = dialogView.findViewById(R.id.closeBtn);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                missingBtn = dialogView.findViewById(R.id.missingCheck);
                seenBtn = dialogView.findViewById(R.id.seenCheck);
                protectedBtn = dialogView.findViewById(R.id.protectedCheck);
                cat = dialogView.findViewById(R.id.catCheckAdopt);
                dog = dialogView.findViewById(R.id.dogCheckAdopt);
                turtle = dialogView.findViewById(R.id.turtleCheckAdopt);
                hamster = dialogView.findViewById(R.id.hamsCheckAdopt);
                rabbit = dialogView.findViewById(R.id.rabbitCheckAdopt);
                duck = dialogView.findViewById(R.id.duckCheckAdopt);
                others = dialogView.findViewById(R.id.othersCheckAdopt);
                male = dialogView.findViewById(R.id.maleCheckAdopt);
                female = dialogView.findViewById(R.id.femaleCheckAdopt);
                small = dialogView.findViewById(R.id.smallCheckAdopt);
                medium = dialogView.findViewById(R.id.mediumCheckAdopt);
                large = dialogView.findViewById(R.id.largeCheckAdopt);
                baby = dialogView.findViewById(R.id.babyCheckAdopt);
                young = dialogView.findViewById(R.id.youngCheckAdopt);
                adult = dialogView.findViewById(R.id.adultCheckAdopt);
                senior = dialogView.findViewById(R.id.seniorCheckAdopt);
                breed = (Spinner) dialogView.findViewById(R.id.spinnerBreedAdopt);
                color = (Spinner) dialogView.findViewById(R.id.spinnerColorAdopt);

                colorAdapter = ArrayAdapter.createFromResource(
                        SearchingLostPetActivity.this,
                        R.array.color,
                        android.R.layout.simple_spinner_item
                );
                colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                color.setAdapter(colorAdapter);
                Drawable selectedBtn = ContextCompat.getDrawable(SearchingLostPetActivity.this, R.drawable.button_rounded);
                Drawable unSelectedBtn = ContextCompat.getDrawable(SearchingLostPetActivity.this, R.drawable.background_selector);
                cat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(cat, selectedBtn, R.color.white);
                        setClick(dog, unSelectedBtn, R.color.black);
                        setClick(turtle, unSelectedBtn, R.color.black);
                        setClick(hamster, unSelectedBtn, R.color.black);
                        setClick(rabbit, unSelectedBtn, R.color.black);
                        setClick(duck, unSelectedBtn, R.color.black);
                        setClick(others, unSelectedBtn, R.color.black);
                        category = "cat";
                        updateBreedSpinner(0);
                        isCategory = true;
                    }
                });
                dog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(cat, unSelectedBtn, R.color.black);
                        setClick(dog, selectedBtn, R.color.white);
                        setClick(turtle, unSelectedBtn, R.color.black);
                        setClick(hamster, unSelectedBtn, R.color.black);
                        setClick(rabbit, unSelectedBtn, R.color.black);
                        setClick(duck, unSelectedBtn, R.color.black);
                        setClick(others, unSelectedBtn, R.color.black);
                        category = "dog";
                        updateBreedSpinner(1);
                        isCategory = true;
                    }
                });
                turtle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(cat, unSelectedBtn, R.color.black);
                        setClick(dog, unSelectedBtn, R.color.black);
                        setClick(turtle, selectedBtn, R.color.white);
                        setClick(hamster, unSelectedBtn, R.color.black);
                        setClick(rabbit, unSelectedBtn, R.color.black);
                        setClick(duck, unSelectedBtn, R.color.black);
                        setClick(others, unSelectedBtn, R.color.black);
                        category = "turtle";
                        updateBreedSpinner(2);
                        isCategory = true;
                    }
                });
                hamster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(cat, unSelectedBtn, R.color.black);
                        setClick(dog, unSelectedBtn, R.color.black);
                        setClick(turtle, unSelectedBtn, R.color.black);
                        setClick(hamster, selectedBtn, R.color.white);
                        setClick(rabbit, unSelectedBtn, R.color.black);
                        setClick(duck, unSelectedBtn, R.color.black);
                        setClick(others, unSelectedBtn, R.color.black);
                        category = "hamster";
                        updateBreedSpinner(3);
                        isCategory = true;
                    }
                });
                rabbit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(cat, unSelectedBtn, R.color.black);
                        setClick(dog, unSelectedBtn, R.color.black);
                        setClick(turtle, unSelectedBtn, R.color.black);
                        setClick(hamster, unSelectedBtn, R.color.black);
                        setClick(rabbit, selectedBtn, R.color.white);
                        setClick(duck, unSelectedBtn, R.color.black);
                        setClick(others, unSelectedBtn, R.color.black);
                        category = "rabbit";
                        updateBreedSpinner(4);
                        isCategory = true;
                    }
                });
                duck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(cat, unSelectedBtn, R.color.black);
                        setClick(dog, unSelectedBtn, R.color.black);
                        setClick(turtle, unSelectedBtn, R.color.black);
                        setClick(hamster, unSelectedBtn, R.color.black);
                        setClick(rabbit, unSelectedBtn, R.color.black);
                        setClick(duck, selectedBtn, R.color.white);
                        setClick(others, unSelectedBtn, R.color.black);
                        category = "duck";
                        updateBreedSpinner(5);
                        isCategory = true;
                    }
                });
                others.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(cat, unSelectedBtn, R.color.black);
                        setClick(dog, unSelectedBtn, R.color.black);
                        setClick(turtle, unSelectedBtn, R.color.black);
                        setClick(hamster, unSelectedBtn, R.color.black);
                        setClick(rabbit, unSelectedBtn, R.color.black);
                        setClick(duck, unSelectedBtn, R.color.black);
                        setClick(others, selectedBtn, R.color.white);
                        category = "others";
                        updateBreedSpinner(6);
                        isCategory = true;
                    }
                });
                missingBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(missingBtn, selectedBtn, R.color.white);
                        setClick(seenBtn, unSelectedBtn, R.color.black);
                        setClick(protectedBtn, unSelectedBtn, R.color.black);
                        typePet = "Missing";
                        isTypePet = true;
                    }
                });
                seenBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(missingBtn, unSelectedBtn, R.color.black);
                        setClick(seenBtn, selectedBtn, R.color.white);
                        setClick(protectedBtn, unSelectedBtn, R.color.black);
                        typePet = "Seen";
                        isTypePet = true;
                    }
                });
                protectedBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(protectedBtn, selectedBtn, R.color.white);
                        setClick(seenBtn, unSelectedBtn, R.color.black);
                        setClick(missingBtn, unSelectedBtn, R.color.black);
                        typePet = "Protected";
                        isTypePet = true;
                    }
                });
                male.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(male, selectedBtn, R.color.white);
                        setClick(female, unSelectedBtn, R.color.black);
                        gender = "male";
                        isGender = true;
                    }
                });
                female.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(male, unSelectedBtn, R.color.black);
                        setClick(female, selectedBtn, R.color.white);
                        gender = "female";
                        isGender = true;
                    }
                });
                small.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(small, selectedBtn, R.color.white);
                        setClick(medium, unSelectedBtn, R.color.black);
                        setClick(large, unSelectedBtn, R.color.black);
                        size = "small";
                        isSize = true;
                    }
                });
                medium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(small, unSelectedBtn, R.color.black);
                        setClick(medium, selectedBtn, R.color.white);
                        setClick(large, unSelectedBtn, R.color.black);
                        size = "medium";
                        isSize = true;
                    }
                });
                large.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(small, unSelectedBtn, R.color.black);
                        setClick(medium, unSelectedBtn, R.color.black);
                        setClick(large, selectedBtn, R.color.white);
                        size = "large";
                        isSize = true;
                    }
                });
                baby.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(baby, selectedBtn, R.color.white);
                        setClick(young, unSelectedBtn, R.color.black);
                        setClick(adult, unSelectedBtn, R.color.black);
                        setClick(senior, unSelectedBtn, R.color.black);
                        age = "baby";
                        isAge = true;
                    }
                });
                young.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(baby, unSelectedBtn, R.color.black);
                        setClick(young, selectedBtn, R.color.white);
                        setClick(adult, unSelectedBtn, R.color.black);
                        setClick(senior, unSelectedBtn, R.color.black);
                        age = "young";
                        isAge = true;
                    }
                });
                adult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(baby, unSelectedBtn, R.color.black);
                        setClick(young, unSelectedBtn, R.color.black);
                        setClick(adult, selectedBtn, R.color.white);
                        setClick(senior, unSelectedBtn, R.color.black);
                        age = "adult";
                        isAge = true;
                    }
                });
                senior.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClick(baby, unSelectedBtn, R.color.black);
                        setClick(young, unSelectedBtn, R.color.black);
                        setClick(adult, unSelectedBtn, R.color.black);
                        setClick(senior, selectedBtn, R.color.white);
                        age = "senior";
                        isAge = true;
                    }
                });

                breed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position > 0 && isCategory) {
                            breedItem = breedAdapter.getItem(position).toString();
                            isBreed = true;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Handle no selection
                    }
                });

                color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position > 0) {
                            colorItem = colorAdapter.getItem(position).toString();
                            isColor = true;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Handle no selection
                    }
                });

                Button viewMatches = dialogView.findViewById(R.id.btnViewMatches);
                viewMatches.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child("Missing pet").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                List<MissingPet> filteredPets = new ArrayList<>();
                                Map<String, String> filters = new HashMap<>();
                                if (isCategory) filters.put("typeId", category);
                                if (isGender) filters.put("gender", gender);
                                if (isSize) filters.put("size", size);
                                if (isAge) filters.put("age", age);
                                if (isColor) filters.put("color", colorItem);
                                if (isBreed) filters.put("breed", breedItem);
                                if (isTypePet) filters.put("typePet", typePet);
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    MissingPet missingPet = snap.getValue(MissingPet.class);
                                    boolean matches = true;

                                    for (Map.Entry<String, String> filter : filters.entrySet()) {
                                        if (!filter.getValue().equals(getFieldValue(missingPet, filter.getKey()))) {
                                            matches = false;
                                            break;
                                        }
                                    }

                                    if (matches) {
                                        filteredPets.add(missingPet);
                                    }
                                }
                                isCategory = false;
                                isGender = false;
                                isAge = false;
                                isSize = false;
                                isColor = false;
                                isBreed = false;
                                isTypePet = false;


                                petList.clear();
                                petList.addAll(filteredPets);
                                populateRecyclerView();
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                });
            }
        });
        search_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCategory = true;
                petList.clear();
                petList.addAll(petListTemp);
                detectorActivity = new DetectorActivity();
                detectorActivity.setModelFile("model.tflite");
                detectorActivity.initialModel(SearchingLostPetActivity.this);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_PICK);


                View dialogView = LayoutInflater.from(SearchingLostPetActivity.this).inflate(R.layout.dectection, null);
                dialog = new Dialog(SearchingLostPetActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchingLostPetActivity.this);
                builder.setView(dialogView);
                dialog = builder.create();
                dialog.show();
                detect_image = dialogView.findViewById(R.id.detect_image);
                Button search_pet = dialogView.findViewById(R.id.search_pet);
                search_pet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String petBreedFound = detectPet();
                        Log.d("ShowPetName", petBreedFound);
                        databaseReference.child("Pet").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                List<MissingPet> filteredPets = new ArrayList<>();
                                for (DataSnapshot snap: snapshot.getChildren()){
                                    MissingPet pet = snap.getValue(MissingPet.class);
                                    assert pet != null;
                                    if(pet.getBreed().equals(petBreedFound)){
                                        filteredPets.add(pet);
                                    }
                                }
                                petList.clear();
                                petList.addAll(filteredPets);
                                populateRecyclerView();
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        });
    }

    private String getFieldValue(MissingPet pet, String fieldName) {
        switch (fieldName) {
            case "typeId":
                return pet.getTypeId();
            case "gender":
                return pet.getGender();
            case "size":
                return pet.getSize();
            case "age":
                return pet.getAge();
            case "color":
                return pet.getColor();
            case "breed":
                return pet.getBreed();
            case "typePet":
                return pet.getTypeMissing();
            default:
                return null;
        }
    }

    private void updateBreedSpinner(int categoryPosition) {
        int breedArrayId;
        switch (categoryPosition) {
            case 0: // Cat
                breedArrayId = R.array.cat_breed;
                break;
            case 1:
                breedArrayId = R.array.dog_breed;
                break;
            case 2:
                breedArrayId = R.array.turtle_breed;
                break;
            case 3:
                breedArrayId = R.array.hamster_breed;
                break;
            case 4:
                breedArrayId = R.array.rabbit_breed;
                break;
            case 5:
                breedArrayId = R.array.duck_breed;
                break;
            case 6:
                breedArrayId = R.array.others_breed;
                break;
            default:
                breedArrayId = R.array.cat_breed; // Default case, should not happen
                break;
        }

        breedAdapter = ArrayAdapter.createFromResource(
                this,
                breedArrayId,
                android.R.layout.simple_spinner_item
        );
        breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breed.setAdapter(breedAdapter);
    }
    private void populateRecyclerView() {
        List<AdoptingPetItem> petItems = new ArrayList<>();
        arrayList.clear();
        for(MissingPet missingPet : petList){
            arrayList.add(missingPet);
        }
        adapter.notifyDataSetChanged();
    }
    private void renderAllMissingPost(){
        Log.d("MissingPetData", "2");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference missingPetRef = firebaseDatabase.getReference().child("Missing pet");
        Query query = missingPetRef.orderByChild("status").equalTo("Waiting");
        query.addValueEventListener(new ValueEventListener() {
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
    private void setClick(TextView textView, Drawable selectedBtn, int color){
        textView.setBackground(selectedBtn);
        textView.setTextColor(ContextCompat.getColor(SearchingLostPetActivity.this, color));
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

    private String detectPet(){
        ArrayList<Recognition> recognitions =  detectorActivity.detect(bitmap);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);

        for(Recognition recognition: recognitions){

            if(recognition.getConfidence() > 0.7 && recognition.getConfidence() > maxConf){
                maxConf = recognition.getConfidence();
                highestConfLabel = recognition.getLabelName();
                RectF location = recognition.getLocation();
                canvas.drawRect(location, boxPaint);
                canvas.drawText(recognition.getLabelName() + ":" + recognition.getConfidence(), location.left, location.top, textPain);
            }

        }
        detect_image.setImageBitmap(mutableBitmap);
        return highestConfLabel;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_PICK && data != null){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                detect_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
