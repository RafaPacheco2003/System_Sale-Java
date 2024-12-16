package system.sales.system_sales.Entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity

@Data
public class ConfirmationToken {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario user;

    public ConfirmationToken() {
    }

    public ConfirmationToken(Usuario user) {
        this.user = user;
        this.token = generateToken();
        this.createdAt = LocalDateTime.now();
        this.expiredAt = this.createdAt.plus(15, ChronoUnit.MINUTES);
    }

    private String generateToken() {
        // Generar un token único, por ejemplo usando UUID
        return UUID.randomUUID().toString();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }
}
