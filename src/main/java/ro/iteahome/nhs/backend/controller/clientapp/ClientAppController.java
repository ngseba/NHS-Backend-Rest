package ro.iteahome.nhs.backend.controller.clientapp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.clientapp.dto.ClientAppCredentials;
import ro.iteahome.nhs.backend.model.clientapp.dto.ClientAppDTO;
import ro.iteahome.nhs.backend.model.clientapp.dto.RoleDTO;
import ro.iteahome.nhs.backend.model.clientapp.entity.ClientApp;
import ro.iteahome.nhs.backend.service.clientapp.ClientAppService;
import ro.iteahome.nhs.backend.service.clientapp.RoleService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client-apps")
public class ClientAppController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private ClientAppService clientAppService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    @PostMapping("/with-role-id/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> addWithRoleId(@RequestBody @Valid ClientAppCredentials clientAppCredentials, @PathVariable int roleId) {
        EntityModel<RoleDTO> roleDTO = roleService.findById(roleId);
        if (roleDTO.getContent() != null) {
            return clientAppService.add(clientAppCredentials, roleDTO.getContent());
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    @GetMapping("/by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> findById(@PathVariable int id) {
        return clientAppService.findById(id);
    }

    @GetMapping("/by-name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> findByName(@PathVariable String name) {
        return clientAppService.findByName(name);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> update(@RequestBody @Valid ClientApp clientApp) {
        return clientAppService.update(clientApp);
    }

    @PutMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> updateRole(@RequestParam int clientAppId, @RequestParam int roleId) {
        return clientAppService.updateRole(clientAppId, roleId);
    }

    @DeleteMapping("/by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> deleteById(@PathVariable int id) {
        return clientAppService.deleteById(id);
    }

    @DeleteMapping("/by-name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> deleteByName(@PathVariable String name) {
        return clientAppService.deleteByName(name);
    }

// OTHER METHODS: ------------------------------------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorCode", "CLI-00");
        errors.put("errorMessage", "CLIENT APP FIELDS HAVE VALIDATION ERRORS.");
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
