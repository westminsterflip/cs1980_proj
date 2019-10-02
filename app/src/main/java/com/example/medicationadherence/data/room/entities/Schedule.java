package com.example.medicationadherence.data.room.entities;

/* Schedule entity in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"medicationID", "time", "weekday"}, foreignKeys =
@ForeignKey(entity = MedicationEntity.class, parentColumns = "medicationID", childColumns = "medicationID", onDelete = CASCADE))
public class Schedule {
	private int medicationID;       /* FK MedicationEntity.medicationID */
	private int numDoses;
	@NonNull
	private String time = "";            /* FORMAT: HHMMSS (for now)*/
    @NonNull
	private String weekday = "";

	public void setMedicationID(int medicationID) {
        this.medicationID = medicationID;
    }
	
	public int getMedicationID() {
        return this.medicationID;
    }

	public void setNumDoses(int numDoses) {
        this.numDoses = numDoses;
    }
	
	public int getNumDoses() {
        return this.numDoses;
    }
	
	public void setTime(String time) {
        this.time = time;
    }
	
	@NonNull
    public String getTime() {
        return this.time;
    }
	
	public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    @NonNull
	public String getWeekday() {
        return this.weekday;
    }
}