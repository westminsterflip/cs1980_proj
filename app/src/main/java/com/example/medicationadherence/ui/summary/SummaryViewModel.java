package com.example.medicationadherence.ui.summary;

import androidx.lifecycle.ViewModel;

import java.util.Calendar;


//TODO: fragments are destroyed by navcontroller upon switcthing, so either make mainactivity viewmodel or custom NavController class
public class SummaryViewModel extends ViewModel {
    private long timeToView = -1; //probably won't schedule for before the epoch
    private int viewScale = 0;

    public long getTimeToView() {
        if (timeToView == -1)
            timeToView = Calendar.getInstance().getTimeInMillis();
        return timeToView;
    }

    public void setTimeToView(long timeToView) {
        this.timeToView = timeToView;
    }

    public int getViewScale() {
        return viewScale;
    }

    public void setViewScale(int viewScale) {
        this.viewScale = viewScale;
    }

    @Override
    protected void onCleared() {
        System.out.println("//////////////////////////////////SUMMARY CLEARED//////////////////////////////");
        super.onCleared();
    }
}
