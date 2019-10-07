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
import com.example.medicationadherence.data.room.entities.Medication;
import com.example.medicationadherence.data.room.entities.MedicationLog;
import com.example.medicationadherence.data.room.entities.Schedule;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {
    private DoctorDAO mDoctorDAO;
    private InstructionsDAO mInstructionsDAO;
    private MedicationDAO mMedicationDAO;
    private MedicationLogDAO mMedicationLogDAO;
    private ScheduleDAO mScheduleDAO;
    private LiveData<List<ScheduleDAO.ScheduleCard>> cardList;

    public Repository(Application application){
        MedicationDatabase medDB = MedicationDatabase.getDatabase(application);
        mDoctorDAO = medDB.getDoctorsDao();
        mInstructionsDAO = medDB.getInstructionsDao();
        mMedicationDAO = medDB.getMedicationDao();
        mMedicationLogDAO = medDB.getMedicationLogDao();
        mScheduleDAO = medDB.getScheduleDao();
        cardList = mScheduleDAO.loadScheduled();
    }

    public LiveData<List<ScheduleDAO.ScheduleCard>> getCardList() {
        return cardList;
    }

    public List<Medication> getMedList(){
        try {
            return new GetMedListTask(mMedicationDAO).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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

    public long insert(Medication medication){
        try {
            return new InsertAsyncTask(mMedicationDAO).execute(medication).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
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

    public List<Doctor> getDocWithName(String name){
        try {
            return new GWNAsyncTask(mDoctorDAO).execute(name).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateDoctor(Long id, String doctorName, String practiceName, String address, String phone){
        new UpdateDoctorAsyncTask(mDoctorDAO, id, doctorName, practiceName, address, phone).execute();
    }

    public Doctor getDocWithID(Long doctorID){
        try {
            return (Doctor)new GetWIDTask(mDoctorDAO).execute(doctorID).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getInstWithID(Long medicationID){
        try {
            return (String)new GetWIDTask(mInstructionsDAO).execute(medicationID).get();
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public Medication getMedWithID(Long medicationID){
        try {
            return (Medication)new GetWIDTask(mMedicationDAO).execute(medicationID).get();
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    private static class GWNAsyncTask extends AsyncTask<String, Void, List<Doctor>>{
        private DoctorDAO doctorDAO;

        public GWNAsyncTask(DoctorDAO doctorDAO) {
            this.doctorDAO = doctorDAO;
        }

        @Override
        protected List<Doctor> doInBackground(String... strings) {
            return doctorDAO.getWithName(strings[0]);
        }
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
                return medicationDAO.insert((Medication) objects[0]);
            } else if (medicationLogDAO != null){
                medicationLogDAO.insert((MedicationLog) objects[0]);
            } else if (scheduleDAO != null){
                scheduleDAO.insert((Schedule) objects[0]);
            }
            return (long) -1;
        }
    }

    private static class GetWIDTask extends AsyncTask<Object, Void, Object> {
        private DoctorDAO doctorDAO;
        private InstructionsDAO instructionsDAO;
        private MedicationDAO medicationDAO;
        private MedicationLogDAO medicationLogDAO;
        private ScheduleDAO scheduleDAO;

        public GetWIDTask(DoctorDAO doctorDAO) {
            this.doctorDAO = doctorDAO;
        }

        public GetWIDTask(InstructionsDAO instructionsDAO) {
            this.instructionsDAO = instructionsDAO;
        }

        public GetWIDTask(MedicationDAO medicationDAO){
            this.medicationDAO = medicationDAO;
        }

        @Override
        protected Object doInBackground(Object... objects) {
            Object lng = objects[0];
            if (doctorDAO != null) {
                return doctorDAO.getWithID((Long) lng);
            } else if (instructionsDAO != null) {
                return instructionsDAO.getInstWithID((Long) lng);
            } else if (medicationDAO != null) {
                return medicationDAO.getMedWithID((Long) lng);
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

    private static class GetMedListTask extends AsyncTask<Void, Void, List<Medication>>{
        private MedicationDAO medicationDAO;

        public GetMedListTask(MedicationDAO medicationDAO) {
            this.medicationDAO = medicationDAO;
        }

        @Override
        protected List<Medication> doInBackground(Void... voids) {
            return medicationDAO.getAllMedications();
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
