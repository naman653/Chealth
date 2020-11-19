package com.btp.chealth.service;

import com.btp.chealth.data.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChealthService {

    @GET("/")
    Call<MealResponse> getMealPlan(@Query("age") int age,
                                   @Query("weight") int weight,
                                   @Query("height") int height,
                                   @Query("gender") String gender);
}
