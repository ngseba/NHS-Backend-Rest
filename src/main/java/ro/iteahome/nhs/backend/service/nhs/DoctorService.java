package ro.iteahome.nhs.backend.service.nhs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.nhs.DoctorController;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.nhs.dto.DoctorDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Doctor;
import ro.iteahome.nhs.backend.model.nhs.entity.Institution;
import ro.iteahome.nhs.backend.repository.nhs.DoctorRepository;
import ro.iteahome.nhs.backend.repository.nhs.InstitutionRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DoctorService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    InstitutionRepository institutionRepository;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public EntityModel<DoctorDTO> add(Doctor doctor) {
        if (!doctorRepository.existsByCnp(doctor.getCnp())) {
            doctorRepository.save(doctor);
            Doctor savedDoctor = doctorRepository.getByCnp(doctor.getCnp());
            DoctorDTO savedDoctorDTO = modelMapper.map(savedDoctor, DoctorDTO.class);
            return new EntityModel<>(
                    savedDoctorDTO,
                    linkTo(methodOn(DoctorController.class).findByCnp(savedDoctorDTO.getCnp())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("DOCTOR");
        }
    }

    public EntityModel<DoctorDTO> findByCnp(String cnp) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByCnp(cnp);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
            return new EntityModel<>(
                    doctorDTO,
                    linkTo(methodOn(DoctorController.class).findByCnp(cnp)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public EntityModel<DoctorDTO> findByEmail(String email) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByEmail(email);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
            return new EntityModel<>(
                    doctorDTO,
                    linkTo(methodOn(DoctorController.class).findByCnp(doctorDTO.getCnp())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public boolean existsByCnp(String cnp) {
        return doctorRepository.existsByCnp(cnp);
    }

    public EntityModel<DoctorDTO> update(DoctorDTO doctorDTO) {
        if (doctorRepository.existsByCnp(doctorDTO.getCnp())) {
            Doctor doctor = doctorRepository.getByCnp(doctorDTO.getCnp());
            Set<Institution> newSetInst = new HashSet<>();

            if (doctorDTO.getInstitutionCUIs().isEmpty()) {
                doctor.setInstitutions(newSetInst);
            } else if (!doctorDTO.getInstitutionCUIs().contains(",")) {
                newSetInst.add(institutionRepository.getByCui(doctorDTO.getInstitutionCUIs()));
            } else {
                String[] CUIs = doctorDTO.getInstitutionCUIs().split(",");

                for (String cui : CUIs
                ) {
                    newSetInst.add(institutionRepository.getByCui(cui));
                }
            }
            doctor.setInstitutions(newSetInst);
            doctor.setCnp(doctorDTO.getCnp());
            doctor.setEmail(doctorDTO.getEmail());
            doctor.setFirstName(doctorDTO.getFirstName());
            doctor.setLastName(doctorDTO.getLastName());
            doctor.setLicenseNo(doctorDTO.getLicenseNo());
            doctor.setPhoneNoRo(doctorDTO.getPhoneNoRo());
            doctor.setSpecialties(doctorDTO.getSpecialties());
            doctor.setTitle(doctorDTO.getTitle());

            Doctor updatedDoctor = doctorRepository.save(doctor);
            DoctorDTO updatedDoctorDTO = modelMapper.map(updatedDoctor, DoctorDTO.class);
            return new EntityModel<>(
                    updatedDoctorDTO,
                    linkTo(methodOn(DoctorController.class).findByCnp(updatedDoctorDTO.getCnp())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public EntityModel<DoctorDTO> deleteByCnp(String cnp) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByCnp(cnp);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
            doctorRepository.delete(doctor);
            return new EntityModel<>(doctorDTO);
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

// OTHER METHODS: ------------------------------------------------------------------------------------------------------

    public String institutionsForDoctor(String cnp) {
        Doctor doctor = doctorRepository.getByCnp(cnp);

        return doctor.getInstitutions().stream()
                .map(Institution::getCui)
                .collect(Collectors.joining(","));
    }
}
