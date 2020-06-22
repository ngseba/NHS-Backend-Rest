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
        if (!nurseRepository.existsByEmail(nurse.getEmail())) {
            nurseRepository.save(nurse);
            Nurse savedNurse = nurseRepository.getByEmail(nurse.getEmail());
            NurseDTO savedNurseDTO = modelMapper.map(savedNurse, NurseDTO.class);
            return new EntityModel<>(
                    savedNurseDTO,
                    linkTo(methodOn(NurseController.class).findById(savedNurseDTO.getId())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("NURSE");
        }
    }

    public EntityModel<NurseDTO> findById(int id) {
        Optional<Nurse> optionalNurse = nurseRepository.findById(id);
        if (optionalNurse.isPresent()) {
            Nurse nurse = optionalNurse.get();
            NurseDTO nurseDTO = modelMapper.map(nurse, NurseDTO.class);
            return new EntityModel<>(
                    nurseDTO,
                    linkTo(methodOn(NurseController.class).findById(id)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

    public EntityModel<NurseDTO> findByEmail(String email) {
        Optional<Nurse> optionalNurse = nurseRepository.findByEmail(email);
        if (optionalNurse.isPresent()) {
            Nurse nurse = optionalNurse.get();
            NurseDTO nurseDTO = modelMapper.map(nurse, NurseDTO.class);
            return new EntityModel<>(
                    nurseDTO,
                    linkTo(methodOn(NurseController.class).findById(nurseDTO.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

    public boolean existsByCnpAndLicenseNo(String cnp, String licenseNo) {
        return nurseRepository.existsByCnpAndLicenseNo(cnp, licenseNo);
    }

    public EntityModel<NurseDTO> update(Nurse nurse) {
        if (nurseRepository.existsById(nurse.getId())) {
            Nurse updatedNurse = nurseRepository.save(nurse);
            NurseDTO updatedNurseDTO = modelMapper.map(updatedNurse, NurseDTO.class);
            return new EntityModel<>(
                    updatedNurseDTO,
                    linkTo(methodOn(NurseController.class).findById(updatedNurseDTO.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

    public EntityModel<NurseDTO> deleteById(int id) {
        Optional<Nurse> optionalNurse = nurseRepository.findById(id);
        if (optionalNurse.isPresent()) {
            Nurse nurse = optionalNurse.get();
            NurseDTO nurseDTO = modelMapper.map(nurse, NurseDTO.class);
            nurseRepository.deleteById(id);
            return new EntityModel<>(nurseDTO);
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

    public EntityModel<NurseDTO> deleteByEmail(String email) {
        Optional<Nurse> optionalNurse = nurseRepository.findByEmail(email);
        if (optionalNurse.isPresent()) {
            Nurse nurse = optionalNurse.get();
            NurseDTO nurseDTO = modelMapper.map(nurse, NurseDTO.class);
            nurseRepository.deleteById(nurse.getId());
            return new EntityModel<>(nurseDTO);
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

// OTHER METHODS: -----------------------------------------------------------------------------------------------------

}
