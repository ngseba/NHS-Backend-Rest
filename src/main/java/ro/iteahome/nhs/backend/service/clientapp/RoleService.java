package ro.iteahome.nhs.backend.service.clientapp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.clientapp.dto.RoleDTO;
import ro.iteahome.nhs.backend.model.clientapp.entity.Role;
import ro.iteahome.nhs.backend.repository.clientapp.RoleRepository;

import javax.persistence.PersistenceException;
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
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public RoleDTO findById(int id) throws Exception {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            try {
                return modelMapper.map(optionalRole.get(), RoleDTO.class);
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public RoleDTO findByName(String name) throws Exception {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isPresent()) {
            try {
                return modelMapper.map(optionalRole.get(), RoleDTO.class);
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public RoleDTO update(Role role) throws Exception {
        if (roleRepository.existsById(role.getId())) {
            try {
                Role savedRole = roleRepository.saveAndFlush(role);
                return modelMapper.map(savedRole, RoleDTO.class);
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public RoleDTO deleteById(int id) throws Exception {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            try {
                RoleDTO targetRoleDTO = modelMapper.map(optionalRole.get(), RoleDTO.class);
                roleRepository.deleteById(id);
                return targetRoleDTO;
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }

    public RoleDTO deleteByName(String name) throws Exception {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isPresent()) {
            try {
                RoleDTO targetRoleDTO = modelMapper.map(optionalRole.get(), RoleDTO.class);
                roleRepository.deleteByName(name);
                return targetRoleDTO;
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ROLE");
        }
    }
}
