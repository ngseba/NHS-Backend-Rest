package ro.iteahome.nhs.backend.controller.clientapp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.clientapp.dto.ClientAppCredentials;
import ro.iteahome.nhs.backend.model.clientapp.dto.ClientAppDTO;
import ro.iteahome.nhs.backend.model.clientapp.entity.ClientApp;
import ro.iteahome.nhs.backend.model.clientapp.entity.Role;
import ro.iteahome.nhs.backend.repository.clientapp.RoleRepository;
import ro.iteahome.nhs.backend.service.clientapp.ClientAppService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/client-apps")
public class ClientAppController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private ClientAppService clientAppService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    @PostMapping("/with-role-id/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> addWithRoleId(@RequestBody @Valid ClientAppCredentials clientAppCredentials, @PathVariable int roleId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isPresent()) {
            return clientAppService.add(clientAppCredentials, optionalRole.get());
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
    public EntityModel<ClientAppDTO> update(@RequestBody ClientApp clientApp) {
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
}
