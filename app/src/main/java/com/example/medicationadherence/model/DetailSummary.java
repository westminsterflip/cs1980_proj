package com.example.medicationadherence.model;

public class DetailSummary {
    private String medName;
    private double percTaken;
    private double percLate;
    //don't need percMissed as it should be sum (also the bar will always be 100% so it doesn't matter

    public DetailSummary(String medName, double percTaken, double percLate) {
        this.medName = medName;
        this.percTaken = percTaken;
        this.percLate = percLate;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public double getPercTaken() {
        return percTaken;
    }

    public void setPercTaken(double percTaken) {
        this.percTaken = percTaken;
    }

    public double getPercLate() {
        return percLate;
    }

    public void setPercLate(double percLate) {
        this.percLate = percLate;
    }
}
