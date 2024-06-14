package com.example.pet_finder_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_finder_app.Class.AdoptPet;
import com.example.pet_finder_app.Class.Pet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AdoptingPetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Dialog dialog;
    private FloatingActionButton addBtn, edit_btn, favorite_btn;
    private Toolbar arrowBack;
    private boolean clicked;
    private ImageView filterAdopt;
    private TextView cat, dog, turtle, hamster, rabbit, duck, others, male, female, small, medium, large, baby, young, adult, senior;
    private static final int REQUEST_NOTIFICATION = 2;
    List<Pet> petList= new ArrayList<>();
    List<Pet> petListTemp = new ArrayList<>();
    Spinner breed, color;
    List<AdoptPet> adoptPets = new ArrayList<>();
    EditText minPrice, maxPrice;
    private boolean isFilter = false;
    private boolean isCategory = false;
    private boolean isGender = false;
    private boolean isSize = false;
    private boolean isAge = false;
    private boolean isColor = false;
    private boolean isBreed = false;
    private boolean isPriceMin = false;
    private boolean isPriceMax = false;
    ArrayAdapter<CharSequence> breedAdapter;
    ArrayAdapter<CharSequence> colorAdapter;
    private int minP, maxP;
    private String breedItem, colorItem, category, gender, size, age;

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.adopting_pet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pet), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdoptingPetActivity.this, HomepageActivity.class));
            }
        });



        recyclerView = findViewById(R.id.recycleView);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    Pet pet = snap.getValue(Pet.class);
                    petList.add(pet);
                }
                Log.d("PET_LIST", "Pet List: " + petList);

                if(!petList.isEmpty() && !isFilter){
                    petListTemp.clear();
                    petListTemp.addAll(petList);
                    isFilter = true;
                }

                populateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("AdoptPet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    AdoptPet adoptPet = snap.getValue(AdoptPet.class);
                    adoptPets.add(adoptPet);
                }
                Log.d("ADOPT_PETS_LIST", "Adopt Pets List: " + adoptPets);
                populateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        addBtn = findViewById(R.id.add_btn);
        edit_btn = findViewById(R.id.edit_btn);
        favorite_btn = findViewById(R.id.favorite_btn);
        filterAdopt = findViewById(R.id.filterAdopt);
        ImageView notifiImg = findViewById(R.id.notification_homepage);
        notifiImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
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


        filterAdopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petList.clear();
                petList.addAll(petListTemp);
                Log.d("PET_LIST", "Pet ListBefore: " + petList);
                View dialogView = LayoutInflater.from(AdoptingPetActivity.this).inflate(R.layout.filter_adopt, null);
                dialog = new Dialog(AdoptingPetActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(AdoptingPetActivity.this);
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
                minPrice = dialogView.findViewById(R.id.editMin);
                maxPrice = dialogView.findViewById(R.id.editMax);
//                breedAdapter = ArrayAdapter.createFromResource(
//                        AdoptingPetActivity.this,
//                        R.array.breed,
//                        android.R.layout.simple_spinner_item
//                );

                colorAdapter = ArrayAdapter.createFromResource(
                        AdoptingPetActivity.this,
                        R.array.color,
                        android.R.layout.simple_spinner_item
                );
                colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                color.setAdapter(colorAdapter);
                isPriceMin = false;
                isPriceMax = false;
                minP = 0;
                maxP = 0;
                Drawable selectedBtn = ContextCompat.getDrawable(AdoptingPetActivity.this, R.drawable.button_rounded);
                Drawable unSelectedBtn = ContextCompat.getDrawable(AdoptingPetActivity.this, R.drawable.background_selector);
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

                minPrice.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() > 0) {
                            isPriceMin = true;
                            try {
                                minP = Integer.parseInt(s.toString());
                                Log.d("Show price", "Min: " + minP);
                            } catch (NumberFormatException e) {
                            }
                        } else {
                        }
                    }
                });

                maxPrice.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() > 0) {
                            isPriceMax = true;
                            try {
                                maxP = Integer.parseInt(s.toString());
                                Log.d("Show price", "Max: " + maxP);
                            } catch (NumberFormatException e) {
                            }
                        } else {
                        }
                    }
                });

                Button viewMatches = dialogView.findViewById(R.id.btnViewMatches);
                viewMatches.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                List<Pet> filteredPets = new ArrayList<>();
                                Map<String, String> filters = new HashMap<>();
                                if (isCategory) filters.put("typeId", category);
                                if (isGender) filters.put("gender", gender);
                                if (isSize) filters.put("size", size);
                                if (isAge) filters.put("age", age);
                                if (isColor) filters.put("color", colorItem);
                                if (isBreed) filters.put("breed", breedItem);

                                AtomicInteger priceCheckCounter = new AtomicInteger(0);
                                int totalPets = (int) snapshot.getChildrenCount();

                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    Pet pet = snap.getValue(Pet.class);
                                    AtomicBoolean matches = new AtomicBoolean(true);
                                    AtomicBoolean priceChecked = new AtomicBoolean(false);

                                    if (isPriceMin || isPriceMax) {
                                        databaseReference.child("AdoptPet").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                boolean priceMatchFound = false;
                                                for (DataSnapshot snap2 : snapshot.getChildren()) {
                                                    AdoptPet adoptPet = snap2.getValue(AdoptPet.class);
                                                    if (adoptPet != null && adoptPet.getIdPet().equals(pet.getIdPet())) {
                                                        int price = Integer.parseInt(adoptPet.getPrice());
                                                        Log.d("Show price", "Pet ID: " + adoptPet.getIdPet() + " Price: " + price);

                                                        if ((minP <= price && maxP >= price) ||
                                                                (maxP > 0 && minP == 0 && maxP >= price) ||
                                                                (minP > 0 && maxP == 0 && minP <= price)) {
                                                            priceMatchFound = true;
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (priceMatchFound) {
                                                    onPriceCheckComplete(matches, pet, filteredPets, filters);
                                                } else {
                                                    int count = priceCheckCounter.incrementAndGet();
                                                    if (count == totalPets) {
                                                        populateRecyclerView();
                                                        dialog.dismiss();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                            }
                                        });
                                    } else {
                                        onPriceCheckComplete(matches, pet, filteredPets, filters);
                                    }
                                }
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

    private void onPriceCheckComplete(AtomicBoolean matches, Pet pet, List<Pet> filteredPets, Map<String, String> filters) {
        for (Map.Entry<String, String> filter : filters.entrySet()) {
            if (!filter.getValue().equals(getFieldValue(pet, filter.getKey()))){
                matches.set(false);
                break;
            }
        }

        if (matches.get()) {
            filteredPets.add(pet);
        }

        isCategory = false;
        isGender = false;
        isAge = false;
        isSize = false;

        petList.clear();
        petList.addAll(filteredPets);
        populateRecyclerView();
        dialog.dismiss();
    }
        private String getFieldValue(Pet pet, String fieldName) {
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
        HashMap<String, AdoptPet> hashMap = new HashMap<>();
        for(AdoptPet adoptPet : adoptPets){
            hashMap.put(adoptPet.getIdPet(), adoptPet);
        }
        for(Pet pet : petList){
            AdoptPet adoptPet = hashMap.get(pet.getIdPet());
            if(adoptPet != null){
                petItems.add(new AdoptingPetItem(
                        pet.getAge(),
                        pet.getCategoryId(),
                        pet.getColor(),
                        pet.getGender(),
                        pet.getIdPet(),
                        pet.getImgUrl().get(0),
                        pet.getName(),
                        pet.getRegisterDate(),
                        adoptPet.getStatus()
                ));
            }
        }
        AdoptingPetAdapter petAdapter = new AdoptingPetAdapter(petItems, this);
        recyclerView.setAdapter(petAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NOTIFICATION && resultCode == RESULT_OK) {
            onBackPressed();
        }
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
    private void setClick(TextView textView, Drawable selectedBtn, int color){
        textView.setBackground(selectedBtn);
        textView.setTextColor(ContextCompat.getColor(AdoptingPetActivity.this, color));
    }
}