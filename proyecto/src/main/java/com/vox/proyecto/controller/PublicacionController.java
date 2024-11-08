package com.vox.proyecto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vox.proyecto.repository.PublicacionRepository;

@Controller
public class PublicacionController {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @DeleteMapping("/eliminarPublicacion/{id}")
    @ResponseBody
    public Map<String, Boolean> eliminarPublicacion(@PathVariable Long id) {
        Map<String, Boolean> response = new HashMap<>();
        Optional<Publicacion> publicacionOpt = publicacionRepository.findById(id);

        if (publicacionOpt.isPresent()) {
            Publicacion publicacion = publicacionOpt.get();
            if (publicacion.isDeletable()) { // Método que verifica si puede ser eliminada
                publicacionRepository.delete(publicacion);
                response.put("success", true);
            } else {
                response.put("success", false); // No se puede eliminar
            }
        } else {
            response.put("success", false); // Publicación no encontrada
        }
        return response;
    }
}
