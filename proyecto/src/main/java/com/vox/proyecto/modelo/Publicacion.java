package com.vox.proyecto.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Publicacion {

    @Id
    @GeneratedValue
    private Long idPub;

    private String descripcion;
    private Date fecha;
    private Boolean anonimo;

    @ManyToOne
    private Usuario autor;

    //ATRIBUTO PAARA REVISAR COMENTARIOS
    @ManyToOne
    private Publicacion publicacionPadre;

    @OneToMany(mappedBy = "publicacionPadre")
    private List<Publicacion> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "publicacion")
    private List<Like> likes = new ArrayList<>();

    //MAPEADO JPA PARA MULTIMEDIA
    @OneToMany(mappedBy = "publicacion")
    private List<Multimedia> multimedia = new ArrayList<>();

    /*Actualicación para Referencia*/
    @OneToMany(mappedBy = "publicacion")
    private List<Referencia> referencias;
    

    //Constructor actualizado para tener lista de comentarios
    public Publicacion(long idPub, String descripcion, Date fecha, boolean anonimo) {
        this.idPub = idPub;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.anonimo = anonimo;
        this.comentarios = new ArrayList<>();
        this.multimedia = new ArrayList<>();
    }


    public Publicacion(Usuario autor, String descripcion, Boolean anonimo) {
        this.autor = autor;
        this.descripcion = descripcion;
        this.fecha = new Date(); 
        this.anonimo = anonimo;
        // Inicializa la lista de likes, lista de comentarios y lista de Multimedia
        this.likes = new ArrayList<>(); 
        this.comentarios = new ArrayList<>(); 
        this.multimedia = new ArrayList<>(); 
    }


    public Publicacion() {
        this.fecha = new Date(); 
        // Inicializa la lista de likes, lista de comentarios y lista de Multimedia
        this.likes = new ArrayList<>(); 
        this.comentarios = new ArrayList<>(); 
        this.multimedia = new ArrayList<>(); 
    }


    // Método para cambiar el anonimato de la publicación
    public void cambiarAnonimato(Boolean anonimo) {
        this.anonimo = anonimo;
    }

    // Método para agregar un like a la publicación
    public void agregarLike(Like nuevoLike) {
        // Check if this user has already liked this publication
        for (Like like : likes) {
            if (like.getUsuario().equals(nuevoLike.getUsuario())) {
                return; // User has already liked this publication, do not add again
            }
        }
        this.likes.add(nuevoLike); // Add the new like if it is unique
    }
    
    //Set para metodo ControllerReferenciaTest
    public void setIdPublicacion(Long idPublicacion) {
        this.idPub = idPublicacion;
    }

    // Método para revelar la identidad del autor
    public void revelarIdentidad() {
        this.anonimo = false;
    }

    // Método para verificar si un usuario es el autor de esta publicación
    public boolean esAutor(Long idUsuario) {
        return this.autor.getIdUsuario().equals(idUsuario);
    }

    // Método para contar la cantidad de likes en la publicación
    public long contarLikes() {
        return likes.size();
    }

    //Métodos para manejar comentarios

    /*Para añadir un nuevo comentario*/
    public void addComentario(Publicacion comentario) {
        /*Se establece la publicación actual como padre*/
        comentario.setPublicacionPadre(this);
        comentarios.add(comentario);
    }

    /*Para eliminar un comentario*/
    public void removeComentario(Publicacion comentario) {
        comentarios.remove(comentario);
    }

    public void actualizarReferencia(long idRef, boolean anonimo, List<Referencia> referencias) {
        for (Referencia r : referencias) {
            if (r.getIdRef() != null && r.getIdRef().equals(idRef)) {
                r.setAnonimoRef(anonimo); 
                break; 
            }
        }
    }
    public void agregarReferencia(Usuario usuario, String comentario, String usuarioRef) {
        // Crear la nueva referencia
        Referencia nuevaReferencia = new Referencia(usuario, this, comentario, usuarioRef, anonimo);
    
        // Agregar la referencia al usuario
        if (usuario.getReferencias() == null) {
            usuario.setReferencias(new ArrayList<>());
        }
        usuario.getReferencias().add(nuevaReferencia);
    
        // Agregar la referencia a la publicación para mantener la relación bidireccional
        if (this.referencias == null) {
            this.referencias = new ArrayList<>();
        }
        this.referencias.add(nuevaReferencia);
    }
    
     // Método para agregar la referencia a la publicación
     public void hacerReferenciacion(Usuario usuario, String usuarioRef, String comentario, Boolean anonimo) {
        // Validar que la publicación no sea nula y que el usuario no se esté refiriendo a sí mismo
        if (usuario == null || usuario.equals(this.getAutor())) {
            throw new IllegalArgumentException("No se puede hacer una referencia a esta publicación.");
        }

        // Crear la nueva referencia
        Referencia nuevaReferencia = new Referencia(usuario, this, comentario, usuarioRef, anonimo);

        // Agregar la referencia al usuario (este método ya lo hace el usuario, pero se puede mantener por claridad)
        if (usuario.getReferencias() == null) {
            usuario.setReferencias(new ArrayList<>());
        }
        usuario.getReferencias().add(nuevaReferencia);

        // Agregar la referencia a la publicación para mantener la relación bidireccional
        if (this.referencias == null) {
            this.referencias = new ArrayList<>();
        }
        this.referencias.add(nuevaReferencia);
    }

    // Método para agregar un comentario a una publicación
    public void agregarComentario(Usuario autor, String textoComentario, boolean esAnonimo) {
      // Validar que el texto del comentario no esté vacío
        if (textoComentario == null || textoComentario.trim().isEmpty()) {
            throw new IllegalArgumentException("El comentario no puede estar vacío.");
        }

        // Crear el nuevo comentario
        Publicacion comentario = new Publicacion(autor, textoComentario, esAnonimo);

     // Agregar el comentario a la lista de comentarios de la publicación actual
        this.addComentario(comentario);
    }

    // Método para revelar la identidad del autor de un comentario específico
    public void revelarIdentidadComentario(Long idComentario) {
        for (Publicacion comentario : comentarios) {
            if (comentario.getIdPub().equals(idComentario)) {
                 // Cambia el anonimato del comentario a falso
                comentario.setAnonimo(false);
                break; 
            }
        }
    }

    public void aniadirContenidoMultimedia(Usuario autor, String descripcion, List<Multimedia> multimediaAdjunta, boolean esAnonimo) {
        // Validación de publicación sin texto y sin multimedia (Flujo alterno 3)
        if ((descripcion == null || descripcion.trim().isEmpty()) && (multimediaAdjunta == null || multimediaAdjunta.isEmpty())) {
            throw new IllegalArgumentException("No puedes publicar sin escribir texto ni adjuntar multimedia.");
        }
    
        // Advertencia si el usuario intenta publicar sin multimedia, pero permite continuar (Flujo alterno 2)
        if (multimediaAdjunta == null || multimediaAdjunta.isEmpty()) {
            System.out.println("Advertencia: Estás publicando sin adjuntar multimedia.");
        }
    
        // Inicializar los atributos de la publicación
        this.autor = autor;
        this.descripcion = descripcion;
        this.fecha = new Date(); // Fecha actual
        this.anonimo = esAnonimo;
    
        // Agregar multimedia si hay archivos adjuntos
        if (multimediaAdjunta != null) {
            for (Multimedia media : multimediaAdjunta) {
                media.setIdPub(this);  // Vincular cada archivo multimedia con esta publicación
                this.multimedia.add(media);
            }
        }
    
        // Publicar en modo normal o anónimo según la opción seleccionada
        if (esAnonimo) {
            System.out.println("Publicación realizada en modo anónimo.");
        } else {
            System.out.println("Publicación realizada en modo normal con la identidad del usuario.");
        }
    }
    public void eliminarContenidoMultimedia(Long idMultimedia) {
        // Buscar el archivo multimedia en la lista de multimedia de la publicación
        Multimedia mediaAEliminar = null;
        
        for (Multimedia media : this.multimedia) {
            if (media.getIdMul().equals(idMultimedia)) {
                mediaAEliminar = media;
                break;
            }
        }
        
        // Si el archivo multimedia fue encontrado, se elimina
        if (mediaAEliminar != null) {
            this.multimedia.remove(mediaAEliminar);
            System.out.println("El contenido multimedia con ID " + idMultimedia + " ha sido eliminado.");
        } else {
            System.out.println("No se encontró contenido multimedia con el ID especificado.");
        }
    }
        
}