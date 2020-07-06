package ro.iteahome.nhs.backend.service.nhs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.nhs.dto.AdminDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Admin;
import ro.iteahome.nhs.backend.repository.nhs.AdminRepository;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public AdminDTO add(Admin admin) throws Exception {
        try {
            Admin savedAdmin = adminRepository.saveAndFlush(admin);
            return modelMapper.map(savedAdmin, AdminDTO.class);
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List<AdminDTO> findAll() throws Exception {
        try {
            List<Admin> adminList = new ArrayList<>(adminRepository.findAll());
            return adminList.stream().map(admin -> modelMapper.map(admin, AdminDTO.class)).collect(Collectors.toList());
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public AdminDTO findById(int id) throws Exception {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            try {
                return modelMapper.map(optionalAdmin.get(), AdminDTO.class);
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO findByEmail(String email) throws Exception {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            try {
                return modelMapper.map(optionalAdmin.get(), AdminDTO.class);
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public Admin findSensitiveById(int id) throws Exception {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            try {
                return optionalAdmin.get();
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public Admin findSensitiveByEmail(String email) throws Exception {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            try {
                return optionalAdmin.get();
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO update(Admin admin) throws Exception {
        if (adminRepository.existsById(admin.getId())) {
            try {
                Admin savedAdmin = adminRepository.saveAndFlush(admin);
                return modelMapper.map(savedAdmin, AdminDTO.class);
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO deleteById(int id) throws Exception {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            try {
                AdminDTO targetAdminDTO = modelMapper.map(optionalAdmin.get(), AdminDTO.class);
                adminRepository.deleteById(id);
                return targetAdminDTO;
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO deleteByEmail(String email) throws Exception {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            try {
                AdminDTO targetAdminDTO = modelMapper.map(optionalAdmin.get(), AdminDTO.class);
                adminRepository.deleteByEmail(email);
                return targetAdminDTO;
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }
}
