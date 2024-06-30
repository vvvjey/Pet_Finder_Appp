package com.example.pet_finder_app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.pet_finder_app.API.ApiService;
import com.example.pet_finder_app.API.DistrictPlaces;
import com.example.pet_finder_app.API.DistrictPlacesResponse;
import com.example.pet_finder_app.API.ProvincePlaces;
import com.example.pet_finder_app.API.ProvincePlacesResponse;
import com.example.pet_finder_app.API.WardPlaces;
import com.example.pet_finder_app.API.WardPlacesReponse;
import com.example.pet_finder_app.Class.MissingPet;
import com.example.pet_finder_app.Class.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FillInforAboutLostPet extends AppCompatActivity {
    private Toolbar arrowBack;
    List<ProvincePlaces> provinceList;
    List<DistrictPlaces> districtList;
    List<WardPlaces> wardList;
    private DatePickerDialog datePickerDialog;
    private Button dateButton,createMissingPostBtn,addImgBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private EditText fullname,address,phoneNumber,request,animalName,descriptionPet;
    StorageReference storageReference;
    FloatingActionButton addImg;
    private List<Uri> imagesToDelete = new ArrayList<>();
    List<Uri> image = new ArrayList<>();
    List<ImageView> uploadImg = new ArrayList<>(5);
    List<ImageView> deleteImg = new ArrayList<>(5);


    List<String> imageUrl = new ArrayList<>(Collections.nCopies(5, ""));
    List<String> imageUrlCheck = new ArrayList<>(Collections.nCopies(5, ""));
    Spinner dropdownPurpose ;
    Spinner dropdownCountry ;
    Spinner dropdownCity ;
    Spinner dropdownDistrict ;
    Spinner dropdownWard ;
    Spinner dropdownGender ;
    Spinner dropdownColor ;
    Spinner dropdownBreed;
    Spinner dropdownAge ;
    Spinner dropdownSize ;
    private boolean isEditMode;
    private String idUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_infor_about_lost_pet);
        arrowBack = (Toolbar) findViewById(R.id.toolbarArrowBack6);
//        fullname = findViewById(R.id.fullNameInput);
        address = findViewById(R.id.addressInput);
//        phoneNumber = findViewById(R.id.phoneNumberInput);
        request = findViewById(R.id.requestInput);
        animalName = findViewById(R.id.animalNameInput);
        descriptionPet = findViewById(R.id.descriptionPetInput);
        createMissingPostBtn = findViewById(R.id.createMissingPostBtn);
        addImg = findViewById(R.id.add_img);
        imageUrlCheck = new ArrayList<>(Arrays.asList("no", "no", "no", "no", "no"));
         dropdownPurpose = findViewById(R.id.purposeSpinner);
         dropdownCountry = findViewById(R.id.countrySpinner);
         dropdownCity = findViewById(R.id.citySpinner);
         dropdownDistrict = findViewById(R.id.districtSpinner);
         dropdownWard = findViewById(R.id.wardSpinner);
         dropdownSize = findViewById(R.id.sizeSpinner);
         dropdownGender = (Spinner)findViewById(R.id.genderSpinner);
         dropdownColor = findViewById(R.id.colorSpinner);
         dropdownBreed = findViewById(R.id.breedSpinner);
         dropdownAge = findViewById(R.id.ageSpinner);
        uploadImg.add(findViewById(R.id.uploadImg1));
        uploadImg.add(findViewById(R.id.uploadImg2));
        uploadImg.add(findViewById(R.id.uploadImg3));
        uploadImg.add(findViewById(R.id.uploadImg4));
        uploadImg.add(findViewById(R.id.uploadImg5));

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


        String[] dropdownPurposeItems = new String[]{"Missing", "Seen", "Protected"};
//        String[] dropdownCountryItems = new String[]{"Vietnam"};
        String[] dropdownSizeItems = new String[]{"Small","Medium","Large"};
        String[] dropdownGenderItems = new String[]{"Male","Female"};
        String[] dropdownColorItems = new String[]{"Red","Yellow","Brown","Black","White","Other"};
        String[] dropdownBreedItems = new String[]{"India","USA","China","Japan","Other"};
        String[] dropdownAgeItems = new String[]{"Baby","Young","Adult","Senior"};


        List<String> provinceNames = new ArrayList<>();
        List<String> districtName = new ArrayList<>();
        List<String> wardName = new ArrayList<>();
        ArrayAdapter<String> dropdownPurposeAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dropdownPurposeItems);
//        ArrayAdapter<String> dropdownCountryAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dropdownCountryItems);
        ArrayAdapter<String> dropdownSizeAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dropdownSizeItems);
        ArrayAdapter<String> dropdownGenderAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dropdownGenderItems);
        ArrayAdapter<String> dropdownColorAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dropdownColorItems);
        ArrayAdapter<String> dropdownBreedAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dropdownBreedItems);
        ArrayAdapter<String> dropdownAgeAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dropdownAgeItems);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        dropdownPurpose.setAdapter(dropdownPurposeAdapter);
//        dropdownCountry.setAdapter(dropdownCountryAdapter);
        dropdownSize.setAdapter(dropdownSizeAdapter);
        dropdownGender.setAdapter(dropdownGenderAdapter);
        dropdownColor.setAdapter(dropdownColorAdapter);
        dropdownBreed.setAdapter(dropdownBreedAdapter);
        dropdownAge.setAdapter(dropdownAgeAdapter);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int dropdownWidth = screenWidth - (int) (50 * getResources().getDisplayMetrics().density);
        dropdownPurpose.setDropDownWidth(dropdownWidth);

//        DATE PICKER
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        initDatePicker();


//        Check Create or Edit
        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);
        if (isEditMode) {
            createMissingPostBtn.setText("Edit");
            FillDataEdit();
        } else {
            createMissingPostBtn.setText("Create");
        }
//


//        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();
////        Initial retrofit
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://vapi.vnappmob.com")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        ApiService api = retrofit.create(ApiService.class);
//
////////////////////////////////////////////////////////
////        CALL GET ALL PROVINCE
//        Call<ProvincePlacesResponse> provinceCall = api.getListProvinces();
//        provinceCall.enqueue(new Callback<ProvincePlacesResponse>() {
//            @Override
//            public void onResponse(Call<ProvincePlacesResponse> call, Response<ProvincePlacesResponse> response) {
//                if (response.isSuccessful()) {
//                    ProvincePlacesResponse provincesResponse = response.body();
//                    if (provincesResponse != null) {
//                        provinceList = provincesResponse.getResults();
//                        for (ProvincePlaces province : provinceList) {
//                            provinceNames.add(province.getProvinceName());
//                        }
//                        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(FillInforAboutLostPet.this, android.R.layout.simple_spinner_dropdown_item, provinceNames);
//                        dropdownCity.setAdapter(cityAdapter);
//                    }
//                } else {
//                    Log.e("API Error", "Failed to fetch provinces: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProvincePlacesResponse> call, Throwable t) {
//                Log.e("API Error", "Failed to fetch provinces: " + t.getMessage());
//            }
//        });
//        //CALL GET DISTRICT BASED ON PROVINCE
//
//        dropdownCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedProvinceName = (String) parent.getItemAtPosition(position);
//
//                for (ProvincePlaces province : provinceList) {
//                    if (province.getProvinceName().equals(selectedProvinceName)) {
//                        Log.d("Selected Province ID", province.getProvinceId());
//                        Call<DistrictPlacesResponse> districtCall = api.getDistrictById(province.getProvinceId());
//                        districtCall.enqueue(new Callback<DistrictPlacesResponse>() {
//                            @Override
//                            public void onResponse(Call<DistrictPlacesResponse> call, Response<DistrictPlacesResponse> response) {
//                                if (response.isSuccessful()) {
//                                    DistrictPlacesResponse districtsResponse = response.body();
//                                    if (districtsResponse != null) {
//                                        districtList = districtsResponse.getResults();
//                                        districtName.clear();
//                                        for (DistrictPlaces district : districtList) {
//                                            districtName.add(district.getDistrict_name());
//                                        }
//                                        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(FillInforAboutLostPet.this, android.R.layout.simple_spinner_dropdown_item, districtName);
//                                        dropdownDistrict.setAdapter(districtAdapter);
//                                    }
//                                } else {
//                                    Log.e("API Error", "Failed to fetch provinces: " + response.message());
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<DistrictPlacesResponse> call, Throwable throwable) {
//                                Log.e("API Error", "Failed to fetch provinces: " + throwable.getMessage());
//                            }
//                        });
//                        break;
//                    }
//                }
//            }
//            //CALL GET DISTRICT BASED ON PROVINCE
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//// CALL GET WARD BASED ON DISTRICT
//        dropdownDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedDistrictName = (String) parent.getItemAtPosition(position);
//                for (DistrictPlaces district : districtList) {
//                    if (district.getDistrict_name().equals(selectedDistrictName)) {
//                        Log.d("Selected District ID", district.getDistrict_id());
//                        Call<WardPlacesReponse> wardCall = api.getWardById(district.getDistrict_id());
//                        wardCall.enqueue(new Callback<WardPlacesReponse>() {
//                            @Override
//                            public void onResponse(Call<WardPlacesReponse> call, Response<WardPlacesReponse> response) {
//                                if (response.isSuccessful()) {
//                                    WardPlacesReponse wardsResponse = response.body();
//                                    if (wardsResponse != null) {
//                                        wardList = wardsResponse.getResults();
//                                        wardName.clear();
//                                        for (WardPlaces ward : wardList) {
//                                            wardName.add(ward.getWard_name());
//                                        }
//                                        ArrayAdapter<String> wardAdapter = new ArrayAdapter<>(FillInforAboutLostPet.this, android.R.layout.simple_spinner_dropdown_item, wardName);
//                                        dropdownWard.setAdapter(wardAdapter);
//                                    }
//                                } else {
//                                    Log.e("API Error", "Failed to fetch provinces: " + response.message());
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<WardPlacesReponse> call, Throwable throwable) {
//                                Log.e("API Error", "Failed to fetch provinces: " + throwable.getMessage());
//                            }
//                        });
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
////////////////////////////////////////////////////////////////////////

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
            }
        });
//        Handle create new missing pet post
        createMissingPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //            Handle validate required input
                if(!validateInputs()){
                    Toast.makeText(FillInforAboutLostPet.this, "Missing required input", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isEditMode) {
                    updateMissingPost("1");
                } else {
                    uploadImage(image);
                }
            }
        });


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

            Toast.makeText(FillInforAboutLostPet.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }
    private void createMissingPost(){
        try{

            DatabaseReference missingPetRef = FirebaseDatabase.getInstance().getReference().child("Missing pet");
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            FirebaseUser currentUser = mAuth.getCurrentUser();

            String missingPetKey = missingPetRef.push().getKey();
            String age = dropdownAge.getSelectedItem().toString();
            String breed = dropdownBreed.getSelectedItem().toString();
            String categoryId = "2";
            String color = dropdownColor.getSelectedItem().toString();
            String description = descriptionPet.getText().toString();
            String gender = dropdownGender.getSelectedItem().toString();
            String idPet = missingPetRef.push().getKey();;
            String petName = animalName.getText().toString();
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String registerDate = dateFormat.format(cal.getTime());
            String size = dropdownSize.getSelectedItem().toString();
            String typeId = "Cat";
            String weight = "no";
            String id = "1";
            String postUserId = currentUser.getUid();
            String typeMissing = dropdownPurpose.getSelectedItem().toString();
            String addressMissing = address.getText().toString();
            String dateMissing = dateButton.getText().toString();
            String requestPoster = request.getText().toString();
            String statusMissing = "Waiting";





            MissingPet pet = new MissingPet(age,breed, categoryId, color, description, gender, idPet, imageUrl, petName, registerDate, size, typeId, weight, id, typeMissing,addressMissing, dateMissing, requestPoster,postUserId,statusMissing);
            missingPetRef.child(missingPetKey).child("age").setValue(pet.getAge());
            missingPetRef.child(missingPetKey).child("breed").setValue(pet.getBreed());
            missingPetRef.child(missingPetKey).child("categoryId").setValue(pet.getCategoryId());
            missingPetRef.child(missingPetKey).child("color").setValue(pet.getColor());
            missingPetRef.child(missingPetKey).child("description").setValue(pet.getDescription());
            missingPetRef.child(missingPetKey).child("gender").setValue(pet.getGender());
            missingPetRef.child(missingPetKey).child("idPet").setValue(pet.getIdPet());
            missingPetRef.child(missingPetKey).child("imgUrl").setValue(pet.getImgUrl());
            missingPetRef.child(missingPetKey).child("name").setValue(pet.getName());
            missingPetRef.child(missingPetKey).child("registerDate").setValue(pet.getRegisterDate());
            missingPetRef.child(missingPetKey).child("size").setValue(pet.getSize());
            missingPetRef.child(missingPetKey).child("typeId").setValue(pet.getTypeId());
            missingPetRef.child(missingPetKey).child("weight").setValue(pet.getWeight());
            missingPetRef.child(missingPetKey).child("id").setValue(pet.getId());
            missingPetRef.child(missingPetKey).child("typeMissing").setValue(pet.getTypeMissing());
            missingPetRef.child(missingPetKey).child("addressMissing").setValue(pet.getAddressMissing());
            missingPetRef.child(missingPetKey).child("dateMissing").setValue(pet.getDateMissing());
            missingPetRef.child(missingPetKey).child("requestPosterMissing").setValue(pet.getRequestPosterMissing());
            missingPetRef.child(missingPetKey).child("postUserId").setValue(pet.getPostUserId());
            missingPetRef.child(missingPetKey).child("status").setValue(pet.getStatusMissing());



            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Success");
            builder.setMessage("Missing pet post created successfully!");
            builder.setPositiveButton("OK", (dialog, which) -> {
                // Start SearchingLostPetActivity
                startActivity(new Intent(getApplicationContext(), SearchingLostPetActivity.class));
                finish(); // Finish the current activity to prevent going back to it with back button
            });
            builder.setCancelable(false); // Prevent dismissing dialog by clicking outside
            builder.show();        } catch (Exception e) {
            Log.e("Error","Error:",e);
        }

    }

    private void uploadImage(List<Uri> files) {
        FirebaseApp.initializeApp(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        AtomicInteger uploadedCount = new AtomicInteger(0); // Counter for successful uploads

        for (Uri file : image) {
            if (!imagesToDelete.contains(file)) { // Skip images that are marked for deletion
                StorageReference ref = storageReference.child(UUID.randomUUID().toString());
                ref.putFile(file).addOnSuccessListener(taskSnapshot -> {
                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        for (int i = 0; i < imageUrl.size(); i++) {
                            if (imageUrl.get(i).equals("") || imageUrl.get(i).isEmpty()) {
                                imageUrl.set(i, uri.toString());
                                break;
                            }
                        }
                        uploadedCount.incrementAndGet();

                        if (uploadedCount.get() == image.size() - imagesToDelete.size()) { // Check if all uploads are done, excluding deleted images
                            createMissingPost();
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(FillInforAboutLostPet.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                    });
                    Toast.makeText(FillInforAboutLostPet.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(FillInforAboutLostPet.this, "Failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

    private boolean validateInputs() {
        String addressText = address.getText().toString().trim();
        String animalNameText = animalName.getText().toString().trim();
        String descriptionPetText = descriptionPet.getText().toString().trim();
        String userRequestText = request.getText().toString().trim();

        if (addressText.isEmpty()) {
            address.setError("Address is required");
            return false;
        }

        if (animalNameText.isEmpty()) {
            animalName.setError("Animal name is required");
            return false;
        }

        if (descriptionPetText.isEmpty()) {
            descriptionPet.setError("Description is required");
            return false;
        }
        if (userRequestText.isEmpty()) {
            address.setError("user request is required");
            return false;
        }


        return true;
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
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
                            Toast.makeText(FillInforAboutLostPet.this, "Failed to get image data", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(FillInforAboutLostPet.this, "Please select an image", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(FillInforAboutLostPet.this, "No empty slot to set the image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(FillInforAboutLostPet.this, "Failed to get image URI", Toast.LENGTH_SHORT).show();
        }
    }
    private void FillDataEdit(){
        Intent intent = getIntent();

        String idPet = intent.getStringExtra("idPet");
        String petName = intent.getStringExtra("petName");
        String petAge = intent.getStringExtra("petAge");
        String petSize = intent.getStringExtra("petSize");
        String petBreed = intent.getStringExtra("petBreed");
        String addressMissing = intent.getStringExtra("addressMissing");
        String petGender = intent.getStringExtra("petGender");
        String petColor = intent.getStringExtra("petColor");
        String registerDate = intent.getStringExtra("petRegisterDate");
        String petMissingDate = intent.getStringExtra("petMissingDate");

        String typeMissing = intent.getStringExtra("petTypeMissing");
        String petDescription = intent.getStringExtra("desciptionPet");

        String petImageUrl = intent.getStringExtra("petImageUrl");
        animalName.setText(petName);
        descriptionPet.setText(petDescription);
//        if (petImageUrl != null && !petImageUrl.isEmpty()) {
//            Picasso.get().load(petImageUrl).into(uploadImg.get(0));
//        }
        if (databaseReference == null) {
            Log.e("MissingAnimalPostContactActivity", "Firebase Database reference is null");
            return;
        }
        databaseReference.child("Missing pet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    MissingPet pet = snap.getValue(MissingPet.class);
                    Log.d("ShowIdPetPost", idPet);
                    if(idPet.equals(pet.getIdPet())){
                        request.setText(pet.getRequestPosterMissing());
                        idUser = pet.getPostUserId();
                        for (int i = 0; i < 5; i++){
                            if(!Objects.equals(pet.getImgUrl().get(i), "")){
                                imageUrl.set(i, pet.getImgUrl().get(i));
//                                imageUrlCheckUpdate.set(i, pet.getImgUrl().get(i) );
                                imageUrlCheck.set(i, pet.getImgUrl().get(i));
                                Log.d("ShowImgUrl", pet.getImgUrl().get(i));
                                Picasso.get().load(pet.getImgUrl().get(i)).into(uploadImg.get(i));
                            }
                        }
                        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snap: snapshot.getChildren()){
                                    User user = snap.getValue(User.class);
                                    if(idUser.equals(snap.getValue(User.class).getUserId())){
                                        assert user != null;
                                        fullname.setText(user.getFullname());
                                        Log.d("ShowImgUrl", user.getFullname());
                                        address.setText(user.getAddress());
                                        phoneNumber.setText(user.getPhoneNumber());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }});

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }});

        setSpinnerSelection(dropdownAge, petAge);
        setSpinnerSelection(dropdownSize, petSize);
        setSpinnerSelection(dropdownBreed, petBreed);
        setSpinnerSelection(dropdownGender, petGender);
        setSpinnerSelection(dropdownColor, petColor);
        setSpinnerSelection(dropdownPurpose, typeMissing);
        if (petMissingDate != null && !petMissingDate.isEmpty()) {
            dateButton.setText(petMissingDate);
        }
        addImg = findViewById(R.id.add_img);
//        if (petImageUrl != null && !petImageUrl.isEmpty()) {
//            Picasso.get().load(petImageUrl).into(uploadImg.get(0));
//        }
    }
    private void setSpinnerSelection(Spinner spinner, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(value);
            if (position >= 0) {
                spinner.setSelection(position);
            }
        }
    }
    private void updateMissingPost(String id) {
        try {
            DatabaseReference missingPetRef = FirebaseDatabase.getInstance().getReference().child("Missing pet");

            missingPetRef.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();
                        if (key != null) {
                            DatabaseReference missingPetNodeRef = missingPetRef.child(key);

                            if (image != null) {

                                uploadImageUpdate(image, new OnImageUploadListener() {
                                    @Override
                                    public void onImageUploadSuccess(String imageUrl) {
                                        // Once the image is uploaded successfully, update the missing pet node with the new image URL
                                        updateMissingPetNodeWithNewImage(missingPetNodeRef, snapshot, imageUrl);
                                    }

                                    @Override
                                    public void onImageUploadFailure(String message) {
                                        Toast.makeText(FillInforAboutLostPet.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                String imgUrl = snapshot.child("imgUrl").getValue(String.class);
                                String age = dropdownAge.getSelectedItem().toString();
                                String breed = dropdownBreed.getSelectedItem().toString();
                                String categoryId = "2";
                                String color = dropdownColor.getSelectedItem().toString();
                                String description = descriptionPet.getText().toString();
                                String gender = dropdownGender.getSelectedItem().toString();
                                String idPet = "1";
                                String petName = animalName.getText().toString();
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String registerDate = dateFormat.format(cal.getTime());
                                String size = dropdownSize.getSelectedItem().toString();
                                String typeId = "Cat";
                                String weight = "no";
                                String id = "1";
                                String typeMissing = dropdownPurpose.getSelectedItem().toString();
                                String addressMissing = "address";
                                String dateMissing = dateButton.getText().toString();
                                String requestPoster = request.getText().toString();
                                String statusMissing = "Waiting";
                                String postUserId = "1";

                                HashMap<String, Object> postValues = new HashMap<>();
                                postValues.put("age", age);
                                postValues.put("breed", breed);
                                postValues.put("categoryId", categoryId);
                                postValues.put("color", color);
                                postValues.put("description", description);
                                postValues.put("gender", gender);
                                postValues.put("idPet", idPet);
                                postValues.put("imgUrl", imgUrl);
                                postValues.put("name", petName);
                                postValues.put("registerDate", registerDate);
                                postValues.put("size", size);
                                postValues.put("typeId", typeId);
                                postValues.put("weight", weight);
                                postValues.put("id", id);
                                postValues.put("typeMissing", typeMissing);
                                postValues.put("addressMissing", addressMissing);
                                postValues.put("dateMissing", dateMissing);
                                postValues.put("requestPoster", requestPoster);
                                postValues.put("postUserId", postUserId);
                                postValues.put("status", statusMissing);

                                missingPetNodeRef.updateChildren(postValues)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(FillInforAboutLostPet.this);
                                                builder.setTitle("Success");
                                                builder.setMessage("Missing pet post updated successfully!");
                                                builder.setPositiveButton("OK", (dialog, which) -> {
                                                    // Start SearchingLostPetActivity
                                                    startActivity(new Intent(getApplicationContext(), MissingAnimalsPostActivity.class));
                                                    finish(); // Finish the current activity to prevent going back to it with back button
                                                });
                                                builder.setCancelable(false); // Prevent dismissing dialog by clicking outside
                                                builder.show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("Error", "Error updating missing pet post", e);
                                                Toast.makeText(FillInforAboutLostPet.this, "Failed to update missing pet post", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Error", "Error querying database", databaseError.toException());
                    Toast.makeText(FillInforAboutLostPet.this, "Failed to update missing pet post", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("Error", "Error:", e);
        }
    }
    interface OnImageUploadListener {
        void onImageUploadSuccess(String imageUrl);
        void onImageUploadFailure(String message);
    }
    private void uploadImageUpdate(List<Uri> file, OnImageUploadListener listener) {
        FirebaseApp.initializeApp(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference ref = storageReference.child("missingPetAssets" + UUID.randomUUID().toString());
        for (int i = 0; i < file.size(); i++){
            ref.putFile(file.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = uri.toString();
                            listener.onImageUploadSuccess(imageUrl);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onImageUploadFailure("Failed to get download URL");
                        }
                    });
                    Toast.makeText(FillInforAboutLostPet.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    listener.onImageUploadFailure("Failed!" + e.getMessage());
                }
            });
        }

    }

    private void updateMissingPetNodeWithNewImage(DatabaseReference missingPetNodeRef, DataSnapshot snapshot, String newImageUrl) {
        // Update the missing pet node with the new image URL
        snapshot.getRef().child("imgUrl").setValue(newImageUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    String imgUrl = newImageUrl;
                    String age = dropdownAge.getSelectedItem().toString();
                    String breed = dropdownBreed.getSelectedItem().toString();
                    String categoryId = "2";
                    String color = dropdownColor.getSelectedItem().toString();
                    String description = descriptionPet.getText().toString();
                    String gender = dropdownGender.getSelectedItem().toString();
                    String idPet = "1";
                    String petName = animalName.getText().toString();
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String registerDate = dateFormat.format(cal.getTime());
                    String size = dropdownSize.getSelectedItem().toString();
                    String typeId = "Cat";
                    String weight = "no";
                    String id = "1";
                    String typeMissing = dropdownPurpose.getSelectedItem().toString();
                    String addressMissing = "address";
                    String dateMissing = dateButton.getText().toString();
                    String requestPoster = request.getText().toString();
                    String statusMissing = "Waiting";
                    String postUserId = "1";

                    HashMap<String, Object> postValues = new HashMap<>();
                    postValues.put("age", age);
                    postValues.put("breed", breed);
                    postValues.put("categoryId", categoryId);
                    postValues.put("color", color);
                    postValues.put("description", description);
                    postValues.put("gender", gender);
                    postValues.put("idPet", idPet);
                    postValues.put("imgUrl", imgUrl);
                    postValues.put("name", petName);
                    postValues.put("registerDate", registerDate);
                    postValues.put("size", size);
                    postValues.put("typeId", typeId);
                    postValues.put("weight", weight);
                    postValues.put("id", id);
                    postValues.put("typeMissing", typeMissing);
                    postValues.put("addressMissing", addressMissing);
                    postValues.put("dateMissing", dateMissing);
                    postValues.put("requestPoster", requestPoster);
                    postValues.put("postUserId", postUserId);
                    postValues.put("status", statusMissing);

                    missingPetNodeRef.updateChildren(postValues)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(FillInforAboutLostPet.this);
                                    builder.setTitle("Success");
                                    builder.setMessage("Missing pet post updated successfully!");
                                    builder.setPositiveButton("OK", (dialog, which) -> {
                                        // Start SearchingLostPetActivity
                                        startActivity(new Intent(getApplicationContext(), MissingAnimalsPostActivity.class));
                                        finish(); // Finish the current activity to prevent going back to it with back button
                                    });
                                    builder.setCancelable(false); // Prevent dismissing dialog by clicking outside
                                    builder.show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("Error", "Error updating missing pet post", e);
                                    Toast.makeText(FillInforAboutLostPet.this, "Failed to update missing pet post", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    // Failed to update the missing pet node with the new image URL
                    Toast.makeText(FillInforAboutLostPet.this, "Failed to update image URL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
