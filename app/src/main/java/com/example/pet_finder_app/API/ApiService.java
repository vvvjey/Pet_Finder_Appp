package com.example.pet_finder_app.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiService {

    @GET("/api/province")
    Call<ProvincePlacesResponse> getListProvinces();
    @GET("/api/province/district/{id}")
    Call<DistrictPlacesResponse> getDistrictById(@Path("id") String id);
    @GET("/api/province/ward/{id}")
    Call<WardPlacesReponse> getWardById(@Path("id") String id);
}
