package com.example.medicationadherence.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MedicationListAdapter extends RecyclerView.Adapter {
    private List<Medication> medicationList;
    private MainViewModel mainModel;
    private ArrayList<MedicationFragment> thisList;
    private ArrayList<Medication> justDeleted = new ArrayList<>();
    private ArrayList<Integer> justDelPos = new ArrayList<>();
    private Activity activity;
    private boolean del = true;

    public MedicationListAdapter(List<Medication> medicationList, MainViewModel mainModel, ArrayList thisList, Activity activity){
        this.medicationList = medicationList;
        this.mainModel = mainModel;
        this.thisList = thisList;
        this.activity = activity;
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
        if(medicationList.get(position).getMedImageURL() != null && !medicationList.get(position).getMedImageURL().equals("")){ //If an image is specified it will load, otherwise the default is a pill on a background
            System.out.println(medicationList.get(position).getMedImageURL());
            try {
                Bitmap image = new SetImageTask(holderm, position).execute().get();
                if (image != null) {
                    holderm.medImage.setImageBitmap(image);
                    holderm.medImage.setBackgroundColor(Integer.parseInt("00FFFFFF", 16));
                    holderm.medImage.setImageTintList(null);
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public void delete(int pos){
        justDeleted.add(medicationList.get(pos));
        System.out.println("added: "+justDelPos.add(pos) + " " + justDelPos.size());
        String name = medicationList.get(pos).getName();
        medicationList.remove(pos);
        notifyDataSetChanged();
        showUndo(name);
    }

    private void showUndo(String name){
        View view = activity.findViewById(R.id.drawer_layout);
        String s = name + " deleted";
        Snackbar undoBar = Snackbar.make(view, s, Snackbar.LENGTH_LONG);
        undoBar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicationList.add(justDelPos.get(justDelPos.size()-1), justDeleted.get(justDeleted.size()-1));
                notifyDataSetChanged();
                justDeleted.clear();
                justDelPos.clear();
            }
        });
        undoBar.show();
        undoBar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                int i;
                for (i = 0; i < justDeleted.size(); i++)
                    if (event != DISMISS_EVENT_ACTION && event != DISMISS_EVENT_CONSECUTIVE || i < justDeleted.size() - 1)
                        mainModel.remove(justDeleted.get(i));
                if(event == DISMISS_EVENT_CONSECUTIVE){
                    int pos = justDelPos.get(justDelPos.size()-1);
                    Medication med = justDeleted.get(justDeleted.size()-1);
                    justDelPos.clear();
                    justDeleted.clear();
                    justDelPos.add(pos);
                    justDeleted.add(med);
                }else if (event != DISMISS_EVENT_ACTION) {
                    justDeleted.clear();
                    justDelPos.clear();
                }
            }
        });
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

    private class SetImageTask extends AsyncTask<Void, Void, Bitmap>{
        private MedicationViewHolder holder;
        private int position;

        public SetImageTask(MedicationViewHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                return BitmapFactory.decodeStream(new URL(medicationList.get(position).getMedImageURL()).openStream());
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}