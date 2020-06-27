package ro.iteahome.nhs.backend.repository.nhs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.nhs.entity.Admin;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin getById(int id);

    Optional<Admin> findOneById (int id);

    Optional<Admin> findByEmail(String email);

    Optional<Admin> findOneByEmail(String email);

    Admin getByEmail(String email);

    Optional<Admin> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

}
