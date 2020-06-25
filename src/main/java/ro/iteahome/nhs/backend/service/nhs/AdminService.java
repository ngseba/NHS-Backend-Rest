package ro.iteahome.nhs.backend.service.nhs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.nhs.AdminController;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.nhs.dto.AdminCreationDTO;
import ro.iteahome.nhs.backend.model.nhs.dto.AdminDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Admin;
import ro.iteahome.nhs.backend.repository.nhs.AdminRepository;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    public EntityModel<AdminDTO> add(AdminCreationDTO adminCreationDTO) {
        if (doesNotExistByEmail(adminCreationDTO)) {
            Admin admin = buildAdmin(adminCreationDTO);
            Admin savedAdmin = adminRepository.save(admin);
            AdminDTO savedAdminDTO = modelMapper.map(savedAdmin, AdminDTO.class);
            return new EntityModel<>(
                    savedAdminDTO,
                    linkTo(methodOn(AdminController.class).findById(savedAdminDTO.getId())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("ADMIN");
        }
    }

    public EntityModel<AdminDTO> findById(int id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            AdminDTO adminDTO = modelMapper.map(admin, AdminDTO.class);
            return new EntityModel<>(
                    adminDTO,
                    linkTo(methodOn(AdminController.class).findById(id)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public EntityModel<AdminDTO> findByEmail(String email) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            AdminDTO adminDTO = modelMapper.map(admin, AdminDTO.class);
            return new EntityModel<>(
                    adminDTO,
                    linkTo(methodOn(AdminController.class).findById(adminDTO.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public EntityModel<Admin> findSensitiveById(int id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            return new EntityModel<>(
                    admin,
                    linkTo(methodOn(AdminController.class).findSensitiveById(admin.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public EntityModel<Admin> findSensitiveByEmail(String email) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            return new EntityModel<>(
                    admin,
                    linkTo(methodOn(AdminController.class).findSensitiveByEmail(admin.getEmail())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public EntityModel<Admin> update(Admin admin) {
        if (adminRepository.existsById(admin.getId())) {
            adminRepository.save(admin);
            Admin updatedAdmin = adminRepository.getById(admin.getId());
            return new EntityModel<>(
                    updatedAdmin,
                    linkTo(methodOn(AdminController.class).findById(updatedAdmin.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public EntityModel<AdminDTO> deleteById(int id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            AdminDTO adminDTO = modelMapper.map(admin, AdminDTO.class);
            adminRepository.delete(admin);
            return new EntityModel<>(adminDTO);
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public EntityModel<AdminDTO> deleteByEmail(String email) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            AdminDTO adminDTO = modelMapper.map(admin, AdminDTO.class);
            adminRepository.delete(admin);
            return new EntityModel<>(adminDTO);
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

// OTHER METHODS: -----------------------------------------------------------------------------------------------------

    private boolean doesNotExistByEmail(AdminCreationDTO adminCreationDTO) {
        return !adminRepository.existsByEmail(adminCreationDTO.getEmail());
    }

    private Admin buildAdmin(AdminCreationDTO adminCreationDTO) {
        Admin admin = modelMapper.map(adminCreationDTO, Admin.class);
        admin.setPassword(passwordEncoder.encode(adminCreationDTO.getPassword()));
        admin.setStatus(1);
        admin.setRole("ADMIN");
        return admin;
    }
}
