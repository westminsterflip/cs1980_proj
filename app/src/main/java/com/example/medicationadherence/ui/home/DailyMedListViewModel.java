package com.example.medicationadherence.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.DailyViewPagerAdapter;
import com.example.medicationadherence.model.DailyMedication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class DailyMedListViewModel extends ViewModel {
    private DailyViewPagerAdapter medAdapter;
    private MutableLiveData<List<List<DailyMedication>>> medications;
    private List<Long> dateList;

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
        List<List<DailyMedication>> bigMedList = new ArrayList<>();
        for(int o = 0; o < 3; o++){
            List<DailyMedication> medList = new ArrayList<>();
            for(int i = 0; i < 20; i++){
                medList.add(new DailyMedication((new Random().nextBoolean()) ? R.mipmap.ic_launcher_round : -1, "DailyMedication" + i, i + " pill(s)", dateList.get(o) + i * 60000, (new Random().nextBoolean())? null : "test"));
            }
            bigMedList.add(medList);
        }
        medications.setValue(bigMedList);
    }

    long getDate() {
        return dateList.get(1);
    }

    void setDate(long date) {
        dateList.set(1,date);
    }

    long getPrevDate() {
        return dateList.get(0);
    }

    public void setPrevDate(long prevDate) {
        dateList.set(0,prevDate);
    }

    public long getNextDate() {
        return dateList.get(2);
    }

    public void setNextDate(long nextDate) {
        dateList.set(2,nextDate);
    }

    public void loadNextMeds(){
        List<List<DailyMedication>> medList = medications.getValue();
        Objects.requireNonNull(medList).remove(0);
        List<DailyMedication> medList1 = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            medList1.add(new DailyMedication((new Random().nextBoolean()) ? R.mipmap.ic_launcher_round : -1, "DailyMedication" + i, i + " pill(s)", dateList.get(2) + i * 60000, ""));
        }
        medList.add(medList1);
        medications.setValue(medList);
    }

    public void loadPrevMeds(){
        List<List<DailyMedication>> medList = medications.getValue();
        Objects.requireNonNull(medList).remove(2);
        List<DailyMedication> medList1 = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            medList1.add(new DailyMedication((new Random().nextBoolean()) ? R.mipmap.ic_launcher_round : -1, "DailyMedication" + i, i + " pill(s)", dateList.get(0) + i * 60000, ""));
        }
        medList.add(0,medList1);
        medications.setValue(medList);
    }

    public List<Long> getDateList() {
        if(dateList == null) {
            dateList = new ArrayList<>();
            dateList.add((long)-1);
            dateList.add((long)-1);
            dateList.add((long)-1);
        }
        return dateList;
    }

    public void setDateList(List<Long> dateList) {
        this.dateList = dateList;
    }
}
