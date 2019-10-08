package com.example.medicationadherence.ui.home.schedule;

import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.os.ConfigurationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.medicationadherence.R;
import com.example.medicationadherence.data.room.entities.Medication;
import com.example.medicationadherence.ui.MainViewModel;
import com.example.medicationadherence.ui.medications.wizard.RootWizardFragment;
import com.example.medicationadherence.ui.medications.wizard.RootWizardViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditScheduleCardFragment extends Fragment implements RootWizardFragment.ErrFragment {
    private boolean fromWizard;
    private RootWizardViewModel wizardModel;
    private Long medID;
    private TextView medName;
    private ImageButton increaseDoses;
    private EditText doseCount;
    private ImageButton decreaseDoses;
    private TextView doseSize;
    private Switch daily;
    private CheckBox sun, mon, tues, wed, thurs, fri, sat;
    private Button setStart;
    private TextView startDate;
    private Button setEnd;
    private TextView endDate;
    private Button setTime;
    private TextView time;
    private TextView dosesAt;
    private MainViewModel mainModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(fromWizard = getParentFragment().getParentFragment() instanceof RootWizardFragment){
            wizardModel = new ViewModelProvider(getParentFragment().getParentFragment()).get(RootWizardViewModel.class);
            if ( wizardModel.getThisList().size() < 3)
                wizardModel.getThisList().add(this);
            else if(!wizardModel.getThisList().get(2).equals(this))
                wizardModel.getThisList().set(2,this);
        } else {
            medID = EditScheduleCardFragmentArgs.fromBundle(getArguments()).getMedicationID();
        }
        System.out.println(fromWizard);
        mainModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_schedule_card, container, false);

        medName = root.findViewById(R.id.scheduleMedName);
        increaseDoses = root.findViewById(R.id.scheduleIncreaseDoses);
        doseCount = root.findViewById(R.id.scheduleDoseCount);
        decreaseDoses = root.findViewById(R.id.scheduleDecreaseDoses);
        doseSize = root.findViewById(R.id.scheduleDoseSize);
        daily = root.findViewById(R.id.scheduleDaily);
        sun = root.findViewById(R.id.scheduleSunday);
        mon = root.findViewById(R.id.scheduleMonday);
        tues = root.findViewById(R.id.scheduleTuesday);
        wed = root.findViewById(R.id.scheduleWednesday);
        thurs = root.findViewById(R.id.scheduleThursday);
        fri = root.findViewById(R.id.scheduleFriday);
        sat = root.findViewById(R.id.scheduleSaturday);
        setStart = root.findViewById(R.id.scheduleSetStart);
        startDate = root.findViewById(R.id.scheduleStart);
        setEnd = root.findViewById(R.id.scheduleSetEnd);
        endDate = root.findViewById(R.id.scheduleEndDate);
        setTime = root.findViewById(R.id.scheduleSetTime);
        time = root.findViewById(R.id.scheduleTime);
        dosesAt = root.findViewById(R.id.scheduleDosesText);

        final CompoundButton.OnCheckedChangeListener dailyListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sun.setChecked(isChecked);
                mon.setChecked(isChecked);
                tues.setChecked(isChecked);
                wed.setChecked(isChecked);
                thurs.setChecked(isChecked);
                fri.setChecked(isChecked);
                sat.setChecked(isChecked);
            }
        };
        daily.setOnCheckedChangeListener(dailyListener);
        CompoundButton.OnCheckedChangeListener dayListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                daily.setOnCheckedChangeListener(null);
                if(sun.isChecked() && mon.isChecked() && tues.isChecked() && wed.isChecked() && thurs.isChecked() && fri.isChecked() && sat.isChecked()){
                    daily.setChecked(true);
                } else {
                    daily.setChecked(false);
                }
                if(fromWizard && (sun.isChecked() || mon.isChecked() || tues.isChecked() || wed.isChecked() || thurs.isChecked() || fri.isChecked() || sat.isChecked()) && wizardModel.getScheduleTime() != -1)
                    wizardModel.setDestinationExitable(2, true);
                daily.setOnCheckedChangeListener(dailyListener);
            }
        };
        sun.setOnCheckedChangeListener(dayListener);
        mon.setOnCheckedChangeListener(dayListener);
        tues.setOnCheckedChangeListener(dayListener);
        wed.setOnCheckedChangeListener(dayListener);
        thurs.setOnCheckedChangeListener(dayListener);
        fri.setOnCheckedChangeListener(dayListener);
        sat.setOnCheckedChangeListener(dayListener);
        increaseDoses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double doseVal = Double.parseDouble(doseCount.getText().toString());
                doseVal = Math.ceil(doseVal * 2.0) / 2.0;
                if(Double.parseDouble(doseCount.getText().toString()) != doseVal){
                    doseCount.setText((doseVal + ""));
                } else {
                    doseCount.setText(((doseVal += .5) + ""));
                }
                if(doseVal != 1){
                    dosesAt.setText((" doses @ "));
                } else {
                    dosesAt.setText((" dose @ "));
                }
            }
        });
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.clear();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        if ( fromWizard)
                            wizardModel.setScheduleTime(c.getTimeInMillis());
                        String timeText;
                        if(DateFormat.is24HourFormat(getContext()))
                            timeText = new SimpleDateFormat("kk:mm", ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)).format(c.getTimeInMillis());
                        else
                            timeText = new SimpleDateFormat("hh:mm aa", ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)).format(c.getTimeInMillis());
                        time.setText(timeText);
                        if(fromWizard && (sun.isChecked() || mon.isChecked() || tues.isChecked() || wed.isChecked() || thurs.isChecked() || fri.isChecked() || sat.isChecked()) && wizardModel.getScheduleTime() != -1)
                            wizardModel.setDestinationExitable(2, true);
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });

        if(fromWizard){
            medName.setHeight(0);
            medName.setVisibility(View.INVISIBLE);
            setStart.setVisibility(View.GONE);
            startDate.setVisibility(View.GONE);
            endDate.setVisibility(View.GONE);
            setEnd.setVisibility(View.GONE);
            doseCount.setText((1+""));
            doseSize.setText(wizardModel.getMedDosage());
        } else {
            Medication medication = mainModel.getMedWithID(medID);
            medName.setText(medication.getName());
            doseSize.setText(medication.getDosage());
        }

        return root;
    }

    @Override
    public void showErrors() {

    }

    @Override
    public void pause() {

    }
}
