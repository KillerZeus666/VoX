package com.vox.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vox.proyecto.modelo.Usuario;
import com.vox.proyecto.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class ControllerUsuarios {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para registrar un nuevo usuario
    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario nuevoUsuario) {
        if (verificarUsuarioExistente(nuevoUsuario.getUsername(), nuevoUsuario.getEmail())) {
            return ResponseEntity.badRequest().body("El nombre de usuario o correo electrónico ya están en uso.");
        }
        usuarioRepository.save(nuevoUsuario);
        return ResponseEntity.ok("Usuario registrado exitosamente.");
    }

    // Método para verificar si un usuario ya existe
    private boolean verificarUsuarioExistente(String username, String email) {
        return usuarioRepository.findByUsername(username) != null;
    }

    // Otros métodos como autenticar, borrar y editar usuario...
}