package ro.iteahome.nhs.backend.controller.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.exception.business.GlobalDatabaseException;
import ro.iteahome.nhs.backend.model.nhs.dto.AdminDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Admin;
import ro.iteahome.nhs.backend.service.clientapp.RoleService;
import ro.iteahome.nhs.backend.service.nhs.AdminService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<EntityModel<AdminDTO>> add(@RequestBody @Valid Admin admin) throws Exception {
        try {
            AdminDTO savedAdminDTO = adminService.add(admin);
            EntityModel<AdminDTO> savedAdminDTOEntity = new EntityModel<>(
                    savedAdminDTO,
                    linkTo(methodOn(AdminController.class).findByEmail(savedAdminDTO.getEmail())).withSelfRel());
            return new ResponseEntity<>(savedAdminDTOEntity, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ADMIN", ex.getMessage());
        }
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

    @GetMapping("/sensitive/by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<Admin> findSensitiveById(@PathVariable int id) {
        return adminService.findSensitiveById(id);
    }

    @GetMapping("/sensitive/by-email/{email}")
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
        errors.put("errorCode", "ADM-04");
        errors.put("errorMessage", "ADMIN FIELDS HAVE VALIDATION ERRORS:");
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
