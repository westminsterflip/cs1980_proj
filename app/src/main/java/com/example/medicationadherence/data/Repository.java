package com.example.medicationadherence.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;

import com.example.medicationadherence.data.room.MedicationDatabase;
import com.example.medicationadherence.data.room.dao.DoctorsDAO;
import com.example.medicationadherence.data.room.dao.InstructionsDAO;
import com.example.medicationadherence.data.room.dao.MedicationDAO;
import com.example.medicationadherence.data.room.dao.MedicationLogDAO;
import com.example.medicationadherence.data.room.dao.ScheduleDAO;
import com.example.medicationadherence.data.room.entities.Doctors;
import com.example.medicationadherence.data.room.entities.Instructions;
import com.example.medicationadherence.data.room.entities.MedicationEntity;
import com.example.medicationadherence.data.room.entities.MedicationLog;
import com.example.medicationadherence.data.room.entities.Schedule;
import com.example.medicationadherence.model.Medication;

import java.util.List;

public class Repository {
    private DoctorsDAO mDoctorDAO;
    private InstructionsDAO mInstructionsDAO;
    private MedicationDAO mMedicationDAO;
    private MedicationLogDAO mMedicationLogDAO;
    private ScheduleDAO mScheduleDAO;
    private MutableLiveData<List<Medication>> medList;

    public Repository(Application application){
        MedicationDatabase medDB = MedicationDatabase.getDatabase(application);
        mDoctorDAO = medDB.getDoctorsDao();
        mInstructionsDAO = medDB.getInstructionsDao();
        mMedicationDAO = medDB.getMedicationDao();
        mMedicationLogDAO = medDB.getMedicationLogDao();
        mScheduleDAO = medDB.getScheduleDao();
        //TODO: fill medList;
    }

    public MutableLiveData<List<Medication>> getMedList(){
        return medList;
    }

    public void insert(Doctors doctor){
        new insertAsyncTask(mDoctorDAO).execute(doctor);
    }

    public void insert(Instructions instructions){
        new insertAsyncTask(mInstructionsDAO).execute(instructions);
    }

    public void insert(MedicationEntity medication){
        new insertAsyncTask(mMedicationDAO).execute(medication);
    }

    public void insert(MedicationLog medicationLog){
        new insertAsyncTask(mMedicationLogDAO).execute(medicationLog);
    }

    public void insert(Schedule schedule){
        new insertAsyncTask(mScheduleDAO).execute(schedule);
    }

    public void deleteAll(){
        new deleteAsyncTask(mDoctorDAO, mInstructionsDAO, mMedicationDAO, mMedicationLogDAO, mScheduleDAO).execute();
    }

    private interface CustomDAO extends Dao{
        void insert(Object o);
    }

    private static class insertAsyncTask extends AsyncTask<Object, Void, Void> {
        private DoctorsDAO doctorsDAO;
        private InstructionsDAO instructionsDAO;
        private MedicationDAO medicationDAO;
        private MedicationLogDAO medicationLogDAO;
        private ScheduleDAO scheduleDAO;

        public insertAsyncTask(InstructionsDAO instructionsDAO) {
            this.instructionsDAO = instructionsDAO;
        }

        public insertAsyncTask(DoctorsDAO doctorsDAO) {
            this.doctorsDAO = doctorsDAO;
        }

        public insertAsyncTask(MedicationDAO medicationDAO) {
            this.medicationDAO = medicationDAO;
        }

        public insertAsyncTask(MedicationLogDAO medicationLogDAO) {
            this.medicationLogDAO = medicationLogDAO;
        }

        public insertAsyncTask(ScheduleDAO scheduleDAO) {
            this.scheduleDAO = scheduleDAO;
        }

        @Override
        protected Void doInBackground(Object... objects) {
            if(doctorsDAO != null){
                doctorsDAO.insert((Doctors) objects[0]);
            } else if (instructionsDAO != null){
                instructionsDAO.insert((Instructions) objects[0]);
            } else if (medicationDAO != null){
                medicationDAO.insert((MedicationEntity) objects[0]);
            } else if (medicationLogDAO != null){
                medicationLogDAO.insert((MedicationLog) objects[0]);
            } else if (scheduleDAO != null){
                scheduleDAO.insert((Schedule) objects[0]);
            }
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private DoctorsDAO doctorsDAO;
        private InstructionsDAO instructionsDAO;
        private MedicationDAO medicationDAO;
        private MedicationLogDAO medicationLogDAO;
        private ScheduleDAO scheduleDAO;

        deleteAsyncTask(DoctorsDAO doctorsDAO, InstructionsDAO instructionsDAO, MedicationDAO medicationDAO, MedicationLogDAO medicationLogDAO, ScheduleDAO scheduleDAO) {
            this.doctorsDAO = doctorsDAO;
            this.instructionsDAO = instructionsDAO;
            this.medicationDAO = medicationDAO;
            this.medicationLogDAO = medicationLogDAO;
            this.scheduleDAO = scheduleDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            doctorsDAO.clearTable();
            instructionsDAO.clearTable();
            medicationDAO.clearTable();
            medicationLogDAO.clearTable();
            scheduleDAO.clearTable();
            return null;
        }
    }
}
