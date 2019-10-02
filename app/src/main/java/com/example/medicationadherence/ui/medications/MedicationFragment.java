package com.example.medicationadherence.ui.medications;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.MedicationListAdapter;
import com.example.medicationadherence.model.Medication;
import com.example.medicationadherence.ui.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

//TODO: open wizard
public class MedicationFragment extends Fragment implements Serializable {
    private MedicationViewModel model;
    private MainViewModel mainModel;
    private ArrayList<MedicationFragment> thisList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(this).get(MedicationViewModel.class);
        final Observer<List<Medication>> medicationObserver = new Observer<List<Medication>>() {
            @Override
            public void onChanged(List<Medication> medList) {
                if (model.getMedAdapter() != null)
                    model.getMedAdapter().notifyDataSetChanged();
                System.out.println("observerd");
            }
        };
        model.getMedications().observe(this, medicationObserver);
        mainModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        if (thisList.isEmpty())
            thisList.add(this);
        else
            thisList.set(0, this);
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_medication, container, false);
        RecyclerView medRecyclerView = root.findViewById(R.id.medicationRecyclerView);
        medRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        model.setMedAdapter(new MedicationListAdapter(model.getMedications().getValue()));
        medRecyclerView.setAdapter(model.getMedAdapter());
        setHasOptionsMenu(true);
        sort(mainModel.getMedSortMode());
        FloatingActionButton addMed = root.findViewById(R.id.addMedButton);
        addMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicationFragmentDirections.ActionNavMedicationsToRootWizardFragment action = MedicationFragmentDirections.actionNavMedicationsToRootWizardFragment(thisList);
                Navigation.findNavController(v).navigate(action);
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.sort, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_az_asc: sort(0); mainModel.setMedSortMode(0); break;
            case R.id.sort_az_desc: sort(1); mainModel.setMedSortMode(1); break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sort(int sortMode){
        switch (sortMode){
            case 0: sortAZAsc(); break;
            case 1: sortAZDesc(); break;
        }
        model.getMedAdapter().notifyDataSetChanged();
    }

    private void sortAZAsc(){
        Collections.sort(Objects.requireNonNull(model.getMedications().getValue()), new Comparator<Medication>() {
            @Override
            public int compare(Medication o1, Medication o2) {
                return (o1.getMedName().compareToIgnoreCase(o2.getMedName()));
            }
        });
    }

    private void sortAZDesc(){
        Collections.sort(Objects.requireNonNull(model.getMedications().getValue()), new Comparator<Medication>() {
            @Override
            public int compare(Medication o1, Medication o2) {
                return (o2.getMedName().compareToIgnoreCase(o1.getMedName()));
            }
        });
    }

    private void sortStartAsc(){
        Collections.sort(Objects.requireNonNull(model.getMedications().getValue()), new Comparator<Medication>() {
            @Override
            public int compare(Medication o1, Medication o2) {
                return (Long.compare(o1.getStartDate(), o2.getStartDate()));
            }
        });
    }

    private void sortStartDesc(){
        Collections.sort(Objects.requireNonNull(model.getMedications().getValue()), new Comparator<Medication>() {
            @Override
            public int compare(Medication o1, Medication o2) {
                return (Long.compare(o2.getStartDate(), o1.getStartDate()));
            }
        });
    }

    private void sortEndAsc(){
        Collections.sort(Objects.requireNonNull(model.getMedications().getValue()), new Comparator<Medication>() {
            @Override
            public int compare(Medication o1, Medication o2) {
                return (Long.compare(o1.getEndDate(), o2.getEndDate()));
            }
        });
    }

    private void sortEndDesc(){
        Collections.sort(Objects.requireNonNull(model.getMedications().getValue()), new Comparator<Medication>() {
            @Override
            public int compare(Medication o1, Medication o2) {
                return (Long.compare(o2.getEndDate(), o1.getEndDate()));
            }
        });
    }

    private void sortActive(){ //sorts active/inactive a-z
        Collections.sort(Objects.requireNonNull(model.getMedications().getValue()), new Comparator<Medication>() {
            @Override
            public int compare(Medication o1, Medication o2) {
                int compared = Boolean.compare(o2.isActive(), o1.isActive());
                return (compared == 0) ? (o1.getMedName().compareToIgnoreCase(o2.getMedName())) : compared;
            }
        });
    }
}
