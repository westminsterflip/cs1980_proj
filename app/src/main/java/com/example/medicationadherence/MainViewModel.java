package com.example.medicationadherence;

import androidx.lifecycle.ViewModel;

import java.util.Calendar;

public class MainViewModel extends ViewModel {
    private long summaryTimeToView = -1;
    private boolean summaryExpand = false;
    private int summaryViewScale = 0;

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
}
