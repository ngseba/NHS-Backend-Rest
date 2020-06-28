package ro.iteahome.nhs.backend.controller.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.model.nhs.dto.NurseDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Nurse;
import ro.iteahome.nhs.backend.model.nhs.reference.DoctorSpecialty;
import ro.iteahome.nhs.backend.model.nhs.reference.NurseSpecialty;
import ro.iteahome.nhs.backend.model.nhs.reference.NurseTitle;
import ro.iteahome.nhs.backend.service.nhs.NurseService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/nurses")
public class NurseController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    NurseService nurseService;

// METHODS: ------------------------------------------------------------------------------------------------------------

    @PostMapping
    public EntityModel<NurseDTO> add(@RequestBody @Valid Nurse nurse) { // TODO: Integrate a way to ask for at least one medical institution id when registering a new doctor.
        return nurseService.add(nurse);
    }

    @GetMapping("/by-email/{email}")
    public EntityModel<NurseDTO> findByEmail(@PathVariable String email) {
        return nurseService.findByEmail(email);
    }

    @GetMapping("/existence/by-cnp/{cnp}")
    public boolean existsByCnpAndLicenseNo(@PathVariable String cnp) {
        return nurseService.existsByCnp(cnp);
    }

    @GetMapping("/by-cnp/{cnp}")
    public EntityModel<NurseDTO> findByCnp(@PathVariable String cnp) {
        return nurseService.findByCnp(cnp);
    }

    @GetMapping("/title")
    public String[] getNurseTitle() {
          return Stream.of(NurseTitle.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @GetMapping("/specialty")
    public String[] getNurseSpecialty() {
        return Stream.of(NurseSpecialty.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @PutMapping
    public EntityModel<NurseDTO> update(@RequestBody @Valid Nurse nurse) {
        return nurseService.update(nurse);
    }

    @DeleteMapping("/by-cnp/{cnp}")
    public EntityModel<NurseDTO> deleteByCnp(@PathVariable String cnp) {
        return nurseService.deleteByCnp(cnp);
    }

    // OTHER METHODS: --------------------------------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorCode", "NUR-00");
        errors.put("errorMessage", "NURSE FIELDS HAVE VALIDATION ERRORS.");
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
