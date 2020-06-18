package ro.iteahome.nhs.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.AdminController;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.dto.person.AdminCredentials;
import ro.iteahome.nhs.backend.model.dto.person.AdminSafeDTO;
import ro.iteahome.nhs.backend.model.dto.person.AdminSensitiveDTO;
import ro.iteahome.nhs.backend.model.entity.person.Admin;
import ro.iteahome.nhs.backend.repository.AdminRepository;

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

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public EntityModel<AdminSafeDTO> add(Admin admin) {
        if (!adminRepository.existsByEmail(admin.getEmail())) {
            adminRepository.save(modelMapper.map(admin, Admin.class));
            Admin savedAdmin = adminRepository.getByEmail(admin.getEmail());
            AdminSafeDTO adminSafeDTO = modelMapper.map(savedAdmin, AdminSafeDTO.class);
            return new EntityModel<>(
                    adminSafeDTO,
                    linkTo(methodOn(AdminController.class).findById(adminSafeDTO.getId())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("ADMIN");
        }
    }

    public EntityModel<AdminSafeDTO> findById(int id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            AdminSafeDTO adminSafeDTO = modelMapper.map(admin, AdminSafeDTO.class);
            return new EntityModel<>(
                    adminSafeDTO,
                    linkTo(methodOn(AdminController.class).findById(id)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public EntityModel<AdminSafeDTO> findByEmail(String email) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            AdminSafeDTO adminSafeDTO = modelMapper.map(admin, AdminSafeDTO.class);
            return new EntityModel<>(
                    adminSafeDTO,
                    linkTo(methodOn(AdminController.class).findById(adminSafeDTO.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public EntityModel<AdminSensitiveDTO> findByCredentials(AdminCredentials adminCredentials) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmailAndPassword(adminCredentials.getEmail(), adminCredentials.getPassword());
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            AdminSensitiveDTO adminSensitiveDTO = modelMapper.map(admin, AdminSensitiveDTO.class);
            return new EntityModel<>(
                    adminSensitiveDTO,
                    linkTo(methodOn(AdminController.class).findById(admin.getId())).withSelfRel());
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

    public EntityModel<AdminSafeDTO> deleteById(int id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            adminRepository.delete(optionalAdmin.get());
            AdminSafeDTO adminSafeDTO = modelMapper.map(optionalAdmin.get(), AdminSafeDTO.class);
            return new EntityModel<>(adminSafeDTO);
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public EntityModel<AdminSafeDTO> deleteByEmail(String email) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            adminRepository.delete(optionalAdmin.get());
            AdminSafeDTO adminSafeDTO = modelMapper.map(optionalAdmin.get(), AdminSafeDTO.class);
            return new EntityModel<>(adminSafeDTO);
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

// OTHER METHODS: -----------------------------------------------------------------------------------------------------

    public boolean existsByCredentials(String email, String password) {
        return adminRepository.existsByEmailAndPassword(email, password);
    }
}
