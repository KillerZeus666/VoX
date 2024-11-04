package com.vox.proyecto.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vox.proyecto.modelo.Multimedia;
import com.vox.proyecto.modelo.Publicacion;
import com.vox.proyecto.repository.MultimediaRepository;
import com.vox.proyecto.repository.PublicacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ControllerMultimediaTest {

    @InjectMocks
    private ControllerMultimedia controller;

    @Mock
    private MultimediaRepository multimediaRepository;

    @Mock
    private PublicacionRepository publicacionRepository;

    @Autowired
    private MockMvc mockMvc;

    private Publicacion publicacion;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        publicacion = new Publicacion();
        publicacion.setIdPub(1L);
    }

    @Test
    public void testAgregarMultimedia_Success() throws Exception {
        Multimedia multimedia = new Multimedia("http://example.com/video.mp4", "video", 1, publicacion);
        
        when(publicacionRepository.existsById(publicacion.getIdPub())).thenReturn(true);
        when(multimediaRepository.save(any(Multimedia.class))).thenReturn(multimedia);

        mockMvc.perform(post("/multimedia/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"http://example.com/video.mp4\", \"tipo\":\"video\", \"orden\":1, \"idPub\":{\"idPub\":1}}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAgregarMultimedia_PublicacionNotFound() throws Exception {
        when(publicacionRepository.existsById(publicacion.getIdPub())).thenReturn(false);

        mockMvc.perform(post("/multimedia/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"http://example.com/video.mp4\", \"tipo\":\"video\", \"orden\":1, \"idPub\":{\"idPub\":1}}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testEliminarMultimedia_Success() throws Exception {
        when(multimediaRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/multimedia/eliminar/1"))
                .andExpect(status().isOk());
        
        verify(multimediaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testEliminarMultimedia_NotFound() throws Exception {
        when(multimediaRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/multimedia/eliminar/1"))
                .andExpect(status().isNotFound());
    }
}
