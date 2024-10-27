import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter 
@ToString
public class Seguimiento {

    @Id
    @GeneratedValue
    private Long idSeguimiento;

    private Long idSeguido;
    private Long idSeguidor;

    // Relaciones de agregacion y composicion 
    Usuario seguidor;
    Usuario seguido;

    // Constructor con parámetros para los atributos principales
    public Seguimiento(Long idSeguido, Long idSeguidor) {
        this.idSeguido = idSeguido;
        this.idSeguidor = idSeguidor;
    }

    // Métodos de la clase 

    public void eliminarSeguimiento() {
        if (seguidor != null && seguido != null) {
            seguidor.getSeguidos().remove(this);
            seguido.getSeguidores().remove(this);
        }
    }

    public static List<Usuario> obtenerSeguidoresUsuario(Long idUsuario, List<Seguimiento> todosLosSeguimientos) {
        List<Usuario> seguidores = new ArrayList<>();
        for (Seguimiento seguimiento : todosLosSeguimientos) {
            if (seguimiento.getIdSeguido().equals(idUsuario)) {
                seguidores.add(seguimiento.getSeguidor());
            }
        }
        return seguidores;
    }
}
