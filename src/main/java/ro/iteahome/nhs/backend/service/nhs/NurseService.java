package ro.iteahome.nhs.backend.service.nhs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.nhs.NurseController;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.nhs.dto.NurseDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Nurse;
import ro.iteahome.nhs.backend.repository.nhs.NurseRepository;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class NurseService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private ModelMapper modelMapper;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public EntityModel<NurseDTO> add(Nurse nurse) {
        if (!nurseRepository.existsByCnp(nurse.getCnp())) {
            nurseRepository.save(nurse);
            Nurse savedNurse = nurseRepository.getByCnp(nurse.getCnp());
            NurseDTO savedNurseDTO = modelMapper.map(savedNurse, NurseDTO.class);
            return new EntityModel<>(
                    savedNurseDTO,
                    linkTo(methodOn(NurseController.class).findByCnp(savedNurseDTO.getCnp())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("NURSE");
        }
    }

    public EntityModel<NurseDTO> findByEmail(String email) {
        Optional<Nurse> optionalNurse = nurseRepository.findByEmail(email);
        if (optionalNurse.isPresent()) {
            Nurse nurse = optionalNurse.get();
            NurseDTO nurseDTO = modelMapper.map(nurse, NurseDTO.class);
            return new EntityModel<>(
                    nurseDTO,
                    linkTo(methodOn(NurseController.class).findByEmail(nurseDTO.getEmail())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

    public EntityModel<NurseDTO> findByCnp(String cnp) {
        Optional<Nurse> optionalNurse = nurseRepository.findByCnp(cnp);
        if (optionalNurse.isPresent()) {
            Nurse nurse = optionalNurse.get();
            NurseDTO nurseDTO = modelMapper.map(nurse, NurseDTO.class);
            return new EntityModel<>(
                    nurseDTO,
                    linkTo(methodOn(NurseController.class).findByCnp(nurseDTO.getCnp())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

    public boolean existsByCnp(String cnp) {
        return nurseRepository.existsByCnp(cnp);
    }

    public EntityModel<NurseDTO> update(Nurse nurse) {
        if (nurseRepository.existsByCnp(nurse.getCnp())) {
            Nurse updatedNurse = nurseRepository.save(nurse);
            NurseDTO updatedNurseDTO = modelMapper.map(updatedNurse, NurseDTO.class);
            return new EntityModel<>(
                    updatedNurseDTO,
                    linkTo(methodOn(NurseController.class).findByCnp(updatedNurseDTO.getCnp())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

    public EntityModel<NurseDTO> deleteByCnp(String cnp) {
        Optional<Nurse> optionalNurse = nurseRepository.findByCnp(cnp);
        if (optionalNurse.isPresent()) {
            Nurse nurse = optionalNurse.get();
            NurseDTO nurseDTO = modelMapper.map(nurse, NurseDTO.class);
            nurseRepository.delete(nurse);
            return new EntityModel<>(nurseDTO);
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

// OTHER METHODS: -----------------------------------------------------------------------------------------------------

}
