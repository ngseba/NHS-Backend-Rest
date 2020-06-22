package ro.iteahome.nhs.backend.model.clientapp.dto;

import ro.iteahome.nhs.backend.model.clientapp.entity.Role;

import java.util.Set;

public class ClientAppDTO {

    private int id;

    private String name;

    // NO PASSWORD.

    private byte status;

    private Set<Role> roles;

// METHODS: ------------------------------------------------------------------------------------------------------------


    public ClientAppDTO() {
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

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
