package com.btp.chealth.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.btp.chealth.R;
import com.btp.chealth.adapters.ExpandableListAdapter;
import com.btp.chealth.data.DayMeal;
import com.btp.chealth.data.MealResponse;
import com.btp.chealth.service.ChealthService;
import com.btp.chealth.utils.Constants;
import com.btp.chealth.utils.PrefService;
import com.btp.chealth.viewmodels.AddFoodViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.btp.chealth.utils.Constants.AGE;
import static com.btp.chealth.utils.Constants.GENDER;
import static com.btp.chealth.utils.Constants.HEIGHT;
import static com.btp.chealth.utils.Constants.WEIGHT;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class AddFoodFragment extends Fragment {

    private List<String> expandableListTitle;
    private AddFoodViewModel mViewModel;
    private HashMap<String, List<String>> dataset;
    private ExpandableListAdapter expandableListAdapter;
    @BindView(R.id.list_view)
    ExpandableListView expandableListView;
    @BindView(R.id.pbProgress)
    ProgressBar progressBar;

    public static AddFoodFragment newInstance() {
        return new AddFoodFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.add_food_fragment, container, false);
        ButterKnife.bind(this, root);

        expandableListTitle = new ArrayList<>();
        dataset = new HashMap<>();
        expandableListAdapter = new ExpandableListAdapter(getContext(), expandableListTitle, dataset);
        expandableListView.setAdapter(expandableListAdapter);
        mViewModel = ViewModelProviders.of(this).get(AddFoodViewModel.class);

        init();
        mViewModel.getMealResponse().observe(getViewLifecycleOwner(), mealResponse -> {
            if (mealResponse != null) {
                expandableListTitle.clear();
                expandableListTitle.add("Mon");
                expandableListTitle.add("Tues");
                expandableListTitle.add("Wed");
                expandableListTitle.add("Thurs");
                expandableListTitle.add("Fri");
                expandableListTitle.add("Sat");
                expandableListTitle.add("Sun");
                dataset.clear();
                dataset.put("Mon", getStringList(mealResponse.getMon()));
                dataset.put("Tues", getStringList(mealResponse.getTues()));
                dataset.put("Wed", getStringList(mealResponse.getWed()));
                dataset.put("Thurs", getStringList(mealResponse.getThurs()));
                dataset.put("Fri", getStringList(mealResponse.getFri()));
                dataset.put("Sat", getStringList(mealResponse.getSat()));
                dataset.put("Sun", getStringList(mealResponse.getSun()));
                expandableListAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    private List<String> getStringList(DayMeal dayMeal) {
        List<String> value = new ArrayList<>();
        String breakfast = "Breakfast: " + dayMeal.getBreakfast();
        String lunch = "Lunch: " + dayMeal.getLunch();
        String dinner = "Dinner: " + dayMeal.getDinner();
        value.add(breakfast);
        value.add(lunch);
        value.add(dinner);
        return value;
    }

    public void init() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.CHEALTH_BASE_URL)
                .build();

        ChealthService chealthService = retrofit.create(ChealthService.class);
        int weight = (int)(Double.parseDouble(PrefService.getInstance().getString(WEIGHT, "0")));
        int height = (int)(Double.parseDouble(PrefService.getInstance().getString(HEIGHT, "0")) * 100);
        String gender = PrefService.getInstance().getString(GENDER, "");
        int age = Integer.parseInt(PrefService.getInstance().getString(AGE, "0"));
        chealthService.getMealPlan(age, weight, height, gender)
                .enqueue(new Callback<MealResponse>() {
                    @Override
                    public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                        if (response.body() != null) {
                            mViewModel.getMealResponse().setValue(response.body());
                        }
                        progressBar.setVisibility(View.GONE);
//                        Toast.makeText(getContext(), call.request().toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<MealResponse> call, Throwable t) {
                        Log.e("asdf", call.request().toString());
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                        Toast.makeText(getContext(), call.request().toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}