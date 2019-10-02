/* Medication entity in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys =
@ForeignKey(entity = Doctors.class, parentColumns = "name", childColumns = "prescribingDoctor", onDelete = CASCADE))
public class Medication {
	@PrimaryKey
	private int medicationID;

	private String name;
	private boolean status;
	private String prescribingDoctor;     /* FK Doctors.name */
	private double dosage;
	private String start_date;            /* FORMAT: YYYY-MM-DD (for now) */
	private String end_date;              /* FORMAT: YYYY-MM-DD (for now) */
	private int containerVolume;
	private double cost;
	
	public String getMedicationID() {
        return this.medicationID;
    }
 
    public void setMedicationID(int medicationID) {
        this.medicationID = medicationID;
    }
	
	public String getName() {
        return this.firstName;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getStatus() {
        return this.status;
    }
 
    public void setStatus(boolean status) {
        this.status = status;
    }
 
    public String getPrescribingDoctor() {
        return this.prescribingDoctor;
    }
	
	public String setPrescribingDoctor(String prescribingDoctor) {
        this.prescribingDoctor = prescribingDoctor;
    }
 
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
	
	public String getStartDate() {
        return this.startDate;
    }
 
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
	public String getEndDate() {
        return this.endDate;
    }
 
    public void setContainerVolume(double containerVolume) {
        this.containerVolume = containerVolume;
    }
	public String getContainerVolume() {
        return this.containerVolume;
    }
 
    public void setCost(double cost) {
        this.cost = cost;
    }
	public String getCost() {
        return this.cost;
    }
}