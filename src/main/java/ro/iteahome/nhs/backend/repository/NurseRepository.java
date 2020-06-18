package ro.iteahome.nhs.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.entity.person.Nurse;

import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Integer> {

    Optional<Nurse> findByEmail(String email);

    Nurse getByEmail(String email);

    void deleteById(int id);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}
