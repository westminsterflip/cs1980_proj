package com.example.medicationadherence.data.room.dao;

/* Doctors entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicationadherence.data.room.entities.Doctors;

import java.util.List;

@Dao
public interface DoctorsDAO {
    @Insert
    public void insert(Doctors... doctors);
 
    @Update
    public void update(Doctors... doctors);
 
    @Delete
    public void delete(Doctors... doctor);

	@Query("SELECT * FROM DOCTORS")
	public List<Doctors> getAllDoctors();

    @Query("DELETE FROM DOCTORS")
    public void clearTable();
}