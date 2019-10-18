package com.example.medicationadherence.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.data.Converters;
import com.example.medicationadherence.ui.home.schedule.EditScheduleFragmentDirections;
import com.example.medicationadherence.ui.medications.wizard.RootWizardViewModel;

import java.util.ArrayList;

public class StartEndDaysScheduleAdapter extends RecyclerView.Adapter {
    ArrayList<Integer> days;
    RootWizardViewModel model;

    public StartEndDaysScheduleAdapter(ArrayList<Integer> days, RootWizardViewModel model) {
        this.days = days;
        this.model = model;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sedsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_schedule_days_start_end_card, parent, false);
        return new SEDSHolder(sedsView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SEDSHolder sedsHolder = (SEDSHolder) holder;
        final boolean[] day= Converters.intToBoolArray(days.get(position));
        sedsHolder.sun.setChecked(day[0]);
        sedsHolder.mon.setChecked(day[1]);
        sedsHolder.tues.setChecked(day[2]);
        sedsHolder.wed.setChecked(day[3]);
        sedsHolder.thurs.setChecked(day[4]);
        sedsHolder.fri.setChecked(day[5]);
        sedsHolder.sat.setChecked(day[6]);
        sedsHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditScheduleFragmentDirections.ActionEditScheduleFragment2ToEditScheduleCardFragment2 action = EditScheduleFragmentDirections.actionEditScheduleFragment2ToEditScheduleCardFragment2(Converters.fromBoolArray(day));
                model.getNavController().navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    class SEDSHolder extends RecyclerView.ViewHolder{
        final CheckBox sun;
        final CheckBox mon;
        final CheckBox tues;
        final CheckBox wed;
        final CheckBox thurs;
        final CheckBox fri;
        final CheckBox sat;
        final CardView card;

        public SEDSHolder(@NonNull View view) {
            super(view);
            sun = view.findViewById(R.id.scheduleCardSunday);
            mon = view.findViewById(R.id.scheduleCardMonday);
            tues = view.findViewById(R.id.scheduleCardTuesday);
            wed = view.findViewById(R.id.scheduleCardWednesday);
            thurs = view.findViewById(R.id.scheduleCardThursday);
            fri = view.findViewById(R.id.scheduleCardFriday);
            sat = view.findViewById(R.id.scheduleCardSaturday);
            card = view.findViewById(R.id.scheduleCardView);
        }
    }
}
