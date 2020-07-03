package ro.iteahome.nhs.backend.controller.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.model.nhs.entity.Institution;
import ro.iteahome.nhs.backend.model.nhs.reference.InstitutionType;
import ro.iteahome.nhs.backend.service.nhs.InstitutionService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/medical-institutions")
public class InstitutionController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    InstitutionService institutionService;

// METHODS: ------------------------------------------------------------------------------------------------------------

    @PostMapping
    public EntityModel<Institution> add(@RequestBody @Valid Institution institution) {
        return institutionService.add(institution);
    }

    @GetMapping("/type")
    public String[] getInstitutionType() {
        return Stream.of(InstitutionType.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @GetMapping("/by-cui/{cui}")
    public EntityModel<Institution> findByCui(@PathVariable String cui) {
        return institutionService.findByCui(cui);
    }

    @PutMapping
    public EntityModel<Institution> update(@RequestBody @Valid Institution institution) {
        return institutionService.update(institution);
    }

    @DeleteMapping("/by-cui/{cui}")
    public EntityModel<Institution> deleteByCui(@PathVariable String cui) {
        return institutionService.deleteByCui(cui);
    }

    // OTHER METHODS: --------------------------------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorCode", "MED-00");
        errors.put("errorMessage", "MEDICAL INSTITUTION FIELDS HAVE VALIDATION ERRORS.");
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
