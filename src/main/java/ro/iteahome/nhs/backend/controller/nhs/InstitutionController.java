package ro.iteahome.nhs.backend.controller.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.exception.business.GlobalDatabaseException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.nhs.dto.InstitutionDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Institution;
import ro.iteahome.nhs.backend.service.nhs.InstitutionService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/medical-institutions")
public class InstitutionController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    InstitutionService institutionService;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<InstitutionDTO>> add(@RequestBody @Valid Institution institution) {
        try {
            InstitutionDTO savedInstitutionDTO = institutionService.add(institution);
            EntityModel<InstitutionDTO> savedInstitutionDTOEntity = new EntityModel<>(
                    savedInstitutionDTO,
                    linkTo(methodOn(InstitutionController.class).findByCui(savedInstitutionDTO.getCui())).withSelfRel());
            return new ResponseEntity<>(savedInstitutionDTOEntity, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new GlobalDatabaseException("INSTITUTION", ex.getCause().getCause().getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CollectionModel<InstitutionDTO>> findAll() {
        try {
            List<InstitutionDTO> institutionDTOList = new ArrayList<>(institutionService.findAll());
            institutionDTOList.forEach(institutionDTO ->
                    institutionDTO.add(linkTo(methodOn(InstitutionController.class).findByCui(institutionDTO.getCui())).withSelfRel()));
            CollectionModel<InstitutionDTO> institutionDTOCollection = new CollectionModel<>(
                    institutionDTOList,
                    linkTo(methodOn(InstitutionController.class).findAll()).withSelfRel());
            return new ResponseEntity<>(institutionDTOCollection, HttpStatus.OK);
        } catch (Exception ex) {
            throw new GlobalDatabaseException("INSTITUTION", ex.getCause().getCause().getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<InstitutionDTO>> institutionArrayList() {
        try {
            List<InstitutionDTO> institutionArrayList = institutionService.findAll();
            return new ResponseEntity<>(institutionArrayList, HttpStatus.OK);
        } catch (Exception ex) {
            throw new GlobalDatabaseException("Institution", ex.getCause().getCause().getMessage());
        }
    }

    @GetMapping("/by-cui/{cui}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<InstitutionDTO>> findByCui(@PathVariable String cui) {
        try {
            InstitutionDTO institutionDTO = institutionService.findByCui(cui);
            EntityModel<InstitutionDTO> institutionDTOEntity = new EntityModel<>(
                    institutionDTO,
                    linkTo(methodOn(InstitutionController.class).findByCui(cui)).withSelfRel());
            return new ResponseEntity<>(institutionDTOEntity, HttpStatus.OK);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getEntityName());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("INSTITUTION", ex.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<InstitutionDTO>> update(@RequestBody @Valid Institution institution) {
        try {
            InstitutionDTO updatedInstitutionDTO = institutionService.update(institution);
            EntityModel<InstitutionDTO> updatedInstitutionDTOEntity = new EntityModel<>(
                    updatedInstitutionDTO,
                    linkTo(methodOn(InstitutionController.class).findByCui(institution.getCui())).withSelfRel());
            return new ResponseEntity<>(updatedInstitutionDTOEntity, HttpStatus.CREATED);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getEntityName());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("INSTITUTION", ex.getCause().getCause().getMessage());
        }
    }

    @DeleteMapping("/by-cui/{cui}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<InstitutionDTO>> deleteByCui(@PathVariable String cui) {
        try {
            InstitutionDTO deletedInstitutionDTO = institutionService.deleteByCui(cui);
            EntityModel<InstitutionDTO> deletedInstitutionDTOEntity = new EntityModel<>(deletedInstitutionDTO);
            return new ResponseEntity<>(deletedInstitutionDTOEntity, HttpStatus.OK);
        } catch (GlobalNotFoundException ex) {
            throw new GlobalNotFoundException(ex.getEntityName());
        } catch (Exception ex) {
            throw new GlobalDatabaseException("INSTITUTION", ex.getCause().getCause().getMessage());
        }
    }

// OTHER METHODS: ------------------------------------------------------------------------------------------------------

    @GetMapping("/types")
    public String[] getInstitutionTypes() {
        return institutionService.getInstitutionTypes();
    }
}
