package com.example.pet_finder_app.API;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoCodingApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    GeoCodingApi apiInterface = new Retrofit.Builder()
            .baseUrl("https://rsapi.goong.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(GeoCodingApi.class);
    @GET("geocode")

    Call<GeoResponse> getGeo(@Query("api_key") String key,
                                 @Query("address") String address
    );

}
