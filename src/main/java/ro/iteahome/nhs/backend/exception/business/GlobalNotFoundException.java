package ro.iteahome.nhs.backend.exception.business;

public class GlobalNotFoundException extends RuntimeException {

    private final String entityName;

    public GlobalNotFoundException(String entityName) {
        super(entityName + " NOT FOUND");
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
