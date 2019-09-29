package com.example.medicationadherence.ui.medications;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.MedicationListAdapter;
import com.example.medicationadherence.model.Medication;

import java.util.List;

public class MedicationFragment extends Fragment {
    private MedicationViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(this).get(MedicationViewModel.class);
        final Observer<List<Medication>> medicationObserver = new Observer<List<Medication>>() {
            @Override
            public void onChanged(List<Medication> medList) {
                if (model.getMedAdapter() != null)
                    model.getMedAdapter().notifyDataSetChanged();
            }
        };
        model.getMedications().observe(this, medicationObserver);
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_medication, container, false);
        RecyclerView medRecyclerView = root.findViewById(R.id.medicationRecyclerView);
        medRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        model.setMedAdapter(new MedicationListAdapter(model.getMedications().getValue()));
        medRecyclerView.setAdapter(model.getMedAdapter());
        return root;
    }
}
