package ro.iteahome.nhs.backend.model.clientapp.dto;

public class ClientAppDTO {

    private static final String ROLE_PREFIX = "ROLE_";

    private int id;

    private String name;

    // NO PASSWORD.

    private byte status;

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
}
