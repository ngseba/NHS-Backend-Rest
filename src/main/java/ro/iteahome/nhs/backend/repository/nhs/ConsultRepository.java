package ro.iteahome.nhs.backend.repository.nhs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.nhs.entity.Consult;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Integer> {
    Consult getByFetcher(String fetcher);
}
