package com.btp.chealth.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.btp.chealth.R;
import com.btp.chealth.data.Feedback;
import com.btp.chealth.utils.Constants;
import com.btp.chealth.viewmodels.FeedbackViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackFragment extends Fragment {

    @BindView(R.id.feedback)
    EditText feedback;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick(R.id.submit)
    void submitFeedback(View view) {
        if(isValid()) {
            view.setClickable(false);
            FirebaseFirestore.getInstance()
                    .collection(Constants.FEEDBACK)
                    .add(new Feedback(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            feedback.getText().toString()))
                    .addOnSuccessListener(documentReference -> {
                        feedback.setText("");
                        view.setClickable(true);
                        Toast.makeText(getContext(), R.string.feedback_submitted, Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private boolean isValid() {
        boolean valid = true;
        if(feedback.getText().toString().isEmpty()) {
            valid = false;
            Toast.makeText(getContext(), R.string.input_empty, Toast.LENGTH_SHORT).show();
        }
        return valid;
    }
}
