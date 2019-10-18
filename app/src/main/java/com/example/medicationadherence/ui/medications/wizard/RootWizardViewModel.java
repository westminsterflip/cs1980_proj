package com.example.medicationadherence.ui.medications.wizard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateFormat;

import androidx.core.os.ConfigurationCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.medicationadherence.R;
import com.example.medicationadherence.data.Converters;
import com.example.medicationadherence.data.room.entities.Doctor;
import com.example.medicationadherence.data.room.entities.Medication;
import com.example.medicationadherence.data.room.entities.Schedule;
import com.example.medicationadherence.ui.medications.MedicationViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class RootWizardViewModel extends ViewModel {
    private int medImage = -1;
    private String medName;
    private String medDosage;
    private String instructions;
    private boolean active = true;
    private String doctorName;
    private long startDate = -1;
    private long endDate = -1;
    private int onHand = -1;
    private int containerVol = -1;
    private double cost = -1;
    private boolean asNeeded = false;
    private MedicationViewModel model;
    private ArrayList<RootWizardFragment.ErrFragment> thisList = new ArrayList<>();
    private int spinnerSelection = 0;
    private String practiceName;
    private String practiceAddress;
    private String phone;
    private Long doctorID = null;
    private MutableLiveData<ArrayList<Integer>> destinations;
    private DatePickerDialog datePickerDialog;
    private boolean scheduleAfter = false;
    private long scheduleTime = -1;
    private ArrayList<Schedule> schedules;
    private ArrayList<Integer> scheduleDays;
    private NavController navController;
    private ArrayList<String> doseEntries;
    public Context context;

    public int getMedImage() {
        return medImage;
    }

    public void setMedImage(int medImage) {
        this.medImage = medImage;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getOnHand() {
        return onHand;
    }

    public void setOnHand(int onHand) {
        this.onHand = onHand;
        containerVol = onHand;
    }

    public int getContainerVol() {
        return containerVol;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isAsNeeded() {
        return asNeeded;
    }

    public void setAsNeeded(boolean asNeeded) {
        this.asNeeded = asNeeded;
    }

    public Medication getMedication(){
        return new Medication(medName, active, doctorID, medDosage, startDate, endDate, containerVol, cost);
    }

    public Doctor getDoctor(){
        return new Doctor(doctorName, practiceName, practiceAddress, phone);
    }

    public void setMedDosage(String medDosage) {
        this.medDosage = medDosage;
    }

    public MedicationViewModel getModel() {
        return model;
    }

    public MedicationViewModel setModel(MedicationViewModel model) {
        return this.model = model;
    }

    public String getMedDosage() {
        return medDosage;
    }

    public ArrayList<RootWizardFragment.ErrFragment> getThisList() {
        return thisList;
    }

    public int getSpinnerSelection() {
        return spinnerSelection;
    }

    public void setSpinnerSelection(int spinnerSelection) {
        this.spinnerSelection = spinnerSelection;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public String getPracticeAddress() {
        return practiceAddress;
    }

    public void setPracticeAddress(String practiceAddress) {
        this.practiceAddress = practiceAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Long doctorID) {
        this.doctorID = doctorID;
    }

    public MutableLiveData<ArrayList<Integer>> getDestinations() {
        if(destinations == null){
            destinations = new MutableLiveData<>();
            Integer[] destsint = {R.id.wizardMedicineDetailFragment, R.id.wizardDoctorDetailFragment};
            ArrayList dests = new ArrayList<>(Arrays.asList(destsint));
            destinations.setValue(dests);
        }
        return destinations;
    }

    public DatePickerDialog getDatePickerDialog() {
        return datePickerDialog;
    }

    public void setDatePickerDialog(DatePickerDialog datePickerDialog) {
        this.datePickerDialog = datePickerDialog;
    }

    public boolean isScheduleAfter() {
        return scheduleAfter;
    }

    public void setScheduleAfter(boolean scheduleAfter) {
        this.scheduleAfter = scheduleAfter;
    }

    public long getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(long scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public ArrayList<Schedule> getSchedules() {
        if(schedules == null)
            schedules = new ArrayList<>();
        return schedules;
    }

    private void loadScheduleLists(){
        if (scheduleDays == null) scheduleDays = new ArrayList<>();
        scheduleDays.clear();
        for (Schedule s : schedules){
            if (scheduleDays.indexOf(Converters.fromBoolArray(s.getWeekdays())) == -1)
                scheduleDays.add(Converters.fromBoolArray(s.getWeekdays()));
        }
    }

    public ArrayList<Integer> getScheduleDays() {
        loadScheduleLists();
        return scheduleDays;
    }

    public NavController getNavController() {
        return navController;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public ArrayList<String> getDoseEntries(boolean[] days){
        if(doseEntries == null)
            doseEntries = new ArrayList<>();
        for(Schedule s : schedules){
            if (Converters.fromBoolArray(s.getWeekdays()) == Converters.fromBoolArray(days)){
                String st = s.getNumDoses() + " @ ";
                if(DateFormat.is24HourFormat(context))
                    st += new SimpleDateFormat("kk:mm", ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)).format(s.getTime());
                else
                    st += new SimpleDateFormat("hh:mm aa", ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)).format(s.getTime());
                if (doseEntries.indexOf(st) == -1)
                    doseEntries.add(st);
            }
        }
        return doseEntries;
    }

    public void removeTime(boolean[] days, String dose){
        for (int i = schedules.size()-1 ; i >= 0; i--){
            String doseText = dose.split(" ")[0];
            Calendar c = Calendar.getInstance();
            c.clear();
            if(dose.split(" ").length == 4){
                c.set(Calendar.HOUR, Integer.valueOf(dose.split(" ")[2].split(":")[0]));
                c.set(Calendar.MINUTE, Integer.valueOf(dose.split(" ")[2].split(":")[1]));
                c.set(Calendar.AM_PM, dose.split(" ")[3].equals("PM") ? Calendar.PM : Calendar.AM);
            } else {
                c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(dose.split(" ")[2].split(":")[0]));
                c.set(Calendar.MINUTE, Integer.valueOf(dose.split(" ")[2].split(":")[1]));
            }
            long time = c.getTimeInMillis();
            if (schedules.get(i).getWeekdays() == days && schedules.get(i).getTime() == time)
                schedules.remove(i);
            doseEntries.remove(dose);
        }
    }

    public void setDoseNull(){
        doseEntries = null;
    }
}
