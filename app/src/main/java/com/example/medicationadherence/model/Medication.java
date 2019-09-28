package com.example.medicationadherence.model;

import java.sql.Time;

public class Medication {
    private int medImage;
    private String medName;
    private String medDosage;
    private String instructions;
    private boolean active;
    private String doctorName;
    private long startDate;
    private long endDate; //set to -1 to not show

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
}
