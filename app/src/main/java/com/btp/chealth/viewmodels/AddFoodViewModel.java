package com.btp.chealth.viewmodels;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.btp.chealth.data.DayMeal;
import com.btp.chealth.data.MealResponse;
import com.btp.chealth.service.ChealthService;
import com.btp.chealth.utils.Constants;
import com.btp.chealth.utils.PrefService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.btp.chealth.utils.Constants.AGE;
import static com.btp.chealth.utils.Constants.GENDER;
import static com.btp.chealth.utils.Constants.HEIGHT;
import static com.btp.chealth.utils.Constants.WEIGHT;

public class AddFoodViewModel extends ViewModel {

    MutableLiveData<MealResponse> mealResponse;

    public AddFoodViewModel() {
        mealResponse = new MutableLiveData<>();
        init();
    }

    public MutableLiveData<MealResponse> getMealResponse() {
        return mealResponse;
    }

    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.CHEALTH_BASE_URL)
                .build();

        ChealthService chealthService = retrofit.create(ChealthService.class);
        int weight = (int)(Double.parseDouble(PrefService.getInstance().getString(WEIGHT, "0")));
        int height = (int)(Double.parseDouble(PrefService.getInstance().getString(HEIGHT, "0")) * 100);
        String gender = PrefService.getInstance().getString(GENDER, "");
        int age = Integer.parseInt(PrefService.getInstance().getString(AGE, "0"));
        chealthService.getMealPlan(age,weight, height, gender)
                .enqueue(new Callback<MealResponse>() {
                    @Override
                    public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                        if (response.body() != null) {
                            mealResponse.setValue(response.body());
                        }
                        Log.e(AddFoodViewModel.class.getName(), call.request().toString());
                    }

                    @Override
                    public void onFailure(Call<MealResponse> call, Throwable t) {
                        Log.e(AddFoodViewModel.class.getName(), call.request().toString());
                    }
                });
    }
}