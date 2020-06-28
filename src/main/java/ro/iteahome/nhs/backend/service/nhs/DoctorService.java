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
import ro.iteahome.nhs.backend.repository.nhs.DoctorRepository;

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

    public EntityModel<DoctorDTO> update(Doctor doctor) {
        if (doctorRepository.existsByCnp(doctor.getCnp())) {
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

    public EntityModel<DoctorDTO> deleteByEmail(String email) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByEmail(email);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
            doctorRepository.delete(doctor);
            return new EntityModel<>(doctorDTO);
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

// OTHER METHODS: -----------------------------------------------------------------------------------------------------

}
