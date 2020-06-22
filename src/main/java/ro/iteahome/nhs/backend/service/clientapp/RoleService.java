package ro.iteahome.nhs.backend.service.clientapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.clientapp.RoleController;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.clientapp.entity.Role;
import ro.iteahome.nhs.backend.repository.clientapp.RoleRepository;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class RoleService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RoleRepository roleRepository;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public EntityModel<Role> add(@Valid Role role) {
        if (!roleRepository.existsByName(role.getName())) {
            Role savedRole = roleRepository.save(role);
            return new EntityModel<>(
                    savedRole,
                    linkTo(methodOn(RoleController.class).findById(savedRole.getId())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("ROLE");
        }
    }

    public EntityModel<Role> findById(int id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            return new EntityModel<>(
                    role,
                    linkTo(methodOn(RoleController.class).findById(role.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public EntityModel<Role> findByName(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            return new EntityModel<>(
                    role,
                    linkTo(methodOn(RoleController.class).findById(role.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public EntityModel<Role> update(@Valid Role role) {
        if (roleRepository.existsById(role.getId())) {
            Role updatedRole = roleRepository.save(role);
            return new EntityModel<>(
                    updatedRole,
                    linkTo(methodOn(RoleController.class).findById(updatedRole.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public EntityModel<Role> deleteById(int id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            roleRepository.delete(role);
            return new EntityModel<>(
                    role,
                    linkTo(methodOn(RoleController.class).findById(role.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public EntityModel<Role> deleteByName(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            roleRepository.delete(role);
            return new EntityModel<>(
                    role,
                    linkTo(methodOn(RoleController.class).findById(role.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}
