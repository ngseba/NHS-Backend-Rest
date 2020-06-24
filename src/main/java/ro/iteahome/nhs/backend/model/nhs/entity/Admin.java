package ro.iteahome.nhs.backend.model.nhs.entity;

import ro.iteahome.nhs.backend.model.clientapp.entity.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "admins")
public class Admin {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "EMAIL CANNOT BE EMPTY.")
    @Email(regexp = ".+@.+\\..+", message = "INVALID EMAIL ADDRESS")
    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR(100)")
    private String email;

    @NotNull(message = "PASSWORD CANNOT BE EMPTY.")
    @Pattern(regexp = "((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,100})", message = "INVALID PASSWORD")
    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(110)")
    private String password;

    @NotNull(message = "FIRST NAME CANNOT BE EMPTY.")
    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String firstName;

    @NotNull(message = "LAST NAME CANNOT BE EMPTY.")
    @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String lastName;

    @NotNull(message = "PHONE NUMBER CANNOT BE EMPTY.")
    @Pattern(regexp = "^0040\\d{9}$", message = "INVALID PHONE NUMBER")
    @Column(name = "phone_ro", nullable = false, unique = true, columnDefinition = "VARCHAR(13)")
    private String phoneNoRo;

    @Column(name = "status", nullable = false, columnDefinition = "INT")
    private int status;

    @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(20)")
    private String role;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public Admin() {
//        this.role = "ADMIN";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNoRo() {
        return phoneNoRo;
    }

    public void setPhoneNoRo(String phoneNoRo) {
        this.phoneNoRo = phoneNoRo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
