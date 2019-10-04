package com.example.medicationadherence.ui;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.medicationadherence.data.Repository;
import com.example.medicationadherence.data.room.entities.Doctor;
import com.example.medicationadherence.data.room.entities.Instructions;
import com.example.medicationadherence.data.room.entities.MedicationEntity;
import com.example.medicationadherence.data.room.entities.MedicationLog;
import com.example.medicationadherence.data.room.entities.Schedule;
import com.example.medicationadherence.model.Medication;

import java.util.Calendar;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private long summaryTimeToView = -1;
    private boolean summaryExpand = false;
    private int summaryViewScale = 0;
    private int medSortMode = 0; //0 = a-z, 1 = z-a
    private Repository repository;
    private MutableLiveData<List<Medication>> medList;
    private MutableLiveData<List<Schedule.ScheduleCard>> cardList;


    public MainViewModel (Application application){
        super(application);
        repository = new Repository(application);
        cardList = repository.getCardList();
    }

    public long getSummaryTimeToView() {
        if (summaryTimeToView == -1)
            summaryTimeToView = Calendar.getInstance().getTimeInMillis();
        return summaryTimeToView;
    }

    public void setSummaryTimeToView(long summaryTimeToView) {
        this.summaryTimeToView = summaryTimeToView;
    }

    public boolean isSummaryExpand() {
        return summaryExpand;
    }

    public void setSummaryExpand(boolean summaryExpand) {
        this.summaryExpand = summaryExpand;
    }

    public int getSummaryViewScale() {
        return summaryViewScale;
    }

    public void setSummaryViewScale(int summaryViewScale) {
        this.summaryViewScale = summaryViewScale;
    }

    public int getMedSortMode() {
        return medSortMode;
    }

    public void setMedSortMode(int medSortMode) {
        this.medSortMode = medSortMode;
    }

    public Repository getRepository() {
        return repository;
    }

    public Long insert(Doctor doctor){
        return repository.insert(doctor);
    }

    public void insert(Instructions instructions){
        repository.insert(instructions);
    }

    public void insert(MedicationEntity medicationEntity){
        repository.insert(medicationEntity);
    }

    public void insert(MedicationLog medicationLog){
        repository.insert(medicationLog);
    }

    public void insert(Schedule schedule){
        repository.insert(schedule);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public void updateDoctor(Long id, String doctorName, String practice, String address, String phone){
        if(phone != null && phone.equals("")) phone = null;
        if(address != null && address.equals("")) address = null;
        if(practice != null && practice.equals("")) practice =null;
        repository.updateDoctor(id, doctorName, practice, address, phone);
    }
}
