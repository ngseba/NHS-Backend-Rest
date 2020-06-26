package ro.iteahome.nhs.backend.repository.nhs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.nhs.entity.Diagnostic;

import java.util.Optional;

@Repository
public interface DiagnosticRepository extends JpaRepository<Diagnostic, Integer> {
    Optional<Diagnostic> findById(int id);

    Diagnostic getById(int id);

    void deleteById(int id);
}
