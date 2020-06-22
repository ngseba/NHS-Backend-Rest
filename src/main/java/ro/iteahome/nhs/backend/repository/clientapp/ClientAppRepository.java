package ro.iteahome.nhs.backend.repository.clientapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.clientapp.entity.ClientApp;

import java.util.Optional;

@Repository
public interface ClientAppRepository extends JpaRepository<ClientApp, Integer> {

    Optional<ClientApp> findOneByName(String name);

    boolean existsByName(String name);
}
