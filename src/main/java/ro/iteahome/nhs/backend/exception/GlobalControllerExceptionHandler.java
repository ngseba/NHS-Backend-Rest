package ro.iteahome.nhs.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.exception.error.GlobalError;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

//  ENTITY EXCEPTIONS: -------------------------------------------------------------------------------------------------

    @ExceptionHandler(GlobalNotFoundException.class)
    public ResponseEntity<GlobalError> handleGlobalNotFoundException(GlobalNotFoundException ex) {
        return new ResponseEntity<>(new GlobalError(ex.getRestEntity().substring(0, 3) + "-01", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GlobalAlreadyExistsException.class)
    public ResponseEntity<GlobalError> handleGlobalAlreadyExistsException(GlobalAlreadyExistsException ex) {
        return new ResponseEntity<>(new GlobalError(ex.getEntityName().substring(0, 3) + "-02", ex.getMessage()), HttpStatus.CONFLICT);
    }

// VALIDATION EXCEPTIONS: ----------------------------------------------------------------------------------------------

    // TODO: Find out why this doesn't work here:
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
//        return new ResponseEntity<>(
//                ex.getBindingResult()
//                        .getFieldErrors()
//                        .stream()
//                        .collect(Collectors.toMap(
//                                FieldError::getField,
//                                FieldError::getDefaultMessage)),
//                HttpStatus.BAD_REQUEST);
//    }

// DATABASE VALIDATION EXCEPTIONS: -------------------------------------------------------------------------------------

    // TODO: Find out how to manage database validation exceptions. I'm guessing the answer is stored procedures.

}
