package ro.iteahome.nhs.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.NurseController;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.entity.person.Nurse;
import ro.iteahome.nhs.backend.repository.NurseRepository;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class NurseService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private NurseRepository nurseRepository;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public EntityModel<Nurse> add(Nurse nurse) {
        if (!nurseRepository.existsByEmail(nurse.getEmail())) {
            nurseRepository.save(nurse);
            Nurse savedNurse = nurseRepository.getByEmail(nurse.getEmail());
            return new EntityModel<>(
                    savedNurse,
                    linkTo(methodOn(NurseController.class).findById(savedNurse.getId())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("NURSE");
        }
    }

    public EntityModel<Nurse> findById(int id) {
        Optional<Nurse> optionalNurse = nurseRepository.findById(id);
        if (optionalNurse.isPresent()) {
            Nurse nurse = optionalNurse.get();
            return new EntityModel<>(
                    nurse,
                    linkTo(methodOn(NurseController.class).findById(id)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

    public EntityModel<Nurse> findByEmail(String email) {
        Optional<Nurse> optionalNurse = nurseRepository.findByEmail(email);
        if (optionalNurse.isPresent()) {
            Nurse nurse = optionalNurse.get();
            return new EntityModel<>(
                    nurse,
                    linkTo(methodOn(NurseController.class).findByEmail(email)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

    public EntityModel<Nurse> update(Nurse nurse) {
        if (nurseRepository.existsById(nurse.getId())) {
            nurseRepository.save(nurse);
            Nurse updatedNurse = nurseRepository.getById(nurse.getId());
            return new EntityModel<>(
                    updatedNurse,
                    linkTo(methodOn(NurseController.class).findById(updatedNurse.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

    public EntityModel<Nurse> deleteById(int id) {
        Optional<Nurse> optionalNurse = nurseRepository.findById(id);
        if (optionalNurse.isPresent()) {
            Nurse nurse = optionalNurse.get();
            nurseRepository.delete(nurse);
            return new EntityModel<>(nurse);
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

    public EntityModel<Nurse> deleteByEmail(String email) {
        Optional<Nurse> optionalNurse = nurseRepository.findByEmail(email);
        if (optionalNurse.isPresent()) {
            Nurse nurse = optionalNurse.get();
            nurseRepository.delete(nurse);
            return new EntityModel<>(nurse);
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

// OTHER METHODS: -----------------------------------------------------------------------------------------------------

}
