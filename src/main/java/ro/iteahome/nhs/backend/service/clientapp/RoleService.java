package ro.iteahome.nhs.backend.service.clientapp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.clientapp.dto.RoleDTO;
import ro.iteahome.nhs.backend.model.clientapp.entity.Role;
import ro.iteahome.nhs.backend.repository.clientapp.RoleRepository;

import javax.persistence.PersistenceException;
import java.util.List;
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

    public List<Role> findAll() throws Exception {
        try {
            return roleRepository.findAll();
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public RoleDTO findByName(String name) throws Exception {
        try {
            Optional<Role> optionalRole = roleRepository.findByName(name);
            if (optionalRole.isPresent()) {
                return modelMapper.map(optionalRole.get(), RoleDTO.class);
            } else {
                throw new GlobalNotFoundException("ROLE");
            }
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public RoleDTO update(Role role) throws Exception {
        try {
            if (roleRepository.existsById(role.getId())) {
                Role savedRole = roleRepository.saveAndFlush(role);
                return modelMapper.map(savedRole, RoleDTO.class);
            } else {
                throw new GlobalNotFoundException("ROLE");
            }
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public RoleDTO deleteByName(String name) throws Exception {
        try {
            Optional<Role> optionalRole = roleRepository.findByName(name);
            if (optionalRole.isPresent()) {
                roleRepository.deleteById(optionalRole.get().getId());
                return modelMapper.map(optionalRole.get(), RoleDTO.class);
            } else {
                throw new GlobalNotFoundException("ROLE");
            }
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
