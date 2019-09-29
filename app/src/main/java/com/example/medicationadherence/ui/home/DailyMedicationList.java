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
import java.util.Calendar;
import java.util.List;

public class DailyMedicationList extends AppCompatActivity{
    private DailyMedListViewModel model;
    private TextView date;
    private ViewPager2 dailyViewPager;

    //TODO: attach viewpager and buttons
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
        model.getDateList();
        if(model.getDate() == -1){
            model.setDate(getIntent().getLongExtra("date",0));
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(model.getDate());
            cal.add(Calendar.DAY_OF_YEAR, 1);
            model.setNextDate(cal.getTimeInMillis());
            cal.add(Calendar.DAY_OF_YEAR, -2);
            model.setPrevDate(cal.getTimeInMillis());
        }
        model.getMedications().observe(this, medicationObserver);

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
        final View.OnClickListener changeDay = new View.OnClickListener() {
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
        dailyViewPager = findViewById(R.id.dailyViewPager);
        model.setMedAdapter(new DailyViewPagerAdapter(model.getDateList(), model.getMedications().getValue()));
        dailyViewPager.setAdapter(new DailyViewPagerAdapter(model.getDateList(), model.getMedications().getValue()));
        dailyViewPager.setCurrentItem(1);
        dailyViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position != 1) {
                    final int dir = position - 1;
                    final Calendar cal2 = Calendar.getInstance();
                    cal2.setTimeInMillis(model.getDate());
                    cal2.add(Calendar.DAY_OF_YEAR, 2*dir);
                    dailyViewPager.post(new Runnable() {
                        @Override
                        public void run() { //This is kind of bad
                            if(dir < 0){
                                model.setNextDate(model.getDate());
                                model.setDate(model.getPrevDate());
                                model.setPrevDate(cal2.getTimeInMillis());
                                model.loadPrevMeds();
                            } else if (dir == 1){
                                model.setPrevDate(model.getDate());
                                model.setDate(model.getNextDate());
                                model.setNextDate(cal2.getTimeInMillis());
                                model.loadNextMeds();
                            }
                            dailyViewPager.setAdapter(model.getMedAdapter());
                            dailyViewPager.setCurrentItem(1);
                            updateText();
                        }
                    });
                }
            }
        });
    }

    private void changeDate(int dir){
        dailyViewPager.setCurrentItem(dailyViewPager.getCurrentItem()+dir);
    }

    private void updateText() {
        String dateText = new SimpleDateFormat("EEE, MMM d").format(model.getDate());
        String year = new SimpleDateFormat("yyyy").format(model.getDate());
        if (!year.equals(Calendar.getInstance().get(Calendar.YEAR) + "")) {
            dateText += " " + year;
        }
        date.setText(dateText);
    }
}
