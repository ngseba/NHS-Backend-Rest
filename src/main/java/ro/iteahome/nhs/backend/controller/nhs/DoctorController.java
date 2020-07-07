package ro.iteahome.nhs.backend.controller.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.model.nhs.dto.DoctorDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Doctor;
import ro.iteahome.nhs.backend.model.nhs.reference.DoctorSpecialty;
import ro.iteahome.nhs.backend.model.nhs.reference.DoctorTitle;
import ro.iteahome.nhs.backend.service.nhs.DoctorService;

import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    DoctorService doctorService;

// METHODS: ------------------------------------------------------------------------------------------------------------

    @PostMapping
    public EntityModel<DoctorDTO> add(@RequestBody @Valid Doctor doctor) { // TODO: Integrate a way to ask for at least one medical institution id when registering a new doctor.
        return doctorService.add(doctor);
    }

    @GetMapping("/by-cnp/{cnp}")
    public EntityModel<DoctorDTO> findByCnp(@PathVariable String cnp) {
        return doctorService.findByCnp(cnp);
    }

    @GetMapping("/by-email/{email}")
    public EntityModel<DoctorDTO> findByEmail(@PathVariable String email) {
        return doctorService.findByEmail(email);
    }

    @GetMapping("/existence/by-cnp/{cnp}")
    public boolean existsByCnp(@PathVariable String cnp) {
        return doctorService.existsByCnp(cnp);
    }

    @GetMapping("/title")
    public String[] retrieveDoctorTitle() {
        return Stream.of(DoctorTitle.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @GetMapping("/specialty")
    public String[] retrieveDoctorSpecialty() {
        return Stream.of(DoctorSpecialty.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @GetMapping("/institution_cui/{cnp}")
    public String retrieveDoctorInstitutions(@PathVariable String cnp) {
        return doctorService.institutionsForDoctor(cnp);
    }

    @PutMapping
    public EntityModel<DoctorDTO> update(@RequestBody @Valid DoctorDTO doctorDTO) {
        return doctorService.update(doctorDTO);
    }

    @DeleteMapping("/by-cnp/{cnp}")
    public EntityModel<DoctorDTO> deleteByCnp(@PathVariable String cnp) {
        return doctorService.deleteByCnp(cnp);
    }
}
