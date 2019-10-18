package com.example.medicationadherence.ui.medications.wizard;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
//TODO: dialog for updating if name exists but fields null
public class WizardDoctorDetailFragment extends Fragment implements RootWizardFragment.ErrFragment {
    private RootWizardViewModel model;
    public Spinner doctorChooser;
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
    private CheckBox scheduleAfter;
    private boolean exitable = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(Objects.requireNonNull(Objects.requireNonNull(getParentFragment()).getParentFragment())).get(RootWizardViewModel.class);
        if(model.getThisList().size() == 1)
            model.getThisList().add(this);
        else if (model.getThisList().get(1) != this)
            model.getThisList().set(1, this);
        mainModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        doctorList = mainModel.getRepository().getDoctors();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wizard_doctor_detail, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Medication");

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
        scheduleAfter = root.findViewById(R.id.wizardScheduleAfter);

        ArrayList<String> doctors = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.doctorChooserItems)));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.support_simple_spinner_dropdown_item, doctors);
        for(Doctor doctor : doctorList){
            adapter.add(doctor.getName());
        }
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
                    doctorName.setText("");
                    practiceName.setText("");
                    practiceAddress.setText("");
                    phone.setText("");
                    model.setDoctorName(null);
                    exitable = true;
                } else {
                    if(position != 1){
                        doctorNameRequired.setVisibility(View.INVISIBLE);
                        exitable = true;
                        doctorName.setText(doctorList.get(position-2).getName());
                        practiceName.setText(doctorList.get(position-2).getPracticeName());
                        practiceAddress.setText(doctorList.get(position-2).getAddress());
                        phone.setText(doctorList.get(position-2).getPhone());
                    } else {
                        doctorName.setText("");
                        practiceName.setText("");
                        practiceAddress.setText("");
                        phone.setText("");
                        exitable = !Objects.requireNonNull(doctorName.getText()).toString().equals("");
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
        if(savedInstanceState == null) {
            doctorChooser.setSelection(model.getSpinnerSelection());
            scheduleAfter.setChecked(model.isScheduleAfter());
        }
        doctorName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                exitable = !s.toString().equals("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        scheduleAfter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("checked: " + isChecked);
                if (isChecked){
                    model.getDestinations().getValue().add(R.id.editScheduleFragment2);
                    model.getDestinations().getValue().add(R.id.editScheduleCardFragment2);
                } else {
                    model.getDestinations().getValue().remove((Integer)R.id.editScheduleFragment2);
                    model.getDestinations().getValue().remove((Integer)R.id.editScheduleCardFragment2);
                }
                model.getDestinations().postValue(model.getDestinations().getValue());
            }
        });
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
    public void pause() {
        model.setSpinnerSelection(doctorChooser.getSelectedItemPosition());
        if(!Objects.requireNonNull(doctorName.getText()).toString().equals(""))
            model.setDoctorName(doctorName.getText().toString());
        if(!Objects.requireNonNull(practiceName.getText()).toString().equals(""))
            model.setPracticeName(practiceName.getText().toString());
        if(!Objects.requireNonNull(practiceAddress.getText()).toString().equals(""))
            model.setPracticeAddress(practiceAddress.getText().toString());
        if(!Objects.requireNonNull(phone.getText()).toString().equals(""))
            model.setPhone(phone.getText().toString());
        model.setScheduleAfter(scheduleAfter.isChecked());
    }

    @Override
    public boolean isExitable() {
        return exitable;
    }
}
