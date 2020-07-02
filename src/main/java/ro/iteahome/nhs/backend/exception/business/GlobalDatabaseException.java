package ro.iteahome.nhs.backend.exception.business;

public class GlobalDatabaseException extends RuntimeException {

    private final String restEntity;

    public GlobalDatabaseException(String restEntity, String originalMessage) {
        super("DATABASE OPERATION FAILED FOR: " + restEntity + ". MESSAGE: " + originalMessage);
        this.restEntity = restEntity;
    }

    public String getRestEntity() {
        return restEntity;
    }
}
