package ro.iteahome.nhs.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.PatientController;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.entity.person.Patient;
import ro.iteahome.nhs.backend.repository.PatientRepository;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PatientService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private PatientRepository patientRepository;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public EntityModel<Patient> add(Patient patient) {
        if (!patientRepository.existsByCnp(patient.getCnp())) {
            patientRepository.save(patient);
            Patient savedPatient = patientRepository.getByCnp(patient.getCnp());
            return new EntityModel<>(
                    savedPatient,
                    linkTo(methodOn(PatientController.class).findById(savedPatient.getId())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("PATIENT");
        }
    }

    public EntityModel<Patient> findById(int id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            return new EntityModel<>(
                    patient,
                    linkTo(methodOn(PatientController.class).findById(id)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("PATIENT");
        }
    }

    public EntityModel<Patient> findByCnp(String cnp) {
        Optional<Patient> optionalPatient = patientRepository.findByCnp(cnp);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            return new EntityModel<>(
                    patient,
                    linkTo(methodOn(PatientController.class).findByCnp(cnp)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("PATIENT");
        }
    }

    public EntityModel<Patient> update(Patient patient) {
        if (patientRepository.existsById(patient.getId())) {
            patientRepository.save(patient);
            Patient updatedPatient = patientRepository.getById(patient.getId());
            return new EntityModel<>(
                    updatedPatient,
                    linkTo(methodOn(PatientController.class).findById(updatedPatient.getId())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("PATIENT");
        }
    }

    public EntityModel<Patient> deleteById(int id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patientRepository.delete(patient);
            return new EntityModel<>(patient);
        } else {
            throw new GlobalNotFoundException("PATIENT");
        }
    }

    public EntityModel<Patient> deleteByCnp(String cnp) {
        Optional<Patient> optionalPatient = patientRepository.findByCnp(cnp);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patientRepository.delete(patient);
            return new EntityModel<>(patient);
        } else {
            throw new GlobalNotFoundException("PATIENT");
        }
    }

// OTHER METHODS: -----------------------------------------------------------------------------------------------------

}
