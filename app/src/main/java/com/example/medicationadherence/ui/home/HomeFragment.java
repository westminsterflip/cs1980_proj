package com.example.medicationadherence.ui.home;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.medicationadherence.R;

import java.util.Calendar;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private CalendarView mainCal;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mainCal = root.findViewById(R.id.calendarView);
        mainCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.clear();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                HomeFragmentDirections.ActionNavHomeToDailyMedListFragment action = HomeFragmentDirections.actionNavHomeToDailyMedListFragment(cal.getTimeInMillis());
                Navigation.findNavController(view).navigate(action);
            }
        });
        Button todayButton = root.findViewById(R.id.todayButton);
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainCal.setDate(Calendar.getInstance().getTimeInMillis());
            }
        });
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.edit_schedule, menu);
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_dots_vertical, null);
        switch (Objects.requireNonNull(getContext()).getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK){
            case Configuration.UI_MODE_NIGHT_NO:
                drawable.setTint(Color.parseColor("#FF000000"));
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                drawable.setTint(Color.parseColor("#FFFFFFFF"));
        }
        toolbar.setOverflowIcon(drawable);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuEditSchedule){
            Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.editScheduleFragment);
        }
        return super.onOptionsItemSelected(item);
    }
}