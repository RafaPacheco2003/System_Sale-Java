package system.sales.system_sales.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;

import java.util.Set;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    private String nombre;

    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios = new HashSet<>();

    
    

}
