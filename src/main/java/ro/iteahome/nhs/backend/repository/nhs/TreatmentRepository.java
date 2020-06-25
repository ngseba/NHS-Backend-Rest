package ro.iteahome.nhs.backend.repository.nhs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.nhs.entity.Treatment;

import java.util.Optional;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Integer>{
    Optional<Treatment> findById(int id);

    Treatment getById(int id);

    void deleteById(int id);
}