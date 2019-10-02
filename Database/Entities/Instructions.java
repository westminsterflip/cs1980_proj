/* Instructions entity in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"medicationID", "instructions"}, foreignKeys =
@ForeignKey(entity = Medication.class, parentColumns = "medicationID", childColumns = "medicationID", onDelete = CASCADE)))
public class Instructions {
	private int medicationID;           /* FK Medication.medicationID */
	private String instructions;
	
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