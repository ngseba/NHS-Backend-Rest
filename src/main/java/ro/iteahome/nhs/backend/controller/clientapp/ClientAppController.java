package ro.iteahome.nhs.backend.controller.clientapp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.model.clientapp.dto.ClientAppCredentials;
import ro.iteahome.nhs.backend.model.clientapp.dto.ClientAppDTO;
import ro.iteahome.nhs.backend.model.clientapp.dto.RoleDTO;
import ro.iteahome.nhs.backend.model.clientapp.entity.ClientApp;
import ro.iteahome.nhs.backend.model.clientapp.entity.Role;
import ro.iteahome.nhs.backend.service.clientapp.ClientAppService;
import ro.iteahome.nhs.backend.service.clientapp.RoleService;

import javax.validation.Valid;

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

    @PostMapping("/with-role-name/{roleName}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> addWithRoleId(@RequestBody @Valid ClientAppCredentials clientAppCredentials, @PathVariable String roleName) throws Exception {
        RoleDTO role = roleService.findByName(roleName);
        return clientAppService.add(clientAppCredentials,role);
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

    @PutMapping("/with-role-name/{roleName}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityModel<ClientAppDTO> update(@RequestBody @Valid ClientApp clientApp, @PathVariable String roleName) {
        return clientAppService.update(clientApp,roleName);
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
