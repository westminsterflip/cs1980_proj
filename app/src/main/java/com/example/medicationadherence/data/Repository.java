package com.example.medicationadherence.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.medicationadherence.data.room.MedicationDatabase;
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
import com.example.medicationadherence.model.Medication;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {
    private DoctorDAO mDoctorDAO;
    private InstructionsDAO mInstructionsDAO;
    private MedicationDAO mMedicationDAO;
    private MedicationLogDAO mMedicationLogDAO;
    private ScheduleDAO mScheduleDAO;
    private LiveData<List<Medication>> medList;
    private LiveData<List<ScheduleDAO.ScheduleCard>> cardList;

    public Repository(Application application){
        MedicationDatabase medDB = MedicationDatabase.getDatabase(application);
        mDoctorDAO = medDB.getDoctorsDao();
        mInstructionsDAO = medDB.getInstructionsDao();
        mMedicationDAO = medDB.getMedicationDao();
        mMedicationLogDAO = medDB.getMedicationLogDao();
        mScheduleDAO = medDB.getScheduleDao();
        //TODO: fill medList;
        cardList = mScheduleDAO.loadScheduled();
    }

    public LiveData<List<ScheduleDAO.ScheduleCard>> getCardList() {
        return cardList;
    }

    public LiveData<List<Medication>> getMedList(){
        return medList;
    }

    public long insert(Doctor doctor){
        try {
            return new InsertAsyncTask(mDoctorDAO).execute(doctor).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Doctor> getDoctors(){
        try {
            return new GetAllDoctorsAsyncTask(mDoctorDAO).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Instructions instructions){
        new InsertAsyncTask(mInstructionsDAO).execute(instructions);
    }

    public void insert(MedicationEntity medication){
        new InsertAsyncTask(mMedicationDAO).execute(medication);
    }

    public void insert(MedicationLog medicationLog){
        new InsertAsyncTask(mMedicationLogDAO).execute(medicationLog);
    }

    public void insert(Schedule schedule){
        new InsertAsyncTask(mScheduleDAO).execute(schedule);
    }

    public void deleteAll(){
        new DeleteAsyncTask(mDoctorDAO, mInstructionsDAO, mMedicationDAO, mMedicationLogDAO, mScheduleDAO).execute();
    }

    public void updateDoctor(Long id, String doctorName, String practiceName, String address, String phone){
        new UpdateDoctorAsyncTask(mDoctorDAO, id, doctorName, practiceName, address, phone).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<Object, Void, Long> {
        private DoctorDAO doctorDAO;
        private InstructionsDAO instructionsDAO;
        private MedicationDAO medicationDAO;
        private MedicationLogDAO medicationLogDAO;
        private ScheduleDAO scheduleDAO;

        public InsertAsyncTask(InstructionsDAO instructionsDAO) {
            this.instructionsDAO = instructionsDAO;
        }

        public InsertAsyncTask(DoctorDAO doctorDAO) {
            this.doctorDAO = doctorDAO;
        }

        public InsertAsyncTask(MedicationDAO medicationDAO) {
            this.medicationDAO = medicationDAO;
        }

        public InsertAsyncTask(MedicationLogDAO medicationLogDAO) {
            this.medicationLogDAO = medicationLogDAO;
        }

        public InsertAsyncTask(ScheduleDAO scheduleDAO) {
            this.scheduleDAO = scheduleDAO;
        }

        @Override
        protected Long doInBackground(Object... objects) {
            if(doctorDAO != null){
                return doctorDAO.insert((Doctor) objects[0]);
            } else if (instructionsDAO != null){
                instructionsDAO.insert((Instructions) objects[0]);
            } else if (medicationDAO != null){
                medicationDAO.insert((MedicationEntity) objects[0]);
            } else if (medicationLogDAO != null){
                medicationLogDAO.insert((MedicationLog) objects[0]);
            } else if (scheduleDAO != null){
                scheduleDAO.insert((Schedule) objects[0]);
            }
            return (long) -1;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private DoctorDAO doctorDAO;
        private InstructionsDAO instructionsDAO;
        private MedicationDAO medicationDAO;
        private MedicationLogDAO medicationLogDAO;
        private ScheduleDAO scheduleDAO;

        DeleteAsyncTask(DoctorDAO doctorDAO, InstructionsDAO instructionsDAO, MedicationDAO medicationDAO, MedicationLogDAO medicationLogDAO, ScheduleDAO scheduleDAO) {
            this.doctorDAO = doctorDAO;
            this.instructionsDAO = instructionsDAO;
            this.medicationDAO = medicationDAO;
            this.medicationLogDAO = medicationLogDAO;
            this.scheduleDAO = scheduleDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            doctorDAO.clearTable();
            instructionsDAO.clearTable();
            medicationDAO.clearTable();
            medicationLogDAO.clearTable();
            scheduleDAO.clearTable();
            return null;
        }
    }

    private static class GetAllDoctorsAsyncTask extends AsyncTask<Void , Void, List<Doctor>>{
        DoctorDAO doctorDAO;

        public GetAllDoctorsAsyncTask(DoctorDAO doctorDAO) {
            this.doctorDAO = doctorDAO;
        }

        @Override
        protected List<Doctor> doInBackground(Void... voids) {
            return doctorDAO.getAllDoctors();
        }
    }

    private static class UpdateDoctorAsyncTask extends AsyncTask<Void, Void, Void>{
        private DoctorDAO doctorDAO;
        private Long id;
        private String doctorName;
        private String practice;
        private String address;
        private String phone;

        public UpdateDoctorAsyncTask(DoctorDAO doctorDAO, Long id, String doctorName, String practice, String address, String phone) {
            this.doctorDAO = doctorDAO;
            this.id = id;
            this.doctorName = doctorName;
            this.practice = practice;
            this.address = address;
            this.phone = phone;
        }

        @Override
        protected Void doInBackground(Void... objects) {
            doctorDAO.update(id, doctorName, practice, address, phone);
            return null;
        }
    }
}
