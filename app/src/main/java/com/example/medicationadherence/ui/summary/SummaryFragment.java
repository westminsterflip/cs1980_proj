package com.example.medicationadherence.ui.summary;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.SummaryDetailAdapter;
import com.example.medicationadherence.model.DetailSummary;
import com.example.medicationadherence.ui.DisableableScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//TODO: add today button
//This fragment doesn't rotate
public class SummaryFragment extends Fragment {
    private long timeToView;
    private int viewScale = 0; //0=Day, 1=Week, 2=Month
    private TextView graphLabel;
    private Calendar cal = Calendar.getInstance();
    private SummaryDetailAdapter detailAdapter;
    private List<DetailSummary> detailList;
    private DisableableScrollView summaryScroll;

    ImageButton next;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            timeToView = Calendar.getInstance().getTimeInMillis();
            System.out.println("Bundle is null");
        } else {
            timeToView = savedInstanceState.getLong("timeToView");
            viewScale = savedInstanceState.getInt("timeToView");
        }
        View root = inflater.inflate(R.layout.fragment_summary, container, false);
        cal.setTimeInMillis(timeToView);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        timeToView = cal.getTimeInMillis();
        System.out.println(cal.getTime());
        graphLabel = root.findViewById(R.id.summaryDWM);
        changeScale();

        next = root.findViewById(R.id.summaryNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTimeToView(1);
            }
        });
        if (cal.getTimeInMillis() + TimeUnit.DAYS.toMillis(1) > Calendar.getInstance().getTimeInMillis()){
            next.setEnabled(false);
        } else {
            next.setEnabled(true);
        }
        ImageButton prev = root.findViewById(R.id.summaryPrev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTimeToView(-1);
            }
        });

        Spinner timeScaleSpinner = root.findViewById(R.id.summaryUnitSelect);
        timeScaleSpinner.setSelection(viewScale);
        timeScaleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewScale = position;
                updateTimeToView(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("This shouldn't happen probably");
            }
        });

        final RecyclerView detailView = root.findViewById(R.id.summaryDetail);

        final ImageButton summaryExpander = root.findViewById(R.id.summaryExpand);
        summaryExpander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailView.getVisibility() == View.VISIBLE) {
                    detailView.setVisibility(View.INVISIBLE);
                    summaryExpander.setRotation(0);
                    summaryScroll.setScrollEnabled(false);
                } else {
                    detailView.setVisibility(View.VISIBLE);
                    summaryExpander.setRotation(180);
                    summaryScroll.setScrollEnabled(true);
                }
            }
        });

        summaryScroll = root.findViewById(R.id.summaryScroll);

        detailView.setLayoutManager(new LinearLayoutManager(getContext()));
        detailList = new ArrayList<>();

        //TODO: actual data population
        for (int i=0; i<20; i++){
            double percTaken = new Random().nextDouble()*100%100.0;
            double percLate = new Random().nextDouble()*100%(100.0-percTaken);
            System.out.println("Taken: "+percTaken+" Late: "+percLate+" Missed: "+(100.0-percLate-percTaken));
            detailList.add(new DetailSummary("Medication " + i, percTaken, percLate));
        }
        detailAdapter = new SummaryDetailAdapter(detailList, getContext());
        detailView.setAdapter(detailAdapter);

        return root;
    }

    @Override
    public void onResume() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onResume();
    }

    @Override
    public void onPause() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        super.onPause();
    }

    //TODO: move all data to viewmodel? persist in some way
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        System.out.println("saved state");
        outState.putInt("viewScale", viewScale);
        outState.putLong("timeToView", timeToView);
        super.onSaveInstanceState(outState);
    }

    //negative = back, 0 = scale changed, positive = forward
    public void updateTimeToView(int dir){
        if(dir > 0){
            System.out.println("next");
            cal.add(Calendar.DAY_OF_YEAR, ((viewScale==0) ? 1 : 0));
            cal.add(Calendar.DAY_OF_YEAR, ((viewScale==1) ? 7 : 0));
            cal.add(Calendar.MONTH, ((viewScale==2) ? 1 : 0));
        }else if (dir < 0){
            System.out.println("prev");
            cal.add(Calendar.DAY_OF_YEAR, ((viewScale==0) ? -1 : 0));
            cal.add(Calendar.DAY_OF_YEAR, ((viewScale==1) ? -7 : 0));
            cal.add(Calendar.MONTH, ((viewScale==2) ? -1 : 0));
        }
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(cal.getTimeInMillis());
        temp.add(Calendar.MONTH, 1);
        if (cal.getTimeInMillis() + TimeUnit.DAYS.toMillis(1) > Calendar.getInstance().getTimeInMillis()){
            next.setEnabled(false);
        } else if (cal.getTimeInMillis() + TimeUnit.DAYS.toMillis(7) > Calendar.getInstance().getTimeInMillis() && viewScale > 0){
            next.setEnabled(false);
        } else if (temp.getTimeInMillis() > Calendar.getInstance().getTimeInMillis() && viewScale > 1){
            next.setEnabled(false);
        } else {
            next.setEnabled(true);
        }
        System.out.println(cal.getTime());
        changeScale();
        //TODO: move time based on value in spinner
        updateMainGraph();
    }

    public void changeScale(){
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String dayName = new SimpleDateFormat("EEE").format(cal.getTime());
        String monthName = new SimpleDateFormat("MMM").format(cal.getTime());
        String label = "unchanged";
        switch (viewScale){
            case 0:
                label = dayName+", "+monthName+" "+day;
                if (year != Calendar.getInstance().get(Calendar.YEAR))
                    label += ", " + year;
                break;
            case 1:
                label = dayName+", "+monthName+" "+day+" - ";
                Calendar calt = Calendar.getInstance();
                calt.setTimeInMillis(cal.getTimeInMillis());
                calt.add(Calendar.DAY_OF_YEAR, 6);
                int dayt = calt.get(Calendar.DAY_OF_MONTH);
                String dayNamet = new SimpleDateFormat("EEE").format(calt.getTime());
                String monthNamet = new SimpleDateFormat("MMM").format(calt.getTime());
                label += dayNamet+", "+monthNamet+" "+dayt;
                break;
            case 2:
                label = monthName + " " + year;
                break;
        }
        graphLabel.setText(label);
    }

    public void updateMainGraph(){
        //TODO: move graph/text values to reflect data from database
    }


}