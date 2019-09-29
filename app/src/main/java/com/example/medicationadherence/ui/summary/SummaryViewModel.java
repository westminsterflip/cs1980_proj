package com.example.medicationadherence.ui.summary;

import androidx.lifecycle.ViewModel;

import com.example.medicationadherence.model.DetailSummary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SummaryViewModel extends ViewModel {
    private List<DetailSummary> detailList;

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
            detailList.add(new DetailSummary("DailyMedication " + i, percTaken, percLate));
        }
    }
}
