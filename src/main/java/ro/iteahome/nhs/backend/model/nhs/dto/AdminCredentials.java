package ro.iteahome.nhs.backend.model.nhs.dto;

public class AdminCredentials {

    private String email;

    private String password;

    public AdminCredentials() {
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
}
