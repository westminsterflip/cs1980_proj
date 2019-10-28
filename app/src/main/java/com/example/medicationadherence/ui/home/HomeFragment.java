package com.example.medicationadherence.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.medicationadherence.R;

import java.util.Calendar;

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
                HomeFragmentDirections.ActionNavHomeToDailyMedListFragment action = HomeFragmentDirections.actionNavHomeToDailyMedListFragment();
                action.setTimeToView(cal.getTimeInMillis());
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
        return root;
    }
}