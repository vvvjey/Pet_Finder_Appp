package com.example.pet_finder_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class AddingPetActivity extends AppCompatActivity {
    Toolbar arrowBack;
    Button backPet;
    FloatingActionButton addImg;
    ImageView uploadImg, deleteImg;
    Uri image;
    Pet pet;
    AdoptPet adopt;
    AdoptOrder order;
    String activity, idPet;
    String categoryItem, sizeItem, genderItem, colorItem, breedItem;
    Spinner category, size, gender, color, breed;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String typeId, name, description, price, age, weight, imageUrl, idPetKey, idAdoptKey, calendarText;
    StorageReference storageReference;
    EditText petName;
    EditText petDes;
    EditText petPrice;
    EditText petAge;
    EditText petWeight;
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    ArrayAdapter<CharSequence> categoryAdapter;
    ArrayAdapter<CharSequence> sizeAdapter;
    ArrayAdapter<CharSequence>  genderAdapter;
    ArrayAdapter<CharSequence> colorAdapter;
    ArrayAdapter<CharSequence> breedAdapter;

    boolean isImage = false;

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
        addImg = findViewById(R.id.add_img);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        backPet = findViewById(R.id.btn_add);
        uploadImg = findViewById(R.id.uploadImg);
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
                activityResultLauncher.launch(intent);
            }
        });
        Button btnAdd = findViewById(R.id.btn_add);



        category = (Spinner) findViewById(R.id.category_spinner);
        size = (Spinner) findViewById(R.id.size_spinner);
        gender = (Spinner) findViewById(R.id.gender_spinner);
        color = (Spinner) findViewById(R.id.color_spinner);
        breed = (Spinner) findViewById(R.id.breed_spinner);


        categoryAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.category,
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
        breedAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.breed,
                android.R.layout.simple_spinner_item
        );

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        category.setAdapter(categoryAdapter);
        size.setAdapter(sizeAdapter);
        gender.setAdapter(genderAdapter);
        color.setAdapter(colorAdapter);
        breed.setAdapter(breedAdapter);

        petName = findViewById(R.id.editNamePet);
        petDes = findViewById(R.id.description_edit);
        petPrice = findViewById(R.id.price_edit);
        petAge = findViewById(R.id.age_edit);
        petWeight = findViewById(R.id.weight_edit);
        simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        calendar = Calendar.getInstance();
        idPet = getIntent().getStringExtra("idPet");
        if(activity.equals("edit")){
            showData();
            Log.d("ShowIntent", activity);
            Log.d("Show id Pet", idPet);
            btnAdd.setText("Edit");
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isName() || isAge() || isBreed() || isCategory() ||
                            isColor() || isDes() || isGender() || isPrice() || isWeight() || isSize() || isImage){
                        if(isImage){
                            uploadImage(image);
                        }
                        Toast.makeText(AddingPetActivity.this, "Saved data", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MyPetActivity.class));
                    }
                    else{
                        Toast.makeText(AddingPetActivity.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                    }
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

        deleteImg = findViewById(R.id.deleteImg);
        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImg.setImageDrawable(null);
                deleteImg.setImageDrawable(null);
                Toast.makeText(AddingPetActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void showData(){
        databaseReference.child("Pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    Log.d("ShowIntent", idPet);
                    Log.d("ShowIntent", snap.getValue(Pet.class).getIdPet());
                    if(idPet.equals(snap.getValue(Pet.class).getIdPet())){
                        Log.d("ShowIntent", "GetTheRightThing");
                        pet = snap.getValue(Pet.class);
                        petName.setText(pet.getName());
                        breed.setSelection(breedAdapter.getPosition(pet.getBreed()));
                        category.setSelection(categoryAdapter.getPosition(pet.getTypeId()));
                        petAge.setText(pet.getAge());
                        size.setSelection(sizeAdapter.getPosition(pet.getSize()));
                        gender.setSelection(genderAdapter.getPosition(pet.getGender()));
                        color.setSelection(colorAdapter.getPosition(pet.getColor()));
                        petWeight.setText(pet.getWeight());
                        petDes.setText(pet.getDescription());
                        Picasso.get().load(pet.getImgUrl()).into(uploadImg);
//                        Log.d("ShowIntent", "GetTheRightThing");
                        idPet = getIntent().getStringExtra("idPet");
                        name = pet.getName();
//                        Log.d("checkTest","Name2: " + name);
                        description = pet.getDescription();
//                        Log.d("checkTest","description: " + description);
                        age = pet.getAge();
//                        Log.d("age","age: " + age);
                        weight = pet.getWeight();
//                        Log.d("weight","weight: " + weight);
                        deleteImg.setImageResource(R.drawable.deletebtn);
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
        if(!age.equals(petAge.getText().toString()))
        {
            databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
                            databaseReference.child("Pet").child(idPet).child("age").setValue(petAge.getText().toString());
                            age = petAge.getText().toString();
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

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    Glide.with(getApplicationContext()).load(image).into(uploadImg);
                    deleteImg.setImageResource(R.drawable.deletebtn);

                }
            } else {
                Toast.makeText(AddingPetActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
    });

    private void uploadImage(Uri file) {
        FirebaseApp.initializeApp(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference ref = storageReference.child(UUID.randomUUID().toString());
        ref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUrl = uri.toString();
                        if(isImage){
                            updateImage();
                        }else{
                            uploadDataFirebase();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddingPetActivity.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(AddingPetActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddingPetActivity.this, "Failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateImage(){
        databaseReference.child("Pet").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (idPet.equals(snapshot.getValue(Pet.class).getIdPet())) {
                        Log.d("IsImageChecked", idPet + ", " + imageUrl );
                        databaseReference.child("Pet").child(idPet).child("imgUrl").setValue(imageUrl);
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
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(price) ||
                TextUtils.isEmpty(age) || TextUtils.isEmpty(weight))  {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        pet = new Pet(age,breedItem, "2", colorItem, description, genderItem, idPetKey, imageUrl, name, calendarText, sizeItem, categoryItem, weight,"1") ;
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
        age = petAge.getText().toString();
        weight = petWeight.getText().toString();
        idPetKey = petRef.getKey();
        idAdoptKey = adoptRef.getKey();
        calendarText = simpleDateFormat.format(calendar.getTime());

    }
}
