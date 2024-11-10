package com.vox.proyecto.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Like {

    @Id
    @GeneratedValue
    private Long idLike;

    private Boolean anonimoLike;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Publicacion publicacion;

    // Constructor completo
    public Like(Usuario usuario, Publicacion publicacion, Boolean anonimo) {
        this.usuario = usuario;
        this.publicacion = publicacion;
        this.anonimoLike = anonimo;
        usuario.getLikes().add(this);
        publicacion.getLikes().add(this);
    }

    // Método para eliminar el like de una publicación
    public void eliminarLike() {
        if (publicacion != null) {
            publicacion.getLikes().remove(this);
            this.publicacion = null;
        }
    }

    // Método para cambiar el anonimato del like
    public void cambiarAnonimatoLike(Boolean anonimo) {
        this.anonimoLike = anonimo;
    }

}