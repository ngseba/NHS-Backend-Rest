package ro.iteahome.nhs.backend.model.nhs.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "consults")
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "patient_cnp", referencedColumnName = "cnp", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_cnp", referencedColumnName = "cnp", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "institution_cui", referencedColumnName = "cui", nullable = false)
    private Institution institution;

    @OneToOne
    @JoinColumn(name = "diagnostic_id", referencedColumnName = "id", nullable = false)
    private Diagnostic diagnostic;

    @OneToOne
    @JoinColumn(name = "treatment_id", referencedColumnName = "id")
    private Treatment treatment;

    public Consult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
}
