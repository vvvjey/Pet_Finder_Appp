package com.example.pet_finder_app;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pet_finder_app.API.DistanceAPI;
import com.example.pet_finder_app.API.DistanceResult;
import com.example.pet_finder_app.API.PlaceApi;
import com.example.pet_finder_app.API.PlaceDetailApi;
import com.example.pet_finder_app.API.PlaceDetailResponse;
import com.example.pet_finder_app.API.PlaceResponse;
import com.example.pet_finder_app.Class.RescueStation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RescueCategoryActivity extends AppCompatActivity implements LocationListener {
    private RecyclerView recyclerView;
    Toolbar arrowBack;
    ConstraintLayout gomap;
    String destinations="";
    List<RescueStation> RescueList = new ArrayList<RescueStation>();
    String locationInput="";
    DatabaseReference dtbRef = FirebaseDatabase.getInstance().getReference().child("RescueStation");

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.rescue_pet);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rescue_pet), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        arrowBack = findViewById(R.id.toolbarArrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomepageActivity.class));
            }
        });
        List<RescueCategoryDomain> RescueList = new ArrayList<RescueCategoryDomain>();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("RescueStation");



// Iterate through the 'predictions' array in the JSON response

        String first = "cuu ho thu cung";

        int limit = 10;
   //     String place_id = "Uox-Qa5QGqtr2nUh1GixtUG8dVHUY4StUeNLLa5YgKxFxWGAEnQi6r3u_SzmYb66uSZ99M8xVv6xZ2UuE531arTLYfYQ";
        String input =  first;
        PlaceApi.apiInterface.getPlace(getString(R.string.goong_map_api_key),input,limit).enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                int predSize = response.body().getPredictions().size();
                for(int i = 0; i < predSize ;i++)
                {
                    String place_id = String.valueOf(response.body().getPredictions().get(i).getPlace_id());

                    String main_text = String.valueOf(response.body().getPredictions().get(i).getStructured_formatting().getMain_text());
                    String secondary_text = String.valueOf(response.body().getPredictions().get(i).getStructured_formatting().getSecondary_text());

                    int size = response.body().getPredictions().get(i).getTerms().size();
                    String province = response.body().getPredictions().get(i).getTerms().get(size - 1).getValue();
                    Log.d("Province", province);
                    Log.d("Name",main_text);
                    Log.d("Place_id",place_id);
                    PlaceDetailApi.apiInterface.getPlaceDetail(getString(R.string.goong_map_api_key), place_id).enqueue(new Callback<PlaceDetailResponse>() {
                        @Override
                        public void onResponse(Call<PlaceDetailResponse> call, Response<PlaceDetailResponse> response) {

                        String lat = String.valueOf(response.body().getResult().getGeometry().getLocation().getLat());
                        String lng = String.valueOf(response.body().getResult().getGeometry().getLocation().getLng());

                         destinations = lat +","+ lng ;

                            String vehicles = "car";
                            String origins = "10.8700,106.8031";

                            DistanceAPI.apiInterface.getDistance(getString(R.string.goong_map_api_key),origins,destinations,vehicles).enqueue(new Callback<DistanceResult>() {
                                @Override
                                public void onResponse(Call<DistanceResult> call, Response<DistanceResult> response) {
                                    String distance = response.body().getRows().get(0).getElements().get(0).getDistance().getText();
                                    Log.d("Distance",distance);
//                                    RescueList.add(new RescueCategoryDomain(R.drawable.rescue_station1, main_text,province,distance));

                                    DatabaseReference newStationRef = rootRef.child(place_id);

                        // Set station details under the newly created child node
                                    newStationRef.child("address").setValue(secondary_text);
                                    newStationRef.child("name").setValue(main_text);
                                    newStationRef.child("province").setValue(province);
                                    newStationRef.child("distance").setValue(distance);
                                    newStationRef.child("place_id").setValue(place_id);
                                    newStationRef.child("geoCode").setValue(destinations);
                                }

                                @Override
                                public void onFailure(Call<DistanceResult> call, Throwable throwable) {

                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<PlaceDetailResponse> call, Throwable throwable) {

                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable throwable) {

            }


        }
        );


        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RescueList.clear(); // Clear default list before adding stations from Firebase

                for (DataSnapshot stationSnapshot : dataSnapshot.getChildren()) {
                    String name = stationSnapshot.child("name").getValue(String.class);
                    String address = stationSnapshot.child("address").getValue(String.class);
                    String distance= stationSnapshot.child("distance").getValue(String.class);
                    String geoCode= stationSnapshot.child("geoCode").getValue(String.class);
                    String place_id = stationSnapshot.getKey();

                    // Optional

                    // Get the unique station ID for potential future use (optional)
                    String stationId = stationSnapshot.getKey();

                    // Create RescueCategoryDomain object with Firebase data
                    RescueCategoryDomain station = new RescueCategoryDomain(
                            R.drawable.rescue_station1,
                            name, address, distance);
                    station.setPlace_id(place_id);
                    station.setGeocode(geoCode);
                    RescueList.add(station);
                    RescueStation rescueStation = stationSnapshot.getValue(RescueStation.class);
                    RescueList.add(rescueStation);

                }
                populateRecyclerView();

                // Notify adapter (if using one) about data change for list update
//                if (yourAdapter != null) {
//                    yourAdapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });



//        RescueList.add(new RescueCategoryDomain(R.drawable.rescue_station1, "SaiGon Pet Adoption","Quan 9, TPHCM", "0.4 kms"));
//        RescueList.add(new RescueCategoryDomain(R.drawable.rescue_station1, "SaiGon Pet Adoption","Quan 9, TPHCM", "0.4 kms"));
//        RescueList.add(new RescueCategoryDomain(R.drawable.rescue_station1, "SaiGon Pet Adoption","Quan 9, TPHCM", "0.4 kms"));
//        RescueList.add(new RescueCategoryDomain(R.drawable.rescue_station1, "SaiGon Pet Adoption","Quan 9, TPHCM", "0.4 kms"));
 //      RescueList.add(new RescueCategoryDomain(R.drawable.rescue_station1, "SaiGon Pet Adoption","Quan 9, TPHCM", "0.4 kms"));

        gomap = findViewById(R.id.gomap);

        gomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RescueCategoryActivity.this, MapActivity.class));
            }
        });



    }

    private void PlaceDetail(String api_key,String place_id){
        PlaceDetailApi.apiInterface.getPlaceDetail(api_key,place_id).enqueue(new Callback<PlaceDetailResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailResponse> call, Response<PlaceDetailResponse> response) {

                String lat =String.valueOf(response.body().getResult().getGeometry().getLocation().getLat());
                String lng =String.valueOf(response.body().getResult().getGeometry().getLocation().getLng());
            }

            @Override
            public void onFailure(Call<PlaceDetailResponse> call, Throwable throwable) {

            }
        });
    }
    private void populateRecyclerView() {
        List<RescueCategoryDomain> rescueCategoryDomainList = new ArrayList<>();
        for(RescueStation rescueStation : RescueList){
            rescueCategoryDomainList.add(new RescueCategoryDomain(
                    R.drawable.rescue_station1,
                    rescueStation.getName(), rescueStation.getAddress(), rescueStation.getDistance()));
        }
        recyclerView = findViewById(R.id.rescue_view);
        RescueCategoryAdapter rescueAdapter = new RescueCategoryAdapter(rescueCategoryDomainList,this);
        recyclerView.setAdapter(rescueAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
    }
    private void DistanceMatrix(String api_key,String origins, String destinations){
        String vehicles = "car";
        origins = "10.8700,106.8031";

        DistanceAPI.apiInterface.getDistance(api_key,origins,destinations,vehicles).enqueue(new Callback<DistanceResult>() {
            @Override
            public void onResponse(Call<DistanceResult> call, Response<DistanceResult> response) {
                String distance = response.body().getRows().get(0).getElements().get(0).getDistance().getText();

            }

            @Override
            public void onFailure(Call<DistanceResult> call, Throwable throwable) {

            }
        });
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }



}
