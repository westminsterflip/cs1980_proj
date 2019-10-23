package com.example.medicationadherence.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.os.ConfigurationCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.data.room.entities.Doctor;
import com.example.medicationadherence.data.room.entities.Medication;
import com.example.medicationadherence.ui.MainViewModel;
import com.example.medicationadherence.ui.medications.MedicationFragment;
import com.example.medicationadherence.ui.medications.MedicationFragmentDirections;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class MedicationListAdapter extends RecyclerView.Adapter {
    private List<Medication> medicationList;
    private MainViewModel mainModel;
    private ArrayList<MedicationFragment> thisList;

    public MedicationListAdapter(List<Medication> medicationList, MainViewModel mainModel, ArrayList thisList){
        this.medicationList = medicationList;
        this.mainModel = mainModel;
        this.thisList = thisList;
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
//        if(medicationList.get(position).getMedImage() != -1){ //If an image is specified it will load, otherwise the default is a pill on a background
//            holderm.medImage.setImageResource(medicationList.get(position).getMedImage());
//            holderm.medImage.setBackgroundColor(Integer.parseInt("00FFFFFF",16));
//            holderm.medImage.setImageTintList(null);
//        }
        Doctor doctor = mainModel.getDoctorWithID(medicationList.get(position).getDoctorID());
        if(doctor == null){
            holderm.doctorName.setVisibility(View.GONE);
        } else {
            holderm.doctorName.setText(doctor.getName());
        }
        holderm.medName.setText(medicationList.get(position).getName());
        holderm.medDosage.setText(medicationList.get(position).getDosage());
        holderm.active.setText(("Active: " + medicationList.get(position).isStatus()));
        holderm.startDate.setText((new SimpleDateFormat("MM/dd/yy", ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)).format(new Date(medicationList.get(position).getStartDate()))+" - "));
        long ed = medicationList.get(position).getEndDate();
        holderm.endDate.setText((ed == -1) ? "" : new SimpleDateFormat("MM/dd/yy", ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)).format(new Date(ed)));
        //holderm.expand.setVisibility(View.GONE);
        //TODO: add onclick listener to open fragment to view all details
        holderm.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicationFragmentDirections.ActionNavMedicationsToRootWizardFragment action = MedicationFragmentDirections.actionNavMedicationsToRootWizardFragment(thisList);
                action.setMedicationID(medicationList.get(position).getMedicationID());
                Navigation.findNavController(v).navigate(action);
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
        final ImageView medImage;
        final TextView medName;
        final TextView doctorName;
        final TextView medDosage;
        final TextView active;
        final TextView startDate;
        final TextView endDate;
        final CardView card;

        MedicationViewHolder(View view){
            super(view);
            medImage=view.findViewById(R.id.medImage);
            medName=view.findViewById(R.id.textViewMedName);
            doctorName=view.findViewById(R.id.textViewDoctorName);
            medDosage=view.findViewById(R.id.textViewMedDosage);
            active=view.findViewById(R.id.textViewActive);
            startDate=view.findViewById(R.id.textViewStartDate);
            endDate=view.findViewById(R.id.textViewEndDate);
            card=view.findViewById(R.id.medicationCard);
        }
    }
}