package system.sales.system_sales.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import system.sales.system_sales.Entity.ConfirmationToken;
import system.sales.system_sales.Entity.Usuario;
import system.sales.system_sales.Repository.ConfirmationTokenRepository;
import system.sales.system_sales.Repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
@Service
public class AuthService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void registerUser(Usuario user) {
        // Registrar al usuario sin confirmar aún
        user.setEnabled(false);
        userRepository.save(user);

        // Generar token de confirmación
        ConfirmationToken token = new ConfirmationToken(user);  // Ahora funciona

        // Guardar token en la base de datos
        confirmationTokenRepository.save(token);

        // Enviar correo de confirmación
        sendConfirmationEmail(user, token);
    }

    private void sendConfirmationEmail(Usuario user, ConfirmationToken token) {
        String confirmationUrl = "http://localhost:8080/confirm?token=" + token.getToken();
        String message = "Por favor, confirma tu registro haciendo clic en el siguiente enlace dentro de los próximos 15 minutos: " + confirmationUrl;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Confirmación de Registro");
        mailMessage.setText(message);

        emailSender.send(mailMessage);
    }
}

