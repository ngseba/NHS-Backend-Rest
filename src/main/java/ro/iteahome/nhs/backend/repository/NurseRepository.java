package ro.iteahome.nhs.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.entity.person.Nurse;

import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Integer> {

    Nurse getById(int id);

    Optional<Nurse> findByEmail(String email);

    Nurse getByEmail(String email);

    boolean existsByEmail(String email);
}
