package system.sales.system_sales.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
