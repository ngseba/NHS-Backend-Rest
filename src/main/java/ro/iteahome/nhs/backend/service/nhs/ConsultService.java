package ro.iteahome.nhs.backend.service.nhs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.model.nhs.dto.ConsultDTO;
import ro.iteahome.nhs.backend.model.nhs.entity.Consult;
import ro.iteahome.nhs.backend.repository.nhs.DiagnosticRepository;
import ro.iteahome.nhs.backend.repository.nhs.InstitutionRepository;
import ro.iteahome.nhs.backend.repository.nhs.PatientRepository;

@Service
public class ConsultService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private InstitutionRepository institutionRepository;
    @Autowired
    private DiagnosticRepository diagnosticRepository;

//    public EntityModel<ConsultDTO> add(Consult consult) {
////        consult
//    }
}
