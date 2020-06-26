package ro.iteahome.nhs.backend.service.nhs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.model.nhs.dto.ConsultDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.*;
import ro.iteahome.nhs.backend.repository.nhs.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class ConsultService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DiagnosticRepository diagnosticRepository;
    @Autowired
    private ConsultRepository consultRepository;
    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private InstitutionService institutionService;

    public void add(ConsultDTO consultDTO) {
        new Consult();
        Consult consult;
        consult = extractConsult(consultDTO);
        extractTreatment(consultDTO, consult);
        extractDiagnostic(consultDTO, consult);
    }

    private Consult extractConsult (ConsultDTO consultDTO){
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

    private void extractTreatment (ConsultDTO consultDTO, Consult consult) {
        Treatment treatment = new Treatment();
        treatment.setDescription(consultDTO.getTreatment_desc());
        treatment.setMaxDays(consultDTO.getMax_days());
        treatment.setMinDays(consultDTO.getMin_days());
        treatment.setSchedule(consultDTO.getTreatment_schedule());
        treatment.setConsult(consult);
        treatmentRepository.save(treatment);
    }
    private void extractDiagnostic (ConsultDTO consultDTO, Consult consult) {
        Diagnostic diagnostic = new Diagnostic();
        diagnostic.setConsult(consult);
        diagnostic.setDescription(consultDTO.getDiagnostic_desc());
        diagnosticRepository.save(diagnostic);
    }
}
