package com.example.medicationadherence.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.model.DailyMedication;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

public class DailyMedicationListAdapter extends RecyclerView.Adapter implements Serializable {
    private List<DailyMedication> medicationList;

    public DailyMedicationListAdapter(List<DailyMedication> medicationList){
        this.medicationList = medicationList;
    }

    @NonNull
    @Override
    public DailyMedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View medicationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_daily_medication_card, parent, false);
        return new DailyMedicationViewHolder(medicationView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        DailyMedicationViewHolder holderm = (DailyMedicationViewHolder) holder;
        if(medicationList.get(position).getMedImage() != -1){ //If an image is specified it will load, otherwise the default is a pill on a background
            holderm.medImage.setImageResource(medicationList.get(position).getMedImage());
            holderm.medImage.setBackgroundColor(Integer.parseInt("00FFFFFF",16));
            holderm.medImage.setImageTintList(null);
        }
        holderm.medName.setText(medicationList.get(position).getMedName());
        //holderm.instructions.setText(medicationList.get(position).getInstructions());
        holderm.medDosage.setText(medicationList.get(position).getMedDosage());
        holderm.dosageTime.setText(new Time(medicationList.get(position).getDosageTime()).toString());
        holderm.medImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: this is a bad way to do it; every time a card is loaded on screen it'll run this
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    public class DailyMedicationViewHolder extends RecyclerView.ViewHolder{
        ImageView medImage;
        TextView medName;
        TextView instructions;
        TextView medDosage;
        TextView dosageTime;

        DailyMedicationViewHolder(View view){
            super(view);
            medImage=view.findViewById(R.id.medImage);
            medName=view.findViewById(R.id.textViewMedName);
            //instructions=view.findViewById(R.id.textViewInstructions);
            medDosage=view.findViewById(R.id.textViewMedDosage);
            dosageTime=view.findViewById(R.id.textViewDosageTime);
        }
    }
}