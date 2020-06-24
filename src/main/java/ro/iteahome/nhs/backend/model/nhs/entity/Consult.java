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

    @OneToOne
    @JoinColumn(name = "patient_cnp", referencedColumnName = "cnp", nullable = false)
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "doctor_cnp", referencedColumnName = "cnp", nullable = false)
    private Doctor doctor;

    @OneToOne
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
}
