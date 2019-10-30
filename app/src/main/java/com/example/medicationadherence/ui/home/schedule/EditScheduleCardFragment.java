package com.example.medicationadherence.ui.home.schedule;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.ScheduleTimeAdapter;
import com.example.medicationadherence.data.Converters;
import com.example.medicationadherence.data.room.entities.Medication;
import com.example.medicationadherence.data.room.entities.Schedule;
import com.example.medicationadherence.ui.MainViewModel;
import com.example.medicationadherence.ui.medications.wizard.RootWizardFragment;
import com.example.medicationadherence.ui.medications.wizard.RootWizardViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class EditScheduleCardFragment extends Fragment implements RootWizardFragment.ErrFragment {
    private boolean fromWizard;
    private RootWizardViewModel wizardModel;
    private Long medID;
    private TextView timeErr;
    private TextView dayErr;
    private Switch daily;
    private CheckBox sun, mon, tues, wed, thurs, fri, sat;
    private boolean exitable = false;
    private boolean[] checks;
    private TimePickerDialog timePickerDialog;
    private boolean cancel = false;
    private AlertDialog.Builder doseCountDialog;
    private boolean[] fill = {false,false,false,false,false,false,false,true};
    private MainViewModel mainModel;
    private int numDoses;
    private EditText doseCount;
    private ImageButton increaseDoses;
    private ImageButton decreaseDoses;
    private TextView doseCountSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(fromWizard = Objects.requireNonNull(getParentFragment()).getParentFragment() instanceof RootWizardFragment){
            wizardModel = new ViewModelProvider(Objects.requireNonNull(getParentFragment().getParentFragment())).get(RootWizardViewModel.class);
            checks = Converters.intToBoolArray(EditScheduleCardFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getDays());
            if ( wizardModel.getThisList().size() < 5)
                wizardModel.getThisList().add(this);
            else if(!wizardModel.getThisList().get(4).equals(this))
                wizardModel.getThisList().set(4,this);
        }
        cancel = Converters.fromBoolArray(checks) == 0;
        mainModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_schedule_card, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Schedule");

        TextView medName = root.findViewById(R.id.scheduleMedName);
        daily = root.findViewById(R.id.scheduleDaily);
        sun = root.findViewById(R.id.scheduleSunday);
        mon = root.findViewById(R.id.scheduleMonday);
        tues = root.findViewById(R.id.scheduleTuesday);
        wed = root.findViewById(R.id.scheduleWednesday);
        thurs = root.findViewById(R.id.scheduleThursday);
        fri = root.findViewById(R.id.scheduleFriday);
        sat = root.findViewById(R.id.scheduleSaturday);
        final Button addTime = root.findViewById(R.id.scheduleSetTime);
        RecyclerView times = root.findViewById(R.id.scheduleTimes);
        timeErr = root.findViewById(R.id.timeCardTimeErr);
        dayErr = root.findViewById(R.id.timeCardDayErr);

        sun.setChecked(checks[0]);
        mon.setChecked(checks[1]);
        tues.setChecked(checks[2]);
        wed.setChecked(checks[3]);
        thurs.setChecked(checks[4]);
        fri.setChecked(checks[5]);
        sat.setChecked(checks[6]);

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
                    exitable = true;
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

        for (Schedule s : wizardModel.getSchedules()){
            if (Converters.fromBoolArray(s.getWeekdays()) == Converters.fromBoolArray(checks))
                s.setWeekdays(fill);
        }

        times.setLayoutManager(new LinearLayoutManager(getContext()));
        final ScheduleTimeAdapter adapter = new ScheduleTimeAdapter(wizardModel, wizardModel.getDoseEntries(fill), fill);
        times.setAdapter(adapter);

        if(Converters.fromBoolArray(checks) != 0 && wizardModel.getDoseEntries(fill).size() != 0)
            exitable = true;
        final TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onTimeSet(TimePicker view, final int hourOfDay, final int minute) {
                View v = inflater.inflate(R.layout.layout_dose_selector, null, false); //TODO: dialog too wide
                increaseDoses = v.findViewById(R.id.doseCountUp);
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
                        if (doseVal - .5 > 0)
                            decreaseDoses.setEnabled(true);
                    }
                });
                doseCount = v.findViewById(R.id.doseCountText);
                doseCount.setText((0+""));
                doseCount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        double doseVal = Double.parseDouble(s.toString());
                        decreaseDoses.setEnabled(doseVal - .5 > 0);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                doseCountSize = v.findViewById(R.id.doseCountSize);
                doseCountSize.setText(("@ " + wizardModel.getMedDosage()));
                decreaseDoses = v.findViewById(R.id.doseCountDown);
                decreaseDoses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double doseVal = Double.parseDouble(doseCount.getText().toString());
                        doseVal = Math.floor(doseVal * 2.0) / 2.0;
                        if(Double.parseDouble(doseCount.getText().toString()) != doseVal){
                            doseCount.setText((doseVal + ""));
                        } else {
                            doseCount.setText(((doseVal -= .5) + ""));
                        }
                        if (doseVal - .5 <= 0)
                            decreaseDoses.setEnabled(false);
                    }
                });
                decreaseDoses.setEnabled(false);
                doseCountDialog = new AlertDialog.Builder(getActivity()).setView(v).setTitle("Select dose count").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar c = Calendar.getInstance();
                        c.clear();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        if ( fromWizard)
                            wizardModel.setScheduleTime(c.getTimeInMillis());
                        wizardModel.getSchedules().add(new Schedule(null, Double.parseDouble(doseCount.getText().toString()), c.getTimeInMillis(), fill));
                        wizardModel.getDoseEntries(fill);
                        adapter.notifyDataSetChanged();
                        if(fromWizard && (sun.isChecked() || mon.isChecked() || tues.isChecked() || wed.isChecked() || thurs.isChecked() || fri.isChecked() || sat.isChecked()) && wizardModel.getDoseEntries(fill).size() != 0)
                            exitable = true;
                    }
                }).setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timePickerDialog.show();
                    }
                });
                doseCountDialog.show();
            }
        };
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add dose selector
                Calendar c = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(getContext(), listener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });

        if(fromWizard){
            medName.setHeight(0);
            medName.setVisibility(View.INVISIBLE);
            ((RootWizardFragment) Objects.requireNonNull(Objects.requireNonNull(getParentFragment()).getParentFragment())).setAdd();
            ((RootWizardFragment) Objects.requireNonNull(getParentFragment().getParentFragment())).setHasLast(false);
        } else {
            Medication medication = mainModel.getMedWithID(medID);
            medName.setText(medication.getName());
        }
        if (savedInstanceState != null){
            if (savedInstanceState.getBoolean("timePickerVisible", false)){
                timePickerDialog = new TimePickerDialog(getContext(), listener, 0,0, DateFormat.is24HourFormat(getContext()));
                timePickerDialog.onRestoreInstanceState(savedInstanceState.getBundle("timePickerBundle"));
            }
        }
        return root;
    }

    @Override
    public void showErrors() {
        timeErr.setVisibility(wizardModel.getDoseEntries(checks).size() == 0 ? View.VISIBLE : View.INVISIBLE);
        dayErr.setVisibility((sun.isChecked() || mon.isChecked() || tues.isChecked() || wed.isChecked() || thurs.isChecked() || fri.isChecked() || sat.isChecked()) ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void pause() {
        boolean[] days = {sun.isChecked(), mon.isChecked(), tues.isChecked(), wed.isChecked(), thurs.isChecked(), fri.isChecked(), sat.isChecked()};
        for (Schedule s : wizardModel.getSchedules()){
            if(Converters.fromBoolArray(s.getWeekdays()) == Converters.fromBoolArray(fill)){
                s.setWeekdays(days);
            }
        }
        wizardModel.setDoseNull();
    }

    public void cancel(){
        ArrayList<Schedule> remove = new ArrayList<>();
        for (Schedule s : wizardModel.getSchedules()){
            if(Converters.fromBoolArray(s.getWeekdays()) == Converters.fromBoolArray(fill)){
                if (cancel)
                    remove.add(s);
                else
                    s.setWeekdays(checks);
            }
        }
        if (cancel)
            wizardModel.getSchedules().removeAll(remove);
        wizardModel.setDoseNull();
    }

    @Override
    public boolean isExitable() {
        return exitable;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (this.isVisible()){
            if (timePickerDialog != null){
                outState.putBoolean("timePickerVisible", timePickerDialog.isShowing());
                outState.putBundle("timePickerBundle", timePickerDialog.onSaveInstanceState());
            }
        }
        if (timePickerDialog != null)
            timePickerDialog.dismiss();
        super.onSaveInstanceState(outState);
    }
}
