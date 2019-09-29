package com.example.medicationadherence.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.MainActivity;
import com.example.medicationadherence.R;
import com.example.medicationadherence.model.Medication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class MedicationListAdapter extends RecyclerView.Adapter {
    private List<Medication> medicationList;
    private Context context;
    private MainActivity mainActivity;

    public MedicationListAdapter(List<Medication> medicationList, Context context, MainActivity mainActivity){
        this.medicationList = medicationList;
        this.context = context;
        this.mainActivity = mainActivity;
    }



    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View medicationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_medication_card, parent, false);
        return new MedicationViewHolder(medicationView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MedicationViewHolder holderm = (MedicationViewHolder) holder;
        if(medicationList.get(position).getMedImage() != -1){ //If an image is specified it will load, otherwise the default is a pill on a background
            holderm.medImage.setImageResource(medicationList.get(position).getMedImage());
            holderm.medImage.setBackgroundColor(Integer.parseInt("00FFFFFF",16));
            holderm.medImage.setImageTintList(null);
        }
        System.out.println(holderm.medImage.getHeight());
        holderm.medName.setText(medicationList.get(position).getMedName());
        holderm.medDosage.setText(medicationList.get(position).getMedDosage());
        holderm.doctorName.setText(medicationList.get(position).getDoctorName());
        holderm.active.setText(("Active: " + medicationList.get(position).isActive()));
        holderm.startDate.setText(new SimpleDateFormat("MM/dd/yy").format(new Date(medicationList.get(position).getStartDate())));
        long ed = medicationList.get(position).getEndDate();
        holderm.endDate.setText((ed == -1) ? "" : " - "+new SimpleDateFormat("MM/dd/yyyy").format(new Date(ed)));
        holderm.expand.setVisibility(View.GONE);
        /*View.OnClickListener expand = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TransitionManager.beginDelayedTransition(holderm.card);
                if(holderm.expand.getRotation() == 180){
                    holderm.expand.setRotation(0);
                    holderm.expandable.setVisibility(View.GONE);
                } else {
                    holderm.expand.setRotation(180);
                    holderm.expandable.setVisibility(View.VISIBLE);
                    String details = ""; //TODO: fill with ammount on hand/cost/container vol/instructions, anything else extra
                    holderm.expandable.setText(details);
                }
            }
        };
        holderm.expand.setOnClickListener(expand);
        holderm.card.setOnClickListener(expand);*/
    }



    @Override
    public int getItemCount() {
        if (medicationList == null){
            return -1;
        }
        return medicationList.size();
    }

    public class MedicationViewHolder extends RecyclerView.ViewHolder{
        ImageView medImage;
        TextView medName;
        TextView doctorName;
        TextView medDosage;
        TextView active;
        TextView startDate;
        TextView endDate;
        ImageButton expand;
        CardView card;
        TextView expandable;

        public MedicationViewHolder(View view){
            super(view);
            medImage=view.findViewById(R.id.medImage);
            medName=view.findViewById(R.id.textViewMedName);
            doctorName=view.findViewById(R.id.textViewDoctorName);
            medDosage=view.findViewById(R.id.textViewMedDosage);
            active=view.findViewById(R.id.textViewActive);
            startDate=view.findViewById(R.id.textViewStartDate);
            endDate=view.findViewById(R.id.textViewEndDate);
            card=view.findViewById(R.id.medicationCard);
            expand=view.findViewById(R.id.medicationCardExpand);
            expandable=view.findViewById(R.id.expandableTextView);
        }
    }
}