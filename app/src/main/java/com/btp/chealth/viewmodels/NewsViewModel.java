package com.btp.chealth.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.btp.chealth.data.Article;
import com.btp.chealth.service.RetrofitService;
import com.btp.chealth.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Article>> articles;

    public NewsViewModel() {
        articles = new MutableLiveData<>();
        articles.setValue(new ArrayList<>());
        init();
    }

    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.NEWS_BASE_URL)
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        retrofitService.getNewsArticles("health", "228b633c250a497195a20dccf5472a09").enqueue(new Callback<com.btp.chealth.data.Response>() {
            @Override
            public void onResponse(Call<com.btp.chealth.data.Response> call, Response<com.btp.chealth.data.Response> response) {
                Log.e("Lol outIfOnResponse", response.raw().request().url().toString());
                if(response.isSuccessful()) {
                    articles.setValue(response.body().getArticles());
                } else {
                    Log.e("Lol", response.message());
                }
            }

            @Override
            public void onFailure(Call<com.btp.chealth.data.Response> call, Throwable t) {
                Log.e("Lol onFailure", t.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Article>> getNewsList() {
        return articles;
    }
}