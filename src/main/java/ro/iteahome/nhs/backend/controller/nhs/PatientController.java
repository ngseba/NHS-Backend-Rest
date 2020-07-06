package ro.iteahome.nhs.backend.controller.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.model.nhs.dto.ConsultDTO;
import ro.iteahome.nhs.backend.model.nhs.dto.PatientDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Patient;
import ro.iteahome.nhs.backend.service.nhs.ConsultService;
import ro.iteahome.nhs.backend.service.nhs.PatientService;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    PatientService patientService;

    @Autowired
    ConsultService consultService;

// METHODS: ------------------------------------------------------------------------------------------------------------

    @PostMapping
    public EntityModel<PatientDTO> add(@RequestBody @Valid Patient patient) {
        return patientService.add(patient);
    }

    @PostMapping("/add-consult")
    public Boolean add(@RequestBody ConsultDTO consultDTO) {
        return consultService.add(consultDTO);
    }

    @GetMapping("/find-consult/{cnp}")
    public List<ConsultDTO> findConsult(@PathVariable String cnp) {
        return consultService.findConsult(cnp);
    }

    @GetMapping("/by-cnp/{cnp}")
    public EntityModel<PatientDTO> findByCnp(@PathVariable String cnp) {
        return patientService.findByCnp(cnp);
    }

    @PutMapping
    public EntityModel<PatientDTO> update(@RequestBody @Valid Patient patient) {
        return patientService.update(patient);
    }

    @DeleteMapping("/by-cnp/{cnp}")
    public EntityModel<PatientDTO> deleteByCnp(@PathVariable String cnp) {
        return patientService.deleteByCnp(cnp);
    }
}
