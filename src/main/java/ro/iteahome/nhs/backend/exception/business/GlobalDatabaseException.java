package ro.iteahome.nhs.backend.exception.business;

public class GlobalDatabaseException extends RuntimeException {

    private final String restEntity;

    public GlobalDatabaseException(String restEntity, Throwable exception) {
        super("DATABASE OPERATION FAILED FOR: " + restEntity + ". MESSAGE: " + exception.getMessage());
        this.restEntity = restEntity;
    }

    public String getRestEntity() {
        return restEntity;
    }
}
