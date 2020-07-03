package ro.iteahome.nhs.backend.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalDatabaseException;
import ro.iteahome.nhs.backend.exception.business.GlobalEntityException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.exception.error.GlobalError;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

//  ENTITY EXCEPTIONS: -------------------------------------------------------------------------------------------------

    @ExceptionHandler(GlobalEntityException.class)
    public ResponseEntity<GlobalError> handleGlobalEntityException(GlobalEntityException ex) {
        return new ResponseEntity<>(new GlobalError(ex.getEntityName().substring(0, 3) + "-00", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GlobalNotFoundException.class)
    public ResponseEntity<GlobalError> handleGlobalNotFoundException(GlobalNotFoundException ex) {
        return new ResponseEntity<>(new GlobalError(ex.getEntityName().substring(0, 3) + "-01", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GlobalAlreadyExistsException.class)
    public ResponseEntity<GlobalError> handleGlobalAlreadyExistsException(GlobalAlreadyExistsException ex) {
        return new ResponseEntity<>(new GlobalError(ex.getEntityName().substring(0, 3) + "-02", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GlobalDatabaseException.class)
    public ResponseEntity<GlobalError> handleGlobalDatabaseValidationException(GlobalDatabaseException ex) {
        return new ResponseEntity<>(new GlobalError(ex.getEntityName().substring(0, 3) + "-03", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

// VALIDATION EXCEPTIONS: ----------------------------------------------------------------------------------------------

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> logger.warn("VALIDATION EXCEPTION \'" + fieldError.getDefaultMessage() + "\' " +
                        "FOR FIELD \'" + fieldError.getField() + "\' " +
                        "OCCURRED IN REQUEST SENT BY APPLICATION \'" + request.getRemoteUser() + "\'. " +
                        "STATUS \'" + status + "\'. " +
                        "HEADERS: \'" + headers + "\'."));
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> errors.put("ERROR IN FIELD \'" + fieldError.getField() + "\'", fieldError.getDefaultMessage()));
        return new ResponseEntity<>(
                errors,
                status);
    }
}
