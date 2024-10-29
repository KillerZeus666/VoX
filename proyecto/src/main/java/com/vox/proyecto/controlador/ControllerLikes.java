package com.vox.proyecto.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vox.proyecto.modelo.Like;
import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.LikeRepository;
import com.vox.proyecto.repository.PublicacionRepository;

@RestController
@RequestMapping("/likes")
public class ControllerLikes {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Método para dar un "Like" a una publicación
    @PostMapping("/darLike")
    public ResponseEntity<Like> darLike(@RequestBody Publicacion publicacion, @RequestParam Long idUser) {
        // Verifica si la publicación existe
        if (!publicacionRepository.existsById(publicacion.getIdPub())) {
            return ResponseEntity.notFound().build();
        }

        // Crea un nuevo Like
        Like nuevoLike = new Like();
        nuevoLike.setPublicacion(publicacion);
        nuevoLike.setIdUser(idUser);

        // Guarda el like y retorna la respuesta
        return ResponseEntity.ok(likeRepository.save(nuevoLike));
    }

    // Método para quitar un "Like" de una publicación
    @DeleteMapping("/quitarLike")
    public ResponseEntity<Void> quitarLike(@RequestBody Publicacion publicacion, @RequestParam Long idUser) {
        // Busca el Like asociado
        Like likeToRemove = likeRepository.findByPublicacionAndIdUser(publicacion, idUser);
        if (likeToRemove != null) {
            likeRepository.delete(likeToRemove);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método para contar los "likes" de una publicación
    @GetMapping("/contarLikes")
    public ResponseEntity<Long> contarLikes(@RequestParam Long publicacionId) {
        long count = likeRepository.countByPublicacionId(publicacionId);
        return ResponseEntity.ok(count);
    }
}
