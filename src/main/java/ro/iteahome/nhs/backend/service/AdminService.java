package ro.iteahome.nhs.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.AdminController;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.dto.AdminCredentials;
import ro.iteahome.nhs.backend.model.dto.AdminDTO;
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

    public EntityModel<AdminDTO> add(Admin admin) {
        if (!adminRepository.existsByEmail(admin.getEmail())) {
            adminRepository.save(modelMapper.map(admin, Admin.class));
            Admin savedAdmin = adminRepository.getByEmail(admin.getEmail());
            AdminDTO adminDTO = modelMapper.map(savedAdmin, AdminDTO.class);
            return new EntityModel<>(
                    adminDTO,
                    linkTo(methodOn(AdminController.class).add(admin)).withSelfRel());
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
                    linkTo(methodOn(AdminController.class).findByEmail(email)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public EntityModel<Admin> findByCredentials(AdminCredentials adminCredentials) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmailAndPassword(adminCredentials.getEmail(), adminCredentials.getPassword());
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            return new EntityModel<>(
                    admin,
                    linkTo(methodOn(AdminController.class).findByCredentials(adminCredentials)).withSelfRel());
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
                    linkTo(methodOn(AdminController.class).update(updatedAdmin)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public EntityModel<AdminDTO> deleteById(int id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            adminRepository.delete(optionalAdmin.get());
            AdminDTO adminDTO = modelMapper.map(optionalAdmin.get(), AdminDTO.class);
            return new EntityModel<>(adminDTO);
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

    public EntityModel<AdminDTO> deleteByEmail(String email) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
        if (optionalAdmin.isPresent()) {
            adminRepository.delete(optionalAdmin.get());
            AdminDTO adminDTO = modelMapper.map(optionalAdmin.get(), AdminDTO.class);
            return new EntityModel<>(adminDTO);
        } else {
            throw new GlobalNotFoundException("ADMIN");
        }
    }

// OTHER METHODS: -----------------------------------------------------------------------------------------------------

    public boolean existsByCredentials(String email, String password) {
        return adminRepository.existsByEmailAndPassword(email, password);
    }
}
