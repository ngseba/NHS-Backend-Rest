package ro.iteahome.nhs.backend.exception.business;

public class GlobalServiceException extends RuntimeException {

    private final String restEntity;

    public GlobalServiceException(String restEntity) {
        super("INTERNAL SERVER ERROR FOR: " + restEntity);
        this.restEntity = restEntity;
    }

    public String getRestEntity() {
        return restEntity;
    }
}
