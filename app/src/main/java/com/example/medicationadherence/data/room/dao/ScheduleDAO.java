package com.example.medicationadherence.data.room.dao;/* Schedule entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import androidx.lifecycle.MutableLiveData;
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
    public void insert(Schedule... schedules);
 
    @Update
    public void update(Schedule... schedules);
 
    @Delete
    public void delete(Schedule... schedule);
	
	@Query("SELECT * FROM SCHEDULE")
	public List<Schedule> getAllSchedules();

    @Query("DELETE FROM SCHEDULE")
    public void clearTable();

    @Query("SELECT MEDICATIONENTITY.medicationID as medicationID, MEDICATIONENTITY.name as medName,"+
           "MEDICATIONENTITY.dosage AS dosageAmt, MEDICATIONENTITY.startDate AS startDate,"+
           " MEDICATIONENTITY.endDate AS endDate, SCHEDULE.numDoses AS doses, SCHEDULE.time AS timeOfDay"+
           ", SCHEDULE.weekdays AS days FROM MEDICATIONENTITY INNER JOIN "+
           "SCHEDULE ON MEDICATIONENTITY.medicationID = SCHEDULE.medicationID")
    public MutableLiveData<List<Schedule.ScheduleCard>> loadScheduled();
}