package ro.iteahome.nhs.backend.exception.business;

public class GlobalDatabaseException extends RuntimeException {

    private final String entityName;

    public GlobalDatabaseException(String entityName, String originalMessage) {
        super("DATABASE OPERATION FAILED FOR: " + entityName + ". MESSAGE: " + originalMessage);
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
