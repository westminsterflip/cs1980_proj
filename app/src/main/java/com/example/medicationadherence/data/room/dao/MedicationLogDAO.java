package com.example.medicationadherence.data.room.dao;/* MedicationLog entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medicationadherence.data.room.entities.MedicationLog;

import java.util.List;

@Dao
public interface MedicationLogDAO {
    @Insert
    void insert(MedicationLog... medicationLogs);
 
    @Update
    void update(MedicationLog... medicationLogs);
 
    @Delete
    void delete(MedicationLog... medicationLog);

	@Query("SELECT * FROM MEDICATIONLOG")
    List<MedicationLog> getAllMedicationLogs();

    @Query("DELETE FROM MEDICATIONLOG")
    void clearTable();

    @Query("select count(*) from medicationlog where not taken and medicationID = :medicationID and date >= :startDate and date < :endDate")
    int getMissed(Long medicationID, long startDate, long endDate);

    @Query("select count(*) from medicationlog inner join medication on medicationlog.medicationID = medication.medicationID where medicationlog.taken and medicationlog.timeLate <= medication.lateTime and medicationlog.medicationID = :medicationID and date >= :startDate and date < :endDate")
    int getOnTime(Long medicationID, long startDate, long endDate);

    @Query("select count(*) from medicationlog inner join medication on medicationlog.medicationID = medication.medicationID where medicationlog.taken and medicationlog.timeLate > medication.lateTime and medicationlog.medicationID = :medicationID and date >= :startDate and date < :endDate")
    int getLate(Long medicationID, long startDate, long endDate);

    @Query("select min(date) from medicationlog")
    long getEarliestLog();
}