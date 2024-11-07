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

    @OneToMany(mappedBy = "publicacion")
    private List<Like> likes = new ArrayList<>();

    //MAPEADO JPA PARA LOS COMENTARIOS
    @OneToMany(mappedBy = "publicacionPadre")
    private List<Publicacion> comentarios = new ArrayList<>();

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


    // Constructor vacío requerido para JPA
    public Publicacion() {
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
    
    public List<Referencia> getReferencias() {
        if (this.referencias == null) {
            this.referencias = new ArrayList<>();
        }
        return referencias;
    }
    
    public void setReferencias(List<Referencia> referencias) {
        this.referencias = referencias;
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

    /*Getter*/
    public List<Publicacion> getComentarios() {
        return comentarios;
    }

    /*Getter y setter para la publicación padre*/
    public Publicacion getPublicacionPadre() {
        return publicacionPadre;
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
}