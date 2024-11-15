package com.example.demo.service;


import com.example.demo.database.entities.Estudante;
import com.example.demo.database.repositories.EstudanteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)// adiciona o mockito ao JUnit nessa classe de teste
class EstudanteServiceTest {

    static  Estudante estudante;

    @Mock
    private EstudanteRepository estudanteRepository;

    @InjectMocks
    private EstudanteService estudanteService;

    @BeforeEach
    void init() {
        estudante = new Estudante(1L,
                "Joaquino",
                "11.22.33",
                Collections.emptyList());
    }

    @Test
    void cadastrarEstudante() {

        when(estudanteRepository.save(any(Estudante.class)))
                .thenReturn(estudante);

        Estudante resultado = estudanteService.cadastrarEstudante("Joaquino",
                "11.22.33");

        verify(estudanteRepository).save(any(Estudante.class));
        assertEquals(estudante.getNome(), resultado.getNome());
        assertEquals(estudante.getMatricula(), resultado.getMatricula());
    }

    @Test
    void listarEstudantes() {
        when(estudanteRepository.findAll())
                .thenReturn(List.of(estudante));

        List<Estudante> resultado = estudanteService.listarEstudantes();

        verify(estudanteRepository).findAll();
        assertEquals(estudante.getNome(), resultado.get(0).getNome());
        assertEquals(estudante.getMatricula(), resultado.get(0).getMatricula());
    }

    @Test
    void buscarEstudantePorId() {
        when(estudanteRepository.findById(anyLong()))
                .thenReturn(Optional.of(estudante));

        Estudante resultado = estudanteService.buscarEstudantePorId(1L);

        verify(estudanteRepository).findById(anyLong());
        assertEquals(estudante.getNome(), resultado.getNome());
        assertEquals(estudante.getMatricula(), resultado.getMatricula());
    }

    @Test
    void atualizarEstudante() {
        when(estudanteRepository.findById(anyLong()))
                .thenReturn(Optional.of(estudante));
        when(estudanteRepository.save(any(Estudante.class)))
                .thenReturn(estudante);

        // when
        Estudante resultado = estudanteService.atualizarEstudante(1L,
                "Mourrice",
                "32.12.23");

        // then
        verify(estudanteRepository).findById(anyLong());
        verify(estudanteRepository).save(any(Estudante.class));
        assertEquals("Mourrice", resultado.getNome());
    }

    @Test
    void removerEstudante() {
        // when
        estudanteService.removerEstudante(1L);

        // then
        verify(estudanteRepository).deleteById(anyLong());
    }
}