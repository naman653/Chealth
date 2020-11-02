package com.btp.chealth.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.btp.chealth.data.Article;
import com.btp.chealth.service.RetrofitService;
import com.btp.chealth.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Article>> articles;
    private MutableLiveData<Boolean> progressVisible;

    public NewsViewModel() {
        articles = new MutableLiveData<>();
        progressVisible = new MutableLiveData<>();
        articles.setValue(new ArrayList<>());
        progressVisible.setValue(true);
        init();
    }

    public void init() {
        progressVisible.setValue(true);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.NEWS_BASE_URL)
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        retrofitService.getNewsArticles("health", "228b633c250a497195a20dccf5472a09").enqueue(new Callback<com.btp.chealth.data.NewsResponse>() {
            @Override
            public void onResponse(Call<com.btp.chealth.data.NewsResponse> call, Response<com.btp.chealth.data.NewsResponse> response) {
                Log.e("Lol outIfOnResponse", response.raw().request().url().toString());
                if(response.isSuccessful()) {
                    articles.setValue(response.body().getArticles());
                } else {
                    Log.e("Lol", response.message());
                }
                progressVisible.setValue(false);
            }

            @Override
            public void onFailure(Call<com.btp.chealth.data.NewsResponse> call, Throwable t) {
                Log.e("Lol onFailure", t.getMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<Article>> getArticles() {
        return articles;
    }

    public MutableLiveData<Boolean> getProgressVisible() {
        return progressVisible;
    }
}