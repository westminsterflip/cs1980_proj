package com.example.medicationadherence.data.room.dao;

/* Instructions entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicationadherence.data.room.entities.Instructions;

import java.util.List;

@Dao
public interface InstructionsDAO {
    @Insert
    void insert(Instructions... instructions);
 
    @Update
    void update(Instructions... instructions);
 
    @Delete
    void delete(Instructions... instruction);
	
	@Query("SELECT * FROM INSTRUCTIONS")
    List<Instructions> getAllInstructions();

    @Query("DELETE FROM INSTRUCTIONS")
    void clearTable();
}