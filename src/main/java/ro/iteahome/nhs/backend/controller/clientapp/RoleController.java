package ro.iteahome.nhs.backend.controller.clientapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.model.clientapp.dto.RoleDTO;
import ro.iteahome.nhs.backend.model.clientapp.entity.Role;
import ro.iteahome.nhs.backend.repository.clientapp.RoleRepository;
import ro.iteahome.nhs.backend.service.clientapp.RoleService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<RoleDTO> add(@RequestBody @Valid Role role) {
        if (!roleService.existsByName(role.getName())) {
            return roleService.add(role);
        } else {
            throw new GlobalAlreadyExistsException("ROLE");
        }
    }

    @GetMapping("/by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<RoleDTO> findById(@PathVariable int id) {
        return roleService.findById(id);
    }

    @GetMapping("/by-name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<Role> findByName(@PathVariable String name) {
        return roleService.findByName(name);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<RoleDTO> update(@RequestBody @Valid Role role) {
        return roleService.update(role);
    }

    @DeleteMapping("/by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<RoleDTO> deleteById(@PathVariable int id) {
        return roleService.deleteById(id);
    }

    @DeleteMapping("/by-name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<RoleDTO> deleteByName(@PathVariable String name) {
        return roleService.deleteByName(name);
    }

// OTHER METHODS: ------------------------------------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorCode", "ROL-00");
        errors.put("errorMessage", "ROLE APP FIELDS HAVE VALIDATION ERRORS.");
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
