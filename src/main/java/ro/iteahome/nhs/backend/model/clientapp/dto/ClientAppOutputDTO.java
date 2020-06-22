package ro.iteahome.nhs.backend.model.clientapp.dto;

import ro.iteahome.nhs.backend.model.clientapp.entity.Role;

import java.util.Set;

public class ClientAppOutputDTO {

    private int id;

    private String name;

    // NO PASSWORD.

    private int status;

    private Set<Role> roles;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public ClientAppOutputDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
