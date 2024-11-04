package com.vox.proyecto.repository;

import static org.junit.jupiter.api.Assertions.*;

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
        // Inicializa y guarda la publicación
        publicacion = new Publicacion();
        publicacion.setIdPub(1L); // Establece un ID de prueba, asegurándote de que no esté en uso.
        publicacionRepository.save(publicacion);
    }

    @Test
    public void testSaveMultimedia() {
        Multimedia multimedia = new Multimedia("http://example.com/video.mp4", "video", 1, publicacion);
        Multimedia savedMultimedia = multimediaRepository.save(multimedia);

        assertNotNull(savedMultimedia.getIdMul(), "El ID de multimedia guardado no debe ser nulo");
        assertEquals("http://example.com/video.mp4", savedMultimedia.getUrl(), "Las URLs deben coincidir");
        assertEquals(multimedia.getTipo(), savedMultimedia.getTipo(), "Los tipos de multimedia deben coincidir");
    }

    @Test
    public void testFindById() {
        Multimedia multimedia = new Multimedia("http://example.com/audio.mp3", "audio", 2, publicacion);
        Multimedia savedMultimedia = multimediaRepository.save(multimedia);

        Optional<Multimedia> foundMultimedia = multimediaRepository.findById(savedMultimedia.getIdMul());
        assertTrue(foundMultimedia.isPresent(), "La multimedia debería estar presente");
        assertEquals(savedMultimedia.getUrl(), foundMultimedia.get().getUrl(), "Las URLs deben coincidir");
    }

    @Test
    public void testDeleteMultimedia() {
        Multimedia multimedia = new Multimedia("http://example.com/image.png", "image", 3, publicacion);
        Multimedia savedMultimedia = multimediaRepository.save(multimedia);

        multimediaRepository.deleteById(savedMultimedia.getIdMul());
        Optional<Multimedia> foundMultimedia = multimediaRepository.findById(savedMultimedia.getIdMul());
        assertFalse(foundMultimedia.isPresent(), "La multimedia debería haber sido eliminada");
    }
}
