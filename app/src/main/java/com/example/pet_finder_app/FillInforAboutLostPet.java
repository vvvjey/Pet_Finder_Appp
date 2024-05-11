package com.example.pet_finder_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pet_finder_app.API.ApiService;
import com.example.pet_finder_app.API.VietnamPlaces;
import com.example.pet_finder_app.API.VietnamPlacesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FillInforAboutLostPet extends AppCompatActivity {
    private Toolbar arrowBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_infor_about_lost_pet);
        arrowBack = findViewById(R.id.toolbarArrowBack3);
        Spinner dropdownPurpose = findViewById(R.id.purposeSpinner);
        Spinner dropdownCountry = findViewById(R.id.countrySpinner);
        Spinner dropdownCity = findViewById(R.id.citySpinner);

        String[] dropdownPurposeItems = new String[]{"Missing", "Seen", "Protected"};
        String[] dropdownCountryItems = new String[]{"Vietnam"};
        List<String> provinceNames = new ArrayList<>();
        ArrayAdapter<String> dropdownPurposeAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dropdownPurposeItems);
        ArrayAdapter<String> dropdownCountryAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dropdownCountryItems);


        dropdownPurpose.setAdapter(dropdownPurposeAdapter);
        dropdownCountry.setAdapter(dropdownCountryAdapter);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int dropdownWidth = screenWidth - (int) (50 * getResources().getDisplayMetrics().density);
        dropdownPurpose.setDropDownWidth(dropdownWidth);


        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://vapi.vnappmob.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService api = retrofit.create(ApiService.class);
        Call<VietnamPlacesResponse> call = api.getListProvinces();
        call.enqueue(new Callback<VietnamPlacesResponse>() {
            @Override
            public void onResponse(Call<VietnamPlacesResponse> call, Response<VietnamPlacesResponse> response) {
                if (response.isSuccessful()) {
                    VietnamPlacesResponse provincesResponse = response.body();
                    if (provincesResponse != null) {
                        List<VietnamPlaces> provinceList = provincesResponse.getResults();
                        for (VietnamPlaces province : provinceList) {
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
            public void onFailure(Call<VietnamPlacesResponse> call, Throwable t) {
                Log.e("API Error", "Failed to fetch provinces: " + t.getMessage());
            }
        });

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchingLostPetActivity.class));
            }
        });
    }

}
