package com.btp.chealth.service;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChealthService {

    @GET("")
    Call<Response> getMealPlan(@Query("weight") int weight,
                               @Query("height") int height,
                               @Query("gender") String gender,
                               @Query("age") int age,
                               @Query("type") String type);
}
