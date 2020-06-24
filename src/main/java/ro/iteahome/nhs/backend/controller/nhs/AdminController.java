package ro.iteahome.nhs.backend.controller.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.model.clientapp.entity.Role;
import ro.iteahome.nhs.backend.model.nhs.dto.AdminDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Admin;
import ro.iteahome.nhs.backend.service.clientapp.RoleService;
import ro.iteahome.nhs.backend.service.nhs.AdminService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admins")
public class AdminController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<AdminDTO> add(@RequestBody @Valid Admin admin) {
        Role role = roleService.findByName("ADMIN").getContent();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        admin.setRoles(roles);
        return adminService.add(admin);
    }

    @GetMapping("/by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<AdminDTO> findById(@PathVariable int id) {
        return adminService.findById(id);
    }

    @GetMapping("/by-email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<AdminDTO> findByEmail(@PathVariable String email) {
        return adminService.findByEmail(email);
    }

    @GetMapping("/for-update/by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<Admin> findSensitiveById(@PathVariable int id) {
        return adminService.findSensitiveById(id);
    }

    @GetMapping("/for-update/by-email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<Admin> findSensitiveByEmail(@PathVariable String email) {
        return adminService.findSensitiveByEmail(email);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<Admin> update(@RequestBody Admin admin) {
        return adminService.update(admin);
    }

    @DeleteMapping("/by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<AdminDTO> deleteById(@PathVariable int id) {
        return adminService.deleteById(id);
    }

    @DeleteMapping("/by-email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<AdminDTO> deleteByEmail(@PathVariable String email) {
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
