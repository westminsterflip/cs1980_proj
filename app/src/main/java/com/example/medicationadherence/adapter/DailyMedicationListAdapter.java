package com.example.medicationadherence.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.data.room.dao.ScheduleDAO;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DailyMedicationListAdapter extends RecyclerView.Adapter implements Serializable {
    private List<ScheduleDAO.ScheduleCard> medicationList;

    public DailyMedicationListAdapter(List<ScheduleDAO.ScheduleCard> medicationList){
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
        final DailyMedicationViewHolder holderm = (DailyMedicationViewHolder) holder;
        //TODO: https://lhncbc.nlm.nih.gov/rximage-api
        if(medicationList.get(position).medImageURL != null && !medicationList.get(position).medImageURL.equals("")){ //If an image is specified it will load, otherwise the default is a pill on a background
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
        } else
            System.out.println(medicationList.get(position).medImageURL);
        holderm.medName.setText(medicationList.get(position).medName);
        String instr = medicationList.get(position).instructions;
        if(instr != null && !instr.equals("")){
            holderm.instructions.setText(medicationList.get(position).instructions);
            View.OnClickListener expand = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holderm.expand.getRotation() == 180){
                        holderm.expand.setRotation(0);
                        holderm.instructions.setVisibility(View.GONE);
                    } else {
                        holderm.expand.setRotation(180);
                        holderm.instructions.setVisibility(View.VISIBLE);
                    }
                }
            };
            holderm.card.setOnClickListener(expand);
            holderm.expand.setOnClickListener(expand);
        } else {
            holderm.expand.setVisibility(View.GONE);
        }
        holderm.medDosage.setText((medicationList.get(position).doses + "@" + medicationList.get(position).dosageAmt));
        holderm.dosageTime.setText(new Time(medicationList.get(position).timeOfDay).toString());
    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    public class DailyMedicationViewHolder extends RecyclerView.ViewHolder{
        final ImageView medImage;
        final TextView medName;
        final TextView instructions;
        final TextView medDosage;
        final TextView dosageTime;
        final ImageButton expand;
        final CardView card;

        DailyMedicationViewHolder(View view){
            super(view);
            medImage=view.findViewById(R.id.medImage);
            medName=view.findViewById(R.id.textViewMedName);
            instructions=view.findViewById(R.id.dailyInstructions);
            medDosage=view.findViewById(R.id.textViewMedDosage);
            dosageTime=view.findViewById(R.id.textViewDosageTime);
            expand=view.findViewById(R.id.dailyMedicationExpand);
            card=view.findViewById(R.id.dailyCard);
        }
    }

    private class SetImageTask extends AsyncTask<Void, Void, Bitmap> {
        private DailyMedicationViewHolder holder;
        private int position;

        public SetImageTask(DailyMedicationViewHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                return BitmapFactory.decodeStream(new URL(medicationList.get(position).medImageURL).openStream());
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}