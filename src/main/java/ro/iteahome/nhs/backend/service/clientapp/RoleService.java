package ro.iteahome.nhs.backend.service.clientapp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.clientapp.dto.RoleDTO;
import ro.iteahome.nhs.backend.model.clientapp.entity.Role;
import ro.iteahome.nhs.backend.repository.clientapp.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public RoleDTO add(Role role) throws Exception {
        try {
            Role savedRole = roleRepository.saveAndFlush(role);
            return modelMapper.map(savedRole, RoleDTO.class);
        } catch (Exception ex) {
            throw new Exception(ex.getCause().getCause().getMessage());
        }
    }

    public RoleDTO findById(int id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            return modelMapper.map(optionalRole.get(), RoleDTO.class);
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public RoleDTO findByName(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isPresent()) {
            return modelMapper.map(optionalRole.get(), RoleDTO.class);
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public RoleDTO update(Role role) throws Exception {
        if (roleRepository.existsById(role.getId())) {
            try {
                roleRepository.saveAndFlush(role);
                Role savedRole = roleRepository.getById(role.getId());
                return modelMapper.map(savedRole, RoleDTO.class);
            } catch (Exception ex) {
                throw new Exception(ex.getCause().getCause().getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public RoleDTO deleteById(int id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
            roleRepository.delete(role);
            return roleDTO;
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public RoleDTO deleteByName(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
            roleRepository.delete(role);
            return roleDTO;
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }
}
