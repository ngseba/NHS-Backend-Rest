package ro.iteahome.nhs.backend.model.entity.medical;

import ro.iteahome.nhs.backend.model.entity.person.Patient;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "diagnostics")
public class Diagnostic { // TODO: Decide if these should be standardized (with all of the complications that would come along with that) and develop it into an entity either way.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @NotNull(message = "DESCRIPTION CANNOT BE EMPTY.")
    @Column(name = "description", nullable = false, columnDefinition = "VARCHAR(255)")
    private String description;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "diagnostics_treatments",
            joinColumns = @JoinColumn(name = "diagnostic_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id"))
    private Set<Treatment> treatments;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Patient patient;

    public Diagnostic() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }
}
