package com.example.medicationadherence.ui.home;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.os.ConfigurationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.DailyViewPagerAdapter;
import com.example.medicationadherence.model.DailyMedication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class DailyMedListFragment extends Fragment {
    private DailyMedListViewModel model;
    private TextView date;
    private ViewPager2 dailyViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        model.getMedications().observe(this, medicationObserver);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_daily_med_list, container, false);
        long timeToView = DailyMedListFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getTimeToView();
        if(model.getDate() == -1){
            model.setDate(timeToView);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(model.getDate());
            cal.add(Calendar.DAY_OF_YEAR, 1);
            model.setNextDate(cal.getTimeInMillis());
            cal.add(Calendar.DAY_OF_YEAR, -2);
            model.setPrevDate(cal.getTimeInMillis());
        }
        /*FloatingActionButton fab = root.findViewById(R.id.addMedButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        ImageButton next = root.findViewById(R.id.dailyNextButton);
        ImageButton prev = root.findViewById(R.id.dailyPrevButton);
        final View.OnClickListener changeDay = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate((v.getId()==R.id.dailyNextButton) ? 1 : -1);
            }
        };
        next.setOnClickListener(changeDay);
        prev.setOnClickListener(changeDay);

        date = root.findViewById(R.id.dailyDateView);
        updateText();

        //TODO: scroll to current time upon opening list
        //TODO: coloring for past events?
        //TODO: time separators in recyclerview
        dailyViewPager = root.findViewById(R.id.dailyViewPager);
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
        return root;
    }

    private void changeDate(int dir){
        dailyViewPager.setCurrentItem(dailyViewPager.getCurrentItem()+dir);
    }

    private void updateText() {
        String dateText = new SimpleDateFormat("EEE, MMM d", ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)).format(model.getDate());
        String year = new SimpleDateFormat("yyyy", ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)).format(model.getDate());
        if (!year.equals(Calendar.getInstance().get(Calendar.YEAR) + "")) {
            dateText += " " + year;
        }
        date.setText(dateText);
    }
}
