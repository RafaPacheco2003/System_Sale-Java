package system.sales.system_sales.Security;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import system.sales.system_sales.DTO.RegisterRequest;
import system.sales.system_sales.Entity.Role;
import system.sales.system_sales.Entity.Usuario;
import system.sales.system_sales.Repository.RoleRepository;
import system.sales.system_sales.Repository.UsuarioRepository;



@RestController
public class AuthController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Verificar si el usuario ya existe
        if (usuarioRepository.findOneByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }

        // Crear un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(registerRequest.getNombre());
        usuario.setEmail(registerRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Asignar roles al usuario
        if (registerRequest.getRoles() != null) {
            Set<Role> roles = new HashSet<>();
            for (Integer roleId : registerRequest.getRoles()) {
                Role role = roleRepository.findById(roleId).orElse(null);
                if (role != null) {
                    roles.add(role);
                }
            }
            usuario.setRoles(roles);
        }

        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario registrado con éxito");
    }

}
