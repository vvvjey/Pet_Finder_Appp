package com.example.pet_finder_app;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.pet_finder_app.Class.AdoptOrder;
import com.example.pet_finder_app.Class.Appoitment;
import com.example.pet_finder_app.Class.Pet;
import com.example.pet_finder_app.Class.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FillInforToAdoptActivity extends AppCompatActivity {
    private Toolbar arrowBack;
    private Spinner genderSpinner;
    private String[] genderDropdownValue = {"Male","Female"};
    List<ProvincePlaces> provinceList;
    List<DistrictPlaces> districtList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    List<WardPlaces> wardList;
    List<Pet> petList = new ArrayList<>();
    List<String> statusTime = new ArrayList<>();
    Pet pet;
    TextView titleName;
    Button fillBtn;
    Spinner dropdownCountry, dropdownCity, dropdownDistrict, dropdownWard, timeSpinner;
    FloatingActionButton addImg;
    List<Uri> image = new ArrayList<>();
    List<ImageView> uploadImg = new ArrayList<>(5);
    List<ImageView> deleteImg = new ArrayList<>(5);
    List<String> imageUrl = new ArrayList<>(Collections.nCopies(5, ""));
    List<String> imageUrlCheck = new ArrayList<>(Collections.nCopies(5, ""));

    User user;
    AdoptOrder adoptOrder;
    Appoitment appoitment;

    ArrayAdapter<String> genderAdapter;
    ArrayAdapter<String> dropdownCountryAdapter;
    ArrayAdapter<CharSequence> timeAdapter;

    EditText addressEdt, nameEdt, dateBirthEdt, requestEdt, dateMeetEdt, phoneEdt, emailEdt;
    String fullName, dateBirth, gender, email, address, country, city,district, ward, phone, requestMsg, dateMeet, timeMeet, dayMeet, fullAddress;
    String idOrder, idUser, idMeet, idPet, namePet;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_infor_to_adopt);

        arrowBack = findViewById(R.id.toolbarArrowBack3);
        genderSpinner = (Spinner)findViewById(R.id.genderSpinner);
        genderAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,genderDropdownValue);
        genderAdapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        timeSpinner = findViewById(R.id.timeSpinner);
        timeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.time,
                android.R.layout.simple_spinner_item
        );
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        dropdownCountry = findViewById(R.id.countrySpinner);
        dropdownCity = findViewById(R.id.citySpinner);
        dropdownDistrict = findViewById(R.id.districtSpinner);
        dropdownWard = findViewById(R.id.wardSpinner);

        imageUrlCheck = new ArrayList<>(Arrays.asList("no", "no", "no", "no", "no"));
        addImg = findViewById(R.id.add_img);
        uploadImg.add(findViewById(R.id.uploadImg1));
        uploadImg.add(findViewById(R.id.uploadImg2));
        uploadImg.add(findViewById(R.id.uploadImg3));
        uploadImg.add(findViewById(R.id.uploadImg4));
        uploadImg.add(findViewById(R.id.uploadImg5));

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

        idUser = "2";
        idPet = getIntent().getStringExtra("idPet");
        namePet = getIntent().getStringExtra("namePet");
        String[] dropdownCountryItems = new String[]{"Vietnam"};
        List<String> provinceNames = new ArrayList<>();
        List<String> districtName = new ArrayList<>();
        List<String> wardName = new ArrayList<>();
        ArrayAdapter<String> dropdownCountryAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dropdownCountryItems);
        dropdownCountry.setAdapter(dropdownCountryAdapter);
        titleName = findViewById(R.id.fillInforToAdoptPetNam);
        titleName.setText(namePet);
        fillBtn = findViewById(R.id.fillBtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        nameEdt = findViewById(R.id.nameEdit);
        dateBirthEdt = findViewById(R.id.birth_text);
        phoneEdt = findViewById(R.id.phone_value);
        requestEdt = findViewById(R.id.requestEdit);
        dateMeetEdt = findViewById(R.id.datemeetEdit);
        emailEdt = findViewById(R.id.emailEdt);
        addressEdt = findViewById(R.id.adress_value);

        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();
//        Initial retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://vapi.vnappmob.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService api = retrofit.create(ApiService.class);
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
                        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(FillInforToAdoptActivity.this, android.R.layout.simple_spinner_dropdown_item, provinceNames);
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
                                        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(FillInforToAdoptActivity.this, android.R.layout.simple_spinner_dropdown_item, districtName);
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
                                        ArrayAdapter<String> wardAdapter = new ArrayAdapter<>(FillInforToAdoptActivity.this, android.R.layout.simple_spinner_dropdown_item, wardName);
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
                onBackPressed();
            }
        });

        fillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(image);
            }

        });

        deleteImg.add(findViewById(R.id.deleteImg1));
        deleteImg.add(findViewById(R.id.deleteImg2));
        deleteImg.add(findViewById(R.id.deleteImg3));
        deleteImg.add(findViewById(R.id.deleteImg4));
        deleteImg.add(findViewById(R.id.deleteImg5));

        deleteImg.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImg.get(0).setImageDrawable(null);
                imageUrlCheck.set(0, "no");
                deleteImg.get(0).setImageDrawable(null);
                Toast.makeText(FillInforToAdoptActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
        deleteImg.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImg.get(1).setImageDrawable(null);
                imageUrlCheck.set(1, "no");
                deleteImg.get(1).setImageDrawable(null);
                Toast.makeText(FillInforToAdoptActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
        deleteImg.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImg.get(2).setImageDrawable(null);
                imageUrlCheck.set(2, "no");
                deleteImg.get(2).setImageDrawable(null);
                Toast.makeText(FillInforToAdoptActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
        deleteImg.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImg.get(3).setImageDrawable(null);
                imageUrlCheck.set(3, "no");
                deleteImg.get(3).setImageDrawable(null);
                Toast.makeText(FillInforToAdoptActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
        deleteImg.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImg.get(4).setImageDrawable(null);
                imageUrlCheck.set(4, "no");
                deleteImg.get(4).setImageDrawable(null);
                Toast.makeText(FillInforToAdoptActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });

        showData();
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
//                                    Toast.makeText(FillInforToAdoptActivity.this, "No empty slot to set the image", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                Toast.makeText(FillInforToAdoptActivity.this, "Failed to get image URI", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(FillInforToAdoptActivity.this, "Failed to get image data", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(FillInforToAdoptActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(FillInforToAdoptActivity.this, "Failed to get image data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FillInforToAdoptActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(FillInforToAdoptActivity.this, "No empty slot to set the image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(FillInforToAdoptActivity.this, "Failed to get image URI", Toast.LENGTH_SHORT).show();
        }
    }
    public void showData(){
        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    if(idUser.equals(snap.getValue(User.class).getUserId())){
//                        Log.d("ShowIntent", "GetTheRightThing");
                        user = snap.getValue(User.class);
                        nameEdt.setText(user.getName());
                        genderSpinner.setSelection(genderAdapter.getPosition(user.getGender()));
                        dateBirthEdt.setText(user.getDateBirth());
                        phoneEdt.setText(user.getPhoneNumber());
                        emailEdt.setText(user.getEmail());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void uploadImage(List<Uri> files) {
        FirebaseApp.initializeApp(this);
        storageReference = FirebaseStorage.getInstance().getReference();

        AtomicInteger uploadedCount = new AtomicInteger(0); // Counter for successful uploads

        for (Uri file : files) {
            StorageReference ref = storageReference.child(UUID.randomUUID().toString());
            ref.putFile(file).addOnSuccessListener(taskSnapshot -> {
                ref.getDownloadUrl().addOnSuccessListener(uri -> {
                    for (int i = 0; i < imageUrl.size(); i++) {
                        if (imageUrl.get(i).equals("") || imageUrl.get(i).isEmpty()) {
                            imageUrl.set(i, uri.toString());
                            break;
                        }
                    }
                    uploadedCount.incrementAndGet(); //

                    if (uploadedCount.get() == files.size()) { // Check if all uploads are done
                        uploadDataFirebase();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(FillInforToAdoptActivity.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                });
                Toast.makeText(FillInforToAdoptActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(FillInforToAdoptActivity.this, "Failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }
    private void uploadDataFirebase(){
//        DatabaseReference petRef = databaseReference.child("Pet").push();
        DatabaseReference adoptRef = databaseReference.child("AdoptOrder").push();
        DatabaseReference meetRef = databaseReference.child("Appointment").push();
        addDataFireBase(adoptRef, meetRef);
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(dateBirth) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(phone))  {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        adoptOrder = new AdoptOrder(idOrder, idPet, "2", requestMsg, "Pretending",statusTime, imageUrl);
        appoitment = new Appoitment(idOrder,dateMeet, idMeet, "1", "2", timeMeet);
        adoptRef.setValue(adoptOrder);
        meetRef.setValue(appoitment);
        Toast.makeText(FillInforToAdoptActivity.this, "Request adopt added successfully", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    private void addDataFireBase(DatabaseReference adoptRef, DatabaseReference meetRef){
        fullName = nameEdt.getText().toString();
        dateBirth = dateBirthEdt.getText().toString();
        email = emailEdt.getText().toString();
        address = addressEdt.getText().toString();
        phone = phoneEdt.getText().toString();
        requestMsg = requestEdt.getText().toString();
        dateMeet = dateMeetEdt.getText().toString();
        gender = genderSpinner.getSelectedItem().toString();
        country = dropdownCountry.getSelectedItem().toString();
        city ="";
        district ="";
        ward = "";
//        if(dropdownCity != null && dropdownWard != null && dropdownDistrict != null){
//            city = dropdownCity.getSelectedItem().toString();
//            district = dropdownDistrict.getSelectedItem().toString();
//            ward = dropdownWard.getSelectedItem().toString();
//        }
        timeMeet = timeSpinner.getSelectedItem().toString();
        fullAddress = address + ", " + country + ", " + city + ", " + district + ", " + ward;

//        adoptRef = databaseReference.child("AdoptOrder").push();
//        meetRef = databaseReference.child("Appointment").push();

        idOrder = adoptRef.getKey();
        idMeet = meetRef.getKey();

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String formattedDate = dateFormat.format(currentTime);
        statusTime.add(formattedDate);
    }

}
