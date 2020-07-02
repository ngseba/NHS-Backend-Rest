package ro.iteahome.nhs.backend.controller.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalDatabaseException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
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
    public ResponseEntity<EntityModel<AdminDTO>> add(@RequestBody @Valid Admin admin) {
        try {
            AdminDTO savedAdminDTO = adminService.add(admin);
            EntityModel<AdminDTO> savedAdminDTOEntity = new EntityModel<>(
                    savedAdminDTO,
                    linkTo(methodOn(AdminController.class).findByEmail(savedAdminDTO.getEmail())).withSelfRel());
            return new ResponseEntity<>(savedAdminDTOEntity, HttpStatus.CREATED);
        } catch (GlobalAlreadyExistsException ex) {
            throw new GlobalAlreadyExistsException(ex.getEntityName());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ADMIN", ex.getMessage());
        }
    }

    @GetMapping("/by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<AdminDTO>> findById(@PathVariable int id) {
        try {
            AdminDTO adminDTO = adminService.findById(id);
            EntityModel<AdminDTO> adminDTOEntity = new EntityModel<>(
                    adminDTO,
                    linkTo(methodOn(AdminController.class).findById(id)).withSelfRel());
            return new ResponseEntity<>(adminDTOEntity, HttpStatus.FOUND);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getRestEntity());
        }
    }

    @GetMapping("/by-email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<AdminDTO>> findByEmail(@PathVariable String email) {
        try {
            AdminDTO adminDTO = adminService.findByEmail(email);
            EntityModel<AdminDTO> adminDTOEntity = new EntityModel<>(
                    adminDTO,
                    linkTo(methodOn(AdminController.class).findByEmail(email)).withSelfRel());
            return new ResponseEntity<>(adminDTOEntity, HttpStatus.FOUND);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getRestEntity());
        }
    }

    @GetMapping("/sensitive/by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<Admin>> findSensitiveById(@PathVariable int id) {
        try {
            Admin admin = adminService.findSensitiveById(id);
            EntityModel<Admin> adminEntity = new EntityModel<>(
                    admin,
                    linkTo(methodOn(AdminController.class).findSensitiveById(id)).withSelfRel());
            return new ResponseEntity<>(adminEntity, HttpStatus.FOUND);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getRestEntity());
        }
    }

    @GetMapping("/sensitive/by-email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<Admin>> findSensitiveByEmail(@PathVariable String email) {
        try {
            Admin admin = adminService.findSensitiveByEmail(email);
            EntityModel<Admin> adminEntity = new EntityModel<>(
                    admin,
                    linkTo(methodOn(AdminController.class).findSensitiveByEmail(email)).withSelfRel());
            return new ResponseEntity<>(adminEntity, HttpStatus.FOUND);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getRestEntity());
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<AdminDTO>> update(@RequestBody Admin admin) {
        try {
            AdminDTO updatedAdminDTO = adminService.update(admin);
            EntityModel<AdminDTO> updatedAdminDTOEntity = new EntityModel<>(
                    updatedAdminDTO,
                    linkTo(methodOn(AdminController.class).findById(admin.getId())).withSelfRel());
            return new ResponseEntity<>(updatedAdminDTOEntity, HttpStatus.OK);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getRestEntity());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ADMIN", ex.getMessage());
        }
    }

    @DeleteMapping("/by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<AdminDTO>> deleteById(@PathVariable int id) {
        AdminDTO deletedAdminDTO = adminService.deleteById(id);
        EntityModel<AdminDTO> deletedAdminDTOEntity = new EntityModel<>(deletedAdminDTO);
        return new ResponseEntity<>(deletedAdminDTOEntity, HttpStatus.OK);
    }

    @DeleteMapping("/by-email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<AdminDTO>> deleteByEmail(@PathVariable String email) {
        AdminDTO deletedAdminDTO = adminService.deleteByEmail(email);
        EntityModel<AdminDTO> deletedAdminDTOEntity = new EntityModel<>(deletedAdminDTO);
        return new ResponseEntity<>(deletedAdminDTOEntity, HttpStatus.OK);
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
