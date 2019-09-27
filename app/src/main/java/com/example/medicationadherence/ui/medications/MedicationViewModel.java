package com.example.medicationadherence.ui.medications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.medicationadherence.R;
import com.example.medicationadherence.model.Medication;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MedicationViewModel extends ViewModel {

    private MutableLiveData<List<Medication>> medications;

    public LiveData<List<Medication>> getMedications(){
        if (medications == null){
            medications = new MutableLiveData<>();
            loadMeds();
        }
        return medications;
    }

    private void loadMeds(){
        //TODO: load from DB
        System.out.println("filling list");
        List<Medication> medList = new ArrayList<>();
        for(int i=0; i<20; i++) {
            medList.add(new Medication(R.mipmap.ic_launcher_round, "Medication " + i, "Doctor " + i, i + " pill(s)", new Time(Calendar.getInstance().getTimeInMillis() + i * 60000)));
        }
        medications.setValue(medList);
    }
}
