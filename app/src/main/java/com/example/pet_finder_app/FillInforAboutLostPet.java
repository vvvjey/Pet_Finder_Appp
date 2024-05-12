package com.example.pet_finder_app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pet_finder_app.API.ApiService;
import com.example.pet_finder_app.API.DistrictPlaces;
import com.example.pet_finder_app.API.DistrictPlacesResponse;
import com.example.pet_finder_app.API.ProvincePlaces;
import com.example.pet_finder_app.API.ProvincePlacesResponse;
import com.example.pet_finder_app.API.WardPlaces;
import com.example.pet_finder_app.API.WardPlacesReponse;
import com.example.pet_finder_app.Class.MissingPet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    private Button dateButton,createMissingPostBtn;
    private EditText fullname,address,phoneNumber,request,animalName,descriptionPet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_infor_about_lost_pet);
        arrowBack = findViewById(R.id.toolbarArrowBack3);
        fullname = findViewById(R.id.fullNameInput);
        address = findViewById(R.id.addressInput);
        phoneNumber = findViewById(R.id.phoneNumberInput);
        request = findViewById(R.id.requestInput);
        animalName = findViewById(R.id.animalNameInput);
        descriptionPet = findViewById(R.id.descriptionPetInput);
        createMissingPostBtn = findViewById(R.id.createMissingPostBtn);


        Spinner dropdownPurpose = findViewById(R.id.purposeSpinner);
        Spinner dropdownCountry = findViewById(R.id.countrySpinner);
        Spinner dropdownCity = findViewById(R.id.citySpinner);
        Spinner dropdownDistrict = findViewById(R.id.districtSpinner);
        Spinner dropdownWard = findViewById(R.id.wardSpinner);
        Spinner dropdownSize = findViewById(R.id.sizeSpinner);
        Spinner dropdownGender = (Spinner)findViewById(R.id.genderSpinner);
        Spinner dropdownColor = findViewById(R.id.colorSpinner);
        Spinner dropdownBreed = findViewById(R.id.breedSpinner);
        Spinner dropdownAge = findViewById(R.id.ageSpinner);

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

//        Handle create new missing pet post
        createMissingPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = dropdownAge.getSelectedItem().toString();
                String categoryId = "2";
                String color = dropdownColor.getSelectedItem().toString();
                String description = "no";
                String gender = dropdownGender.getSelectedItem().toString();
                String idPet = "1";
                String imgUrl = "img";
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


                MissingPet missingPet = new MissingPet(age, categoryId, color, description, gender, idPet, imgUrl, petName, registerDate, size, typeId, weight, id, typeMissing,addressMissing, dateMissing, detailDescription);
//                boolean validate_input = validateInputs();
//                if(validate_input){
//
//                }
                createMissingPost(missingPet);
            }
        });

    }
    private void createMissingPost(MissingPet pet){
        try{
            DatabaseReference missingPetRef = FirebaseDatabase.getInstance().getReference().child("Missing pet");

            String missingPetKey = missingPetRef.push().getKey();

            // Set the values of the missing pet attributes under the generated key
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

            // Log a message to indicate that the missing pet data has been saved
            Log.d("Data missing pet", "Missing pet data saved ");
        } catch (Exception e) {
            Log.e("Error","Error:",e);
        }

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

}
