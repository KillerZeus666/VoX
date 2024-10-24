import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel; 

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Usuario {

    @Id
    @GeneratedValue
    Long idUsuario;

    String nombre;
    String username;
    String password;
    int edad;
    String carrera;
    String semestre;
    String biografia;
    String email;


  // Relaciones de agregacion y composicion 
    List <Like> likes; 
    List <Publicacion> publicaciones; 
    List <Seguimiento> seguidores;  
    List <Seguimiento> seguidos; 




  //Metodos de la clase
    public void seguir(Usuario usuario) {
        // Implement logic to add user to following list (consider using JPA relationships)
    }

    public void dejarSeguir(Usuario usuario) {
        // Implement logic to remove user from following list
    }

    public List<Usuario> verSeguidores(){

    }

    public void buscarSeguidorUsername(String username){

    }

    public void buscarSeguidorNombre(String nombre){

    }

    public void darLike(Publicacion publicacion, Boolean anonimo){

    }

    public void quitarLike(Publicacion publicacion){

    }

    public Publicacion hacerPublicacion(String descripcion, Boolean anonimo){

    }

    public void revelarIdentidadPub (Publicacion publicacion, Boolean anonimo){

    }

    public void revelarTodaIdentidadPub(){

    }

    public void editarUsername (String username, String nuevoUsername){
        
    }
}
