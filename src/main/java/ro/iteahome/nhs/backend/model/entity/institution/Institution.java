package ro.iteahome.nhs.backend.model.entity.institution;

import ro.iteahome.nhs.backend.repository.reference.institution.InstitutionType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "institutions")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @NotNull(message = "MEDICAL INSTITUTION TYPE CANNOT BE EMPTY.")
    @Column(name = "type", nullable = false, columnDefinition = "VARCHAR(50)")
    private InstitutionType type;

    @NotNull(message = "CUI CANNOT BE EMPTY.")
    @Column(name = "cui", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String cui;

    @NotNull(message = "NAME CANNOT BE EMPTY.")
    @Column(name = "name", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String name;

    @NotNull(message = "ADDRESS CANNOT BE EMPTY.")
    @Column(name = "address", nullable = false, columnDefinition = "VARCHAR(100)")
    private String address;

    @NotNull(message = "PHONE NUMBER CANNOT BE EMPTY.")
    @Pattern(regexp = "^0040\\d{9}$", message = "INVALID PHONE NUMBER")
    @Column(name = "phone_ro", nullable = false, unique = true, columnDefinition = "VARCHAR(13)")
    private String phoneNoRo;

    @NotNull(message = "EMAIL CANNOT BE EMPTY.")
    @Email(regexp = ".+@.+\\.\\w+", message = "INVALID EMAIL ADDRESS")
    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String email;

    @NotNull(message = "WEBSITE CANNOT BE EMPTY.")
    @Column(name = "website", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String website;

    public Institution() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InstitutionType getType() {
        return type;
    }

    public void setType(InstitutionType type) {
        this.type = type;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNoRo() {
        return phoneNoRo;
    }

    public void setPhoneNoRo(String phoneNoRo) {
        this.phoneNoRo = phoneNoRo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
