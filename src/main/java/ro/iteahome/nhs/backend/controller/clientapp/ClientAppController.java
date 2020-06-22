package ro.iteahome.nhs.backend.controller.clientapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.clientapp.dto.ClientAppDTO;
import ro.iteahome.nhs.backend.model.clientapp.entity.ClientApp;
import ro.iteahome.nhs.backend.model.clientapp.entity.Role;
import ro.iteahome.nhs.backend.repository.clientapp.RoleRepository;
import ro.iteahome.nhs.backend.service.clientapp.ClientAppService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/client-apps")
public class ClientAppController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    ClientAppService clientAppService;

    @Autowired
    RoleRepository roleRepository;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    @PostMapping("/with-role-id/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> add(@RequestBody @Valid ClientApp clientApp, @PathVariable int roleId) {
        if (!clientAppService.existsByName(clientApp.getName())) {
            Optional<Role> optionalRole = roleRepository.findById(roleId);
            if (optionalRole.isPresent()) {
                Set<Role> roles = new HashSet<>();
                roles.add(optionalRole.get());
                clientApp.setRoles(roles);
                return clientAppService.add(clientApp);
            } else {
                throw new GlobalNotFoundException("ROLE");
            }
        } else {
            throw new GlobalAlreadyExistsException("CLIENT APP");
        }
    }

    @GetMapping("/by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> findById(@PathVariable int id) {
        return clientAppService.findById(id);
    }

//    @GetMapping("/by-name/{name}")
//    public EntityModel<ClientAppDTO> findByName(@PathVariable String name) {
//        return clientAppService.findByName(name);
//    }

//    @PutMapping
//    public EntityModel<ClientApp> update(@RequestBody ClientApp clientApp) {
//        return clientAppService.update(clientApp);
//    }

//    @DeleteMapping("/by-id/{id}")
//    public EntityModel<ClientAppDTO> deleteById(@PathVariable int id) {
//        return clientAppService.deleteById(id);
//    }

//    @DeleteMapping("/by-name/{name}")
//    public EntityModel<ClientAppDTO> deleteByName(@PathVariable String name) {
//        return clientAppService.deleteByName(name);
//    }
}
