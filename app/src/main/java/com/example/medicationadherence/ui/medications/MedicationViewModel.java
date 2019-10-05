package com.example.medicationadherence.ui.medications;

import androidx.lifecycle.ViewModel;

import com.example.medicationadherence.adapter.MedicationListAdapter;
import com.example.medicationadherence.data.room.entities.Medication;

import java.util.List;

public class MedicationViewModel extends ViewModel {
    private MedicationListAdapter medAdapter;
    private List<Medication> medList;

    public MedicationListAdapter getMedAdapter() {
        return medAdapter;
    }

    public void setMedAdapter(MedicationListAdapter medAdapter) {
        this.medAdapter = medAdapter;
    }

    public List<Medication> getMedList() {
        return medList;
    }

    public void setMedList(List<Medication> medList) {
        this.medList = medList;
    }
}
