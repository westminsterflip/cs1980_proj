package com.example.medicationadherence.model;

import androidx.annotation.NonNull;

public class DailyMedication implements Comparable{
    private int medImage;
    private String medName;
    private String medDosage;
    private long dosageTime;
    private String instructions;

    public DailyMedication(int medImage, String medName, String medDosage, long dosageTime, String instructions){
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

    public long getDosageTime() {
        return dosageTime;
    }

    public void setDosageTime(long dosageTime) {
        this.dosageTime = dosageTime;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


    @Override
    public int compareTo(@NonNull Object o) {
        return Long.compare(this.getDosageTime(), ((DailyMedication) o).getDosageTime());
    }
}