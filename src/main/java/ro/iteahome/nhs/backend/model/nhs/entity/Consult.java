package ro.iteahome.nhs.backend.model.nhs.entity;

import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "fetcher", nullable = false, columnDefinition = "VARCHAR(4000)")
    private String fetcher;

    @ManyToOne
    @JoinColumn(name = "patient_cnp", referencedColumnName = "cnp", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_cnp", referencedColumnName = "cnp", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "institution_cui", referencedColumnName = "cui", nullable = false)
    private Institution institution;


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

    public String getFetcher() {
        return fetcher;
    }

    public void setFetcher(String string) {
        this.fetcher = string;
    }
}
