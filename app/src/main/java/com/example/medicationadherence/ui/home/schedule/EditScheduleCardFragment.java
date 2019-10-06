package com.example.medicationadherence.ui.home.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.medicationadherence.R;
import com.example.medicationadherence.ui.medications.wizard.RootWizardFragment;
import com.example.medicationadherence.ui.medications.wizard.RootWizardViewModel;

public class EditScheduleCardFragment extends Fragment {
    private boolean fromWizard;
    private RootWizardViewModel wizardModel;
    Long medID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(fromWizard = getParentFragment().getParentFragment() instanceof RootWizardFragment){
            wizardModel = new ViewModelProvider(getParentFragment().getParentFragment()).get(RootWizardViewModel.class);
        } else {
            medID = EditScheduleCardFragmentArgs.fromBundle(getArguments()).getMedicationID();
        }
        System.out.println(fromWizard);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_schedule_card, container, false);
    }
}
