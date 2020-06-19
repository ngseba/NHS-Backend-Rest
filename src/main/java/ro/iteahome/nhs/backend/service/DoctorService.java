package ro.iteahome.nhs.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.DoctorController;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.dto.person.DoctorDTO;
import ro.iteahome.nhs.backend.model.entity.person.Doctor;
import ro.iteahome.nhs.backend.repository.DoctorRepository;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DoctorService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ModelMapper modelMapper;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public EntityModel<DoctorDTO> add(Doctor doctor) {
        if (!doctorRepository.existsByEmail(doctor.getEmail())) {
            doctorRepository.save(doctor);
            Doctor savedDoctor = doctorRepository.getByEmail(doctor.getEmail());
            DoctorDTO savedDoctorDTO = modelMapper.map(savedDoctor, DoctorDTO.class);
            return new EntityModel<>(
                    savedDoctorDTO,
                    linkTo(methodOn(DoctorController.class).findById(savedDoctorDTO.getId())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("DOCTOR");
        }
    }

    public EntityModel<DoctorDTO> findById(int id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
            return new EntityModel<>(
                    doctorDTO,
                    linkTo(methodOn(DoctorController.class).findById(id)).withSelfRel());
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
                    linkTo(methodOn(DoctorController.class).findById(doctorDTO.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public boolean existsByCnpAndLicenseNo(String cnp, String licenseNo) {
        return doctorRepository.existsByCnpAndLicenseNo(cnp, licenseNo);
    }

    public EntityModel<DoctorDTO> update(Doctor doctor) {
        if (doctorRepository.existsById(doctor.getId())) {
            Doctor updatedDoctor = doctorRepository.save(doctor);
            DoctorDTO updatedDoctorDTO = modelMapper.map(updatedDoctor, DoctorDTO.class);
            return new EntityModel<>(
                    updatedDoctorDTO,
                    linkTo(methodOn(DoctorController.class).findById(updatedDoctorDTO.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public EntityModel<DoctorDTO> deleteById(int id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
            doctorRepository.deleteById(id);
            return new EntityModel<>(doctorDTO);
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public EntityModel<DoctorDTO> deleteByEmail(String email) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByEmail(email);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
            doctorRepository.deleteById(doctor.getId());
            return new EntityModel<>(doctorDTO);
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

// OTHER METHODS: -----------------------------------------------------------------------------------------------------

}
