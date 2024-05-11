package com.example.pet_finder_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import android.view.View;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pet_finder_app.Class.AdoptPet;
import com.example.pet_finder_app.Class.Pet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class AddingPetActivity extends AppCompatActivity {
    Toolbar arrowBack;
    Button backPet;
    FloatingActionButton addImg;
    ImageView uploadImg;
    Uri image;
    Pet pet;
    AdoptPet adopt;
    String categoryItem, sizeItem, genderItem, colorItem, breedItem;
    Spinner category, size, gender, color, breed;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String typeId, name, description, price, age, weight, imageUrl, idPetKey, idAdoptKey, calendarText;
    StorageReference storageReference;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    uploadImg = findViewById(R.id.uploadImg);
                    Glide.with(getApplicationContext()).load(image).into(uploadImg);
                }
            } else {
                Toast.makeText(AddingPetActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
    });
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
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("*/*");
                activityResultLauncher.launch(intent);
            }
        });



        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(image);
            }
        });

        category = (Spinner) findViewById(R.id.category_spinner);
        size = (Spinner) findViewById(R.id.size_spinner);
        gender = (Spinner) findViewById(R.id.gender_spinner);
        color = (Spinner) findViewById(R.id.color_spinner);
        breed = (Spinner) findViewById(R.id.breed_spinner);


        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.category,
                android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.size,
                android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender,
                android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.color,
                android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> breedAdapter = ArrayAdapter.createFromResource(
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
    }

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
                        uploadDataFirebase();
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

    private void uploadDataFirebase(){
        DatabaseReference petRef = databaseReference.child("Pet").push();
        DatabaseReference adoptRef = databaseReference.child("AdoptPet").push();
        addDatatoFirebase(petRef, adoptRef);
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(price) ||
                TextUtils.isEmpty(age) || TextUtils.isEmpty(weight))  {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        pet = new Pet(age, "2", colorItem, description, genderItem, idPetKey, imageUrl, name, calendarText, sizeItem, typeId, weight) ;
        adopt = new AdoptPet(null, idAdoptKey, idPetKey, price, "Castrated");
        petRef.setValue(pet);
        adoptRef.setValue(adopt);
        Toast.makeText(AddingPetActivity.this, "Adopt Pet added successfully", Toast.LENGTH_SHORT).show();
    }

    private void addDatatoFirebase(DatabaseReference petRef, DatabaseReference adoptRef) {
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryItem = category.getItemAtPosition(position).toString();
                typeId = getResources().getStringArray(R.array.category)[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sizeItem = size.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderItem = gender.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorItem = color.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        breed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                breedItem = breed.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        EditText petName = findViewById(R.id.editName);
        name = petName.getText().toString();
        EditText petDes = findViewById(R.id.description_edit);
        description = petDes.getText().toString();
        EditText petPrice = findViewById(R.id.price_edit);
        price = petPrice.getText().toString();
        EditText petAge = findViewById(R.id.age_edit);
        age = petAge.getText().toString();
        EditText petWeight = findViewById(R.id.weight_edit);
        weight = petWeight.getText().toString();
        idPetKey = petRef.getKey();
        idAdoptKey = adoptRef.getKey();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar calendar = Calendar.getInstance();
        calendarText = simpleDateFormat.format(calendar.getTime());

    }
}
