package ro.iteahome.nhs.backend.exception.business;

public class GlobalEntityException extends RuntimeException {

    private final String entityName;

    public GlobalEntityException(String entityName, String originalMessage) {
        super("INTERNAL ERROR FOR ENTITY: \"" + entityName + "\". MESSAGE: \"" + originalMessage + "\"");
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
