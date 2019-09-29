package com.example.medicationadherence.ui.summary;

import androidx.lifecycle.ViewModel;

import com.example.medicationadherence.model.DetailSummary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


//TODO: fragments are destroyed by navcontroller upon switcthing, so make mainactivity viewmodel
public class SummaryViewModel extends ViewModel {
    private long timeToView = -1; //probably won't schedule for before the epoch
    private int viewScale = 0;
    private boolean expand = false;
    private List<DetailSummary> detailList;

    long getTimeToView() {
        if (timeToView == -1)
            timeToView = Calendar.getInstance().getTimeInMillis();
        return timeToView;
    }

    void setTimeToView(long timeToView) {
        this.timeToView = timeToView;
    }

    int getViewScale() {
        return viewScale;
    }

    void setViewScale(int viewScale) {
        this.viewScale = viewScale;
    }

    @Override
    protected void onCleared() {
        System.out.println("//////////////////////////////////SUMMARY CLEARED//////////////////////////////");
        super.onCleared();
    }

    boolean isExpand() {
        return expand;
    }

    void setExpand(boolean expand) {
        this.expand = expand;
    }

    public List<DetailSummary> getDetailList() {
        if(detailList == null)
            loadList();
        return detailList;
    }

    public void setDetailList(List<DetailSummary> detailList) {
        this.detailList = detailList;
    }

    private void loadList(){
        detailList = new ArrayList<>();
        //TODO: actual data population
        for (int i=0; i<20; i++){
            double percTaken = new Random().nextDouble()*100%100.0;
            double percLate = new Random().nextDouble()*100%(100.0-percTaken);
            System.out.println("Taken: "+percTaken+" Late: "+percLate+" Missed: "+(100.0-percLate-percTaken));
            detailList.add(new DetailSummary("DailyMedication " + i, percTaken, percLate));
        }
    }
}
