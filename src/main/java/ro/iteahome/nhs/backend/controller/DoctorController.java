package ro.iteahome.nhs.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.model.dto.person.DoctorDTO;
import ro.iteahome.nhs.backend.model.entity.person.Doctor;
import ro.iteahome.nhs.backend.service.DoctorService;

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

    @GetMapping("/by-id/{id}")
    public EntityModel<DoctorDTO> findById(@PathVariable int id) {
        return doctorService.findById(id);
    }

    @GetMapping("/by-email/{email}")
    public EntityModel<DoctorDTO> findByEmail(@PathVariable String email) {
        return doctorService.findByEmail(email);
    }

    @GetMapping("/existence/by-cnp-and-license-number")
    public boolean existsByCnpAndLicenseNo(@RequestParam String cnp, @RequestParam String licenseNo) {
        return doctorService.existsByCnpAndLicenseNo(cnp, licenseNo);
    }

    @PutMapping
    public EntityModel<DoctorDTO> update(@RequestBody @Valid Doctor doctor) {
        return doctorService.update(doctor);
    }

    @DeleteMapping("/by-id/{id}")
    public EntityModel<DoctorDTO> deleteById(@PathVariable int id) {
        return doctorService.deleteById(id);
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
