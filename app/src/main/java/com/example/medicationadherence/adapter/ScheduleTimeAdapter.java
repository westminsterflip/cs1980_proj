package com.example.medicationadherence.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.ui.medications.wizard.RootWizardViewModel;

import java.util.ArrayList;

public class ScheduleTimeAdapter extends RecyclerView.Adapter {
    private RootWizardViewModel model;
    private ArrayList<String> doses;
    private boolean[] days;

    public ScheduleTimeAdapter(RootWizardViewModel model, ArrayList<String> doses, boolean[] days) {
        this.model = model;
        this.doses = doses;
        this.days = days;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View timeCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_time_card, parent, false);
        return new TimeHolder(timeCardView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        TimeHolder timeHolder = (TimeHolder) holder;
        timeHolder.dose.setText(doses.get(position));
        timeHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.removeTime(days, doses.get(position));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return doses.size();
    }

    class TimeHolder extends RecyclerView.ViewHolder{
        private TextView dose;
        private ImageButton remove;

        public TimeHolder(@NonNull View view) {
            super(view);
            this.dose = view.findViewById(R.id.timeCardDoses);
            this.remove = view.findViewById(R.id.timeCardRemove);
        }
    }
}
