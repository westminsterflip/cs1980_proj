package com.example.medicationadherence.ui.medications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.MedicationListAdapter;
import com.example.medicationadherence.model.Medication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MedicationViewModel extends ViewModel {

    private MutableLiveData<List<Medication>> medications;

    private MedicationListAdapter medAdapter;

    LiveData<List<Medication>> getMedications(){
        if (medications == null){
            medications = new MutableLiveData<>();
            loadMeds();
        }
        return medications;
    }

    private void loadMeds(){
        //TODO: load from DB
        List<Medication> medList = new ArrayList<>();
        for(int i=0; i<20; i++) {
            medList.add(new Medication((new Random().nextBoolean()) ? R.mipmap.ic_launcher_round: -1, "Medication " + i, i + " pill(s)", "", "Doctor" + 1, 5, -1));
        }
        medications.setValue(medList);
    }

    MedicationListAdapter getMedAdapter() {
        return medAdapter;
    }

    void setMedAdapter(MedicationListAdapter medAdapter) {
        this.medAdapter = medAdapter;
    }
}
