package com.btp.chealth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.btp.chealth.R;
import com.btp.chealth.activities.LoginActivity;
import com.btp.chealth.activities.MainActivity;
import com.btp.chealth.viewmodels.SettingsViewModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class SettingsFragment extends Fragment {

    @BindView(R.id.ivProfilePic)
    ImageView profilePic;
    @BindView(R.id.tvAge)
    TextView age;
    @BindView(R.id.tvBmi)
    TextView bmi;
    @BindView(R.id.tvEmail)
    TextView email;
    @BindView(R.id.tvWeight)
    TextView weight;
    @BindView(R.id.tvName)
    TextView name;
    @BindView(R.id.tvSex)
    TextView sex;

    private SettingsViewModel mViewModel;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Glide.with(getContext()).load(user.getPhotoUrl()).into(profilePic);
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());
    }

    @OnClick(R.id.btLogOut)
    public void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.action_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
