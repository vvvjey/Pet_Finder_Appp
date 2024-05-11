package com.example.pet_finder_app.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET("/api/province")
    Call<VietnamPlacesResponse> getListProvinces();
}
