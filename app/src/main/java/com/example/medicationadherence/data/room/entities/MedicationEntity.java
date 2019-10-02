package com.example.medicationadherence.data.room.entities;

/* MedicationEntity entity in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys =
@ForeignKey(entity = Doctors.class, parentColumns = "doctorID", childColumns = "doctorID", onDelete = CASCADE), indices = {
@Index("doctorID")})
public class MedicationEntity {
	@PrimaryKey
	private int medicationID;

	private String name;
	private boolean status;
	private int doctorID;     /* FK Doctors.name */
	private double dosage;
	private String startDate;            /* FORMAT: YYYY-MM-DD (for now) */
	private String endDate;              /* FORMAT: YYYY-MM-DD (for now) */
	private int containerVolume;
	private double cost;

    public int getMedicationID() {
        return medicationID;
    }

    public void setMedicationID(int medicationID) {
        this.medicationID = medicationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public double getDosage() {
        return dosage;
    }

    public void setDosage(double dosage) {
        this.dosage = dosage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getContainerVolume() {
        return containerVolume;
    }

    public void setContainerVolume(int containerVolume) {
        this.containerVolume = containerVolume;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}