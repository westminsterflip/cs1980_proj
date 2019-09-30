package com.example.medicationadherence.ui.settings;

import android.os.Bundle;

import androidx.preference.PreferenceDialogFragmentCompat;

public class DataDialogPreferenceFragment extends PreferenceDialogFragmentCompat {
    public static DataDialogPreferenceFragment newInstance(String key){
        DataDialogPreferenceFragment fragment = new DataDialogPreferenceFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if(positiveResult){
            //TODO: clear data from room
        }
    }
}
