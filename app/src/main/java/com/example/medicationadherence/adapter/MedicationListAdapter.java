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

import com.example.medicationadherence.MainActivity;
import com.example.medicationadherence.R;
import com.example.medicationadherence.model.Medication;

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
        View medicationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_daily_medication_card, parent, false);
        return new MedicationViewHolder(medicationView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MedicationViewHolder holderm = (MedicationViewHolder) holder;
        if(medicationList.get(position).getMedImage() != -1){ //If an image is specified it will load, otherwise the default is a pill on a background
            holderm.medImage.setImageResource(medicationList.get(position).getMedImage());
            holderm.medImage.setBackgroundColor(Integer.parseInt("00FFFFFF",16));
            holderm.medImage.setImageTintList(null);
        }
        holderm.medName.setText(medicationList.get(position).getMedName());
        holderm.medDosage.setText(medicationList.get(position).getMedDosage());
        holderm.medImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //mainActivity.showPopup(medicationList.get(position));
            }
        });
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
        TextView dosageTime;

        public MedicationViewHolder(View view){
            super(view);
            medImage=view.findViewById(R.id.medImage);
            medName=view.findViewById(R.id.textViewMedName);
            doctorName=view.findViewById(R.id.textViewDoctorName);
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