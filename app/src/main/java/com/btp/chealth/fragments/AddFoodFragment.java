package com.btp.chealth.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btp.chealth.R;
import com.btp.chealth.viewmodels.AddFoodViewModel;

public class AddFoodFragment extends Fragment {

    private AddFoodViewModel mViewModel;

    public static AddFoodFragment newInstance() {
        return new AddFoodFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_food_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddFoodViewModel.class);
        // TODO: Use the ViewModel
    }

}