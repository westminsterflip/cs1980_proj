package com.example.medicationadherence.data.room.entities;

/* MedicationLog entity in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.SET_NULL;

@Entity(primaryKeys = {"medicationID", "date", "timeLate"}, foreignKeys =
@ForeignKey(entity = Medication.class, parentColumns = "medicationID", childColumns = "medicationID", onDelete = SET_NULL))
public class MedicationLog {
	private int medicationID;  /* FK Medication.medicationID */
    private long date;
	private boolean taken;     /* true if taken, false if missed */
	@NonNull
    private Integer timeLate = -1; //Can't be null and a primary key
	
	public int getMedicationID() {
        return this.medicationID;
    }
 
    public void setMedicationID(int medicationID) {
        this.medicationID = medicationID;
    }
	
	public long getDate() {
        return this.date;
    }
 
    public void setDate(long date) {
        this.date = date;
    }
	
	public boolean getTaken() {
        return this.taken;
    }
 
    public void setTaken(boolean taken) {
        this.taken = taken;
    }
	
	public @NonNull Integer getTimeLate() {
        return this.timeLate;
    }
 
    public void setTimeLate(@NonNull Integer timeLate) {
        this.timeLate = timeLate;
    }
}