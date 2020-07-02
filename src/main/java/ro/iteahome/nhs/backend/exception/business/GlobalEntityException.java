package ro.iteahome.nhs.backend.exception.business;

public class GlobalEntityException extends RuntimeException {

    private final String restEntity;

    public GlobalEntityException(String restEntity, String originalMessage) {
        super("INTERNAL ERROR FOR ENTITY: \"" + restEntity + "\". MESSAGE: \"" + originalMessage + "\"");
        this.restEntity = restEntity;
    }

    public String getRestEntity() {
        return restEntity;
    }
}
