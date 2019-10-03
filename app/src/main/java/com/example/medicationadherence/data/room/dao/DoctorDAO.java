package com.example.medicationadherence.data.room.dao;

/* Doctor entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicationadherence.data.room.entities.Doctor;

import java.util.List;

@Dao
public interface DoctorDAO {
    @Insert
    public long insert(Doctor doctors);
 
    @Update
    public void update(Doctor... doctors);
 
    @Delete
    public void delete(Doctor... doctor);

	@Query("SELECT * FROM Doctor")
	public List<Doctor> getAllDoctors();

    @Query("DELETE FROM Doctor")
    public void clearTable();
}