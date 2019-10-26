package com.example.medicationadherence.ui.home.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.StartEndDaysScheduleAdapter;
import com.example.medicationadherence.data.Converters;
import com.example.medicationadherence.data.room.entities.Schedule;
import com.example.medicationadherence.ui.medications.wizard.RootWizardFragment;
import com.example.medicationadherence.ui.medications.wizard.RootWizardViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.Objects;

public class EditScheduleFragment extends Fragment implements RootWizardFragment.ErrFragment {
    RootWizardViewModel wizardModel;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wizardModel = new ViewModelProvider(Objects.requireNonNull(Objects.requireNonNull(getParentFragment()).getParentFragment())).get(RootWizardViewModel.class);
        wizardModel.getSchedules();
        wizardModel.getScheduleDays();
        if ( wizardModel.getThisList().size() < 4)
            wizardModel.getThisList().add(this);
        else if(!wizardModel.getThisList().get(3).equals(this))
            wizardModel.getThisList().set(3,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_schedule, container, false);
        recyclerView = root.findViewById(R.id.scheduleEditorRecyclerView);
        fab = root.findViewById(R.id.scheduleAddButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditScheduleFragmentDirections.ActionEditScheduleFragment2ToEditScheduleCardFragment2 action = EditScheduleFragmentDirections.actionEditScheduleFragment2ToEditScheduleCardFragment2(0);
                wizardModel.getNavController().navigate(action);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new StartEndDaysScheduleAdapter(wizardModel.getScheduleDays(), wizardModel));
        ((RootWizardFragment) Objects.requireNonNull(getParentFragment().getParentFragment())).setHasLast(true);
        ((RootWizardFragment)getParentFragment().getParentFragment()).setHasNext(false);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Schedule");
        System.out.println("list:");
        for (Schedule s : wizardModel.getSchedules()){
            System.out.println(Converters.fromBoolArray(s.getWeekdays()) + " " + new Time(s.getTime()));
        }
        return root;
    }

    @Override
    public void showErrors() {

    }

    @Override
    public void pause() {

    }

    @Override
    public boolean isExitable() {
        return true;
    }
}
