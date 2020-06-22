package ro.iteahome.nhs.backend.repository.nhs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.nhs.entity.Patient;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Patient getById(int id);

    Optional<Patient> findByCnp(String cnp);

    Patient getByCnp(String cnp);

    boolean existsByCnp(String cnp);
}
