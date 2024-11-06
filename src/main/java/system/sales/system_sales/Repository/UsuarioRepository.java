package system.sales.system_sales.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.sales.system_sales.Entity.Usuario;

import java.util.Optional;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    

    Optional<Usuario> findOneByEmail(String email);

    Optional<Usuario> findByEmail(String email);

}
