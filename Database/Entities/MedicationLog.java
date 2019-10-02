/* MedicationLog entity in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"medicationID", "date", "timeLate"}, foreignKeys =
@ForeignKey(entity = Medication.class, parentColumns = "medicationID", childColumns = "medicationID", onDelete = CASCADE)))
public class MedicationLog {
	private int medicationID;  /* FK Medication.medicationID */
	private String date;	   /* FORMAT: YYYY-MM-DD (for now) */
	private boolean taken;     /* true if taken, false if missed */
	private String timeLate;
	
	public String getMedicationID() {
        return this.medicationID;
    }
 
    public void setMedicationID(int medicationID) {
        this.medicationID = medicationID;
    }
	
	public String getDate() {
        return this.date;
    }
 
    public void setDate(String date) {
        this.date = date;
    }
	
	public boolean getTaken() {
        return this.taken;
    }
 
    public void setTaken(boolean taken) {
        this.taken = taken;
    }
	
	public String getTimeLate() {
        return this.timeLate;
    }
 
    public void setTimeLate(String timeLate) {
        this.timeLate = timeLate;
    }
}