package com.example.medicationadherence.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.DailyMedicationListAdapter;
import com.example.medicationadherence.model.DailyMedication;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class DailyMedicationList extends AppCompatActivity {

    private RecyclerView medRecyclerView;
    private DailyMedicationListAdapter medAdapter;
    private ArrayList<DailyMedication> medList;


    //TODO: add as needed meds at bottom
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //TODO: coloring for past events?
        //TODO: time separators in recyclerview
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        medRecyclerView = findViewById(R.id.dailyMedRecyclerView);
        medRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState != null){
            //medList = (ArrayList<DailyMedication>) savedInstanceState.getSerializable("medList");
            //medlist still exists for some reason?
            medAdapter = (DailyMedicationListAdapter) savedInstanceState.getSerializable("medAdapter");
            hideBar();
        } else{
            medList = new ArrayList<>();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    populateMedList(medList);
                }
            });
            medAdapter = new DailyMedicationListAdapter(medList, this, this);
        }
        medRecyclerView.setAdapter(medAdapter);
    }

    public void showPopup(DailyMedication med){
        //TODO: fix, make real popup
        TextView popupMedName = findViewById(R.id.popupMedName);
        if (popupMedName != null) popupMedName.setText(med.getMedName());
        Intent intent = new Intent(this, MedPopup.class);
        startActivity(intent);
    }

    public void updateMedList() {
        medAdapter.notifyDataSetChanged();
        //medRecyclerView.invalidate();
    }

    //TODO: progressbar doesn't work right
    public void hideBar(){
        View progBar = findViewById(R.id.dailyMedProgress);
        progBar.setVisibility(View.INVISIBLE);
    }

    public void populateMedList(List<DailyMedication> medList){
        //TODO: actual data population
        System.out.println("Population start time: "+Calendar.getInstance().getTime());
        for(int i=0; i<20; i++){
            long time = Calendar.getInstance().getTimeInMillis();
            //while (Calendar.getInstance().getTimeInMillis()<time+1000);
            medList.add(new DailyMedication((new Random().nextBoolean()) ? R.mipmap.ic_launcher_round: -1, "DailyMedication "+i, i+" pill(s)", new Time(Calendar.getInstance().getTimeInMillis()+i*60000), ""));
            //TODO: may want to move up, not great to call each time
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateMedList();
                }
            });
            System.out.println("looped");
        }
        System.out.println("Population end time: "+Calendar.getInstance().getTime());
        hideBar();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("medList", medList);
        outState.putSerializable("medAdapter", medAdapter);
        hideBar();
        super.onSaveInstanceState(outState);
    }
}
