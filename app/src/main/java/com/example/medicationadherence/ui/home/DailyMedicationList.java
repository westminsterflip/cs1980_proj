package com.example.medicationadherence.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.DailyViewPagerAdapter;
import com.example.medicationadherence.model.DailyMedication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyMedicationList extends AppCompatActivity{
    private DailyMedListViewModel model;
    private TextView date;
    private ViewPager2 dailyViewPager;

    //TODO: add as needed meds at bottom
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(this).get(DailyMedListViewModel.class);
        final Observer<List<List<DailyMedication>>> medicationObserver = new Observer<List<List<DailyMedication>>>() {
            @Override
            public void onChanged(List<List<DailyMedication>> dailyMedications) {
                if (model.getMedAdapter() != null) {
                    model.getMedAdapter().notifyDataSetChanged();
                }
            }
        };
        model.getMedications().observe(this, medicationObserver);
        if(model.getDate() == -1){
            model.setDate(getIntent().getLongExtra("date",0));
        }

        setContentView(R.layout.activity_daily_medication_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.addMedButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        ImageButton next = findViewById(R.id.dailyNextButton);
        ImageButton prev = findViewById(R.id.dailyPrevButton);
        View.OnClickListener changeDay = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate((v.getId()==R.id.dailyNextButton) ? 1 : -1);
            }
        };
        next.setOnClickListener(changeDay);
        prev.setOnClickListener(changeDay);

        date = findViewById(R.id.dailyDateView);
        updateText();

        //TODO: scroll to current time upon opening list
        //TODO: coloring for past events?
        //TODO: time separators in recyclerview
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<Long> dateList = new ArrayList<>();
        dateList.add(model.getPrevDate());
        dateList.add(model.getDate());
        dateList.add(model.getNextDate());
        dailyViewPager = findViewById(R.id.dailyViewPager);
        model.setMedAdapter(new DailyViewPagerAdapter(dateList, model.getMedications().getValue()));
        dailyViewPager.setAdapter(new DailyViewPagerAdapter(dateList, model.getMedications().getValue()));
    }

    private void changeDate(int dir){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(model.getDate());
        cal.add(Calendar.DAY_OF_YEAR, dir);
        model.setDate(cal.getTimeInMillis());
        updateText();
    }

    private void updateText(){
        String dateText = new SimpleDateFormat("EEE, MMM d").format(model.getDate());
        String year = new SimpleDateFormat("yyyy").format(model.getDate());
        if (!year.equals(Calendar.getInstance().get(Calendar.YEAR)+"")){
            dateText += " " + year;
        }
        date.setText(dateText);
        updateData();
    }

    private void updateData(){
        //TODO: modify list
    }

    /*//TODO: progressbar doesn't work right
    public void hideBar() {
        View progBar = findViewById(R.id.dailyMedProgress);
        progBar.setVisibility(View.INVISIBLE);
    }*/
}
