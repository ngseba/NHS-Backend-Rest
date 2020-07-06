package ro.iteahome.nhs.backend.repository.nhs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.nhs.entity.Consult;
import ro.iteahome.nhs.backend.model.nhs.entity.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Integer> {
    Consult getByFetcher(String fetcher);

    Optional<Consult> getByPatient(Patient patient);

    ArrayList<Consult> findByPatient(Patient patient);
}
