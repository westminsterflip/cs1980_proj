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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.medicationadherence.R;
import com.example.medicationadherence.ui.MainViewModel;
import com.example.medicationadherence.ui.medications.MedicationFragment;
import com.example.medicationadherence.ui.medications.MedicationViewModel;

import java.util.ArrayList;
import java.util.Objects;


//TODO: hide keyboard when back pressed in top bar
public class RootWizardFragment extends Fragment {
    private ArrayList<Integer> destinations;
    private RootWizardViewModel model;
    private MedicationViewModel medModel;
    private MainViewModel mainModel;
    private Button cancelBack;
    private ImageButton backArrow;
    private Button nextFinish;
    private ImageButton nextArrow;
    private NavController innerNavController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(RootWizardViewModel.class);
        final Observer<ArrayList<Integer>> destObserver = new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> integers) {
                if(innerNavController!=null){
                    if(Objects.requireNonNull(innerNavController.getCurrentDestination()).getId() == destinations.get(0))
                        setHasLast(false);
                    else
                        setHasLast(true);
                    if(innerNavController.getCurrentDestination().getId() == destinations.get(destinations.size()-1))
                        setHasNext(false);
                    else
                        setHasNext(true);
                }
            }
        };
        model.getDestinations().observe(this, destObserver);
        destinations = model.getDestinations().getValue();
        if (model.getModel() == null)
            medModel = model.setModel(new ViewModelProvider((MedicationFragment) RootWizardFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getMedFragmentInst().get(0)).get(MedicationViewModel.class));
        else
            medModel = model.getModel();
        mainModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
    }

    @Nullable
    @Override
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_root_wizard, container, false);
        backArrow = root.findViewById(R.id.rootWizardBackArrow);
        cancelBack = root.findViewById(R.id.rootWizardBackCancel);
        nextArrow = root.findViewById(R.id.rootWizardNextArrow);
        nextFinish = root.findViewById(R.id.rootWizardNextFinish);
        innerNavController = Navigation.findNavController(root.findViewById(R.id.wizard_nav_host_fragment));
        cancelBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager manager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getActivity().getCurrentFocus() != null) {
                    Objects.requireNonNull(manager).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                int currentLoc = Objects.requireNonNull(innerNavController.getCurrentDestination()).getId();
                if (currentLoc == destinations.get(0)){
                    Navigation.findNavController(root).navigateUp();
                } else {
                    innerNavController.navigateUp();
                    innerNavController.navigate(R.id.wizardMedicineDetailFragment);
                    if (innerNavController.getCurrentDestination().getId() == destinations.get(0)){
                        setHasLast(false);
                    }
                    setHasNext(true);
                }
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {
                int currentLoc = Objects.requireNonNull(innerNavController.getCurrentDestination()).getId();
                if (currentLoc == destinations.get(0)){
                    Navigation.findNavController(root).navigateUp();
                } else {
                    innerNavController.navigateUp();
                    if (innerNavController.getCurrentDestination().getId() == destinations.get(0)){
                        setHasLast(false);
                    }
                    setHasNext(true);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        requireActivity().getOnBackPressedDispatcher().addCallback(Objects.requireNonNull(getChildFragmentManager().findFragmentById(R.id.wizard_nav_host_fragment)), callback);
        nextFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager manager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getActivity().getCurrentFocus() != null) {
                    Objects.requireNonNull(manager).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                int currentLoc = Objects.requireNonNull(innerNavController.getCurrentDestination()).getId();
                if(model.getDestinationExitable(destinations.indexOf(currentLoc))) {
                    if(currentLoc == destinations.get(destinations.size()-1)){
                        model.getThisList().get(model.getThisList().size()-1).pause();
                        //Navigation.findNavController(v).navigateUp();
                        if(model.getDoctorName()!= null && !model.getDoctorName().equals("")){
                            if(model.getSpinnerSelection() == 1) {//add new doctor if doesn't exist
                                //TODO check if exists then this: AlertDialog.Builder, edit if exists
                                model.setDoctorID(mainModel.insert(model.getDoctor()));
                            } else if(model.getSpinnerSelection() > 1){
                                if(mainModel.getRepository().getDoctors().indexOf(model.getDoctor())==-1)
                                    mainModel.updateDoctor(mainModel.getRepository().getDoctors().get(model.getSpinnerSelection()-2).getDoctorID(), model.getDoctorName(), model.getPracticeName(), model.getPracticeAddress(), model.getPhone());
                            }
                        }
                        System.out.println(model.getDoctorID());
                        mainModel.insert(model.getMedication());
                    } else {
                        innerNavController.navigate(destinations.get(destinations.indexOf(currentLoc)+1));
                        if (innerNavController.getCurrentDestination().getId() == destinations.get(destinations.size()-1)){
                            setHasNext(false);
                        }
                        setHasLast(true);
                    }
                } else {
                    ErrFragment fragment = model.getThisList().get(destinations.indexOf(currentLoc));
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
        void pause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("nextText", nextFinish.getText().toString());
        outState.putString("backText", cancelBack.getText().toString());
        outState.putBoolean("nextVisible", nextArrow.getVisibility() == View.VISIBLE);
        outState.putBoolean("backVisible", backArrow.getVisibility() == View.VISIBLE);
        super.onSaveInstanceState(outState);
    }

    public void setHasNext(boolean hasNext){
        nextFinish.setText(hasNext ? "Next" : "Finish");
        nextArrow.setVisibility(hasNext ? View.VISIBLE : View.INVISIBLE);
    }

    public void setHasLast(boolean hasLast){
        cancelBack.setText(hasLast ? "Back" : "Cancel");
        backArrow.setVisibility(hasLast ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onDetach() {
        InputMethodManager manager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            Objects.requireNonNull(manager).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        super.onDetach();
    }
}
