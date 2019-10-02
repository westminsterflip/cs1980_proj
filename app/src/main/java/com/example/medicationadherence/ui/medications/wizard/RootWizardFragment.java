package com.example.medicationadherence.ui.medications.wizard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.medicationadherence.R;
import com.example.medicationadherence.ui.medications.MedicationFragment;
import com.example.medicationadherence.ui.medications.MedicationViewModel;

import java.util.Arrays;
import java.util.Objects;


//TODO: hide keyboard when back pressed in top bar
public class RootWizardFragment extends Fragment {
    private final Integer[] destinations = {R.id.wizardMedicineDetailFragment, R.id.wizardDoctorDetailFragment};
    private RootWizardViewModel model;
    private MedicationViewModel medModel;
    private Button cancelBack;
    private ImageButton backArrow;
    private Button nextFinish;
    private ImageButton nextArrow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(this).get(RootWizardViewModel.class);
        MedicationFragment medicationFragment = (MedicationFragment) RootWizardFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getMedFragmentInst().get(0);
        if (model.getModel() == null)
            medModel = model.setModel(ViewModelProviders.of((MedicationFragment) RootWizardFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getMedFragmentInst().get(0)).get(MedicationViewModel.class));
        else
            medModel = model.getModel();
    }

    @Nullable
    @Override
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_root_wizard, container, false);
        final NavController innerNavController = Navigation.findNavController(root.findViewById(R.id.wizard_nav_host_fragment));
        backArrow = root.findViewById(R.id.rootWizardBackArrow);
        cancelBack = root.findViewById(R.id.rootWizardBackCancel);
        nextArrow = root.findViewById(R.id.rootWizardNextArrow);
        nextFinish = root.findViewById(R.id.rootWizardNextFinish);
        cancelBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getActivity().getCurrentFocus() != null) {
                    manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                int currentLoc = Objects.requireNonNull(innerNavController.getCurrentDestination()).getId();
                if (currentLoc == destinations[0]){
                    Navigation.findNavController(root).navigateUp();
                } else {
                    innerNavController.navigateUp();
                    innerNavController.navigate(R.id.wizardMedicineDetailFragment);
                    if (innerNavController.getCurrentDestination().getId() == destinations[0]){
                        cancelBack.setText("Cancel");
                        backArrow.setVisibility(View.INVISIBLE);
                    }
                    nextFinish.setText("Next");
                    nextArrow.setVisibility(View.VISIBLE);
                }
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {
                int currentLoc = Objects.requireNonNull(innerNavController.getCurrentDestination()).getId();
                if (currentLoc == destinations[0]){
                    Navigation.findNavController(root).navigateUp();
                } else {
                    innerNavController.navigateUp();
                    if (innerNavController.getCurrentDestination().getId() == destinations[0]){
                        cancelBack.setText("Cancel");
                        backArrow.setVisibility(View.INVISIBLE);
                    }
                    nextFinish.setText("Next");
                    nextArrow.setVisibility(View.VISIBLE);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        requireActivity().getOnBackPressedDispatcher().addCallback(Objects.requireNonNull(getChildFragmentManager().findFragmentById(R.id.wizard_nav_host_fragment)), callback);
        nextFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getActivity().getCurrentFocus() != null) {
                    manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                int currentLoc = Objects.requireNonNull(innerNavController.getCurrentDestination()).getId();
                if(model.getDestinationExitable(Arrays.asList(destinations).indexOf(currentLoc))) {
                    if(currentLoc == destinations[destinations.length-1]){
                        Navigation.findNavController(v).navigateUp();
                        v.getRootView().post(new Runnable() {
                            @Override
                            public void run() {
                                Objects.requireNonNull(medModel.getMedications().getValue()).add(model.getMedication());
                                //TODO enter data into database
                            }
                        });
                    } else {

                        innerNavController.navigate(destinations[Arrays.asList(destinations).indexOf(currentLoc)+1]);
                        if (innerNavController.getCurrentDestination().getId() == destinations[destinations.length-1]){
                            nextFinish.setText("Finish");
                            nextArrow.setVisibility(View.INVISIBLE);
                        }
                        cancelBack.setText("Back");
                        backArrow.setVisibility(View.VISIBLE);
                    }
                } else {
                    ErrFragment fragment = model.getThisList().get(Arrays.asList(destinations).indexOf(currentLoc));
                    fragment.showErrors();
                }

            }
        });

        if(savedInstanceState != null) {
            nextFinish.setText(savedInstanceState.getString("nextText"));
            cancelBack.setText(savedInstanceState.getString("backText"));
            nextArrow.setVisibility((savedInstanceState.getBoolean("nextVisible"))? View.VISIBLE : View.INVISIBLE);
            backArrow.setVisibility((savedInstanceState.getBoolean("backVisible"))? View.VISIBLE : View.INVISIBLE);
        }
        return root;
    }

    public interface ErrFragment{
        void showErrors();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("nextText", nextFinish.getText().toString());
        outState.putString("backText", cancelBack.getText().toString());
        outState.putBoolean("nextVisible", nextArrow.getVisibility() == View.VISIBLE);
        outState.putBoolean("backVisible", backArrow.getVisibility() == View.VISIBLE);
        super.onSaveInstanceState(outState);
    }
}
