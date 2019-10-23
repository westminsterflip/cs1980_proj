package com.example.medicationadherence.data.room.dao;/* Medication entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicationadherence.data.room.entities.Medication;

import java.util.List;

@Dao
public interface MedicationDAO {
    @Insert
    long insert(Medication medications);
 
    @Update
    void update(Medication... medications);

    @Query("update medication set name = :name, status = :status, doctorID = :doctorID, dosage = :dosage, startDate = :startDate, endDate = :endDate, containerVolume = :containerVolume, cost = :cost where medicationID = :id")
    void update(Long id, String name, boolean status, Long doctorID, String dosage, long startDate, long endDate, int containerVolume, double cost);
 
    @Delete
    void delete(Medication... medication);
	
	@Query("SELECT * FROM Medication")
    List<Medication> getAllMedications();

	@Query("DELETE FROM Medication")
    void clearTable();

	@Query("SELECT * FROM MEDICATION WHERE medicationID = :medicationID")
    Medication getMedWithID(Long medicationID);
}