package ro.iteahome.nhs.backend.model.clientapp.dto;

public class RoleDTO {

    private int id;

    private String name;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public RoleDTO() {
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

    @Override
    public String toString() {
        return name;
    }
}
