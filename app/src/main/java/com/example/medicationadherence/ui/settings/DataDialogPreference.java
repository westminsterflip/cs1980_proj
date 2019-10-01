package com.example.medicationadherence.ui.settings;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.DialogPreference;

import com.example.medicationadherence.R;
public class DataDialogPreference extends DialogPreference {
    private final int dialogLayoutResID = R.layout.layout_data_dialog_pref;

    public DataDialogPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DataDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, defStyleAttr);
    }

    public DataDialogPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dialogPreferenceStyle);
    }

    public DataDialogPreference(Context context) {
        this(context, null);
    }

    @Override
    public int getDialogLayoutResource() {
        return dialogLayoutResID;
    }
}
