package system.sales.system_sales.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import system.sales.system_sales.Entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByNombre(String nombre);
}
