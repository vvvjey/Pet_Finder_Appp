package com.example.pet_finder_app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
    private EditText fullname,address,phoneNumber,request,animalName,descriptionPet;
    StorageReference storageReference;
    FloatingActionButton addImg;
    Uri image;
    ImageView uploadImg;

    String imageUrl;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_infor_about_lost_pet);
        arrowBack = (Toolbar) findViewById(R.id.toolbarArrowBack6);
        fullname = findViewById(R.id.fullNameInput);
        address = findViewById(R.id.addressInput);
        phoneNumber = findViewById(R.id.phoneNumberInput);
        request = findViewById(R.id.requestInput);
        animalName = findViewById(R.id.animalNameInput);
        descriptionPet = findViewById(R.id.descriptionPetInput);
        createMissingPostBtn = findViewById(R.id.createMissingPostBtn);
        addImg = findViewById(R.id.add_img);

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
        uploadImg = findViewById(R.id.uploadImg);


        String[] dropdownPurposeItems = new String[]{"Missing", "Seen", "Protected"};
        String[] dropdownCountryItems = new String[]{"Vietnam"};
        String[] dropdownSizeItems = new String[]{"Small","Medium","Large"};
        String[] dropdownGenderItems = new String[]{"Male","Female"};
        String[] dropdownColorItems = new String[]{"Red","Yellow","Brown","Black","White","Other"};
        String[] dropdownBreedItems = new String[]{"India","USA","China","Japan","Other"};
        String[] dropdownAgeItems = new String[]{"Baby","Young","Adult","Senior"};


        List<String> provinceNames = new ArrayList<>();
        List<String> districtName = new ArrayList<>();
        List<String> wardName = new ArrayList<>();
        ArrayAdapter<String> dropdownPurposeAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dropdownPurposeItems);
        ArrayAdapter<String> dropdownCountryAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dropdownCountryItems);
        ArrayAdapter<String> dropdownSizeAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dropdownSizeItems);
        ArrayAdapter<String> dropdownGenderAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dropdownGenderItems);
        ArrayAdapter<String> dropdownColorAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dropdownColorItems);
        ArrayAdapter<String> dropdownBreedAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dropdownBreedItems);
        ArrayAdapter<String> dropdownAgeAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dropdownAgeItems);


        dropdownPurpose.setAdapter(dropdownPurposeAdapter);
        dropdownCountry.setAdapter(dropdownCountryAdapter);
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


//


        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();
//        Initial retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://vapi.vnappmob.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService api = retrofit.create(ApiService.class);

//////////////////////////////////////////////////////
//        CALL GET ALL PROVINCE
        Call<ProvincePlacesResponse> provinceCall = api.getListProvinces();
        provinceCall.enqueue(new Callback<ProvincePlacesResponse>() {
            @Override
            public void onResponse(Call<ProvincePlacesResponse> call, Response<ProvincePlacesResponse> response) {
                if (response.isSuccessful()) {
                    ProvincePlacesResponse provincesResponse = response.body();
                    if (provincesResponse != null) {
                        provinceList = provincesResponse.getResults();
                        for (ProvincePlaces province : provinceList) {
                            provinceNames.add(province.getProvinceName());
                        }
                        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(FillInforAboutLostPet.this, android.R.layout.simple_spinner_dropdown_item, provinceNames);
                        dropdownCity.setAdapter(cityAdapter);
                    }
                } else {
                    Log.e("API Error", "Failed to fetch provinces: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProvincePlacesResponse> call, Throwable t) {
                Log.e("API Error", "Failed to fetch provinces: " + t.getMessage());
            }
        });
        //CALL GET DISTRICT BASED ON PROVINCE

        dropdownCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProvinceName = (String) parent.getItemAtPosition(position);

                for (ProvincePlaces province : provinceList) {
                    if (province.getProvinceName().equals(selectedProvinceName)) {
                        Log.d("Selected Province ID", province.getProvinceId());
                        Call<DistrictPlacesResponse> districtCall = api.getDistrictById(province.getProvinceId());
                        districtCall.enqueue(new Callback<DistrictPlacesResponse>() {
                            @Override
                            public void onResponse(Call<DistrictPlacesResponse> call, Response<DistrictPlacesResponse> response) {
                                if (response.isSuccessful()) {
                                    DistrictPlacesResponse districtsResponse = response.body();
                                    if (districtsResponse != null) {
                                        districtList = districtsResponse.getResults();
                                        districtName.clear();
                                        for (DistrictPlaces district : districtList) {
                                            districtName.add(district.getDistrict_name());
                                        }
                                        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(FillInforAboutLostPet.this, android.R.layout.simple_spinner_dropdown_item, districtName);
                                        dropdownDistrict.setAdapter(districtAdapter);
                                    }
                                } else {
                                    Log.e("API Error", "Failed to fetch provinces: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<DistrictPlacesResponse> call, Throwable throwable) {
                                Log.e("API Error", "Failed to fetch provinces: " + throwable.getMessage());
                            }
                        });
                        break;
                    }
                }
            }
            //CALL GET DISTRICT BASED ON PROVINCE

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
// CALL GET WARD BASED ON DISTRICT
        dropdownDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDistrictName = (String) parent.getItemAtPosition(position);
                for (DistrictPlaces district : districtList) {
                    if (district.getDistrict_name().equals(selectedDistrictName)) {
                        Log.d("Selected District ID", district.getDistrict_id());
                        Call<WardPlacesReponse> wardCall = api.getWardById(district.getDistrict_id());
                        wardCall.enqueue(new Callback<WardPlacesReponse>() {
                            @Override
                            public void onResponse(Call<WardPlacesReponse> call, Response<WardPlacesReponse> response) {
                                if (response.isSuccessful()) {
                                    WardPlacesReponse wardsResponse = response.body();
                                    if (wardsResponse != null) {
                                        wardList = wardsResponse.getResults();
                                        wardName.clear();
                                        for (WardPlaces ward : wardList) {
                                            wardName.add(ward.getWard_name());
                                        }
                                        ArrayAdapter<String> wardAdapter = new ArrayAdapter<>(FillInforAboutLostPet.this, android.R.layout.simple_spinner_dropdown_item, wardName);
                                        dropdownWard.setAdapter(wardAdapter);
                                    }
                                } else {
                                    Log.e("API Error", "Failed to fetch provinces: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<WardPlacesReponse> call, Throwable throwable) {
                                Log.e("API Error", "Failed to fetch provinces: " + throwable.getMessage());
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchingLostPetActivity.class));
            }
        });
////////////////////////////////////////////////////////////////////////

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("*/*");
                activityResultLauncher.launch(intent);
            }
        });
//        Handle create new missing pet post
        createMissingPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              uploadImage(image);
            }
        });

    }
    private void createMissingPost(){
        try{
            DatabaseReference missingPetRef = FirebaseDatabase.getInstance().getReference().child("Missing pet");

            String missingPetKey = missingPetRef.push().getKey();
            String age = dropdownAge.getSelectedItem().toString();
            String categoryId = "2";
            String color = dropdownColor.getSelectedItem().toString();
            String description = "no";
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
            String detailDescription = descriptionPet.getText().toString();

            MissingPet pet = new MissingPet(age, categoryId, color, description, gender, idPet, imageUrl, petName, registerDate, size, typeId, weight, id, typeMissing,addressMissing, dateMissing, detailDescription);
            missingPetRef.child(missingPetKey).child("age").setValue(pet.getAge());
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
            missingPetRef.child(missingPetKey).child("detailDescription").setValue(pet.getDetailDescription());

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
                        createMissingPost();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FillInforAboutLostPet.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(FillInforAboutLostPet.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FillInforAboutLostPet.this, "Failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validateInputs() {
        String fullName = fullname.getText().toString().trim();
        String addressText = address.getText().toString().trim();
        String phoneNumberText = phoneNumber.getText().toString().trim();
        String animalNameText = animalName.getText().toString().trim();
        String descriptionPetText = descriptionPet.getText().toString().trim();

        if (fullName.isEmpty()) {
            fullname.setError("Full name is required");
            return false;
        }

        if (addressText.isEmpty()) {
            address.setError("Address is required");
            return false;
        }

        if (phoneNumberText.isEmpty()) {
            phoneNumber.setError("Phone number is required");
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
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    Glide.with(getApplicationContext()).load(image).into(uploadImg);
                }
            } else {
                Toast.makeText(FillInforAboutLostPet.this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
    });
}
