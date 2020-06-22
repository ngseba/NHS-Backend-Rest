package ro.iteahome.nhs.backend.controller.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.model.nhs.dto.PatientDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Patient;
import ro.iteahome.nhs.backend.service.nhs.PatientService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patients")
public class PatientController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    PatientService patientService;

// METHODS: ------------------------------------------------------------------------------------------------------------

    @PostMapping
    public EntityModel<PatientDTO> add(@RequestBody @Valid Patient patient) {
        return patientService.add(patient);
    }

    @GetMapping("/by-id/{id}")
    public EntityModel<PatientDTO> findById(@PathVariable int id) {
        return patientService.findById(id);
    }

    @GetMapping("/by-cnp/{cnp}")
    public EntityModel<PatientDTO> findByCnp(@PathVariable String cnp) {
        return patientService.findByCnp(cnp);
    }

    @PutMapping
    public EntityModel<PatientDTO> update(@RequestBody @Valid Patient patient) {
        return patientService.update(patient);
    }

    @DeleteMapping("/by-id/{id}")
    public EntityModel<PatientDTO> deleteById(@PathVariable int id) {
        return patientService.deleteById(id);
    }

    @DeleteMapping("/by-cnp/{cnp}")
    public EntityModel<PatientDTO> deleteByCnp(@PathVariable String cnp) {
        return patientService.deleteByCnp(cnp);
    }

    // OTHER METHODS: --------------------------------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorCode", "PAC-00");
        errors.put("errorMessage", "PATIENT FIELDS HAVE VALIDATION ERRORS.");
        errors.putAll(ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage)));
        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST);
    }
}
