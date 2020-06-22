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
import ro.iteahome.nhs.backend.service.nhs.NurseService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/by-id/{id}")
    public EntityModel<NurseDTO> findById(@PathVariable int id) {
        return nurseService.findById(id);
    }

    @GetMapping("/by-email/{email}")
    public EntityModel<NurseDTO> findByEmail(@PathVariable String email) {
        return nurseService.findByEmail(email);
    }

    @GetMapping("/existence/by-cnp-and-license-number")
    public boolean existsByCnpAndLicenseNo(@RequestParam String cnp, @RequestParam String licenseNo) {
        return nurseService.existsByCnpAndLicenseNo(cnp, licenseNo);
    }

    @PutMapping
    public EntityModel<NurseDTO> update(@RequestBody @Valid Nurse nurse) {
        return nurseService.update(nurse);
    }

    @DeleteMapping("/by-id/{id}")
    public EntityModel<NurseDTO> deleteById(@PathVariable int id) {
        return nurseService.deleteById(id);
    }

    @DeleteMapping("/by-email/{email}")
    public EntityModel<NurseDTO> deleteByEmail(@PathVariable String email) {
        return nurseService.deleteByEmail(email);
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
