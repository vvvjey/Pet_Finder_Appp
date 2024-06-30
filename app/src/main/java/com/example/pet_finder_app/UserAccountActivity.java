package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet_finder_app.API.ApiService;
import com.example.pet_finder_app.API.DistrictPlaces;
import com.example.pet_finder_app.API.DistrictPlacesResponse;
import com.example.pet_finder_app.API.ProvincePlaces;
import com.example.pet_finder_app.API.ProvincePlacesResponse;
import com.example.pet_finder_app.API.WardPlaces;
import com.example.pet_finder_app.API.WardPlacesReponse;
import com.example.pet_finder_app.Class.FavouritePet;
import com.example.pet_finder_app.Class.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAccountActivity extends AppCompatActivity {
    Toolbar arrowBack;
    String[] petId= {"idpet1","idpet2"};
    String userId = "userTest";
    TextView editName ;
    TextView editUsername, editGender, editAddress, editPhoneNumber;
    TextView editCity, editDistrict, editWard;
    TextView editEmail;
    String password ;
    ImageView user_img;
    private FirebaseAuth auth;
    String testName = "user1";

    Spinner dropdownCountry ;
    Spinner dropdownCity ;
    Spinner dropdownDistrict ;
    Spinner dropdownWard ;
    List<ProvincePlaces> provinceList;
    List<DistrictPlaces> districtList;
    List<WardPlaces> wardList;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account);
        auth = FirebaseAuth.getInstance();
        arrowBack = findViewById(R.id.toolbarArrowBack);
        Button saveChangesButton = findViewById(R.id.btn_save_change);
        editUsername = findViewById(R.id.editUserName);
        editEmail = findViewById(R.id.editEmail);
        editAddress=findViewById(R.id.editAddress);
        editGender = findViewById(R.id.editGender);
        editPhoneNumber= findViewById(R.id.editPhoneNumber);
        user_img = findViewById(R.id.user_img);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AccountSettingActivity.class));
            }
        });
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfoToFirebase();
            }
        });


///---------

        dropdownCity = findViewById(R.id.citySpinner);
        dropdownDistrict = findViewById(R.id.districtSpinner);
        dropdownWard = findViewById(R.id.wardSpinner);


        List<String> provinceNames = new ArrayList<>();
        List<String> districtName = new ArrayList<>();
        List<String> wardName = new ArrayList<>();

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
                            Log.d("Province",province.getProvinceName());
                            provinceNames.add(province.getProvinceName());
                        }
                        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(UserAccountActivity.this, android.R.layout.simple_spinner_dropdown_item, provinceNames);
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
                                        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(UserAccountActivity.this, android.R.layout.simple_spinner_dropdown_item, districtName);
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
                                        ArrayAdapter<String> wardAdapter = new ArrayAdapter<>(UserAccountActivity.this, android.R.layout.simple_spinner_dropdown_item, wardName);
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


       ///-----
        getUser();

    }
    private void saveUserInfoToFirebase(){
        String newEmail = editEmail.getText().toString();
        String newName = editUsername.getText().toString();
        String newGender = editGender.getText().toString();

        String newPhoneNumber = editPhoneNumber.getText().toString();
        String city = dropdownCity.getSelectedItem().toString();
        String district = dropdownCity.getSelectedItem().toString();
        String ward = dropdownCity.getSelectedItem().toString();

        String newAddress = city +", "+district+", "+ward ;

        // Create a Map to store user information
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", newEmail);
        userInfo.put("name", newName);
        userInfo.put("gender", newGender);
        userInfo.put("address", newAddress);
        userInfo.put("phonenumber", newPhoneNumber);

        // Get a reference to the user node
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(uid);

        // Update user information in Firebase
        userRef.setValue(userInfo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserAccountActivity.this, "User information saved!", Toast.LENGTH_SHORT).show();
                            // Optionally, reload user data or navigate back to another activity
                        } else {
                            Log.w("TAG", "Error saving user information", task.getException());
                            Toast.makeText(UserAccountActivity.this, "Error saving user information!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void defaultUser(){
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference dtbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = dtbRef.child("User").child(uid);
        userRef.child("address").setValue("Ho Chi Minh City");
        userRef.child("gender").setValue("Female");
        userRef.child("phonenumber").setValue("XXX");
        userRef.child("imgUser").setValue("https://firebasestorage.googleapis.com/v0/b/petfinderserverside.appspot.com/o/avatar.png?alt=media&token=b2fd7682-6c13-4853-8ba7-2fb3470c3bb2");
        userRef.child("dateBirth").setValue("28-04-3200");
    }
    private void getUser(){
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference dtbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = dtbRef.child("User").child(uid);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    // Extract the fullname from the snapshot
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String name  = dataSnapshot.child("name").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String address =dataSnapshot.child("address").getValue(String.class);

                    String phonenumber = dataSnapshot.child("phonenumber").getValue(String.class);
                   // String image = dataSnapshot.child("imgUser").getValue(String.class);
                    editEmail.setText(email);
                    editUsername.setText(name);
                    editGender.setText(gender);
                    editAddress.setText(address);
                    editPhoneNumber.setText(phonenumber);
                    Picasso.get().load(user.getImgUser()).into(user_img);
                    Log.d("Img",user.getImgUser());

                } else {
                    defaultUser();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors during database read operation
                Log.w("TAG", "Failed to read value", error.toException());
            }
        });
    }
    private void testData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = "user123";
        String[] petIds = {"idpet1", "idpet2"};
        List<String> list = Arrays.asList(petIds);
// Get a reference to the "favouritePet" node
        DatabaseReference favoritePetRef = database.getReference("FavouritePet");

// Create a child node with the user ID
        DatabaseReference userRef = favoritePetRef.child(userId);
        userRef.setValue(list)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Firebase", "Pet IDs added successfully!");
                            // Handle successful addition (e.g., show a toast message)
                        } else {
                            Log.w("Firebase", "Error adding pet IDs", task.getException());
                            // Handle addition failure (e.g., show an error message)
                        }
                    }
                });

    }

}
