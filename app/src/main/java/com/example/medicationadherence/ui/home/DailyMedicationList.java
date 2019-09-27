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
import com.example.medicationadherence.model.Medication;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyMedicationList extends AppCompatActivity {

    private RecyclerView medRecyclerView;
    private DailyMedicationListAdapter medAdapter;
    private ArrayList<Medication> medList;
    private AsyncTask task;

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
        //TODO: time separators in recyclerview
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        medRecyclerView = findViewById(R.id.dailyMedRecyclerView);
        medRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState != null){
            //medList = (ArrayList<Medication>) savedInstanceState.getSerializable("medList");
            medAdapter = (DailyMedicationListAdapter) savedInstanceState.getSerializable("medAdapter");
        } else{
            medList = new ArrayList<>();
            task.execute(new Runnable() {
                @Override
                public void run() {
                    populateMedList(medList);
                }
            });
            medAdapter = new DailyMedicationListAdapter(medList, this, this);
        }
        medRecyclerView.setAdapter(medAdapter);
    }

    @Override
    protected void onDestroy() {
        //TODO: this is wrong
        //task.cancel(true);
        super.onDestroy();
    }

    public void showPopup(Medication med){
        //TODO: fix, make real popup
        //TODO: can be same popup for daily and overall list
        TextView popupMedName = findViewById(R.id.popupMedName);
        if (popupMedName != null) popupMedName.setText(med.getMedName());
        Intent intent = new Intent(this, MedPopup.class);
        startActivity(intent);
    }

    public void updateMedList() {
        medAdapter.notifyDataSetChanged();
        medRecyclerView.invalidate();
    }

    //TODO: progressbar doesn't work right
    public void hideBar(){
        View progBar = findViewById(R.id.dailyMedProgress);
        progBar.setVisibility(View.INVISIBLE);
    }

    public void populateMedList(List<Medication> medList){
        //TODO: actual data population
        //TODO: pause task?
        //TODO: stop task when activity ends
        System.out.println("Population start time: "+Calendar.getInstance().getTime());
        for(int i=0; i<20; i++){
            long time = Calendar.getInstance().getTimeInMillis();
            while (Calendar.getInstance().getTimeInMillis()<time+1000);
            medList.add(new Medication(R.mipmap.ic_launcher_round, "Medication "+i, "Doctor "+i, i+" pill(s)", new Time(Calendar.getInstance().getTimeInMillis()+i*60000)));
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
