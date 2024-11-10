package com.vox.proyecto.modelo;

//import java.util.Date;
//import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Multimedia {

    @Id
    @GeneratedValue
    private Long idMul;

    private String url;
    private String tipo;
    private long orden;

    @ManyToOne
    private Publicacion idPub;

    /*Hacer métodos*/
    /*Constructor por defecto*/
    public Multimedia() {}

    /*Constructor con todos los atributos*/
    public Multimedia(String url, String tipo, long orden, Publicacion idPub) {
        this.url = url;
        this.tipo = tipo;
        this.orden = orden;
        this.idPub = idPub;
    }

    /*Getters*/
    public Long getIdMul() {
        return idMul;
    }

    public String getUrl() {
        return url;
    }

    public String getTipo() {
        return tipo;
    }

    public long getOrden() {
        return orden;
    }

    public Publicacion getIdPub() {
        return idPub;
    }

    /*Setters*/
    public void setIdMul(Long idMul) {
        this.idMul = idMul;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setOrden(long orden) {
        this.orden = orden;
    }

    public void setIdPub(Publicacion idPub) {
        this.idPub = idPub;
    }
    
}