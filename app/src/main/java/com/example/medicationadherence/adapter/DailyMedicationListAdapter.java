package com.example.medicationadherence.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.model.DailyMedication;
import com.example.medicationadherence.ui.home.DailyMedicationList;

import java.io.Serializable;
import java.util.List;

public class DailyMedicationListAdapter extends RecyclerView.Adapter implements Serializable {
    private List<DailyMedication> medicationList;
    private Context context;
    DailyMedicationList mainActivity;

    public DailyMedicationListAdapter(List<DailyMedication> medicationList, Context context, DailyMedicationList mainActivity){
        this.medicationList = medicationList;
        this.context = context;
        this.mainActivity = mainActivity;
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
        holderm.dosageTime.setText(medicationList.get(position).getDosageTime().toString());
        holderm.medImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: this is a bad way to do it; every time a card is loaded on screen it'll run this
                mainActivity.showPopup(medicationList.get(position));
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

        public DailyMedicationViewHolder(View view){
            super(view);
            medImage=view.findViewById(R.id.medImage);
            medName=view.findViewById(R.id.textViewMedName);
            //instructions=view.findViewById(R.id.textViewInstructions);
            medDosage=view.findViewById(R.id.textViewMedDosage);
            dosageTime=view.findViewById(R.id.textViewDosageTime);
        }
    }

    public class MedDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.layout_med_popup, null))
                    .setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MedDialogFragment.this.getDialog().cancel();
                        }
                    });
            return builder.create();
        }
    }
}