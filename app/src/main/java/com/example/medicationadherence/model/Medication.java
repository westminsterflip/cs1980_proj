package com.example.medicationadherence.model;

public class Medication {
    private int medImage;
    private String medName;
    private String medDosage;
    private String instructions;
    private boolean active;
    private String doctorName;
    private long startDate;
    private long endDate; //set to -1 to not show
    private int onHand;
    private int containerVol;
    private double cost;
    private boolean asNeeded;

    public Medication(int medImage, String medName, String medDosage, String instructions, String doctorName, long startDate, long endDate) {
        this.medImage = medImage;
        this.medName = medName;
        this.medDosage = medDosage;
        this.instructions = instructions;
        this.doctorName = doctorName;
        this.startDate = startDate;
        this.endDate = endDate;
        active = true;
    }

    public Medication(int medImage, String medName, String medDosage, String instructions, boolean active, String doctorName, long startDate, long endDate) {
        this.medImage = medImage;
        this.medName = medName;
        this.medDosage = medDosage;
        this.instructions = instructions;
        this.active = active;
        this.doctorName = doctorName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Medication(int medImage, String medName, String medDosage, String instructions, boolean active, String doctorName, long startDate, long endDate, int onHand, int containerVol, double cost, boolean asNeeded) {
        this.medImage = medImage;
        this.medName = medName;
        this.medDosage = medDosage;
        this.instructions = instructions;
        this.active = active;
        this.doctorName = doctorName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.onHand = onHand;
        this.containerVol = containerVol;
        this.cost = cost;
        this.asNeeded = asNeeded;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedDosage() {
        return medDosage;
    }

    public void setMedDosage(String medDosage) {
        this.medDosage = medDosage;
    }

    public int getMedImage() {
        return medImage;
    }

    public void setMedImage(int medImage) {
        this.medImage = medImage;
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
}
