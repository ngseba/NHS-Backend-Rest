package ro.iteahome.nhs.backend.service.nhs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.nhs.PatientController;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.nhs.dto.ConsultDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.*;
import ro.iteahome.nhs.backend.repository.nhs.ConsultRepository;
import ro.iteahome.nhs.backend.repository.nhs.DiagnosticRepository;
import ro.iteahome.nhs.backend.repository.nhs.PatientRepository;
import ro.iteahome.nhs.backend.repository.nhs.TreatmentRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ConsultService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DiagnosticRepository diagnosticRepository;

    @Autowired
    private ConsultRepository consultRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private InstitutionService institutionService;

    public EntityModel<ConsultDTO> add(ConsultDTO consultDTO) {
        new Consult();
        Consult consult;
        consult = extractConsult(consultDTO);
        extractTreatment(consultDTO, consult);
        extractDiagnostic(consultDTO, consult);
        return new EntityModel<>(
                consultDTO,
                linkTo(methodOn(PatientController.class).findConsult(consultDTO.getPatient_cnp())).withSelfRel());
    }

    public List<ConsultDTO> findConsult(String patientCnp) {
        Patient patient;
        List<ConsultDTO> consultDTO = new ArrayList<>();

        patient = patientRepository.findByCnp(patientCnp).get();

        List<Consult> optionalConsult = consultRepository.findByPatient(patient);

        if (!optionalConsult.isEmpty()) {
            return optionalConsult.stream()
                .map(consultDTOs ->getConsultDTOs(optionalConsult))
                    .collect(Collectors.toList())
                    .get(0);
        } else {
            throw new GlobalNotFoundException("Consult DTO");
        }
    }

    private List<ConsultDTO> getConsultDTOs (List<Consult> consults) {
//        Patient patient = new Patient();
        Treatment treatment = new Treatment();
        Diagnostic diagnostic = new Diagnostic();

        List<ConsultDTO> consultDTOs = new ArrayList<>();
        ConsultDTO consultDTO = new ConsultDTO();

        for (Consult consult : consults
             ) {
            treatment = treatmentRepository.getByConsult(consult);
            diagnostic = diagnosticRepository.getByConsult(consult);


            consultDTO.setDate(consult.getDate());
            consultDTO.setDiagnostic_desc(diagnostic.getDescription());
            consultDTO.setDoctor_cnp(consult.getDoctor().getCnp());
            consultDTO.setInstitution_cui(consult.getInstitution().getCui());
            consultDTO.setMax_days(treatment.getMaxDays());
            consultDTO.setMin_days(treatment.getMinDays());
            consultDTO.setPatient_cnp(consult.getPatient().getCnp());
            consultDTO.setTreatment_desc(treatment.getDescription());
            consultDTO.setTreatment_schedule(treatment.getSchedule());
            consultDTOs.add(consultDTO);
        }
        return consultDTOs;
    }

    private Consult extractConsult(ConsultDTO consultDTO) {
        Consult consult = new Consult();
        LocalDate localDate = LocalDate.now();
        LocalTime time = LocalTime.now();
        Date date = Date.from(localDate.atTime(time).toInstant(ZoneOffset.MAX));

//        get Doctor by cnp
        Doctor doctor = modelMapper.map(doctorService.findByCnp(consultDTO.getDoctor_cnp()), Doctor.class);
//        get Patient by cnp
        Patient patient = modelMapper.map(patientService.findByCnp(consultDTO.getPatient_cnp()), Patient.class);
//      get institution by cui
        Institution institution = modelMapper.map(institutionService.findByCui(consultDTO.getInstitution_cui()), Institution.class);

        consult.setDoctor(doctor);
        consult.setInstitution(institution);
        consult.setPatient(patient);
        consult.setDate(date);
        String unique_fetcher = consultDTO.getDoctor_cnp() + consultDTO.getPatient_cnp() + consultDTO.getDate().toString();
        consult.setFetcher(unique_fetcher);
        consultRepository.save(consult);

        consult = consultRepository.getByFetcher(unique_fetcher);
        return consult;
    }

    private Treatment extractTreatment(ConsultDTO consultDTO, Consult consult) {
        Treatment treatment = new Treatment();
        treatment.setDescription(consultDTO.getTreatment_desc());
        treatment.setMaxDays(consultDTO.getMax_days());
        treatment.setMinDays(consultDTO.getMin_days());
        treatment.setSchedule(consultDTO.getTreatment_schedule());
        treatment.setConsult(consult);
        treatmentRepository.save(treatment);
        return treatment;
    }

    private Diagnostic extractDiagnostic(ConsultDTO consultDTO, Consult consult) {
        Diagnostic diagnostic = new Diagnostic();
        diagnostic.setConsult(consult);
        diagnostic.setDescription(consultDTO.getDiagnostic_desc());
        diagnosticRepository.save(diagnostic);
        return diagnostic;
    }
}
