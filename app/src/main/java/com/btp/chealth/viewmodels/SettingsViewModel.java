package com.btp.chealth.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.btp.chealth.data.User;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<User> user;

    public SettingsViewModel() {
        user = new MutableLiveData<>();
        user.setValue(new User());
        init();
    }

    public void init() {

    }

    public LiveData<User> getUser() {
        return user;
    }
}
