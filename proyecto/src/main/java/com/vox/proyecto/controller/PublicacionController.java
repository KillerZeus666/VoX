package com.vox.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.PublicacionRepository;

import java.util.List;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Método para crear una nueva publicación
    @PostMapping
    public ResponseEntity<Void> crearPublicacion(@RequestBody Publicacion publicacion) {
        // Aquí puedes establecer el usuario actual según tu lógica de autenticación
        Usuario usuarioActual = ...; // Lógica para obtener el usuario actual

        if (publicacion.getTexto().isEmpty()) {
            return ResponseEntity.badRequest().build(); // Respuesta de error si el texto está vacío
        }

        // Establecer el usuario en la publicación
        if (publicacion.getModo().equals("normal")) {
            publicacion.setUsuario(usuarioActual);
        } else {
            publicacion.setUsuario(null); // Para publicaciones anónimas
        }

        publicacionRepository.save(publicacion);
        return ResponseEntity.ok().build(); // Respuesta exitosa
    }
}
