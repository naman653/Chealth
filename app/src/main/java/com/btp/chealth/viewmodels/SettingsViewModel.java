package com.btp.chealth.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.btp.chealth.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.btp.chealth.utils.Constants.USER;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<User> user;
    private MutableLiveData<Boolean> progressVisible;

    public SettingsViewModel() {
        user = new MutableLiveData<>();
        progressVisible = new MutableLiveData<>();
        user.setValue(new User());
        progressVisible.setValue(true);
        init();
    }

    public void init() {
        progressVisible.setValue(true);
        FirebaseFirestore.getInstance()
                .collection(USER)
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    User u = documentSnapshot.toObject(User.class);
                    if (u == null)
                        u = new User();
                    user.setValue(u);
                    progressVisible.setValue(false);
                })
                .addOnFailureListener(e -> {
                    progressVisible.setValue(false);
                });
    }

    public LiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<Boolean> getProgressVisible() {
        return progressVisible;
    }
}
