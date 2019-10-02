package com.example.medicationadherence.data.room.entities;

/* Instructions entity in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"medicationID", "instructions"}, foreignKeys =
@ForeignKey(entity = MedicationEntity.class, parentColumns = "medicationID", childColumns = "medicationID", onDelete = CASCADE))
public class Instructions {
	private int medicationID;           /* FK MedicationEntity.medicationID */
    @NonNull
	private String instructions = "";
	
	public void setMedicationID(int medicationID) {
        this.medicationID = medicationID;
    }

	public int getMedicationID() {
        return this.medicationID;
    }
	
	public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

	public String getInstructions() {
        return this.instructions;
    }
	
}