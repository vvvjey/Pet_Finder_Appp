package com.example.pet_finder_app;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.pet_finder_app.Class.AdoptOrder;
import com.example.pet_finder_app.Class.AdoptPet;
import com.example.pet_finder_app.Class.Pet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class AddingPetActivity extends AppCompatActivity {
    Toolbar arrowBack;
    Button backPet;
    FloatingActionButton addImg;
    List<Uri> image = new ArrayList<>();

    List<ImageView> uploadImg = new ArrayList<>(5);
    List<ImageView> deleteImg = new ArrayList<>(5);
    private List<Uri> imagesToDelete = new ArrayList<>();
    Pet pet;
    AdoptPet adopt;
    AdoptOrder order;
    String activity, idPet;
    String ageItem, categoryItem, sizeItem, genderItem, colorItem, breedItem;
    Spinner age,category, size, gender, color, breed;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String typeId, name, description, price, weight, idPetKey, idAdoptKey, calendarText;
    List<String> imageUrl = new ArrayList<>(Collections.nCopies(5, ""));
    List<String> imageUrlCheck = new ArrayList<>(Collections.nCopies(5, ""));
    List<String> imageUrlCheckUpdate = new ArrayList<>(Collections.nCopies(5, ""));
    StorageReference storageReference;
    EditText petName;
    EditText petDes;
    EditText petPrice;
    EditText petAge;
    EditText petWeight;
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    ArrayAdapter<CharSequence> ageAdapter;
    ArrayAdapter<CharSequence> categoryAdapter;
    ArrayAdapter<CharSequence> sizeAdapter;
    ArrayAdapter<CharSequence>  genderAdapter;
    ArrayAdapter<CharSequence> colorAdapter;
    ArrayAdapter<CharSequence> breedAdapter;

    boolean isImage = false;
    boolean isImgDeleted = false;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.adding_new_pet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addpet), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageUrlCheck = new ArrayList<>(Arrays.asList("no", "no", "no", "no", "no"));
        addImg = findViewById(R.id.add_img);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        backPet = findViewById(R.id.btn_add);
        uploadImg.add(findViewById(R.id.uploadImg1));
        uploadImg.add(findViewById(R.id.uploadImg2));
        uploadImg.add(findViewById(R.id.uploadImg3));
        uploadImg.add(findViewById(R.id.uploadImg4));
        uploadImg.add(findViewById(R.id.uploadImg5));


        activity = getIntent().getStringExtra("activity");


        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyPetActivity.class));
            }
        });
        backPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SuccessAddingActivity.class));
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity.equals("edit")){
                    isImage = true;
                }
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
            }
        });
        Button btnAdd = findViewById(R.id.btn_add);


        age = (Spinner) findViewById(R.id.age_spinner);
        size = (Spinner) findViewById(R.id.size_spinner);
        gender = (Spinner) findViewById(R.id.gender_spinner);
        color = (Spinner) findViewById(R.id.color_spinner);
        category = (Spinner) findViewById(R.id.category_spinner);
        breed = (Spinner) findViewById(R.id.breed_spinner);

        ageAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.age,
                android.R.layout.simple_spinner_item
        );

        sizeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.size,
                android.R.layout.simple_spinner_item
        );
        genderAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender,
                android.R.layout.simple_spinner_item
        );
        colorAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.color,
                android.R.layout.simple_spinner_item
        );
        categoryAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.category,
                android.R.layout.simple_spinner_item
        );
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        age.setAdapter(ageAdapter);
        category.setAdapter(categoryAdapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateBreedSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        size.setAdapter(sizeAdapter);
        gender.setAdapter(genderAdapter);
        color.setAdapter(colorAdapter);


        petName = findViewById(R.id.editNamePet);
        petDes = findViewById(R.id.description_edit);
        petPrice = findViewById(R.id.price_edit);
        petWeight = findViewById(R.id.weight_edit);
        simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        calendar = Calendar.getInstance();
        idPet = getIntent().getStringExtra("idPet");

        deleteImg.add(findViewById(R.id.deleteImg1));
        deleteImg.add(findViewById(R.id.deleteImg2));
        deleteImg.add(findViewById(R.id.deleteImg3));
        deleteImg.add(findViewById(R.id.deleteImg4));
        deleteImg.add(findViewById(R.id.deleteImg5));

        deleteImg.get(0).setOnClickListener(v -> deleteImage(0));
        deleteImg.get(1).setOnClickListener(v -> deleteImage(1));
        deleteImg.get(2).setOnClickListener(v -> deleteImage(2));
        deleteImg.get(3).setOnClickListener(v -> deleteImage(3));
        deleteImg.get(4).setOnClickListener(v -> deleteImage(4));


        if(activity.equals("edit")){
            showData();
            Log.d("ShowIntent", activity);
            Log.d("Show id Pet", idPet);
            btnAdd.setText("Edit");
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(isName() || isAge() || isBreed() || isCategory() ||
                                    isColor() || isDes() || isGender() || isPrice() || isWeight() || isSize() || isImage){
                                if(isImage){
                                    Log.d("ShowImg1", "Yes you are in");
                                    uploadImage(image);
                                }
                                Toast.makeText(AddingPetActivity.this, "Saved data", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MyPetActivity.class));
                            }
                            else{
                                Toast.makeText(AddingPetActivity.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 1000);

                }
            });
        }else{
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadImage(image);
                }
            });
        }


    }
    private void updateBreedSpinner(int categoryPosition) {
        int breedArrayId;
        switch (categoryPosition) {
            case 0: // Cat
                breedArrayId = R.array.cat_breed;
                break;
            case 1: // Cat
                breedArrayId = R.array.cat_breed;
                break;
            case 2:
                breedArrayId = R.array.dog_breed;
                break;
            case 3:
                breedArrayId = R.array.turtle_breed;
                break;
            case 4:
                breedArrayId = R.array.hamster_breed;
                break;
            case 5:
                breedArrayId = R.array.rabbit_breed;
                break;
            case 6:
                breedArrayId = R.array.duck_breed;
                break;
            case 7:
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
    private void deleteImage(int index) {
        Uri selectedImageUri = image.get(index);
        if (selectedImageUri != null) {
            // Mark the image as deleted
            imagesToDelete.add(selectedImageUri);

            // Update the UI to reflect deletion
            uploadImg.get(index).setImageDrawable(null);
            imageUrlCheck.set(index, "no");
            imageUrl.set(index, "");
            deleteImg.get(index).setImageDrawable(null);

            Toast.makeText(AddingPetActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public void showData(){
        databaseReference.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    Log.d("ShowIntent", idPet);
                    Log.d("ShowIntent", snap.getValue(Pet.class).getIdPet());
                    if(idPet.equals(snap.getValue(Pet.class).getIdPet())){
                        pet = snap.getValue(Pet.class);
                        Log.d("ShowIntent", "GetTheRightThing");
                        category.setSelection(categoryAdapter.getPosition(pet.getTypeId()));
                        updateBreedSpinner(category.getSelectedItemPosition());
                        int breedPos = breedAdapter.getPosition(pet.getBreed());
                        petName.setText(pet.getName());
                        Log.d("ShowIdCategory", String.valueOf(category.getSelectedItemPosition()));
                        Log.d("ShowIdCategory", "StartHere");
                        Log.d("ShowIdCategory", pet.getBreed());
                        Log.d("ShowIdCategory", String.valueOf(breedAdapter.getPosition(pet.getBreed())));
                        size.setSelection(sizeAdapter.getPosition(pet.getSize()));
                        sizeItem = pet.getSize();
                        gender.setSelection(genderAdapter.getPosition(pet.getGender()));
                        color.setSelection(colorAdapter.getPosition(pet.getColor()));
                        colorItem = pet.getColor();
                        petWeight.setText(pet.getWeight());
                        petDes.setText(pet.getDescription());
                        for (int i = 0; i < 5; i++){
                            if(!Objects.equals(pet.getImgUrl().get(i), "")){
                                imageUrl.set(i, pet.getImgUrl().get(i));
                                imageUrlCheckUpdate.set(i, pet.getImgUrl().get(i) );
                                imageUrlCheck.set(i, pet.getImgUrl().get(i));
                                Log.d("ShowImgUrl", pet.getImgUrl().get(i));
                                Picasso.get().load(pet.getImgUrl().get(i)).into(uploadImg.get(i));
                            }
                        }
//                        Log.d("ShowIntent", "GetTheRightThing");
                        idPet = getIntent().getStringExtra("idPet");
                        name = pet.getName();
//                        Log.d("checkTest","Name2: " + name);
                        description = pet.getDescription();
//                        Log.d("checkTest","description: " + description);
                        age.setSelection(ageAdapter.getPosition(pet.getAge()));
                        ageItem = pet.getAge();
//                        Log.d("age","age: " + age);
                        weight = pet.getWeight();
//                        Log.d("weight","weight: " + weight);
                        for (int i = 0; i < deleteImg.size(); i++){
                            if(!Objects.equals(pet.getImgUrl().get(i), "")){
                                deleteImg.get(i).setImageResource(R.drawable.deletebtn);
                            }
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                breed.setSelection(breedAdapter.getPosition(pet.getBreed()));
                                breedItem = pet.getBreed();
                            }
                        }, 1000);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("AdoptPet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    if(idPet.equals(snap.getValue(AdoptPet.class).getIdPet())){
                        adopt = snap.getValue(AdoptPet.class);
                        petPrice.setText(adopt.getPrice());
                        price = adopt.getPrice();
                        Log.d("adopt","adopt: " + adopt);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public boolean isName(){
        Log.d("checkTest","Name: " + name);
        Log.d("checkTest","TestName: " + petName.getText().toString());
        if(!name.equals(petName.getText().toString()))
        {
            databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
//                        Log.d("checkTest", "Accessed");
//                        Log.d("checkTest","idPet1: " + idPet);
//                        Log.d("checkTest","idPet2: " + snapshot.getValue(Pet.class).getIdPet());
                        databaseReference.child("Pet").child(idPet).child("name").setValue(petName.getText().toString());
                        name = petName.getText().toString();
                    } else {
                        Log.d("ConstraintViolation", "idPet does not match the desired value.");
                    }
                }}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseError", databaseError.getMessage());
                }
            });
            return true;
        }else{
            return false;
        }
    }
    public boolean isDes(){
        if(!description.equals(petDes.getText().toString()))
        {
            databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
                            databaseReference.child("Pet").child(idPet).child("description").setValue(petDes.getText().toString());
                            description = petDes.getText().toString();
                        } else {
                            Log.d("ConstraintViolation", "idPet does not match the desired value.");
                        }
                    }}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseError", databaseError.getMessage());
                }
            });
            return true;
        }else{
            return false;
        }
    }
    public boolean isPrice(){
        if(!price.equals(petPrice.getText().toString()))
        {
            databaseReference.child("AdoptPet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (idPet.equals(snapshot.getValue(AdoptPet.class).getIdPet())) {
                            databaseReference.child("AdoptPet").child(snapshot.getValue(AdoptPet.class).getId()).child("price").setValue(petPrice.getText().toString());
                            price = petPrice.getText().toString();
                        } else {
                            Log.d("ConstraintViolation", "idPet does not match the desired value.");
                        }
                    }}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseError", databaseError.getMessage());
                }
            });
            return true;
        }else{
            return false;
        }
    }
    public boolean isAge(){
        if (ageItem == null || age.getSelectedItem() == null) {
            return false;
        }
        if(!ageItem.equals(age.getSelectedItem().toString()))
        {
            databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
                            databaseReference.child("Pet").child(idPet).child("age").setValue(age.getSelectedItem().toString());
                            ageItem = age.getSelectedItem().toString();
                        } else {
                            Log.d("ConstraintViolation", "idPet does not match the desired value.");
                        }
                    }}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseError", databaseError.getMessage());
                }
            });
            return true;
        }else{
            return false;
        }
    }
//    public boolean isAge(){
//        if(!petAge.getText().toString().equals(age))
//        {
//            databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
//                            databaseReference.child("Pet").child(idPet).child("age").setValue(petAge.getText().toString());
//                            ageItem = age.getSelectedItem().toString();
//                        } else {
//                            Log.d("ConstraintViolation", "idPet does not match the desired value.");
//                        }
//                    }}
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Log.d("FirebaseError", databaseError.getMessage());
//                }
//            });
//            return true;
//        }else{
//            return false;
//        }
//    }
    public boolean isWeight(){
        if(!weight.equals(petWeight.getText().toString()))
        {
            databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
                            databaseReference.child("Pet").child(idPet).child("weight").setValue(petWeight.getText().toString());
                            weight = petWeight.getText().toString();
                        } else {
                            Log.d("ConstraintViolation", "idPet does not match the desired value.");
                        }
                    }}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseError", databaseError.getMessage());
                }
            });
            return true;
        }else{
            return false;
        }
    }

    public boolean isCategory(){
        if (categoryItem == null || category.getSelectedItem() == null) {
            return false;
        }
        if(!categoryItem.equals(category.getSelectedItem().toString()))
        {
            databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
                            databaseReference.child("Pet").child(idPet).child("category").setValue(category.getSelectedItem().toString());
                            categoryItem = category.getSelectedItem().toString();
                        } else {
                            Log.d("ConstraintViolation", "idPet does not match the desired value.");
                        }
                    }}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseError", databaseError.getMessage());
                }
            });
            return true;
        }else{
            return false;
        }
    }

    public boolean isColor (){
        if (colorItem == null || color.getSelectedItem() == null) {
            return false;
        }
        if(!colorItem.equals(color.getSelectedItem().toString()))
        {
            databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
                            databaseReference.child("Pet").child(idPet).child("color").setValue(color.getSelectedItem().toString());
                            colorItem = color.getSelectedItem().toString();
                        } else {
                            Log.d("ConstraintViolation", "idPet does not match the desired value.");
                        }
                    }}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseError", databaseError.getMessage());
                }
            });
            return true;
        }else{
            return false;
        }
    }

    public boolean isGender (){
        if (genderItem == null || gender.getSelectedItem() == null) {
            return false;
        }
        if(!genderItem.equals(gender.getSelectedItem().toString()))
        {
            databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
                            databaseReference.child("Pet").child(idPet).child("gender").setValue(gender.getSelectedItem().toString());
                            genderItem = gender.getSelectedItem().toString();
                        } else {
                            Log.d("ConstraintViolation", "idPet does not match the desired value.");
                        }
                    }}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseError", databaseError.getMessage());
                }
            });
            return true;
        }else{
            return false;
        }
    }

    public boolean isBreed(){

        if (breedItem == null || breed.getSelectedItem() == null) {
            return false;
        }

        if(!breedItem.equals(breed.getSelectedItem().toString())) {
            databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
                            databaseReference.child("Pet").child(idPet).child("breed").setValue(breed.getSelectedItem().toString());
                            breedItem = breed.getSelectedItem().toString();
                        } else {
                            Log.d("ConstraintViolation", "idPet does not match the desired value.");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseError", databaseError.getMessage());
                }
            });
            return true;
        } else {
            return false;
        }
    }

    public boolean isSize(){
        if (sizeItem == null || size.getSelectedItem() == null) {
            return false;
        }
        if(!sizeItem.equals(size.getSelectedItem().toString()))
        {
            databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
                            databaseReference.child("Pet").child(idPet).child("size").setValue(size.getSelectedItem().toString());
                            sizeItem = size.getSelectedItem().toString();
                        } else {
                            Log.d("ConstraintViolation", "idPet does not match the desired value.");
                        }
                    }}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseError", databaseError.getMessage());
                }
            });
            return true;
        }else{
            return false;
        }
    }

//    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == RESULT_OK) {
//                        if (result.getData() != null) {
//                            Uri selectedImageUri = result.getData().getData();
//                            if (selectedImageUri != null) {
//                                image.add(selectedImageUri);
//                                boolean imageSet = false;
//
//                                for (int i = 0; i < imageUrlCheck.size(); i++) {
//                                    if (imageUrlCheck.get(i).equals("no")) {
//                                        deleteImg.get(i).setImageResource(R.drawable.deletebtn);
//                                        Glide.with(getApplicationContext()).load(selectedImageUri).into(uploadImg.get(i));
//                                        imageUrlCheck.set(i, "yes");
//                                        imageSet = true;
//                                        break;
//                                    }
//                                }
//
//                                if (!imageSet) {
//                                    Toast.makeText(AddingPetActivity.this, "No empty slot to set the image", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                Toast.makeText(AddingPetActivity.this, "Failed to get image URI", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(AddingPetActivity.this, "Failed to get image data", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(AddingPetActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//    );

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            ClipData clipData = result.getData().getClipData();
                            if (clipData != null) {
                                for (int i = 0; i < clipData.getItemCount(); i++) {
                                    Uri selectedImageUri = clipData.getItemAt(i).getUri();
                                    handleSelectedImage(selectedImageUri);
                                }
                            } else {
                                Uri selectedImageUri = result.getData().getData();
                                handleSelectedImage(selectedImageUri);
                            }
                        } else {
                            Toast.makeText(AddingPetActivity.this, "Failed to get image data", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddingPetActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void handleSelectedImage(Uri selectedImageUri) {
        if (selectedImageUri != null) {
            image.add(selectedImageUri);
            boolean imageSet = false;

            for (int i = 0; i < imageUrlCheck.size(); i++) {
                if (imageUrlCheck.get(i).equals("no")) {
                    deleteImg.get(i).setImageResource(R.drawable.deletebtn);
                    Glide.with(getApplicationContext()).load(selectedImageUri).into(uploadImg.get(i));
                    imageUrlCheck.set(i, "yes");
                    imageSet = true;
                    break;
                }
            }

            if (!imageSet) {
                Toast.makeText(AddingPetActivity.this, "No empty slot to set the image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AddingPetActivity.this, "Failed to get image URI", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(List<Uri> files) {
        FirebaseApp.initializeApp(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        AtomicInteger uploadedCount = new AtomicInteger(0); // Counter for successful uploads

        // Ensure imageUrl list is large enough
        while (imageUrl.size() < files.size()) {
            imageUrl.add("");
        }

        for (Uri file : files) {
            if (!imagesToDelete.contains(file)) {
                StorageReference ref = storageReference.child(UUID.randomUUID().toString());
                ref.putFile(file).addOnSuccessListener(taskSnapshot -> {
                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        for (int i = 0; i < imageUrl.size(); i++) {
                            if (imageUrl.get(i).isEmpty()) {
                                imageUrl.set(i, uri.toString());
                                break;
                            }
                        }
                        uploadedCount.incrementAndGet(); // Increment counter for successful uploads

                        if (uploadedCount.get() == files.size() - imagesToDelete.size()) { // Check if all uploads are done
                            if (isImage) {
                                Log.d("ShowImg2", "Yes you are in");
                                updateImage();
                            } else {
                                uploadDataFirebase();
                            }
                            Toast.makeText(AddingPetActivity.this, "All images uploaded successfully!", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(AddingPetActivity.this, "You have to add images of pet and other fields", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(AddingPetActivity.this, "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }).addOnFailureListener(e -> {
                    Toast.makeText(AddingPetActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        }
    }



    private void updateImage(){
        databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
                        Log.d("IsImageChecked", idPet + ", " + imageUrl );
                        if(isImgDeleted){
                            databaseReference.child("Pet").child(idPet).child("imgUrl").setValue(imageUrlCheckUpdate);
                        }else{
                            databaseReference.child("Pet").child(idPet).child("imgUrl").setValue(imageUrl);
                        }
                    } else {
                        Log.d("ConstraintViolation", "idPet does not match the desired value.");
                    }
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseError", databaseError.getMessage());
            }
        });
    }
    private void uploadDataFirebase(){
        DatabaseReference petRef = databaseReference.child("Pet").push();
        DatabaseReference adoptRef = databaseReference.child("AdoptPet").push();
        DatabaseReference orderRef = databaseReference.child("AdoptOrder").push();
        addDatatoFirebase(petRef, adoptRef);
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(price)
                || TextUtils.isEmpty(weight) || category.getSelectedItemPosition() == 0 || age.getSelectedItemPosition() == 0
        || size.getSelectedItemPosition() == 0 || gender.getSelectedItemPosition() == 0 || color.getSelectedItemPosition() == 0
        || breed.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        pet = new Pet(ageItem,breedItem, "2", colorItem, description, genderItem, idPetKey, imageUrl, name, calendarText, sizeItem, categoryItem, weight,"1") ;
        adopt = new AdoptPet(null, idAdoptKey, idPetKey, price, "Castrated");
        petRef.setValue(pet);
        adoptRef.setValue(adopt);
        Toast.makeText(AddingPetActivity.this, "Adopt Pet added successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MyPetActivity.class));
    }

    private void addDatatoFirebase(DatabaseReference petRef, DatabaseReference adoptRef) {

        categoryItem = category.getSelectedItem().toString();

        sizeItem = size.getSelectedItem().toString();

        genderItem = gender.getSelectedItem().toString();

        colorItem = color.getSelectedItem().toString();

        breedItem = breed.getSelectedItem().toString();

        name = petName.getText().toString();
        description = petDes.getText().toString();
        price = petPrice.getText().toString();
        ageItem = age.getSelectedItem().toString();
        weight = petWeight.getText().toString();
        idPetKey = petRef.getKey();
        idAdoptKey = adoptRef.getKey();
        calendarText = simpleDateFormat.format(calendar.getTime());

    }
}
