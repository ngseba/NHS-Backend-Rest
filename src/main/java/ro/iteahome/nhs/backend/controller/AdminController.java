package ro.iteahome.nhs.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.model.dto.person.AdminCredentials;
import ro.iteahome.nhs.backend.model.dto.person.AdminSafeDTO;
import ro.iteahome.nhs.backend.model.dto.person.AdminSensitiveDTO;
import ro.iteahome.nhs.backend.model.entity.person.Admin;
import ro.iteahome.nhs.backend.service.AdminService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admins")
public class AdminController {

    // DEPENDENCIES: ---------------------------------------------------------------------------------------------------

    @Autowired
    AdminService adminService;


    // C.R.U.D. METHODS: -----------------------------------------------------------------------------------------------

    @PostMapping
    public EntityModel<AdminSafeDTO> add(@RequestBody @Valid Admin admin) {
        return adminService.add(admin);
    }

    @GetMapping("/by-id/{id}")
    public EntityModel<AdminSafeDTO> findById(@PathVariable int id) {
        return adminService.findById(id);
    }

    @GetMapping("/by-email/{email}")
    public EntityModel<AdminSafeDTO> findByEmail(@PathVariable String email) {
        return adminService.findByEmail(email);
    }

    @GetMapping("/by-credentials")
    public EntityModel<AdminSensitiveDTO> findByCredentials(@RequestBody AdminCredentials adminCredentials) {
        return adminService.findByCredentials(adminCredentials);
    }

    @GetMapping("/existence")
    public boolean existsByCredentials(@RequestParam String email, @RequestParam String password) {
        return adminService.existsByCredentials(email, password);
    }

    @PutMapping
    public EntityModel<Admin> update(@RequestBody Admin admin) {
        return adminService.update(admin);
    }

    @DeleteMapping("/by-id/{id}")
    public EntityModel<AdminSafeDTO> deleteById(@PathVariable int id) {
        return adminService.deleteById(id);
    }

    @DeleteMapping("/by-email/{email}")
    public EntityModel<AdminSafeDTO> deleteByEmail(@PathVariable String email) {
        return adminService.deleteByEmail(email);
    }

    // OTHER METHODS: --------------------------------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorCode", "ADM-00");
        errors.put("errorMessage", "ADMIN FIELDS HAVE VALIDATION ERRORS.");
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
