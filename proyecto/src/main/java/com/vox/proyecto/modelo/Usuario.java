package com.vox.proyecto.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue
    private Long idUsuario;

    private String nombre;
    private String username;
    private String password;
    private int edad;
    private String carrera;
    private String semestre;
    private String biografia;
    private String email;
    private boolean notificacionesActivas;

    /* Actualización para Referencia */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Referencia> referencias;

    @OneToMany(mappedBy = "autor")
    private List<Publicacion> publicaciones = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Like> likes = new ArrayList<>();

    @OneToMany
    private List<Seguimiento> seguidores = new ArrayList<>();

    @OneToMany
    private List<Seguimiento> seguidos = new ArrayList<>();

    // Constructor vacío
    public Usuario() {
    }

    // Constructor completo para inicializar todos los atributos
    public Usuario(String nombre, String username, String password, int edad, String carrera, String semestre,
                   String biografia, String email, boolean notificacionesActivas) {
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.edad = edad;
        this.carrera = carrera;
        this.semestre = semestre;
        this.biografia = biografia;
        this.email = email;
        this.notificacionesActivas = notificacionesActivas;
    }

    public void seguir(Usuario usuario) {
        if (!this.equals(usuario)) {
            Seguimiento seguimiento = new Seguimiento(this.getIdUsuario(), usuario.getIdUsuario());
            seguimiento.setSeguidor(this);
            seguimiento.setSeguido(usuario);
            this.seguidos.add(seguimiento);
            usuario.getSeguidores().add(seguimiento);
        }
    }

    public void dejarSeguir(Usuario usuario) {
        if (usuario != null && usuario.getIdUsuario() != null) {
            seguidos.removeIf(s -> s.getSeguido() != null && s.getSeguido().getIdUsuario() != null && s.getSeguido().getIdUsuario().equals(usuario.getIdUsuario()));
            usuario.getSeguidores().removeIf(s -> s.getSeguidor() != null && s.getSeguidor().getIdUsuario() != null && s.getSeguidor().getIdUsuario().equals(this.getIdUsuario()));
        }
    }

    public List<Usuario> verSeguidores() {
        List<Usuario> listaSeguidores = new ArrayList<>();
        for (Seguimiento seg : seguidores) {
            listaSeguidores.add(seg.getSeguidor());
        }
        return listaSeguidores;
    }

    public Usuario buscarSeguidorUsername(String username) {
        return verSeguidores().stream()
                .filter(usuario -> usuario.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarSeguidorNombre(String nombre) {
        return verSeguidores().stream()
                .filter(usuario -> usuario.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    public void darLike(Publicacion publicacion, boolean anonimo) {
        Like like = new Like(this, publicacion, anonimo);
        publicacion.agregarLike(like);
    }

    public void darLikePublico(Publicacion publicacion) {
        darLike(publicacion, false);
    }

    public void darLikeAnonimo(Publicacion publicacion) {
        darLike(publicacion, true);
    }

    public void quitarLike(Publicacion publicacion) {
        publicacion.getLikes().removeIf(l -> l.getUsuario().equals(this));
        this.likes.removeIf(l -> l.getPublicacion().equals(publicacion));
    }

    public Publicacion hacerPublicacion(String descripcion, Boolean anonimo) {
        Publicacion nuevaPub = new Publicacion(this, descripcion, anonimo);
        this.publicaciones.add(nuevaPub);
        return nuevaPub;
    }

    public void revelarIdentidadPub(Publicacion publicacion, Boolean anonimo) {
        if (this.publicaciones.contains(publicacion)) {
            publicacion.cambiarAnonimato(anonimo);
        }
    }

    public void revelarTodaIdentidadPub() {
        this.publicaciones.forEach(pub -> pub.cambiarAnonimato(false));
    }

    public void editarUsername(String username, String nuevoUsername) {
        if (this.username.equals(username)) {
            this.username = nuevoUsername;
        }
    }

    public boolean eliminarPublicacion(Publicacion pubAEliminar) {
        return this.publicaciones.remove(pubAEliminar);
    }

    public void hacerReferenciacion(String usuarioRef, Publicacion publicacion, String comentario, Boolean anonimo) {
        if (publicacion == null || publicacion.getAutor().equals(this)) {
            throw new IllegalArgumentException("No se puede hacer una referencia a esta publicación.");
        }
        Referencia nuevaReferencia = new Referencia(this, publicacion, comentario, usuarioRef, anonimo);
        if (this.referencias == null) {
            this.referencias = new ArrayList<>();
        }
        this.referencias.add(nuevaReferencia);
        if (publicacion.getReferencias() == null) {
            publicacion.setReferencias(new ArrayList<>());
        }
        publicacion.getReferencias().add(nuevaReferencia);
    }

    
}