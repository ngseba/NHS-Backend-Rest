package ro.iteahome.nhs.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.entity.person.Doctor;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Doctor getById(int id);

    Optional<Doctor> findByEmail(String email);

    Doctor getByEmail(String email);

    boolean existsByEmail(String email);
}
