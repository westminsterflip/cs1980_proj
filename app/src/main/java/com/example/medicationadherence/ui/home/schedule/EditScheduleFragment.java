package com.example.medicationadherence.ui.home.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.medicationadherence.R;
import com.example.medicationadherence.ui.medications.wizard.RootWizardFragment;

public class EditScheduleFragment extends Fragment implements RootWizardFragment.ErrFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_schedule, container, false);

        return root;
    }

    @Override
    public void showErrors() {

    }

    @Override
    public void pause() {

    }
}
