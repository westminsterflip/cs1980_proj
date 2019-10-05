package com.example.medicationadherence.ui.medications.wizard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.medicationadherence.R;
import com.example.medicationadherence.data.room.entities.Doctor;
import com.example.medicationadherence.data.room.entities.Medication;
import com.example.medicationadherence.ui.medications.MedicationViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class RootWizardViewModel extends ViewModel {
    private int medImage = -1;
    private String medName;
    private String medDosage;
    private String instructions;
    private boolean active = true;
    private String doctorName;
    private long startDate = -1;
    private long endDate = -1;
    private int onHand = -1;
    private int containerVol = -1;
    private double cost = -1;
    private boolean asNeeded = false;
    private MedicationViewModel model;
    private boolean[] destinationExitable = {false, true};
    private ArrayList<RootWizardFragment.ErrFragment> thisList = new ArrayList<>();
    private int spinnerSelection = 0;
    private String practiceName;
    private String practiceAddress;
    private String phone;
    private Long doctorID = null;
    private MutableLiveData<ArrayList<Integer>> destinations;

    public int getMedImage() {
        return medImage;
    }

    public void setMedImage(int medImage) {
        this.medImage = medImage;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getOnHand() {
        return onHand;
    }

    public void setOnHand(int onHand) {
        this.onHand = onHand;
        containerVol = onHand;
    }

    public int getContainerVol() {
        return containerVol;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isAsNeeded() {
        return asNeeded;
    }

    public void setAsNeeded(boolean asNeeded) {
        this.asNeeded = asNeeded;
    }

    public Medication getMedication(){
        return new Medication(medName, active, doctorID, medDosage, startDate, endDate, containerVol, cost);
    }

    public Doctor getDoctor(){
        return new Doctor(doctorName, practiceName, practiceAddress, phone);
    }

    public void setMedDosage(String medDosage) {
        this.medDosage = medDosage;
    }

    public MedicationViewModel getModel() {
        return model;
    }

    public MedicationViewModel setModel(MedicationViewModel model) {
        return this.model = model;
    }

    public String getMedDosage() {
        return medDosage;
    }

    public boolean getDestinationExitable(int position){
        return destinationExitable[position];
    }

    public void setDestinationExitable(int position, boolean isExitable){
        destinationExitable[position] = isExitable;
    }

    public ArrayList<RootWizardFragment.ErrFragment> getThisList() {
        return thisList;
    }

    public int getSpinnerSelection() {
        return spinnerSelection;
    }

    public void setSpinnerSelection(int spinnerSelection) {
        this.spinnerSelection = spinnerSelection;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public String getPracticeAddress() {
        return practiceAddress;
    }

    public void setPracticeAddress(String practiceAddress) {
        this.practiceAddress = practiceAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Long doctorID) {
        this.doctorID = doctorID;
    }

    public MutableLiveData<ArrayList<Integer>> getDestinations() {
        if(destinations == null){
            destinations = new MutableLiveData<>();
            Integer[] destsint = {R.id.wizardMedicineDetailFragment, R.id.wizardDoctorDetailFragment};
            ArrayList dests = new ArrayList<>(Arrays.asList(destsint));
            destinations.setValue(dests);
        }
        return destinations;
    }

    public void setDestinations(MutableLiveData<ArrayList<Integer>> destinations) {
        this.destinations = destinations;
    }
}
