/* Database used to store user information locally
   
   Includes the following tables: 
      -- Medication
	  -- Instructions
	  -- Schedule
	  -- MedicationLog
	  -- Doctors
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import static android.arch.persistence.room.ForeignKey.CASCADE;

@Database(version = 1, entities = {Medication.class, Instructions.class, Schedule.class, MedicationLog.class, Doctors.class})
abstract class MedicationDatabase extends RoomDatabase{
	public abstract MedicationDAO getMedicationDao();
	public abstract MedicationLogDAO getMedicationLogDao();
	public abstract DoctorsDAO getDoctorsDao();
	public abstract InstructionsDAO getInstructionsDao();
	public abstract ScheduleDao getScheduleDao();
}