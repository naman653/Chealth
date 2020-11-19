package com.btp.chealth.service;

import com.btp.chealth.data.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("/v2/everything")
    Call<NewsResponse> getNewsArticles(@Query("q") String d, @Query("apiKey") String apiKey);
}