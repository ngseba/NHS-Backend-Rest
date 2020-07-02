package ro.iteahome.nhs.backend.exception.business;

public class GlobalNotFoundException extends RuntimeException {

    private final String restEntity;

    public GlobalNotFoundException(String entityName) {
        super(entityName + " NOT FOUND.");
        this.restEntity = entityName;
    }

    public String getRestEntity() {
        return restEntity;
    }
}
