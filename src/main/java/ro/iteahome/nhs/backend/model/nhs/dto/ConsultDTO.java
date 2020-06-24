package ro.iteahome.nhs.backend.model.nhs.dto;

import java.util.Date;

public class ConsultDTO {
    private Date date;
    private int patientID;
    private int doctorID;
    private int institutionID;
    private String diagnosticDesc;
    private String treatmentDesc;
    private String treatmentSchedule;
    private int minDays;
    private int maxDays;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }

    public String getDiagnosticDesc() {
        return diagnosticDesc;
    }

    public void setDiagnosticDesc(String diagnosticDesc) {
        this.diagnosticDesc = diagnosticDesc;
    }

    public String getTreatmentDesc() {
        return treatmentDesc;
    }

    public void setTreatmentDesc(String treatmentDesc) {
        this.treatmentDesc = treatmentDesc;
    }

    public String getTreatmentSchedule() {
        return treatmentSchedule;
    }

    public void setTreatmentSchedule(String treatmentSchedule) {
        this.treatmentSchedule = treatmentSchedule;
    }

    public int getMinDays() {
        return minDays;
    }

    public void setMinDays(int minDays) {
        this.minDays = minDays;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }
}
