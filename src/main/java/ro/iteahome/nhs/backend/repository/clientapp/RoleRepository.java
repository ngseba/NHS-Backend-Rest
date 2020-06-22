package ro.iteahome.nhs.backend.repository.clientapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iteahome.nhs.backend.model.clientapp.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
