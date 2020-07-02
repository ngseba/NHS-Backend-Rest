package ro.iteahome.nhs.backend.exception.business;

public class GlobalDatabaseValidationException extends RuntimeException {

    private final String restEntity;

    public GlobalDatabaseValidationException(String restEntity) {
        super("DATABASE ENTRY VALIDATION FAILED FOR: " + restEntity);
        this.restEntity = restEntity;
    }

    public String getRestEntity() {
        return restEntity;
    }
}
