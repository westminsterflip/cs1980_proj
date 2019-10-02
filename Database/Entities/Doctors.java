/* Doctors entity in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Doctors {
	@PrimaryKey
	private int doctorID;
	
	private String name;
	private String practiceName;
	private String address;
	private String phone;
	
	public void setName(String name) {
        this.name = name;
    }
	
	public String getName() {
        return this.name;
    }
	
	public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }
	
	public String getPracticeName() {
        return this.practiceName;
    }
	
	public void setAddress(String address) {
        this.address = address;
    }
	
	public String getAddress() {
        return this.address;
    }
	
	public void setPhone(String phone) {
        this.phone = phone;
    }
	
	public String getPhone() {
        return this.phone;
    }
	
	public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
	
	public int getDoctorID() {
        return this.doctorID;
    }
}