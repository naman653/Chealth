package com.btp.chealth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.btp.chealth.R;
import com.btp.chealth.data.User;
import com.btp.chealth.utils.PrefService;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.btp.chealth.utils.Constants.AGE;
import static com.btp.chealth.utils.Constants.BMI;
import static com.btp.chealth.utils.Constants.FEMALE;
import static com.btp.chealth.utils.Constants.GENDER;
import static com.btp.chealth.utils.Constants.HEIGHT;
import static com.btp.chealth.utils.Constants.IS_EDIT;
import static com.btp.chealth.utils.Constants.MALE;
import static com.btp.chealth.utils.Constants.OTHER;
import static com.btp.chealth.utils.Constants.USER;
import static com.btp.chealth.utils.Constants.WEIGHT;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.ivProfilePic)
    ImageView profilePic;
    @BindView(R.id.tvName)
    TextView name;
    @BindView(R.id.tvEmail)
    TextView email;
    @BindView(R.id.rgGender)
    RadioGroup gender;
    @BindView(R.id.rbFemale)
    RadioButton female;
    @BindView(R.id.rbOther)
    RadioButton other;
    @BindView(R.id.rbMale)
    RadioButton male;
    @BindView(R.id.etAge)
    EditText age;
    @BindView(R.id.etHeight)
    EditText height;
    @BindView(R.id.etWeight)
    EditText weight;
    @BindView(R.id.pbProgress)
    ProgressBar progressBar;

    private boolean isEdit;
    private FirebaseUser currentUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);
        Intent i = getIntent();
        isEdit = i.getBooleanExtra(IS_EDIT, false);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        fetchDetails();
    }

    private void fetchDetails() {
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(this).load(currentUser.getPhotoUrl()).into(profilePic);
        name.setText(currentUser.getDisplayName());
        email.setText(currentUser.getEmail());

        FirebaseFirestore.getInstance()
                .collection(USER)
                .document(currentUser.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    user = documentSnapshot.toObject(User.class);
                    setUi();
                    progressBar.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                });
    }

    @OnClick(R.id.btSave)
    public void save() {
        if (isValid()) {
            if (user == null)
                user = new User();
            user.setUserid(currentUser.getUid());
            user.setAge(age.getText().toString());
            PrefService.getInstance().saveData(AGE, user.getAge());
            user.setHeightFoot(height.getText().toString());
            PrefService.getInstance().saveData(HEIGHT, user.getHeightFoot());
            user.setWeight(weight.getText().toString());
            PrefService.getInstance().saveData(WEIGHT, user.getWeight());
            switch (gender.getCheckedRadioButtonId()) {
                case R.id.rbMale:
                    user.setSex(MALE);
                    break;
                case R.id.rbFemale:
                    user.setSex(FEMALE);
                    break;
                case R.id.rbOther:
                    user.setSex(OTHER);
                    break;
            }
            PrefService.getInstance().saveData(GENDER, user.getSex());
            double heightM = Double.parseDouble(user.getHeightFoot());
            double weightKg = Double.parseDouble(user.getWeight());
            double bmi = weightKg / (heightM * heightM);
            user.setBmi((bmi + "").substring(0, 5));
            PrefService.getInstance().saveData(BMI, user.getBmi());
            progressBar.setVisibility(View.VISIBLE);
            FirebaseFirestore.getInstance()
                    .collection(USER)
                    .document(currentUser.getUid())
                    .set(user)
                    .addOnSuccessListener(aVoid -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        if (!isEdit) {
                            Intent i = new Intent(EditProfileActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    });
        }
    }

    private void setUi() {
        if (user == null)
            user = new User();
        age.setText(user.getAge());
        switch (user.getSex()) {
            case MALE:
                male.setChecked(true);
                break;
            case FEMALE:
                female.setChecked(true);
                break;
            case OTHER:
                other.setChecked(true);
        }
        height.setText(user.getHeightFoot());
        weight.setText(user.getWeight());
    }

    private boolean isValid() {
        boolean isValid = true;
        if (age.getText().toString().isEmpty()) {
            isValid = false;
            age.setError("Enter your age");
        }
        if (weight.getText().toString().isEmpty()) {
            isValid = false;
            weight.setError("Enter your weight");
        }
        if (height.getText().toString().isEmpty()) {
            isValid = false;
            height.setError("Enter your height");
        }
        if (!male.isChecked() && !female.isChecked() && !other.isChecked()) {
            isValid = false;
            Toast.makeText(this, "Enter gender", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }
}