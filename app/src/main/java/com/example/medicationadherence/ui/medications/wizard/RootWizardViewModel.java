package com.example.medicationadherence.ui.medications.wizard;

import androidx.lifecycle.ViewModel;

import com.example.medicationadherence.model.Medication;
import com.example.medicationadherence.ui.medications.MedicationViewModel;

public class RootWizardViewModel extends ViewModel {
    private int medImage = -1;
    private String medName;
    private String medDosage;
    private String instructions;
    private boolean active = true;
    private String doctorName;
    private long startDate;
    private long endDate = -1;
    private int onHand;
    private int containerVol;
    private double cost;
    private boolean asNeeded;
    private MedicationViewModel model;

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
    }

    public int getContainerVol() {
        return containerVol;
    }

    public void setContainerVol(int containerVol) {
        this.containerVol = containerVol;
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
        return new Medication(medImage, medName, medDosage, instructions, active, doctorName, startDate, endDate, onHand, containerVol, cost, asNeeded);
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
}
