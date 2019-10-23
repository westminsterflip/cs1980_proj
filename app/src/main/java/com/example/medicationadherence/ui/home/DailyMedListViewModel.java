package com.example.medicationadherence.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.medicationadherence.adapter.DailyViewPagerAdapter;
import com.example.medicationadherence.data.room.dao.ScheduleDAO;
import com.example.medicationadherence.ui.MainViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class DailyMedListViewModel extends ViewModel {
    private DailyViewPagerAdapter medAdapter;
    private MutableLiveData<List<List<ScheduleDAO.ScheduleCard>>> medications;
    private List<Long> dateList;
    private MainViewModel mainModel;
    private int day;
    private List<ScheduleDAO.ScheduleCard> cardList;

    DailyViewPagerAdapter getMedAdapter() {
        return medAdapter;
    }

    void setMedAdapter(DailyViewPagerAdapter medAdapter) {
        this.medAdapter = medAdapter;
    }

    MutableLiveData<List<List<ScheduleDAO.ScheduleCard>>> getMedications(){
        if (medications == null){
            medications = new MutableLiveData<>();
            loadMeds();
        }
        return medications;
    }

    public void loadMeds(){
        //TODO: load from db
        List<List<ScheduleDAO.ScheduleCard>> bigMedList = new ArrayList<>();
        for(int o = 0; o < 3; o++){
            List<ScheduleDAO.ScheduleCard> medList = new ArrayList<>();
            for(ScheduleDAO.ScheduleCard s : cardList){
                if(s.days[(day + 5 + o) % 7] && s.startDate <= dateList.get(1) && (s.endDate >= dateList.get(1) || s.endDate == -1))
                    medList.add(s);
            }
            bigMedList.add(medList);
        }
        medications.setValue(bigMedList);
    }

    long getDate() {
        return dateList.get(1);
    }

    void setDate(long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        System.out.println(day = c.get(Calendar.DAY_OF_WEEK));
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
        List<List<ScheduleDAO.ScheduleCard>> medList = medications.getValue();
        Objects.requireNonNull(medList).remove(0);
        List<ScheduleDAO.ScheduleCard> medList1 = new ArrayList<>();
        for(ScheduleDAO.ScheduleCard s : cardList){
            if(s.days[day % 7] && s.startDate <= dateList.get(2) && (s.endDate >= dateList.get(2) || s.endDate == -1))
                medList1.add(s);
        }
        medList.add(medList1);
        medications.setValue(medList);
    }

    public void loadPrevMeds(){
        List<List<ScheduleDAO.ScheduleCard>> medList = medications.getValue();
        Objects.requireNonNull(medList).remove(2);
        List<ScheduleDAO.ScheduleCard> medList1 = new ArrayList<>();
        for(ScheduleDAO.ScheduleCard s : cardList){
            if(s.days[(day + 5) % 7]&& s.startDate <= dateList.get(0) && (s.endDate >= dateList.get(0) || s.endDate == -1))
                medList1.add(s);
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

    public MainViewModel getMainModel() {
        return mainModel;
    }

    public void setMainModel(MainViewModel mainModel) {
        this.mainModel = mainModel;
    }

    public void setCardList(List<ScheduleDAO.ScheduleCard> cardList) {
        this.cardList = cardList;
    }
}
