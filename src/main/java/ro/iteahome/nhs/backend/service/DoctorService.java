package ro.iteahome.nhs.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.DoctorController;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
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

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public EntityModel<Doctor> add(Doctor doctor) {
        if (!doctorRepository.existsByEmail(doctor.getEmail())) {
            doctorRepository.save(doctor);
            Doctor savedDoctor = doctorRepository.getByEmail(doctor.getEmail());
            return new EntityModel<>(
                    savedDoctor,
                    linkTo(methodOn(DoctorController.class).findById(savedDoctor.getId())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("DOCTOR");
        }
    }

    public EntityModel<Doctor> findById(int id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            return new EntityModel<>(
                    doctor,
                    linkTo(methodOn(DoctorController.class).findById(id)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public EntityModel<Doctor> findByEmail(String email) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByEmail(email);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            return new EntityModel<>(
                    doctor,
                    linkTo(methodOn(DoctorController.class).findById(doctor.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public EntityModel<Doctor> update(Doctor doctor) {
        if (doctorRepository.existsById(doctor.getId())) {
            doctorRepository.save(doctor);
            Doctor updatedDoctor = doctorRepository.getById(doctor.getId());
            return new EntityModel<>(
                    updatedDoctor,
                    linkTo(methodOn(DoctorController.class).findById(updatedDoctor.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public EntityModel<Doctor> deleteById(int id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctorRepository.delete(doctor);
            return new EntityModel<>(doctor); // TODO: Figure out why this returns an html error and not an EntityModel.
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

    public EntityModel<Doctor> deleteByEmail(String email) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByEmail(email);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctorRepository.delete(doctor);
            return new EntityModel<>(doctor); // TODO: Figure out why this returns an html error and not an EntityModel.
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

// OTHER METHODS: -----------------------------------------------------------------------------------------------------

}
