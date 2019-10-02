package com.example.medicationadherence.ui.medications.wizard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.medicationadherence.R;

import java.util.Objects;

//TODO: https://developer.android.com/guide/topics/text/autofill
//TODO: if you type text in a number field the app freezes and crashes
public class WizardMedicineDetailFragment extends Fragment {
    private RootWizardViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(Objects.requireNonNull(getParentFragment())).get(RootWizardViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wizard_medicine_detail, container, false);

        return root;
    }

}
