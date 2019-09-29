package com.example.medicationadherence.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.DailyMedicationListAdapter;
import com.example.medicationadherence.model.DailyMedication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class DailyMedListViewModel extends ViewModel {
    private DailyMedicationListAdapter medAdapter;
    private MutableLiveData<List<DailyMedication>> medications;

    DailyMedicationListAdapter getMedAdapter() {
        return medAdapter;
    }

    void setMedAdapter(DailyMedicationListAdapter medAdapter) {
        this.medAdapter = medAdapter;
    }

    MutableLiveData<List<DailyMedication>> getMedications(){
        if (medications == null){
            medications = new MutableLiveData<>();
            loadMeds();
        }
        return medications;
    }

    private void loadMeds(){
        //TODO: load from db
        System.out.println("filling daily list");
        List<DailyMedication> medList = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            medList.add(new DailyMedication((new Random().nextBoolean()) ? R.mipmap.ic_launcher_round : -1, "DailyMedication" + i, i + " pill(s)", Calendar.getInstance().getTimeInMillis() + i * 60000, ""));
        }
        medications.setValue(medList);
    }
}
