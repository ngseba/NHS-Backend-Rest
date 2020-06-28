package ro.iteahome.nhs.backend.controller.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.model.nhs.dto.DoctorDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Doctor;
import ro.iteahome.nhs.backend.model.nhs.reference.DoctorSpecialty;
import ro.iteahome.nhs.backend.model.nhs.reference.DoctorTitle;
import ro.iteahome.nhs.backend.service.nhs.DoctorService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    DoctorService doctorService;

// METHODS: ------------------------------------------------------------------------------------------------------------

    @PostMapping
    public EntityModel<DoctorDTO> add(@RequestBody @Valid Doctor doctor) { // TODO: Integrate a way to ask for at least one medical institution id when registering a new doctor.
        return doctorService.add(doctor);
    }

    @GetMapping("/by-cnp/{cnp}")
    public EntityModel<DoctorDTO> findByCnp(@PathVariable String cnp) {
        return doctorService.findByCnp(cnp);
    }

    @GetMapping("/by-email/{email}")
    public EntityModel<DoctorDTO> findByEmail(@PathVariable String email) {
        return doctorService.findByEmail(email);
    }

    @GetMapping("/existence/by-cnp")
    public boolean existsByCnpAndLicenseNo(@RequestParam String cnp) {
        return doctorService.existsByCnp(cnp);
    }

    @GetMapping("/retrieve-doctor-title")
    public DoctorTitle[] retrieveDoctorTitle() {
        return DoctorTitle.values();
    }

    @GetMapping("/retrieve-doctor-specialty")
    public DoctorSpecialty[] retrieveDoctorSpecialty() {
        return DoctorSpecialty.values();
    }

    @PutMapping
    public EntityModel<DoctorDTO> update(@RequestBody @Valid Doctor doctor) {
        return doctorService.update(doctor);
    }

    @DeleteMapping("/by-cnp/{cnp}")
    public EntityModel<DoctorDTO> deleteById(@PathVariable String cnp) {
        return doctorService.deleteById(cnp);
    }

    @DeleteMapping("/by-email/{email}")
    public EntityModel<DoctorDTO> deleteByEmail(@PathVariable String email) {
        return doctorService.deleteByEmail(email);
    }

    // OTHER METHODS: --------------------------------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorCode", "DOC-00");
        errors.put("errorMessage", "DOCTOR FIELDS HAVE VALIDATION ERRORS.");
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
