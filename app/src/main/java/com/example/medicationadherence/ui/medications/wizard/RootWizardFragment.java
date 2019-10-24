package com.example.medicationadherence.ui.medications.wizard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.medicationadherence.R;
import com.example.medicationadherence.data.Converters;
import com.example.medicationadherence.data.room.entities.Doctor;
import com.example.medicationadherence.data.room.entities.Instructions;
import com.example.medicationadherence.data.room.entities.Medication;
import com.example.medicationadherence.data.room.entities.Schedule;
import com.example.medicationadherence.ui.MainViewModel;
import com.example.medicationadherence.ui.medications.MedicationFragment;
import com.example.medicationadherence.ui.medications.MedicationViewModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private boolean scheduled = false;
    private int index = -1;
    private MedicationFragment medicationFragment;

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
        mainModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        model.context = getContext();
        model.setsMedID(RootWizardFragmentArgs.fromBundle(getArguments()).getMedicationID());
        if (model.getModel() == null)
            medModel = model.setModel(new ViewModelProvider((MedicationFragment) RootWizardFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getMedFragmentInst().get(0)).get(MedicationViewModel.class));
        else
            medModel = model.getModel();
        medicationFragment = (MedicationFragment) RootWizardFragmentArgs.fromBundle(getArguments()).getMedFragmentInst().get(0);
        if(model.getsMedID() != -1) {
            Medication m = mainModel.getMedWithID(model.getsMedID());
            model.setMedication(m);
            for (int i = 0; i < medModel.getMedList().size(); i ++){
                System.out.println(medModel.getMedList().get(i).getMedicationID() + " " + medModel.getMedList().get(i).getName());
                if (medModel.getMedList().get(i).getMedicationID().equals(model.getsMedID())){
                    index = i;
                    break;
                }
            }
        }
        model.setMainViewModel(mainModel);
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
        model.setNavController(innerNavController = Navigation.findNavController(root.findViewById(R.id.wizard_nav_host_fragment)));
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
                    if(currentLoc == R.id.editScheduleCardFragment2) {
                        model.getSchedules().addAll(model.getRemoved());
                        model.getThisList().get(model.getDestinations().getValue().indexOf(R.id.editScheduleCardFragment2)).pause();
                    }
                    innerNavController.navigateUp();
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
            public void onClick(final View v) {
                InputMethodManager manager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getActivity().getCurrentFocus() != null) {
                    Objects.requireNonNull(manager).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                final int currentLoc = Objects.requireNonNull(innerNavController.getCurrentDestination()).getId();
                if(model.getThisList().get(model.getDestinations().getValue().indexOf(currentLoc)).isExitable()) {

                    if(currentLoc == R.id.wizardDoctorDetailFragment){
                        model.getThisList().get(1).pause();
                        if(model.getDoctorName()!= null && !model.getDoctorName().equals("")){
                            if(model.getSpinnerSelection() == 1) {//add new doctor if doesn't exist
                                if(mainModel.getDocWithName(model.getDoctorName())==null || mainModel.getDocWithName(model.getDoctorName()).size()==0){
                                    model.setDoctorID(mainModel.insert(model.getDoctor()));
                                    if(currentLoc == destinations.get(destinations.size()-1)){
                                        model.getThisList().get(model.getThisList().size()-1).pause();
                                        addMedGoUp(v);
                                    } else {
                                        innerNavController.navigate(destinations.get(destinations.indexOf(currentLoc)+1));
                                        if (innerNavController.getCurrentDestination().getId() == destinations.get(destinations.size()-1)){
                                            setHasNext(false);
                                        }
                                        setHasLast(true);
                                    }
                                } else {
                                    new AlertDialog.Builder(Objects.requireNonNull(getContext())).setTitle("Update Doctor?")
                                            .setMessage(R.string.doctor_update)
                                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    ((WizardDoctorDetailFragment)model.getThisList().get(destinations.indexOf(R.id.wizardDoctorDetailFragment))).doctorChooser.performClick();
                                                }
                                            })
                                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    model.setDoctorID(mainModel.insert(model.getDoctor()));
                                                    if(currentLoc == destinations.get(destinations.size()-1)){
                                                        model.getThisList().get(model.getThisList().size()-1).pause();
                                                        addMedGoUp(v);
                                                    } else {
                                                        innerNavController.navigate(destinations.get(destinations.indexOf(currentLoc)+1));
                                                        if (innerNavController.getCurrentDestination().getId() == destinations.get(destinations.size()-1)){
                                                            setHasNext(false);
                                                        }
                                                        setHasLast(true);
                                                    }
                                                }
                                            })
                                            .show();
                                }
                            } else if(model.getSpinnerSelection() > 1){
                                int pos = -1;
                                List<Doctor> doctors = mainModel.getRepository().getDoctors();
                                for(int i = 0; i < doctors.size(); i++){
                                    if (doctors.get(i).getDoctorID().equals(model.getDoctorID())){
                                        pos = i;
                                        break;
                                    }
                                }
                                if(pos!=-1) {
                                    mainModel.updateDoctor(mainModel.getRepository().getDoctors().get(model.getSpinnerSelection() - 2).getDoctorID(), model.getDoctorName(), model.getPracticeName(), model.getPracticeAddress(), model.getPhone());
                                    if(currentLoc == destinations.get(destinations.size()-1)){
                                        model.getThisList().get(model.getThisList().size()-1).pause();
                                        addMedGoUp(v);
                                    } else {
                                        innerNavController.navigate(destinations.get(destinations.indexOf(currentLoc)+1));
                                        if (innerNavController.getCurrentDestination().getId() == destinations.get(destinations.size()-1)){
                                            setHasNext(false);
                                        }
                                        setHasLast(true);
                                    }
                                } else {
                                    int i = 1/0;
                                }
                            } else {
                                int i = 1/0;//this shouldn't be reachable, so crash app if it is reached
                            }
                        } else {
                            if(currentLoc == destinations.get(destinations.size()-1)){
                                model.getThisList().get(model.getThisList().size()-1).pause();
                                addMedGoUp(v);
                            } else {
                                innerNavController.navigate(destinations.get(destinations.indexOf(currentLoc)+1));
                                if (innerNavController.getCurrentDestination().getId() == destinations.get(destinations.size()-1)){
                                    setHasNext(false);
                                }
                                setHasLast(true);
                            }
                        }
                    } else if(currentLoc == R.id.editScheduleCardFragment2){
                        model.getThisList().get(model.getDestinations().getValue().indexOf(R.id.editScheduleCardFragment2)).pause();
                        innerNavController.navigateUp();
                    } else if (currentLoc == R.id.editScheduleFragment2){
                        scheduled = true;
                        model.getThisList().get(model.getThisList().size()-1).pause();
                        addMedGoUp(v);
                    } else {
                        if(currentLoc == destinations.get(destinations.size()-1)){
                            model.getThisList().get(model.getThisList().size()-1).pause();
                            addMedGoUp(v);
                        } else {
                            innerNavController.navigate(destinations.get(destinations.indexOf(currentLoc)+1));
                            if (innerNavController.getCurrentDestination().getId() == destinations.get(destinations.size()-1)){
                                setHasNext(false);
                            }
                            setHasLast(true);
                        }
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
        boolean isExitable();
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

    public void setAdd(){
        nextFinish.setText("Add");
        nextArrow.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDetach() {
        InputMethodManager manager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null) {
            Objects.requireNonNull(manager).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        super.onDetach();
    }

    void addMedGoUp(View v){
        long medID;
        if(index == -1){
            model.setsMedID(medID = mainModel.insert(model.getMedication()));
            medModel.getMedList().add(model.getMedicationWID());
        } else {
            System.out.println(model.getMedName() + " " + model.getsMedID());
            System.out.println("index: " + index);
            medModel.getMedList().set(index, model.getMedicationWID());
            medicationFragment.sort(mainModel.getMedSortMode());
            mainModel.updateMedication(medID = model.getsMedID(), model.getMedName(), model.isActive(), model.getDoctorID(), model.getMedDosage(), model.getStartDate(), model.getEndDate(), model.getContainerVol(), model.getCost());
        }
        Navigation.findNavController(v).navigateUp();
        if (model.getInstructions() != null && !model.getInstructions().equals(""))
            mainModel.insert(new Instructions(medID, model.getInstructions()));
        if (scheduled) {
            List<Schedule> tmp = mainModel.getScheduleFM(medID);
            for (Schedule s : tmp) {
                System.out.println(s.getMedicationID() + " " + new Time(s.getTime()) + " " + Converters.fromBoolArray(s.getWeekdays()) + " " + s.getNumDoses());
            }
            System.out.println("***************************************************");
            for (Schedule s : model.getSchedules()) {
                s.setMedicationID(medID);
                if (!tmp.contains(s))
                    mainModel.insert(s);
                else
                    System.out.println(s.getMedicationID() + " " + new Time(s.getTime()) + " " + Converters.fromBoolArray(s.getWeekdays()) + " " + s.getNumDoses());
            }
            tmp.removeAll(model.getSchedules());
            for (Schedule s : tmp){
                mainModel.remove(s);
            }
        }
    }
}
