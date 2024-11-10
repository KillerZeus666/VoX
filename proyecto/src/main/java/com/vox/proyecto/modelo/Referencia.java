package com.vox.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Referencia {

    @Id
    @GeneratedValue
    private Long idRef;

    private Boolean anonimoRef; // Para saber si es una referencia pública o privada
    private String username;
    private String comentario;

    @ManyToOne
    private Publicacion publicacion;

    @ManyToOne
    private Usuario usuario;

    // Constructor por defecto
    public Referencia() {
    }

    // Constructor para referencia pública o privada
    public Referencia(Usuario usuario, Publicacion publicacion, String comentario, String usuarioRef, Boolean anonimoRef) {
        this.usuario = usuario;         // Usuario que hace la referencia
        this.publicacion = publicacion; // Publicación a la que se hace referencia
        this.comentario = comentario;   // Comentario que acompaña la referencia
        this.username = usuarioRef;     // Usuario etiquetado (referenciado)
        this.anonimoRef = anonimoRef;   // Si es una referencia pública o privada
    }

    /* Métodos adicionales si es necesario */
    public Long getIdRef() {
        return idRef;
    }

    public Boolean getAnonimoRef() {
        return anonimoRef;
    }

    public String getUsername() {
        return username;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setAnonimoRef(Boolean anonimoRef) {
        this.anonimoRef = anonimoRef;
    }

    public void notificarReferenciacion() {
        // Verificar si el usuario mencionado existe y tiene notificaciones activadas
        if (usuario != null) {
            if (usuario.isNotificacionesActivas()) {
                System.out.println("Notificación enviada a " + usuario.getUsername() + " por una mención en la publicación.");
                // Aquí podrías implementar la lógica real de envío de notificación, como llamar a un servicio de notificaciones
            } else {
                System.out.println("El usuario " + usuario.getUsername() + " tiene las notificaciones desactivadas.");
            }
        } else {
            throw new IllegalArgumentException("El usuario mencionado no existe. No se puede enviar la notificación.");
        }
    }
}