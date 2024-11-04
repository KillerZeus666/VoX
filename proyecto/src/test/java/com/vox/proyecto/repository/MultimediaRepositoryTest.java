package com.vox.proyecto.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.vox.proyecto.modelo.Multimedia;
import com.vox.proyecto.modelo.Publicacion;

import java.util.Optional;

@DataJpaTest
public class MultimediaRepositoryTest {

    @Autowired
    private MultimediaRepository multimediaRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    private Publicacion publicacion;

    @BeforeEach
    public void setUp() {
        publicacion = new Publicacion(); // Asegúrate de inicializar adecuadamente la publicación
        publicacion.setIdPub(1L); // Establece un ID de publicación de prueba
        publicacionRepository.save(publicacion);
    }

    @Test
    public void testSaveMultimedia() {
        Multimedia multimedia = new Multimedia("http://example.com/video.mp4", "video", 1, publicacion);
        Multimedia savedMultimedia = multimediaRepository.save(multimedia);
        
        assertNotNull(savedMultimedia.getIdMul());
        assertEquals("http://example.com/video.mp4", savedMultimedia.getUrl());
    }

    @Test
    public void testFindById() {
        Multimedia multimedia = new Multimedia("http://example.com/audio.mp3", "audio", 2, publicacion);
        Multimedia savedMultimedia = multimediaRepository.save(multimedia);
        
        Optional<Multimedia> foundMultimedia = multimediaRepository.findById(savedMultimedia.getIdMul());
        assertTrue(foundMultimedia.isPresent());
        assertEquals(savedMultimedia.getUrl(), foundMultimedia.get().getUrl());
    }

    @Test
    public void testDeleteMultimedia() {
        Multimedia multimedia = new Multimedia("http://example.com/image.png", "image", 3, publicacion);
        Multimedia savedMultimedia = multimediaRepository.save(multimedia);
        
        multimediaRepository.deleteById(savedMultimedia.getIdMul());
        Optional<Multimedia> foundMultimedia = multimediaRepository.findById(savedMultimedia.getIdMul());
        assertTrue(foundMultimedia.isEmpty());
    }
}
