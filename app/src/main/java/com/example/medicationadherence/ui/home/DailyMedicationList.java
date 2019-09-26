package com.example.medicationadherence.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.DailyMedicationListAdapter;
import com.example.medicationadherence.model.Medication;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyMedicationList extends AppCompatActivity {

    private RecyclerView medRecyclerView;
    private DailyMedicationListAdapter medAdapter;
    private List<Medication> medList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_medication_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: Add med button?
        /*FloatingActionButton fab = findViewById(R.id.addMedButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Button addMed = findViewById(R.id.addMedButton);
        addMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //action
            }
        });*/
        medRecyclerView = findViewById(R.id.dailyMedRecyclerView);
        medRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        medList = new ArrayList<>();
        populateMedList(medList);

        medAdapter = new DailyMedicationListAdapter(medList, this, this);
        medRecyclerView.setAdapter(medAdapter);
    }

    public void showPopup(Medication med){
        //TODO: fix, make real popup
        //TODO: can be same popup for daily and overall list
        TextView popupMedName = findViewById(R.id.popupMedName);
        if (popupMedName != null) popupMedName.setText(med.getMedName());
        Intent intent = new Intent(this, MedPopup.class);
        startActivity(intent);
    }

    public void populateMedList(List<Medication> medList){
        //TODO: actual data population
        for(int i=0; i<20; i++){
            medList.add(new Medication(R.mipmap.ic_launcher_round, "Medication "+i, "Doctor "+i, i+" pill(s)", new Time(Calendar.getInstance().getTimeInMillis()+i*60000)));
        }
    }

}
