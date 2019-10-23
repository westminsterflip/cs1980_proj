package com.example.medicationadherence.data.room.dao;/* Schedule entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicationadherence.data.room.entities.Schedule;

import java.util.List;

@Dao
public interface ScheduleDAO {
    @Insert
    void insert(Schedule... schedules);
 
    @Update
    void update(Schedule... schedules);
 
    @Delete
    void delete(Schedule... schedule);
	
	@Query("SELECT * FROM SCHEDULE")
    List<Schedule> getAllSchedules();

    @Query("DELETE FROM SCHEDULE")
    void clearTable();

    @Query("SELECT Medication.medicationID as medicationID, Medication.name as medName,"+
           "Medication.dosage AS dosageAmt, Medication.startDate AS startDate,"+
           " Medication.endDate AS endDate, SCHEDULE.numDoses AS doses, SCHEDULE.time AS timeOfDay"+
           ", SCHEDULE.weekdays AS days, Instructions.instructions AS instructions FROM Medication INNER JOIN "+
           "SCHEDULE ON Medication.medicationID = SCHEDULE.medicationID LEFT JOIN Instructions ON instructions.medicationID = Medication.medicationID")
    List<ScheduleCard> loadScheduled();

    class ScheduleCard{
        public int medicationID;
        public String medName;
        public String dosageAmt;
        public long startDate;
        public long endDate;
        public int doses;
        public long timeOfDay;
        public boolean[] days;
        public String instructions;

        public ScheduleCard(String medName, String dosageAmt, long startDate, long endDate, int doses, long timeOfDay, boolean[] days, String instructions) {
            this.medName = medName;
            this.dosageAmt = dosageAmt;
            this.startDate = startDate;
            this.endDate = endDate;
            this.doses = doses;
            this.timeOfDay = timeOfDay;
            this.days = days;
            this.instructions = instructions;
        }
    }
}