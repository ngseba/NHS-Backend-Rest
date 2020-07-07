package ro.iteahome.nhs.backend.controller.clientapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.exception.business.GlobalDatabaseException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.clientapp.dto.RoleDTO;
import ro.iteahome.nhs.backend.model.clientapp.entity.Role;
import ro.iteahome.nhs.backend.repository.clientapp.RoleRepository;
import ro.iteahome.nhs.backend.service.clientapp.RoleService;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


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
    public ResponseEntity<EntityModel<RoleDTO>> add(@RequestBody @Valid Role role) {
        try {
            RoleDTO savedRoleDTO = roleService.add(role);
            EntityModel<RoleDTO> savedRoleDTOEntity = new EntityModel<>(
                    savedRoleDTO,
                    linkTo(methodOn(RoleController.class).findByName(savedRoleDTO.getName())).withSelfRel());
            return new ResponseEntity<>(savedRoleDTOEntity, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ROLE", ex.getCause().getCause().getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    //public CollectionModel<Role> findAll() {
    public List<Role> findAll() {
        try {
            List<Role> roles = roleService.findAll();
//            CollectionModel<Role> rolesEntity = new CollectionModel<>(
//                    roles,
//                    linkTo(methodOn(RoleController.class).findAll()).withSelfRel());
//            return rolesEntity;
              return roles;
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ROLE", ex.getCause().getCause().getMessage());
        }
    }

    @GetMapping("/by-name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<RoleDTO>> findByName(@PathVariable String name) {
        try {
            RoleDTO roleDTO = roleService.findByName(name);
            EntityModel<RoleDTO> roleDTOEntity = new EntityModel<>(
                    roleDTO,
                    linkTo(methodOn(RoleController.class).findByName(name)).withSelfRel());
            return new ResponseEntity<>(roleDTOEntity, HttpStatus.OK);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getEntityName());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ROLE", ex.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<RoleDTO>> update(@RequestBody @Valid Role role) {
        try {
            RoleDTO updatedRoleDTO = roleService.update(role);
            EntityModel<RoleDTO> updatedRoleDTOEntity = new EntityModel<>(
                    updatedRoleDTO,
                    linkTo(methodOn(RoleController.class).findByName(role.getName())).withSelfRel());
            return new ResponseEntity<>(updatedRoleDTOEntity, HttpStatus.CREATED);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getEntityName());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ROLE", ex.getCause().getCause().getMessage());
        }
    }

    @DeleteMapping("/by-name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<RoleDTO>> deleteByName(@PathVariable String name) {
        try {
            RoleDTO deletedRoleDTO = roleService.deleteByName(name);
            EntityModel<RoleDTO> deletedRoleDTOEntity = new EntityModel<>(deletedRoleDTO);
            return new ResponseEntity<>(deletedRoleDTOEntity, HttpStatus.OK);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getEntityName());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("ROLE", ex.getCause().getCause().getMessage());
        }
    }
}
