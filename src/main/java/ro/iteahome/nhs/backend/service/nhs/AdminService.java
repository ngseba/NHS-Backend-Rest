package ro.iteahome.nhs.backend.service.nhs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.nhs.dto.AdminDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Admin;
import ro.iteahome.nhs.backend.repository.nhs.AdminRepository;

import javax.persistence.PersistenceException;
import java.util.Optional;

@Service
public class AdminService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public AdminDTO add(Admin admin) throws Exception {
        try {
            Admin savedAdmin = adminRepository.saveAndFlush(admin);
            return modelMapper.map(savedAdmin, AdminDTO.class);
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
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

    public AdminDTO deleteByEmail(String email) throws Exception {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            try {
                adminRepository.deleteById(optionalAdmin.get().getId());
                return modelMapper.map(optionalAdmin.get(), AdminDTO.class);
            } catch (PersistenceException ex) {
                throw new Exception(ex.getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }
}
