package com.example.medicationadherence.data.room.entities;

/* MedicationLog entity in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"medicationID", "date", "timeLate"}, foreignKeys =
@ForeignKey(entity = Medication.class, parentColumns = "medicationID", childColumns = "medicationID", onDelete = CASCADE))
public class MedicationLog {
	private Long medicationID;  /* FK Medication.medicationID */
    private long date;
	private boolean taken;     /* true if taken, false if missed */
    private long timeLate = -1L; //Can't be null and a primary key
	
	public Long getMedicationID() {
        return this.medicationID;
    }
 
    public void setMedicationID(Long medicationID) {
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
	
	public long getTimeLate() {
        return this.timeLate;
    }
 
    public void setTimeLate(long timeLate) {
        this.timeLate = timeLate;
    }
}