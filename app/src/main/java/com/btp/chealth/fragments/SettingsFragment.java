package com.btp.chealth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.btp.chealth.R;
import com.btp.chealth.activities.EditProfileActivity;
import com.btp.chealth.activities.LoginActivity;
import com.btp.chealth.utils.PrefService;
import com.btp.chealth.viewmodels.SettingsViewModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.btp.chealth.utils.Constants.AGE;
import static com.btp.chealth.utils.Constants.EDIT_CODE;
import static com.btp.chealth.utils.Constants.IS_EDIT;

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
    @BindView(R.id.tvHeight)
    TextView height;
    @BindView(R.id.tvName)
    TextView name;
    @BindView(R.id.tvSex)
    TextView sex;
    @BindView(R.id.pbProgress)
    ProgressBar progressBar;

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
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Glide.with(getContext()).load(currentUser.getPhotoUrl()).into(profilePic);
        name.setText(currentUser.getDisplayName());
        email.setText(currentUser.getEmail());
        age.setText(String.format("%s Yrs", PrefService.getInstance().getString(AGE, "")));
        sex.setText(PrefService.getInstance().getString(AGE, ""));
        weight.setText(String.format("%s Kgs", PrefService.getInstance().getString(AGE, "")));
        height.setText(String.format("%s Mts", PrefService.getInstance().getString(AGE, "")));
        bmi.setText(PrefService.getInstance().getString(AGE, ""));
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent i = new Intent(getContext(), EditProfileActivity.class);
                i.putExtra(IS_EDIT, true);
                startActivityForResult(i, EDIT_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_CODE) {
            mViewModel.init();
        }
    }
}
