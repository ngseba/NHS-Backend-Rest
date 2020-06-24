package ro.iteahome.nhs.backend.model.nhs.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "treatments")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @NotNull(message = "DESCRIPTION CANNOT BE EMPTY.")
    @Column(name = "description", nullable = false, columnDefinition = "VARCHAR(4000)")
    private String description;

    @Column(name = "schedule", columnDefinition = "VARCHAR(255)")
    private String schedule;

    @NotNull(message = "MINIMUM NUMBER OF DAYS CANNOT BE EMPTY.")
    @Column(name = "min_days", nullable = false, columnDefinition = "INT")
    private int minDays;

    @NotNull(message = "MAXIMUM NUMBER OF DAYS CANNOT BE EMPTY.")
    @Column(name = "max_days", nullable = false, columnDefinition = "INT")
    private int maxDays;

    public Treatment() {
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

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
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
