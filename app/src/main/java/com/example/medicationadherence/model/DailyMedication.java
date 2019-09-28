package com.example.medicationadherence.model;

import java.sql.Time;

public class DailyMedication {
    private int medImage;
    private String medName;
    private String medDosage;
    private Time dosageTime;
    private String instructions;

    public DailyMedication(int medImage, String medName, String medDosage, Time dosageTime, String instructions){
        this.medImage = medImage;
        this.medName = medName;
        this.medDosage = medDosage;
        this.dosageTime = dosageTime;
        this.instructions = instructions;
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

    public Time getDosageTime() {
        return dosageTime;
    }

    public void setDosageTime(Time dosageTime) {
        this.dosageTime = dosageTime;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}