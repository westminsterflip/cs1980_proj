package com.example.medicationadherence.ui.medications.wizard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateFormat;

import androidx.core.os.ConfigurationCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.medicationadherence.R;
import com.example.medicationadherence.adapter.ScheduleTimeAdapter;
import com.example.medicationadherence.data.Converters;
import com.example.medicationadherence.data.room.entities.Doctor;
import com.example.medicationadherence.data.room.entities.Medication;
import com.example.medicationadherence.data.room.entities.Schedule;
import com.example.medicationadherence.ui.MainViewModel;
import com.example.medicationadherence.ui.medications.MedicationViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class RootWizardViewModel extends ViewModel {
    private String medImage = null;
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
    private long sMedID = -1;
    private MainViewModel mainViewModel;
    private ArrayList<Schedule> removed = new ArrayList<>();
    private RootWizardFragment fragment;
    private int listLength = 0;
    private long late;
    private int selPos = 0;
    private AlertDialog doseDialog;
    private ScheduleTimeAdapter scheduleTimeAdapter;

    String getMedImage() {
        return medImage;
    }

    void setMedImage(String medImage) {
        this.medImage = medImage;
    }

    String getMedName() {
        return medName;
    }

    void setMedName(String medName) {
        this.medName = medName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    boolean isActive() {
        return active;
    }

    void setActive(boolean active) {
        this.active = active;
    }

    String getDoctorName() {
        return doctorName;
    }

    void setDoctorName(String doctorName) {
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

    int getOnHand() {
        return onHand;
    }

    void setOnHand(int onHand) {
        this.onHand = onHand;
        containerVol = onHand;
    }

    int getContainerVol() {
        return containerVol;
    }

    double getCost() {
        return cost;
    }

    void setCost(double cost) {
        this.cost = cost;
    }

    boolean isAsNeeded() {
        return asNeeded;
    }

    void setAsNeeded(boolean asNeeded) {
        this.asNeeded = asNeeded;
    }

    public Medication getMedication(){
        return new Medication(medImage, medName, active, doctorID, medDosage, startDate, endDate, containerVol, cost, late);
    }

    Medication getMedicationWID(){
        return new Medication(sMedID, medImage, medName, active, doctorID, medDosage, startDate, endDate, containerVol, cost, late);
    }

    public Doctor getDoctor(){
        return new Doctor(doctorName, practiceName, practiceAddress, phone);
    }

    void setMedDosage(String medDosage) {
        this.medDosage = medDosage;
    }

    public MedicationViewModel getModel() {
        return model;
    }

    MedicationViewModel setModel(MedicationViewModel model) {
        return this.model = model;
    }

    public String getMedDosage() {
        return medDosage;
    }

    public ArrayList<RootWizardFragment.ErrFragment> getThisList() {
        return thisList;
    }

    int getSpinnerSelection() {
        return spinnerSelection;
    }

    void setSpinnerSelection(int spinnerSelection) {
        this.spinnerSelection = spinnerSelection;
    }

    String getPracticeName() {
        return practiceName;
    }

    void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    String getPracticeAddress() {
        return practiceAddress;
    }

    void setPracticeAddress(String practiceAddress) {
        this.practiceAddress = practiceAddress;
    }

    String getPhone() {
        return phone;
    }

    void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Long doctorID) {
        this.doctorID = doctorID;
    }

    @SuppressWarnings("unchecked")
    MutableLiveData<ArrayList<Integer>> getDestinations() {
        if(destinations == null){
            destinations = new MutableLiveData<>();
            Integer[] destsint = {R.id.wizardMedicineDetailFragment, R.id.wizardImageSelector, R.id.wizardDoctorDetailFragment};
            ArrayList dests = new ArrayList<>(Arrays.asList(destsint));
            destinations.setValue(dests);
        }
        return destinations;
    }

    DatePickerDialog getDatePickerDialog() {
        return datePickerDialog;
    }

    void setDatePickerDialog(DatePickerDialog datePickerDialog) {
        this.datePickerDialog = datePickerDialog;
    }

    boolean isScheduleAfter() {
        return scheduleAfter;
    }

    void setScheduleAfter(boolean scheduleAfter) {
        this.scheduleAfter = scheduleAfter;
    }

    public long getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(long scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Schedule> getSchedules() {
        if(schedules == null)
            if(sMedID == -1)
                schedules = new ArrayList<>();
            else
                schedules = (ArrayList)mainViewModel.getScheduleFM(sMedID);
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

    void setNavController(NavController navController) {
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
                removed.add(schedules.remove(i));
            doseEntries.remove(dose);
        }
    }

    public void setDoseNull(){
        doseEntries = null;
    }

    long getsMedID() {
        return sMedID;
    }

    void setsMedID(long sMedID) {
        this.sMedID = sMedID;
    }

//    public void setMedication(String medImage, String medName, boolean active, Long doctorID, String medDosage, long startDate, long endDate, int containerVol, double cost, long late){
//        this.medName = medName;
//        this.active = active;
//        this.doctorID = doctorID;
//        this.medDosage = medDosage;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.containerVol = containerVol;
//        this.cost = cost;
//        this.medImage = medImage;
//        this.late = late;
//    }

    public void setMedication(Medication m){
        medName = m.getName();
        active = m.isStatus();
        doctorID = m.getDoctorID();
        medDosage = m.getDosage();
        startDate = m.getStartDate();
        endDate = m.getEndDate();
        containerVol = m.getContainerVolume();
        cost = m.getCost();
        late = m.getLateTime();
        medImage = m.getMedImageURL();
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    ArrayList<Schedule> getRemoved() {
        return removed;
    }

    public RootWizardFragment getFragment() {
        return fragment;
    }

    public void setFragment(RootWizardFragment fragment) {
        this.fragment = fragment;
    }

    int getListLength() {
        return listLength;
    }

    void setListLength(int listLength) {
        this.listLength = listLength;
    }

    String getLate() {
        String out;
        if (late%3600000 == 0 || late == 0){
            out = TimeUnit.MILLISECONDS.toHours(late) + "hr";
        } else {
            out = TimeUnit.MILLISECONDS.toMinutes(late) + "min";
        }
        System.out.println("OUT: " + out + " LATE: " + late);
        String[] list = fragment.getResources().getStringArray(R.array.lateTimes);
        for (String s : list) {
            if (s.contains(out)) {
                return s;
            }
        }
        return null;
    }

    void setLate(String late) {
        if (late.contains("hr")){
            this.late = TimeUnit.HOURS.toMillis(Integer.parseInt(late.replaceAll("[\\D]", "")));
        } else {
            this.late = TimeUnit.MINUTES.toMillis(Integer.parseInt(late.replaceAll("[\\D]", "")));
        }
        System.out.println("late: " + this.late);
    }

    public void removeSchedules(int days){
        ArrayList<Schedule> temp = new ArrayList<>();
        for (Schedule s : schedules){
            if(Converters.fromBoolArray(s.getWeekdays()) == days)
                temp.add(s);
        }
        schedules.removeAll(temp);
    }

    int getSelPos() {
        return selPos;
    }

    void setSelPos(int selPos) {
        this.selPos = selPos;
    }

    public AlertDialog getDoseDialog() {
        return doseDialog;
    }

    public void setDoseDialog(AlertDialog doseDialog) {
        this.doseDialog = doseDialog;
    }

    public ScheduleTimeAdapter getScheduleTimeAdapter() {
        return scheduleTimeAdapter;
    }

    public void setScheduleTimeAdapter(ScheduleTimeAdapter scheduleTimeAdapter) {
        this.scheduleTimeAdapter = scheduleTimeAdapter;
    }

    public long getLateNS(){
        return late;
    }
}
