package com.example.medicationadherence.ui.settings;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.medicationadherence.R;
import com.example.medicationadherence.ui.MainViewModel;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {
    private MainViewModel mainModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        Preference darkModeEnable = findPreference("darkModeEnable");
        Objects.requireNonNull(darkModeEnable).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(newValue.equals(true))
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                return true;
            }
        });
        Preference darkModeOverride = findPreference("enableDMOverride");
        Objects.requireNonNull(darkModeOverride).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(newValue.equals(true)) {
                    if (PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity())).getBoolean("darkModeEnable", true))
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    else
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                }
                return true;
            }
        });
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        //TODO: change to alertdialog
        if (preference instanceof DataDialogPreference){
            new AlertDialog.Builder(getContext()).setTitle("Delete Database")
                .setMessage(R.string.data_dialog_warning)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainModel.deleteAll();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
        } else
            super.onDisplayPreferenceDialog(preference);
    }


}