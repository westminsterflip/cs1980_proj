package com.example.medicationadherence.data.room;

/* Database used to store user information locally
   
   Includes the following tables: 
      -- MedicationEntity
	  -- Instructions
	  -- Schedule
	  -- MedicationLog
	  -- Doctor
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.medicationadherence.data.room.dao.DoctorDAO;
import com.example.medicationadherence.data.room.dao.InstructionsDAO;
import com.example.medicationadherence.data.room.dao.MedicationDAO;
import com.example.medicationadherence.data.room.dao.MedicationLogDAO;
import com.example.medicationadherence.data.room.dao.ScheduleDAO;
import com.example.medicationadherence.data.room.entities.Doctor;
import com.example.medicationadherence.data.room.entities.Instructions;
import com.example.medicationadherence.data.room.entities.MedicationEntity;
import com.example.medicationadherence.data.room.entities.MedicationLog;
import com.example.medicationadherence.data.room.entities.Schedule;

@Database(version = 1, entities = {MedicationEntity.class, Instructions.class, Schedule.class, MedicationLog.class, Doctor.class}, exportSchema = false)
public abstract class MedicationDatabase extends RoomDatabase {
	public abstract MedicationDAO getMedicationDao();
	public abstract MedicationLogDAO getMedicationLogDao();
	public abstract DoctorDAO getDoctorsDao();
	public abstract InstructionsDAO getInstructionsDao();
	public abstract ScheduleDAO getScheduleDao();

	private static volatile MedicationDatabase INSTANCE;

	public static MedicationDatabase getDatabase(final Context context){
		if (INSTANCE == null){
			synchronized (MedicationDatabase.class){
				if (INSTANCE == null){
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MedicationDatabase.class, "medication_database").build();
				}
			}
		}
		return INSTANCE;
	}
}