package com.btp.chealth.fragments;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.btp.chealth.R;
import com.btp.chealth.data.User;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.btp.chealth.utils.Constants.USER;

public class HomeFragment extends Fragment {

    @BindView(R.id.chart1)
    LineChart heightChart;
    @BindView(R.id.chart2)
    LineChart weightChart;
    @BindView(R.id.chart3)
    LineChart bmiChart;
    @BindView(R.id.pbProgress)
    ProgressBar progressBar;

    private FirebaseUser currentUser;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        fetchData();
//        setData();
        return v;
    }

    public void fetchData() {

        FirebaseFirestore.getInstance()
                .collection(USER)
                .document(currentUser.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    user = documentSnapshot.toObject(User.class);
//                    setUi();
                    setData(user.getWeightList(), weightChart, "Weights in KG");
                    setData(user.getHeightList(), heightChart, "Height in Metres");
                    progressBar.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                });
    }

    private void setData(List<String> dataset, LineChart chart, String label) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < dataset.size(); i++) {
            values.add(new Entry(i, Float.parseFloat(dataset.get(i))));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.notify();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, label);

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            set1.setFillColor(Color.RED);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        }
        chart.invalidate();
    }
}
