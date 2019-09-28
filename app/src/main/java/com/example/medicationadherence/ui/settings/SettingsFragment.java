package com.example.medicationadherence.ui.settings;


import android.os.Bundle;
import android.view.View;

import androidx.preference.PreferenceFragmentCompat;

import com.example.medicationadherence.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    public void test(View v){
        System.out.println("worked");
    }
}