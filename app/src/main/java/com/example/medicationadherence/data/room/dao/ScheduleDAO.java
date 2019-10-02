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
    public void insert(Schedule... schedules);
 
    @Update
    public void update(Schedule... schedules);
 
    @Delete
    public void delete(Schedule... schedule);
	
	@Query("SELECT * FROM SCHEDULE")
	public List<Schedule> getAllSchedules();

    @Query("DELETE FROM SCHEDULE")
    public void clearTable();
}