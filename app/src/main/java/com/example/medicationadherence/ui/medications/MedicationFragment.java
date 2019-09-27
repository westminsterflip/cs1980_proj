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

import com.example.medicationadherence.MainActivity;
import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.MedicationListAdapter;
import com.example.medicationadherence.model.Medication;

import java.util.ArrayList;
import java.util.List;

public class MedicationFragment extends Fragment {
    private MedicationViewModel model;
    private MedicationListAdapter medAdapter;//TODO: move to viewmodel
    private RecyclerView medRecyclerView;
    private List<Medication> medicationList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(this).get(MedicationViewModel.class);
        final Observer<List<Medication>> medicationObserver = new Observer<List<Medication>>() {
            @Override
            public void onChanged(List<Medication> medList) {
                System.out.println("*****************\r\rUpdated\r\r*****************");
                if (medicationList == null) medicationList = new ArrayList<>();
                for (Medication i:medList){
                    System.out.println(i.getMedName());
                    medicationList.add(i);
                }
                for (Medication i:medicationList){
                    System.out.println(i.getMedName());
                }
                if (medAdapter != null)
                    medAdapter.notifyDataSetChanged();
            }
        };
        model.getMedications().observe(this, medicationObserver);
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_medication, container, false);
        if(medicationList == null){
            System.out.println("SDAAIGOSDFHDGDOIASDOIHPOJSDPOKSDIHOGUINHASDOIGJNO?N?N?N?N");
        }
        medRecyclerView = root.findViewById(R.id.medicationRecyclerView);
        medRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        medAdapter = new MedicationListAdapter(medicationList, getContext(), (MainActivity)getActivity());
        //TODO: wait for list to be not null
        medRecyclerView.setAdapter(medAdapter);
        return root;
    }
}
