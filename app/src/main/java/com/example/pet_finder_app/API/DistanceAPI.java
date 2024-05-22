package com.example.pet_finder_app.API;
import io.reactivex.Single ;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DistanceAPI {
    @GET("maps/api/distancematrix/json")
    Single<Result> getDistance(@Query("key") String key,
                        @Query("origins") String origins,
                        @Query("destination") String destination);



}
