package ro.iteahome.nhs.backend.service.nhs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.nhs.dto.AdminDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Admin;
import ro.iteahome.nhs.backend.repository.nhs.AdminRepository;

import java.util.Optional;

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
        } catch (Exception ex) {
            throw new Exception(ex.getCause().getCause().getMessage());
        }
    }

    public AdminDTO findById(int id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            return modelMapper.map(optionalAdmin.get(), AdminDTO.class);
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO findByEmail(String email) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            return modelMapper.map(optionalAdmin.get(), AdminDTO.class);
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public Admin findSensitiveById(int id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            return optionalAdmin.get();
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public Admin findSensitiveByEmail(String email) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            return optionalAdmin.get();
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO update(Admin admin) throws Exception {
        if (adminRepository.existsById(admin.getId())) {
            try {
                adminRepository.saveAndFlush(admin);
                Admin savedAdmin = adminRepository.getById(admin.getId());
                return modelMapper.map(savedAdmin, AdminDTO.class);
            } catch (Exception ex) {
                throw new Exception(ex.getCause().getCause().getMessage());
            }
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO deleteById(int id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            AdminDTO adminDTO = modelMapper.map(admin, AdminDTO.class);
            adminRepository.delete(admin);
            return adminDTO;
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public AdminDTO deleteByEmail(String email) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            AdminDTO adminDTO = modelMapper.map(admin, AdminDTO.class);
            adminRepository.delete(admin);
            return adminDTO;
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }
}
