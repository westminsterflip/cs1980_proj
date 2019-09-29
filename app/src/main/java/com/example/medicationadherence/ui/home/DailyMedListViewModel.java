package com.example.medicationadherence.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.DailyViewPagerAdapter;
import com.example.medicationadherence.model.DailyMedication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class DailyMedListViewModel extends ViewModel {
    private DailyViewPagerAdapter medAdapter;
    private MutableLiveData<List<List<DailyMedication>>> medications;
    private long prevDate = -1;
    private long date = -1;
    private long nextDate = -1;

    DailyViewPagerAdapter getMedAdapter() {
        return medAdapter;
    }

    void setMedAdapter(DailyViewPagerAdapter medAdapter) {
        this.medAdapter = medAdapter;
    }

    MutableLiveData<List<List<DailyMedication>>> getMedications(){
        if (medications == null){
            medications = new MutableLiveData<>();
            loadMeds();
        }
        return medications;
    }

    private void loadMeds(){
        //TODO: load from db
        System.out.println("filling daily list");
        List<List<DailyMedication>> bigMedList = new ArrayList<>();
        for(int o = 0; o < 3; o++){
            List<DailyMedication> medList = new ArrayList<>();
            for(int i = 0; i < 20; i++){
                medList.add(new DailyMedication((new Random().nextBoolean()) ? R.mipmap.ic_launcher_round : -1, "DailyMedication" + i, i + " pill(s)", Calendar.getInstance().getTimeInMillis() + i * 60000, ""));
            }
            bigMedList.add(medList);
        }
        medications.setValue(bigMedList);
    }

    long getDate() {
        return date;
    }

    void setDate(long date) {
        this.date = date;
    }

    public long getPrevDate() {
        return prevDate;
    }

    public void setPrevDate(long prevDate) {
        this.prevDate = prevDate;
    }

    public long getNextDate() {
        return nextDate;
    }

    public void setNextDate(long nextDate) {
        this.nextDate = nextDate;
    }
}
