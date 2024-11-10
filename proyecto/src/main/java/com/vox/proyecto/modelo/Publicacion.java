package com.vox.proyecto.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import jakarta.persistence.ManyToMany;
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

    @ManyToMany
    private Usuario autor; 

    @OneToMany(mappedBy = "publicacion")
    private List<Like> likes = new ArrayList<>();

    // Constructor con parámetros
    public Publicacion(Long idAutor, String descripcion, Boolean anonimo) {
        this.descripcion = descripcion;
        this.fecha = new Date();
        this.anonimo = anonimo;
        this.autor = new Usuario(); 
    }

    public void cambiarAnonimato(Boolean anonimo) {
        this.anonimo = anonimo;
    }

    public void agregarLike(Like nuevoLike) {
        this.likes.add(nuevoLike);
    }

    // Método para revelar la identidad del autor
    public void revelarIdentidad() {
        this.anonimo = false; 
    }

    // Método para verificar si un usuario es el autor de esta publicación
    public boolean esAutor(Long idUsuario) {
        return this.autor.getIdUsuario().equals(idUsuario);
    }

    // Método para contar likes
    public long contarLikes() {
        return likes.size();
    }

    private boolean compartida;
    private boolean enCampaña;

    // Método para verificar si la publicación es eliminable
    public boolean isDeletable() {
        return !compartida && !enCampaña;
    }
}
