package ro.iteahome.nhs.backend.repository.nhs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.nhs.entity.Institution;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

    Institution getById(int id);

    Optional<Institution> findByCui(String cui);

    Institution getByCui(String cui);

    ArrayList<Institution> findAll();

    boolean existsByCui(String cui);
}
