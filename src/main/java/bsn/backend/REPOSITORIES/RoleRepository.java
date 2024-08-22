package bsn.backend.REPOSITORIES;

import bsn.backend.ROLES.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findRoleByName(String name);
}
