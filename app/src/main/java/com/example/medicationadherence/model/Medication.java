package com.example.medicationadherence.model;

import java.io.Serializable;
import java.sql.Time;

public class Medication implements Serializable {
    private int medImage;
    private String medName;
    private String doctorName;
    private String medDosage;
    private Time dosageTime;

    public Medication(int medImage, String medName, String doctorName, String medDosage, Time dosageTime){
        this.medImage = medImage;
        this.medName = medName;
        this.doctorName = doctorName;
        this.medDosage = medDosage;
        this.dosageTime = dosageTime;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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
}