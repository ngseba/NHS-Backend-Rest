package ro.iteahome.nhs.backend.model.nhs.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "diagnostics")
public class Diagnostic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @NotNull(message = "DESCRIPTION CANNOT BE EMPTY")
    @Column(name = "description", nullable = false, columnDefinition = "VARCHAR(255)")
    private String description;

    @OneToOne
    @NotNull(message = "DIAGNOSTIC MUST BE LINKED TO A CONSULT.")
    @JoinColumn(name = "consult_id", referencedColumnName = "id", nullable = false)
    private Consult consult;

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

    public Consult getConsult() {
        return consult;
    }

    public void setConsult(Consult consult) {
        this.consult = consult;
    }
}
