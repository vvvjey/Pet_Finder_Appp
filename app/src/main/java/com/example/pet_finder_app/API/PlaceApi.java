package com.example.pet_finder_app.API;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    PlaceApi apiInterface = new Retrofit.Builder()
            .baseUrl("https://rsapi.goong.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PlaceApi.class);
    @GET("Place/AutoComplete")

//    Single<Result> getDistance(@Query("api_key") String key,
//                               @Query("origins") String origins,
//                               @Query("destinations") String destinations,
//                               @Query("vehicle") String vehicle
//                               );
    Call<PlaceResponse> getPlace(@Query("api_key") String key,
                                 @Query("input") String input
    );

}