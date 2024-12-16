package system.sales.system_sales.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import system.sales.system_sales.Entity.ConfirmationToken;
import system.sales.system_sales.Entity.Usuario;
import system.sales.system_sales.Repository.ConfirmationTokenRepository;
import system.sales.system_sales.Repository.UsuarioRepository;

@RestController
@RequestMapping("/confirm")
public class ConfirmationController {
    
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UsuarioRepository userRepository;

    @GetMapping
    public String confirmAccount(@RequestParam("token") String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token no válido"));

        if (confirmationToken.isExpired()) {
            return "El enlace de confirmación ha expirado.";
        }

        Usuario user = confirmationToken.getUser();
        user.setEnabled(true); // Activar la cuenta
        userRepository.save(user);

        return "Cuenta confirmada con éxito!";
    }
}
