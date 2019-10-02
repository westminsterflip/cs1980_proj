/* Schedule entity in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"medicationID", "time", "weekday"}, foreignKeys =
@ForeignKey(entity = Medication.class, parentColumns = "medicationID", childColumns = "medicationID", onDelete = CASCADE)))
public class Schedule {
	private int medicationID;    /* FK Medication.medicationID */
	private int numDoses;
	private String time;           /* FORMAT: HHMMSS (for now)*/
	private String weekday;

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
	
	public String getTime() {
        return this.time;
    }
	
	public void setWeekday(String weekday) {
        this.weekday = weekday;
    }
	
	public String getWeekday() {
        return this.weekday;
    }
}