package ro.iteahome.nhs.backend.model.nhs.dto;

import ro.iteahome.nhs.backend.model.clientapp.entity.Role;

public class AdminDTO {

    private int id;

    private String email;

    // NO PASSWORD.

    private String firstName;

    private String lastName;

    private String phoneNoRo;

    private int status;

    private Role role;

    public AdminDTO() {
        this.role = new Role();
        role.setId(1);
        role.setName("ROLE_ADMIN");
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
}
