package com.example.pet_finder_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

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
    List<WardPlaces> wardList;
    List<Pet> petList = new ArrayList<>();
    Pet pet;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView titleName;
    Button fillBtn;
    Spinner dropdownCountry, dropdownCity, dropdownDistrict, dropdownWard, timeSpinner;


    User user;
    AdoptOrder adoptOrder;
    Appoitment appoitment;

    ArrayAdapter<String> genderAdapter;
    ArrayAdapter<String> dropdownCountryAdapter;
    ArrayAdapter<CharSequence> timeAdapter;

    EditText addressEdt, nameEdt, dateBirthEdt, requestEdt, dateMeetEdt, phoneEdt, emailEdt;
    String fullName, dateBirth, gender, email, address, country, city,district, ward, phone, requestMsg, dateMeet, timeMeet, dayMeet, fullAddress;
    String idOrder, idUser, idMeet, idPet, namePet;
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
                addDataFireBase();
            }
        });

        showData();
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

    private void addDataFireBase(){
        fullName = nameEdt.getText().toString();
        dateBirth = dateBirthEdt.getText().toString();
        email = emailEdt.getText().toString();
        address = addressEdt.getText().toString();
        phone = phoneEdt.getText().toString();
        requestMsg = requestEdt.getText().toString();
        dateMeet = dateMeetEdt.getText().toString();
        gender = genderSpinner.getSelectedItem().toString();
        country = dropdownCountry.getSelectedItem().toString();
        city = dropdownCity.getSelectedItem().toString();
        district = dropdownDistrict.getSelectedItem().toString();
        ward = dropdownWard.getSelectedItem().toString();
        timeMeet = timeSpinner.getSelectedItem().toString();

        fullAddress = address + ", " + country + ", " + city + ", " + district + ", " + ward;

        DatabaseReference adoptRef = databaseReference.child("AdoptOrder").push();
        DatabaseReference meetRef = databaseReference.child("Appointment").push();

        idOrder = adoptRef.getKey();
        idMeet = meetRef.getKey();

        adoptOrder = new AdoptOrder(idOrder, idPet, "2", requestMsg, "Pretending");
        appoitment = new Appoitment(idOrder,dateMeet, idMeet, "1", "2", timeMeet);
        adoptRef.setValue(adoptOrder);
        meetRef.setValue(appoitment);
        Toast.makeText(FillInforToAdoptActivity.this, "Request adopt added successfully", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

}
