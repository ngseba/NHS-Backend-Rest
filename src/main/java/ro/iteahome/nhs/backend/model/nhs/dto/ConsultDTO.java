package ro.iteahome.nhs.backend.model.nhs.dto;

import java.util.Date;

public class ConsultDTO {
    private Date date;
    private int patient_cnp;
    private int doctor_cnp;
    private String institution_cui;
    private String diagnostic_desc;
    private String treatment_desc;
    private String treatment_schedule;
    private int min_days;
    private int max_days;

    public ConsultDTO() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPatient_cnp() {
        return patient_cnp;
    }

    public void setPatient_cnp(int patient_cnp) {
        this.patient_cnp = patient_cnp;
    }

    public int getDoctor_cnp() {
        return doctor_cnp;
    }

    public void setDoctor_cnp(int doctor_cnp) {
        this.doctor_cnp = doctor_cnp;
    }

    public String getInstitution_cui() {
        return institution_cui;
    }

    public void setInstitution_cui(String institution_cui) {
        this.institution_cui = institution_cui;
    }

    public String getDiagnostic_desc() {
        return diagnostic_desc;
    }

    public void setDiagnostic_desc(String diagnostic_desc) {
        this.diagnostic_desc = diagnostic_desc;
    }

    public String getTreatment_desc() {
        return treatment_desc;
    }

    public void setTreatment_desc(String treatment_desc) {
        this.treatment_desc = treatment_desc;
    }

    public String getTreatment_schedule() {
        return treatment_schedule;
    }

    public void setTreatment_schedule(String treatment_schedule) {
        this.treatment_schedule = treatment_schedule;
    }

    public int getMin_days() {
        return min_days;
    }

    public void setMin_days(int min_days) {
        this.min_days = min_days;
    }

    public int getMax_days() {
        return max_days;
    }

    public void setMax_days(int max_days) {
        this.max_days = max_days;
    }
}
