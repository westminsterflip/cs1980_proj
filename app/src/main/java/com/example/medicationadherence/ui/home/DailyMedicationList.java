package com.example.medicationadherence.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.DailyMedicationListAdapter;
import com.example.medicationadherence.model.DailyMedication;

import java.util.List;

public class DailyMedicationList extends AppCompatActivity {
    private DailyMedListViewModel model;
    private RecyclerView medRecyclerView;


    //TODO: add as needed meds at bottom
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(this).get(DailyMedListViewModel.class);
        final Observer<List<DailyMedication>> medicationObserver = new Observer<List<DailyMedication>>() {
            @Override
            public void onChanged(List<DailyMedication> dailyMedications) {
                if (model.getMedAdapter() != null) {
                    model.getMedAdapter().notifyDataSetChanged();
                }
            }
        };
        model.getMedications().observe(this, medicationObserver);
        setContentView(R.layout.activity_daily_medication_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.addMedButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        //TODO: scroll to current time upon opening list
        //TODO: coloring for past events?
        //TODO: time separators in recyclerview
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        medRecyclerView = findViewById(R.id.dailyMedRecyclerView);
        medRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        model.setMedAdapter(new DailyMedicationListAdapter(model.getMedications().getValue(), this, this));
        medRecyclerView.setAdapter(model.getMedAdapter());
    }

    public void showPopup(DailyMedication med) {
        //TODO: fix, make real popup
        TextView popupMedName = findViewById(R.id.popupMedName);
        if (popupMedName != null) popupMedName.setText(med.getMedName());
        Intent intent = new Intent(this, MedPopup.class);
        startActivity(intent);
    }

    /*//TODO: progressbar doesn't work right
    public void hideBar() {
        View progBar = findViewById(R.id.dailyMedProgress);
        progBar.setVisibility(View.INVISIBLE);
    }*/
}
