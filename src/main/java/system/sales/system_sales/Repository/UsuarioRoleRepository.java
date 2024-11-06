package system.sales.system_sales.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.sales.system_sales.Entity.UsuarioRole;

import java.util.List;

@Repository
public interface UsuarioRoleRepository extends JpaRepository<UsuarioRole, Integer> {
    // Método para obtener los roles asociados con un usuario por su id
    List<UsuarioRole> findByUsuarioId(Integer usuarioId);
}
