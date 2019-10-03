package com.example.medicationadherence.ui.medications.wizard;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.medicationadherence.R;
import com.example.medicationadherence.data.room.entities.Doctor;
import com.example.medicationadherence.ui.MainViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


//TODO: https://developers.google.com/places/android-sdk/autocomplete
//TODO: https://developer.android.com/guide/topics/text/autofill
public class WizardDoctorDetailFragment extends Fragment implements RootWizardFragment.ErrFragment {
    private RootWizardViewModel model;
    private Spinner doctorChooser;
    private TextInputLayout doctorNameLayout;
    private TextInputEditText doctorName;
    private TextView doctorNameRequired;
    private TextInputLayout practiceNameLayout;
    private TextInputEditText practiceName;
    private TextInputLayout practiceAddressLayout;
    private TextInputEditText practiceAddress;
    private TextInputLayout phoneLayout;
    private TextInputEditText phone;
    private List<Doctor> doctorList;
    private MainViewModel mainModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(Objects.requireNonNull(getParentFragment().getParentFragment())).get(RootWizardViewModel.class);
        if(model.getThisList().size() == 1)
            model.getThisList().add(this);
        else if (model.getThisList().get(1) != this)
            model.getThisList().set(1, this);
        mainModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        doctorList = mainModel.getRepository().getDoctors();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wizard_doctor_detail, container, false);

        doctorChooser = root.findViewById(R.id.wizardDoctorChooser);
        doctorNameLayout = root.findViewById(R.id.textInputDoctorName);
        doctorName = root.findViewById(R.id.wizardDoctorName);
        doctorNameRequired = root.findViewById(R.id.wizardDocNameRequired);
        practiceNameLayout = root.findViewById(R.id.textInputPracticeName);
        practiceName = root.findViewById(R.id.wizardPracticeName);
        practiceAddressLayout = root.findViewById(R.id.textInputAddress);
        practiceAddress = root.findViewById(R.id.wizardPracticeAddress);
        phoneLayout = root.findViewById(R.id.textInputPhone);
        phone = root.findViewById(R.id.wizardPhone);

        ArrayList<String> doctors = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.doctorChooserItems)));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, doctors);
        for(Doctor doctor : doctorList){
            adapter.add(doctor.getName());
        }
        //TODO: add doctors from db
        doctorChooser.setAdapter(adapter);
        doctorChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    doctorNameLayout.setVisibility(View.GONE);
                    doctorNameRequired.setVisibility(View.GONE);
                    practiceNameLayout.setVisibility(View.GONE);
                    practiceAddressLayout.setVisibility(View.GONE);
                    phoneLayout.setVisibility(View.GONE);
                    model.setDoctorName(null);
                    model.setDestinationExitable(1, true);
                } else {
                    if(position != 1){
                        doctorName.setEnabled(false);
                        practiceName.setEnabled(false);
                        practiceAddress.setEnabled(false);
                        phone.setEnabled(false);
                        doctorNameRequired.setVisibility(View.INVISIBLE);
                        model.setDestinationExitable(1, true);
                        //TODO: fill fields with data from db
                    } else {
                        doctorName.setEnabled(true);
                        practiceName.setEnabled(true);
                        practiceAddress.setEnabled(true);
                        phone.setEnabled(true);
                        model.setDestinationExitable(1, !doctorName.getText().toString().equals(""));
                    }
                    doctorNameLayout.setVisibility(View.VISIBLE);
                    if(savedInstanceState == null)
                        doctorNameRequired.setVisibility(View.INVISIBLE);
                    else if (savedInstanceState.getBoolean("showErrors") && position == 1)
                        showErrors();
                    practiceNameLayout.setVisibility(View.VISIBLE);
                    practiceAddressLayout.setVisibility(View.VISIBLE);
                    phoneLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //This shouldn't happen
            }
        });
        if(savedInstanceState == null)
            doctorChooser.setSelection(model.getSpinnerSelection());
        doctorName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                model.setDestinationExitable(1, !s.toString().equals(""));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //TODO: error check phone number
        return root;
    }

    @Override
    public void showErrors() {
        doctorNameRequired.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        if(model.getDoctorName() != null)
            doctorName.setText(model.getDoctorName());
        if(model.getPracticeName() != null)
            practiceName.setText(model.getPracticeName());
        if(model.getPracticeAddress() != null)
            practiceAddress.setText(model.getPracticeAddress());
        if(model.getPhone() != null)
            phone.setText(model.getPhone());
        super.onResume();
    }

    @Override
    public void onPause() {
        pause();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("showErrors", doctorNameRequired.getVisibility() == View.VISIBLE);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void pause() {
        model.setSpinnerSelection(doctorChooser.getSelectedItemPosition());
        if(!doctorName.getText().toString().equals(""))
            model.setDoctorName(doctorName.getText().toString());
        if(!practiceName.getText().toString().equals(""))
            model.setPracticeName(practiceName.getText().toString());
        if(!practiceAddress.getText().toString().equals(""))
            model.setPracticeAddress(practiceAddress.getText().toString());
        if(!phone.getText().toString().equals(""))
            model.setPhone(phone.getText().toString());
    }
}
