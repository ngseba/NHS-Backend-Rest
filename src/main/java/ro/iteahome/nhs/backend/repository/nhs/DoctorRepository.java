package ro.iteahome.nhs.backend.repository.nhs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.nhs.entity.Doctor;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findByEmail(String email);

    Doctor getByEmail(String email);

    void deleteById(int id);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCnpAndLicenseNo(String cnp, String licenseNo);
}
