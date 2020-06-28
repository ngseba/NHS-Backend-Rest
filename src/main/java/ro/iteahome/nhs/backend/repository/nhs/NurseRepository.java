package ro.iteahome.nhs.backend.repository.nhs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.nhs.entity.Nurse;

import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Integer> {

    Optional<Nurse> findByEmail(String email);

    Optional<Nurse> findByCnp(String cnp);

    Nurse getByEmail(String email);

    void deleteById(int id);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCnpAndLicenseNo(String cnp, String licenseNo);
}
