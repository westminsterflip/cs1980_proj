package com.example.medicationadherence.ui.medications.wizard;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.ConfigurationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.medicationadherence.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

//TODO: https://developer.android.com/guide/topics/text/autofill
//TODO: if you type text in a number field the app freezes and crashes
public class WizardMedicineDetailFragment extends Fragment implements RootWizardFragment.ErrFragment {
    private RootWizardViewModel model;
    private TextInputEditText medName;
    private TextView medNameRequired;
    private Switch active;
    private Button setStart;
    private TextView startDate;
    private Button setEnd;
    private TextView endDate;
    private TextInputEditText perPillDosage;
    private Spinner dosageUnitSelector;
    private TextView dosageRequired;
    private TextInputEditText onHand;
    private TextInputEditText cost;
    private CheckBox asNeeded;
    private TextInputEditText instructions;
    private DatePickerDialog datePickerDialog; //TODO: Currently has to be dismissed on theme/configuration change or app crashes

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(Objects.requireNonNull(getParentFragment().getParentFragment())).get(RootWizardViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wizard_medicine_detail, container, false);

        medName = root.findViewById(R.id.wizardMedName);
        medNameRequired = root.findViewById(R.id.wizardMedNameRequired);
        active = root.findViewById(R.id.wizardActive);
        setStart = root.findViewById(R.id.wizardSetStartDate);
        startDate = root.findViewById(R.id.wizardStartDate);
        setEnd = root.findViewById(R.id.wizardSetEndDate);
        endDate = root.findViewById(R.id.wizardEndDate);
        perPillDosage = root.findViewById(R.id.wizardPerPillDosage);
        dosageUnitSelector = root.findViewById(R.id.wizardDosageSpinner);
        dosageRequired = root.findViewById(R.id.wizardPillDoseRequired);
        onHand = root.findViewById(R.id.wizardOnHand);
        cost = root.findViewById(R.id.wizardCost);
        asNeeded = root.findViewById(R.id.wizardAsNeeded);
        instructions = root.findViewById(R.id.wizardInstructions);
        final TextView endBefore = root.findViewById(R.id.wizardEndDateBefore);

        if(savedInstanceState != null) {
            medNameRequired.setVisibility((savedInstanceState.getBoolean("medNameRequiredVisible", false)) ? View.VISIBLE : View.INVISIBLE);
            dosageRequired.setVisibility((savedInstanceState.getBoolean("dosageRequiredVisible", false)) ? View.VISIBLE : View.INVISIBLE);;
        }

        setStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("startdate");
                final Calendar cal = Calendar.getInstance();
                System.out.println(cal.get(Calendar.MONTH));
                cal.set(Calendar.HOUR_OF_DAY,0);
                cal.clear(Calendar.AM_PM);
                cal.clear(Calendar.MINUTE);
                cal.clear(Calendar.SECOND);
                cal.clear(Calendar.MILLISECOND);
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        cal.clear();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        startDate.setText(new SimpleDateFormat("MM/dd/yyyy", ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)).format(new Date(cal.getTimeInMillis())));
                        model.setStartDate(cal.getTimeInMillis());
                    }
                };
                datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), listener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setOnCancelListener(null);
                datePickerDialog.show();
            }
        });
        setEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("enddate");
                final Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY,0);
                cal.clear(Calendar.AM_PM);
                cal.clear(Calendar.MINUTE);
                cal.clear(Calendar.SECOND);
                cal.clear(Calendar.MILLISECOND);
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        cal.clear();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        if(cal.getTimeInMillis() < model.getStartDate()){
                            model.setEndDate(-1);
                            endDate.setText("");
                            endBefore.setVisibility(View.VISIBLE);
                        } else {
                            endBefore.setVisibility(View.INVISIBLE);
                            endDate.setText(new SimpleDateFormat("MM/dd/yyyy", ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)).format(new Date(cal.getTimeInMillis())));
                        }
                        model.setEndDate(cal.getTimeInMillis());
                    }
                };
                DatePickerDialog.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        model.setEndDate(-1);
                        endDate.setText("");
                    }
                };
                datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), listener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setOnCancelListener(cancelListener);
                datePickerDialog.show();
            }
        });
        medName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")) {
                    model.setMedName(s.toString());
                    model.setDestinationExitable(0, !perPillDosage.getText().toString().equals(""));
                } else {
                    model.setDestinationExitable(0,false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        perPillDosage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")) {
                    model.setMedDosage(s.toString() + dosageUnitSelector.getSelectedItem());
                    model.setDestinationExitable(0, !medName.getText().toString().equals(""));
                } else {
                    model.setDestinationExitable(0, false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return root;
    }

    @Override
    public void onResume() {
        if (model.getMedName() != null)
            medName.setText(model.getMedName());
        active.setChecked(model.isActive());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        if(model.getStartDate() == -1){
            model.setStartDate(cal.getTimeInMillis());
        }
        startDate.setText(new SimpleDateFormat("MM/dd/yyyy", ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)).format(new Date(model.getStartDate())));
        if(model.getEndDate() != -1)
            endDate.setText(new SimpleDateFormat("MM/dd/yyyy", ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)).format(new Date(model.getEndDate())));
        if(model.getMedDosage() != null)
            perPillDosage.setText(model.getMedDosage().replaceAll("[^\\d.]",""));
        String unit;
        if(model.getMedDosage() != null && !dosageUnitSelector.getSelectedItem().equals(unit = model.getMedDosage().replaceAll("[\\d.]", "")))
            dosageUnitSelector.setSelection(Arrays.asList(getResources().getStringArray(R.array.medDosageUnits)).indexOf(unit));
        if(model.getOnHand() != -1)
            onHand.setText(model.getOnHand());
        if (model.getCost() != -1)
            cost.setText((model.getCost()+""));
        asNeeded.setChecked(model.isAsNeeded());
        if(model.getInstructions() != null)
            instructions.setText(model.getInstructions());
        if(model.getThisList().isEmpty())
            model.getThisList().add(this);
        else if (model.getThisList().get(0) != this)
            model.getThisList().set(0, this);
        super.onResume();
    }

    @Override
    public void onPause() {
        model.setMedName(medName.getText().toString());
        model.setMedDosage(perPillDosage.getText().toString()+dosageUnitSelector.getSelectedItem());
        if(!instructions.getText().toString().equals(""))
            model.setInstructions(instructions.getText().toString());
        model.setActive(active.isChecked());
        if(!onHand.getText().toString().equals(""))
            model.setOnHand(Integer.parseInt(onHand.getText().toString()));
        if(!cost.getText().toString().equals(""))
            model.setCost(Double.parseDouble(cost.getText().toString()));
        model.setAsNeeded(asNeeded.isChecked());
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("medNameRequiredVisible", medNameRequired.getVisibility() == View.VISIBLE);
        outState.putBoolean("dosageRequiredVisible", dosageRequired.getVisibility() == View.VISIBLE);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showErrors() {
        if(medName.getText().toString().equals(""))
            medNameRequired.setVisibility(View.VISIBLE);
        else
            medNameRequired.setVisibility(View.INVISIBLE);
        if(perPillDosage.getText().toString().equals(""))
            dosageRequired.setVisibility(View.VISIBLE);
        else
            dosageRequired.setVisibility(View.INVISIBLE);
    }
}
