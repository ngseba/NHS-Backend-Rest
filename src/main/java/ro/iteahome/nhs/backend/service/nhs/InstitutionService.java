package ro.iteahome.nhs.backend.service.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.nhs.InstitutionController;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.nhs.entity.Institution;
import ro.iteahome.nhs.backend.repository.nhs.InstitutionRepository;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class InstitutionService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private InstitutionRepository institutionRepository;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public EntityModel<Institution> add(Institution institution) {
        if (!institutionRepository.existsByCui(institution.getCui())) {
            institutionRepository.save(institution);
            Institution savedInstitution = institutionRepository.getByCui(institution.getCui());
            return new EntityModel<>(
                    savedInstitution,
                    linkTo(methodOn(InstitutionController.class).findByCui(savedInstitution.getCui())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("MEDICAL INSTITUTION");
        }
    }

    public ArrayList<Institution> findAll() throws Exception {
        try {
            return institutionRepository.findAll();
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public EntityModel<Institution> findByCui(String cui) {
        Optional<Institution> optionalInstitution = institutionRepository.findByCui(cui);
        if (optionalInstitution.isPresent()) {
            Institution institution = optionalInstitution.get();
            return new EntityModel<>(
                    institution,
                    linkTo(methodOn(InstitutionController.class).findByCui(institution.getCui())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("MEDICAL INSTITUTION");
        }
    }

    public EntityModel<Institution> update(Institution institution) {
        if (institutionRepository.existsById(institution.getId())) {
            institutionRepository.save(institution);
            Institution updatedInstitution = institutionRepository.getById(institution.getId());
            return new EntityModel<>(
                    updatedInstitution,
                    linkTo(methodOn(InstitutionController.class).findByCui(updatedInstitution.getCui())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("MEDICAL INSTITUTION");
        }
    }

    public EntityModel<Institution> deleteByCui(String cui) {
        Optional<Institution> optionalInstitution = institutionRepository.findByCui(cui);
        if (optionalInstitution.isPresent()) {
            Institution institution = optionalInstitution.get();
            institutionRepository.delete(institution);
            return new EntityModel<>(institution);
        } else {
            throw new GlobalNotFoundException("MEDICAL INSTITUTION");
        }
    }

// OTHER METHODS: -----------------------------------------------------------------------------------------------------
}
