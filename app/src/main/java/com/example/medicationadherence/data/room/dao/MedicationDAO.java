package com.example.medicationadherence.data.room.dao;/* MedicationEntity entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicationadherence.data.room.entities.MedicationEntity;

import java.util.List;

@Dao
public interface MedicationDAO {
    @Insert
    public void insert(MedicationEntity... medications);
 
    @Update
    public void update(MedicationEntity... medications);
 
    @Delete
    public void delete(MedicationEntity... medication);
	
	@Query("SELECT * FROM MEDICATIONENTITY")
	public List<MedicationEntity> getAllMedications();

	@Query("DELETE FROM MEDICATIONENTITY")
    public void clearTable();
}