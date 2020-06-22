package ro.iteahome.nhs.backend.model.nhs.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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
    @JoinTable(
            name = "consults_patients",
            joinColumns = @JoinColumn(name = "consult_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false, unique = true))
    private Patient patient;

    @ManyToOne
    @JoinTable(
            name = "consults_doctors",
            joinColumns = @JoinColumn(name = "consult_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"))
    private Doctor doctor;

    @ManyToOne
    @JoinTable(
            name = "consults_institutions",
            joinColumns = @JoinColumn(name = "consult_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "institution_id", referencedColumnName = "id"))
    private Institution institution;

    @OneToMany
    @JoinTable(
            name = "consults_diagnostics",
            joinColumns = @JoinColumn(name = "diagnostic_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "consult_id", referencedColumnName = "id"))
    private Set<Diagnostic> diagnostics;

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
}
