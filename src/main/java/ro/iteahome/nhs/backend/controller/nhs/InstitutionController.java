package ro.iteahome.nhs.backend.controller.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.exception.business.GlobalDatabaseException;
import ro.iteahome.nhs.backend.model.nhs.entity.Institution;
import ro.iteahome.nhs.backend.model.nhs.reference.InstitutionType;
import ro.iteahome.nhs.backend.service.nhs.InstitutionService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Stream;

@RestController
@RequestMapping("/medical-institutions")
public class InstitutionController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    InstitutionService institutionService;

// METHODS: ------------------------------------------------------------------------------------------------------------

    @PostMapping
    public EntityModel<Institution> add(@RequestBody @Valid Institution institution) {
        return institutionService.add(institution);
    }

    @GetMapping("/type")
    public String[] getInstitutionType() {
        return Stream.of(InstitutionType.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<Institution>> institutionArrayList () {
        try {
            ArrayList<Institution> institutionArrayList = new ArrayList<>(institutionService.findAll());
            return new ResponseEntity<>(institutionArrayList, HttpStatus.OK);
        } catch (Exception ex) {
            throw new GlobalDatabaseException("Institution", ex.getCause().getCause().getMessage());
        }
    }

    @GetMapping("/by-cui/{cui}")
    public EntityModel<Institution> findByCui(@PathVariable String cui) {
        return institutionService.findByCui(cui);
    }

    @PutMapping
    public EntityModel<Institution> update(@RequestBody @Valid Institution institution) {
        return institutionService.update(institution);
    }

    @DeleteMapping("/by-cui/{cui}")
    public EntityModel<Institution> deleteByCui(@PathVariable String cui) {
        return institutionService.deleteByCui(cui);
    }
}
