package ro.iteahome.nhs.backend.service.nhs;

import org.apache.tomcat.jni.Local;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.model.nhs.dto.ConsultDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Consult;
import ro.iteahome.nhs.backend.model.nhs.entity.Doctor;
import ro.iteahome.nhs.backend.model.nhs.entity.Patient;
import ro.iteahome.nhs.backend.repository.nhs.*;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.ZoneId;
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

    public EntityModel<ConsultDTO> add(Consult consult) {
//        diagnosticRepository.add(Diagnostic)
        return null;
    }

    private Consult extractConsult (ConsultDTO consultDTO){
        Consult consult = new Consult();
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        consult.setDate(date);

//        get Doctor by cnp
        Doctor doctor = modelMapper.map(doctorService.findByCnp(consultDTO.getDoctor_cnp()), Doctor.class);

//        get Patient by cnp
        Patient patient = modelMapper.map(patientService.findByCnp(consultDTO.getPatient_cnp()), Patient.class);




        consult.setDoctor(doctor);
        return consult;
    }
}
