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

@Entity(primaryKeys = {"medicationID", "time", "weekdays"}, foreignKeys =
@ForeignKey(entity = MedicationEntity.class, parentColumns = "medicationID", childColumns = "medicationID", onDelete = CASCADE))
public class Schedule {
	private int medicationID;       /* FK MedicationEntity.medicationID */
	private int numDoses;
	private long time;
	@NonNull
	private boolean[] weekdays;     //boolean array for scheduled on day {SMTWTFS}

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
	
	public void setTime(long time) {
        this.time = time;
    }
	
	@NonNull
    public long getTime() {
        return this.time;
    }

    @NonNull
    public boolean[] getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(@NonNull boolean[] weekdays) {
        this.weekdays = weekdays;
    }
}