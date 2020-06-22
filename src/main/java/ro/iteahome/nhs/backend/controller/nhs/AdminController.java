package ro.iteahome.nhs.backend.controller.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.model.nhs.dto.AdminDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Admin;
import ro.iteahome.nhs.backend.service.nhs.AdminService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admins")
public class AdminController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    AdminService adminService;

    @Autowired
    PasswordEncoder passwordEncoder;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    @PostMapping
    public EntityModel<AdminDTO> add(@RequestBody @Valid Admin admin) {
        return adminService.add(admin);
    }

    @GetMapping("/by-id/{id}")
    public EntityModel<AdminDTO> findById(@PathVariable int id) {
        return adminService.findById(id);
    }

    @GetMapping("/by-email/{email}")
    public EntityModel<AdminDTO> findByEmail(@PathVariable String email) {
        System.err.println(passwordEncoder.encode("P@ssW0rd!"));
        return adminService.findByEmail(email);
    }

    @GetMapping("/by-credentials")
    public EntityModel<Admin> findByCredentials(@RequestParam String email, @RequestParam String password) {
        return adminService.findByCredentials(email, password);
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
    public EntityModel<AdminDTO> deleteById(@PathVariable int id) {
        return adminService.deleteById(id);
    }

    @DeleteMapping("/by-email/{email}")
    public EntityModel<AdminDTO> deleteByEmail(@PathVariable String email) {
        return adminService.deleteByEmail(email);
    }

    @DeleteMapping("/by-credentials")
    public void deleteByCredentials(@RequestParam String email, @RequestParam String password) {
        adminService.deleteByCredentials(email, password);
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
