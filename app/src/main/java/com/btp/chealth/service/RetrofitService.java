package com.btp.chealth.service;

import com.btp.chealth.data.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

//    @Headers("Authorization: Bearer 228b633c250a497195a20dccf5472a09")
    @GET("/v2/everything")
    Call<Response> getNewsArticles(@Query("q") String d, @Query("apiKey") String apiKey);
}